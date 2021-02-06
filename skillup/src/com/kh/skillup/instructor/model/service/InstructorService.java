package com.kh.skillup.instructor.model.service;

import static com.kh.skillup.common.JDBCTemplate.*;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kh.skillup.instructor.exception.FileInsertFailedException;
import com.kh.skillup.instructor.model.dao.InstructorDAO;
import com.kh.skillup.instructor.model.vo.Attachment;
import com.kh.skillup.instructor.model.vo.Instructor;
import com.kh.skillup.instructor.model.vo.PageInfo;
import com.kh.skillup.lesson.model.vo.Lesson;

/**
 * @author tnejd
 *
 */
/**
 * @author tnejd
 *
 */
public class InstructorService {
	
	private InstructorDAO dao = new InstructorDAO();
	

	// 전문가 등록 저장 -------------------------------------------------------------------------
	/** 전문가 등록 저장 Service
	 * @param map
	 * @return result
	 * @throws Exception
	 */
	public int insertSaveInstructor(Map<String, Object> map) throws Exception {
		Connection conn = getConnetion();

		int result = 0;
		
		// 1. 글 제목/내용 크로스 사이트 스크립팅 방지 처리
		String introduceContent = (String) map.get("introduceContent");
		String careerContent = (String) map.get("careerContent");

		introduceContent = replaceParameter(introduceContent);
		careerContent = replaceParameter(careerContent);

		// 2. 글 내용 개행문자 \r\n -> <br> 변경 처리
		introduceContent = introduceContent.replaceAll("\r\n", "<br>");
		careerContent = careerContent.replaceAll("\r\n", "<br>");
		
		// 처리된 내용을 다시 map에 추가
		map.put("introduceContent", introduceContent);
		map.put("careerContent", careerContent);

		try {
			// 3. 전문가 부분(이름, 소개, 경력, 카테고리, 저장여부)만 INSTRUCTOR 테이블에 삽입하는 DAO 호출
			result = dao.insertSaveInstructor(conn, map);
			
			// 4. 파일 정보 부분만 ATTACHMENT 테이블에 삽입하는 DAO 호출
			List<Attachment> fList = (List<Attachment>) map.get("fList");

			// 게시글 부분 삽입 성공 && 파일 정보가 있을 경우
			if (result > 0 && !fList.isEmpty()) {

				result = 0; // result 재활용을 위해 0으로 초기화

				// fList의 요소를 하나씩 반복 접근하여
				// DAO 메소드를 반복 호출해 정보를 삽입
				for (Attachment at : fList) {

					at.setParentInstrNo((int)map.get("memberNo"));

					result = dao.insertAttachment(conn, at);

					if (result == 0) { // 파일 정보 삽입 실패
						// 강제로 예외 발생
						throw new FileInsertFailedException("파일 정보 삽입 실패");
					}
				}
			}

		} catch (Exception e) {
			// 게시글 또는 파일 정보 삽입 중 에러 발생 시
			// 서버에 저장된 파일을 삭제하는 작업이 필요함.

			List<Attachment> fList = (List<Attachment>) map.get("fList");

			if (!fList.isEmpty()) {

				for (Attachment at : fList) {

					String filePath = at.getFilePath();
					String fileName = at.getFileName();

					File deleteFile = new File(filePath + fileName);

					if (deleteFile.exists()) {
						// 해당 경로에 해당 파일이 존재하면
						deleteFile.delete(); // 해당 파일 삭제
					}
				}
			}

		// 에러페이지가 보여질 수 있도록 catch한 Exception을 Controller로 던져줌
		throw e;

		} // end catch

		// 6. 트랜잭션 처리
		if (result > 0) {
			commit(conn);

		} else {
			rollback(conn);
		}
		
		// 7. 커넥션 반환
		close(conn);

		// 8. 결과 반환
		return result;
	}
	
	
	/** 전문가 신청 저장 여부 확인 Service
	 * @param memberNo
	 * @return checkInstr
	 * @throws Exception
	 */
	public int checkInstr(int memberNo) throws Exception {
		
		Connection conn = getConnetion();

		int checkInstr = dao.checkInstr(conn, memberNo);
		
		close(conn);
		
		return checkInstr;
	}

	

	/** 전문가 신청 저장 -> 신청 Service
	 * @param map
	 * @return result
	 * @throws Exception
	 */
	public int updateEnrollment(Map<String, Object> map) throws Exception {
		Connection conn = getConnetion();

		int result = 0;
		
		// 1. 글 제목/내용 크로스 사이트 스크립팅 방지 처리
		String introduceContent = (String) map.get("introduceContent");
		String careerContent = (String) map.get("careerContent");

		introduceContent = replaceParameter(introduceContent);
		careerContent = replaceParameter(careerContent);

		// 2. 글 내용 개행문자 \r\n -> <br> 변경 처리
		introduceContent = introduceContent.replaceAll("\r\n", "<br>");
		careerContent = careerContent.replaceAll("\r\n", "<br>");
		
		// 처리된 내용을 다시 map에 추가
		map.put("introduceContent", introduceContent);
		map.put("careerContent", careerContent);

		try {
			// 3. 전문가 부분(이름, 소개, 경력, 카테고리)만 INSTRUCTOR 테이블에 삽입하는 DAO 호출
			result = dao.updateEnrollment(conn, map);
			
			// 4. 파일 정보 부분만 ATTACHMENT 테이블에 삽입하는 DAO 호출
			List<Attachment> fList = (List<Attachment>) map.get("fList");

			// 게시글 부분 삽입 성공 && 파일 정보가 있을 경우
			if (result > 0 && !fList.isEmpty()) {

				result = 0; // result 재활용을 위해 0으로 초기화

				// fList의 요소를 하나씩 반복 접근하여
				// DAO 메소드를 반복 호출해 정보를 삽입
				for (Attachment at : fList) {

					at.setParentInstrNo((int)map.get("memberNo"));

					result = dao.insertAttachment(conn, at);

					if (result == 0) { // 파일 정보 삽입 실패
						// 강제로 예외 발생
						throw new FileInsertFailedException("파일 정보 삽입 실패");
					}
				}
			}

		} catch (Exception e) {
			// 게시글 또는 파일 정보 삽입 중 에러 발생 시
			// 서버에 저장된 파일을 삭제하는 작업이 필요함.

			List<Attachment> fList = (List<Attachment>) map.get("fList");

			if (!fList.isEmpty()) {

				for (Attachment at : fList) {

					String filePath = at.getFilePath();
					String fileName = at.getFileName();

					File deleteFile = new File(filePath + fileName);

					if (deleteFile.exists()) {
						// 해당 경로에 해당 파일이 존재하면
						deleteFile.delete(); // 해당 파일 삭제
					}
				}
			}

		// 에러페이지가 보여질 수 있도록 catch한 Exception을 Controller로 던져줌
		throw e;

		} // end catch

		// 6. 트랜잭션 처리
		if (result > 0) {
			commit(conn);

		} else {
			rollback(conn);
		}
		
		// 7. 커넥션 반환
		close(conn);

		// 8. 결과 반환
		return result;
	}

	
	// 전문가 등록 Service -------------------------------------------------------------------------
	/**
	 * 전문가 등록 Service (글 + 파일)
	 * 
	 * @param map
	 * @return result
	 * @throws Exception
	 */
	public int insertInstructor(Map<String, Object> map) throws Exception {

		Connection conn = getConnetion();

		int result = 0;
		
		// 1. 글 제목/내용 크로스 사이트 스크립팅 방지 처리
		String introduceContent = (String) map.get("introduceContent");
		String careerContent = (String) map.get("careerContent");

		introduceContent = replaceParameter(introduceContent);
		careerContent = replaceParameter(careerContent);

		// 2. 글 내용 개행문자 \r\n -> <br> 변경 처리
		introduceContent = introduceContent.replaceAll("\r\n", "<br>");
		careerContent = careerContent.replaceAll("\r\n", "<br>");
		
		// 처리된 내용을 다시 map에 추가
		map.put("introduceContent", introduceContent);
		map.put("careerContent", careerContent);

		try {
			// 3. 전문가 부분(이름, 소개, 경력, 카테고리)만 INSTRUCTOR 테이블에 삽입하는 DAO 호출
			result = dao.insertInstructor(conn, map);
			
			// 4. 파일 정보 부분만 ATTACHMENT 테이블에 삽입하는 DAO 호출
			List<Attachment> fList = (List<Attachment>) map.get("fList");

			// 게시글 부분 삽입 성공 && 파일 정보가 있을 경우
			if (result > 0 && !fList.isEmpty()) {

				result = 0; // result 재활용을 위해 0으로 초기화

				// fList의 요소를 하나씩 반복 접근하여
				// DAO 메소드를 반복 호출해 정보를 삽입
				for (Attachment at : fList) {

					at.setParentInstrNo((int)map.get("memberNo"));

					result = dao.insertAttachment(conn, at);

					if (result == 0) { // 파일 정보 삽입 실패
						// 강제로 예외 발생
						throw new FileInsertFailedException("파일 정보 삽입 실패");
					}
				}
			}

		} catch (Exception e) {
			// 게시글 또는 파일 정보 삽입 중 에러 발생 시
			// 서버에 저장된 파일을 삭제하는 작업이 필요함.

			List<Attachment> fList = (List<Attachment>) map.get("fList");

			if (!fList.isEmpty()) {

				for (Attachment at : fList) {

					String filePath = at.getFilePath();
					String fileName = at.getFileName();

					File deleteFile = new File(filePath + fileName);

					if (deleteFile.exists()) {
						// 해당 경로에 해당 파일이 존재하면
						deleteFile.delete(); // 해당 파일 삭제
					}
				}
			}

		// 에러페이지가 보여질 수 있도록 catch한 Exception을 Controller로 던져줌
		throw e;

		} // end catch

		// 6. 트랜잭션 처리
		if (result > 0) {
			commit(conn);

		} else {
			rollback(conn);
		}
		
		// 7. 커넥션 반환
		close(conn);

		// 8. 결과 반환
		return result;
	}

	// 크로스 사이트 스크립팅 방지 메소드
	private String replaceParameter(String param) {
		String result = param;
		if (param != null) {
			result = result.replaceAll("&", "&amp;");
			result = result.replaceAll("<", "&lt;");
			result = result.replaceAll(">", "&gt;");
			result = result.replaceAll("\"", "&quot;");
		}

		return result;
	}
	
	// 전문가 승인 -------------------------------------------------------------------------
	
	/**
	 * 전문가 신청 목록 조회 페이징 처리를 위한 값 계산 Service
	 * 
	 * @param cp
	 * @return pInfo
	 * @throws Exception
	 */
	public PageInfo getPageInfo(String cp) throws Exception {
		Connection conn = getConnetion();
		
		// cp가 null일 경우
		int currentPage = cp == null ? 1 : Integer.parseInt(cp);
		
		// DB에서 전체 게시글 수를 조회하여 반환 받기
		int listCount = dao.getListCount(conn);
		
		close(conn);
		
		// 얻어온 현재 페이지와, DB에서 조회한 전체 게시글 수를 이용하여
		// PageInfo 객체를 생성함
		return new PageInfo(currentPage, listCount);
	}
	
	// 전문가 마이페이지 -------------------------------------------------------------------------
	/** 전문가 마이페이지 화면 출력용 Service
	 * @param instrNo
	 * @return instructor
	 * @throws Exception
	 */
	public Instructor updateView(int instrNo) throws Exception {
		
		Connection conn = getConnetion();
		
		Instructor instructor = dao.selectInstructor(conn, instrNo);
		
		instructor.setIntroduction(instructor.getIntroduction().replaceAll("<br>", "\r\n"));
		instructor.setCareer(instructor.getCareer().replaceAll("<br>", "\r\n"));
		
		close(conn);
		
		return instructor;
	}
	
	
	/**
	 * 게시글에 포함된 이미지 목록 조회 Service
	 * 
	 * @param instrNo
	 * @return fList
	 * @throws Exception
	 */
	public List<Attachment> selectInstrFiles(int instrNo) throws Exception {
		Connection conn = getConnetion();

		List<Attachment> fList = dao.selectInstrFiles(conn, instrNo);

		close(conn);

		return fList;
	}
	
	/** 전문가 마이페이지 수정 Service
	 * @param map
	 * @return result
	 * @throws Exception
	 */
	public int updateInstructor(Map<String, Object> map) throws Exception {
		Connection conn = getConnetion();
		
		int result = 0;
		
		List<Attachment> deleteFiles = null;
		
		// 1. 글 제목/내용 크로스 사이트 스크립팅 방지 처리
		String introduceContent = (String) map.get("introduceContent");
		String careerContent = (String) map.get("careerContent");

		introduceContent = replaceParameter(introduceContent);
		careerContent = replaceParameter(careerContent);

		// 2. 글 내용 개행문자 \r\n -> <br> 변경 처리
		introduceContent = introduceContent.replaceAll("\r\n", "<br>");
		careerContent = careerContent.replaceAll("\r\n", "<br>");
		
		// 처리된 내용을 다시 map에 추가
		map.put("introduceContent", introduceContent);
		map.put("careerContent", careerContent);
		
		try {
			
			result = dao.updateInstructor(conn, map);
			
			List<Attachment> newFileList = (List<Attachment>)map.get("fList");
			
			if(result > 0 && !newFileList.isEmpty()) {
				
				List<Attachment> oldFileList = dao.selectInstrFiles(conn, (int)map.get("memberNo"));
				
				result = 0;
				deleteFiles = new ArrayList<Attachment>();
				
				for(Attachment newFile : newFileList) {
					
					boolean flag = true;
					
					for(Attachment oldFile : oldFileList) {
					
						if(newFile.getFileLevel() == oldFile.getFileLevel()) {
		                      
		                      // 기존 파일을 삭제 List에 추가
		                      deleteFiles.add(oldFile);
		                      
		                      // 새 이미지 정보에 이전 파일 번호를 추가 -> 파일 번호를 이용한 수정 진행
		                      newFile.setFileNo(oldFile.getFileNo());
		                      
		                      flag = false;
		                      
		                      break;
						}
	                }
					
					// flag 값에 따라 파일 정보 insert 또는 update수행
	                if(flag) {
	                   result = dao.insertAttachment(conn, newFile);
	                }else {
	                   result = dao.updateAttachment(conn, newFile);
	                }
					
					// 파일 정보 삽입 또는 수정 중 실패 시
	                if(result == 0) {
	                   // 강제로 사용자 정의 예외 발생
	                   throw new FileInsertFailedException("파일 정보 삽입 또는 수정 실패");
	                }
					
				}
			}
			
		}catch (Exception e){
		  // 게시글 수정 중 실패 또는 오류 발생 시
          // 서버에 미리 저장되어 있던 이미지 파일 삭제
          List<Attachment> fList = (List<Attachment>)map.get("fList");
          
          if(!fList.isEmpty()) {
             for(Attachment at : fList) {
                String filePath = at.getFilePath();
                String fileName = at.getFileName();
                
                File deleteFile = new File(filePath + fileName);
                
                if(deleteFile.exists()) {
                   // 해당 경로에 해당 파일이 존재하면
                   deleteFile.delete(); // 해당 파일 삭제
                }
             }
          }
          
          // 에러페이지가 보여질 수 있도록 catch한 Exception을 Controller로 던져줌
          throw e; 
       
		}
		
		// 5. 트랜잭션 처리 및 삭제 목록에 있는 파일 삭제
	       if(result > 0) {
	          commit(conn);
	          
	          // DB 정보와 맞지 않는 파일(deleteFiles) 삭제 진행
	          if(deleteFiles != null) {
	             
	             for(Attachment at : deleteFiles) {
	                String filePath = at.getFilePath();
	                String fileName = at.getFileName();
	                
	                File deleteFile = new File(filePath + fileName);
	                
	                if(deleteFile.exists()) {
	                   deleteFile.delete();
	                }
	             }
	          }
	       }else {
	          rollback(conn);
	       }
	       return result;
	    }
	
	// 전문가 승인 -------------------------------------------------------------------------

	/** 전문가 신청 목록 조회 Service
	 * @param pInfo
	 * @return iList
	 * @throws Exception
	 */
	public List<Instructor> selectInstrList(PageInfo pInfo) throws Exception {
		Connection conn = getConnetion();

		List<Instructor> iList = dao.selectInstrList(conn, pInfo);

		close(conn);

		return iList;
	}
	
	/** 전문가 신청 목록 상세 조회 Service
	 * @param instrNo
	 * @return instr
	 * @throws Exception
	 */
	public Instructor selectInstr(int instrNo) throws Exception {
		Connection conn = getConnetion();
		Instructor instr = dao.selectInstructor(conn, instrNo);
		
		close(conn);
		
		return instr;
	}

	/** 전문가 승인에 의한 회원 등급 변경 Service
	 * @param instrNo
	 * @return result
	 * @throws Exception
	 */
	public int updateMemberGrade(int instrNo) throws Exception {
		Connection conn = getConnetion();
		int result = dao.updateMemberGrade(conn, instrNo);
		
		if(result > 0)		commit(conn);
		else			    rollback(conn);
		
		return result;
	}

	/** 전문가 승인에 의한 승인 여부 변경 Service
	 * @param instrNo
	 * @return result
	 * @throws Exception
	 */
	public int updatePermitFl(int instrNo) throws Exception {
		Connection conn = getConnetion();
		int result = dao.updatePermitFl(conn, instrNo);
		
		if(result > 0)		commit(conn);
		else			    rollback(conn);
		
		return result;
	}
	
	// 전문가 상세 조회 --------------------------------------------------------
	/** 진행 중인 수업 개수 조회 Service
	 * @param pInfo
	 * @param instrNo
	 * @return lessonList
	 * @throws Exception
	 */
	public List<Lesson> selectLessonList(PageInfo pInfo, int instrNo) throws Exception {
		Connection conn = getConnetion();
		
		List<Lesson> lessonList = dao.selectLessonList(conn, pInfo, instrNo);
		
		close(conn);
		
		return lessonList;
	}

	/** 진행 중인 수업 썸네일 조회 Service
	 * @param pInfo
	 * @param instrNo
	 * @return lList
	 * @throws Exception
	 */
	public List<Attachment> selectThumbnailList(PageInfo pInfo, int instrNo) throws Exception {
		Connection conn = getConnetion();
		List<Attachment> lList = dao.selectThumbnailList(conn, pInfo, instrNo);
		close(conn);
		return lList;
	}


	/** 진행 중인 수업 페이징 처리를 위한 값 계산 Service
	 * @param cp
	 * @param instrNo 
	 * @return PageInfo
	 * @throws Exception
	 */
	public PageInfo getLessonPageInfo(String cp, int instrNo) throws Exception {
		Connection conn = getConnetion();
		
		// cp가 null일 경우
		int currentPage = cp == null ? 1 : Integer.parseInt(cp);
		
		// DB에서 전체 게시글 수를 조회하여 반환 받기
		int listCount = dao.getLessonListCount(conn, instrNo);
		
		close(conn);
		
		// 얻어온 현재 페이지와, DB에서 조회한 전체 게시글 수를 이용하여
		// PageInfo 객체를 생성함
		return new PageInfo(currentPage, listCount);
	}


	/** 전문가 신청 저장 여부 확인 Service
	 * @param instrNo
	 * @return result
	 * @throws Exception
	 */
	public int selectSaveFl(int instrNo) throws Exception {
		Connection conn = getConnetion();
		
		int result = dao.selectSaveFl(conn, instrNo);
		
		close(conn);
		
		return result;
	}

}