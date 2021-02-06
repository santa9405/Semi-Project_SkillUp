package com.kh.skillup.lesson.model.service;

import static com.kh.skillup.common.JDBCTemplate.*;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import com.kh.skillup.lesson.model.dao.LessonDAO;
import com.kh.skillup.lesson.model.exception.FileInsertFailedException;
import com.kh.skillup.lesson.model.vo.Attachment;
import com.kh.skillup.lesson.model.vo.Lesson;
import com.kh.skillup.lesson.model.vo.PageInfo;

public class LessonService {
	private LessonDAO dao = new LessonDAO();

	public PageInfo getPageInfo(String cp) throws Exception {
		Connection conn = getConnetion();
		
		int currentPage = (cp == null ? 1 : Integer.parseInt(cp));
		int listCount = dao.getListCount(conn);
		
		close(conn);
		
		return new PageInfo(currentPage, listCount);
	}

	public List<Lesson> selectLessonList(PageInfo pInfo) throws Exception {
		Connection conn = getConnetion();
		List<Lesson> lessonList = dao.selectLessonList(conn, pInfo);
		close(conn);
		return lessonList;
	}
	
	public List<Lesson> selectMyLessonList(PageInfo pInfo, int memberNo) throws Exception {
		Connection conn = getConnetion();
		List<Lesson> lessonList = dao.selectMyLessonList(conn, pInfo, memberNo);
		close(conn);
		return lessonList;
	}

	public Lesson selectLesson(int lessonNo) throws Exception {
		Connection conn = getConnetion();
		Lesson lesson = dao.selectLesson(conn,lessonNo);
		
		if(lesson != null) {
			int result = dao.increseReadCnt(conn, lessonNo);
			
			if(result > 0) {
				commit(conn);
				lesson.setReadCnt(lesson.getReadCnt() + 1);
			}
			else rollback(conn);
		}
		close(conn);
		return lesson;
	}

	public int insertLesson(Map<String, Object> map) throws Exception {
		Connection conn = getConnetion();
		int result = 0;
		
		int lessonNo = dao.selectNextNo(conn);
		
		if(lessonNo > 0) {
			map.put("lessonNo", lessonNo);

			Lesson lesson = (Lesson) map.get("lesson");
			lesson.setLessonTitle(replaceParameter(lesson.getLessonTitle()));
			lesson.setLessonContent(replaceParameter(lesson.getLessonContent()));
			lesson.setLessonContent(lesson.getLessonContent().replaceAll("\r\n", "<br>"));
			map.put("lesson", lesson);

			try {
				result = dao.insertLesson(conn, map);
				
				List<Attachment> fList = (List<Attachment>) map.get("fList");
				
				if(result > 0 && !fList.isEmpty()) {
					result = 0; 
					
					for(Attachment at: fList) {
						at.setParentLessonNo(lessonNo);
						result = dao.insertAttachment(conn, at);
						
						if(result == 0)
							throw new FileInsertFailedException("파일 정보 삽입 실패");
					}
				}
			} catch (Exception e) { 
				
				List<Attachment> fList = (List<Attachment>)map.get("fList");
				
				if(!fList.isEmpty())
					for(Attachment at: fList) {
						File deleteFile = new File(at.getFilePath() + at.getFileName());
						if(deleteFile.exists())
							deleteFile.delete();
					}
				throw e; 
			}	
			
			if(result > 0) {
				commit(conn);
				result = lessonNo;
			}
			else 
				rollback(conn);
		}
		close(conn);
		return result;
	}
	
	public String replaceParameter(String param) {
		String result = param;
		
		if(param != null) {
			result = result.replaceAll("&", "&amp;");
			result = result.replaceAll("<", "&lt;");
			result = result.replaceAll(">", "&gt;");
			result = result.replaceAll("\"", "&quot");
		}
		return result;
	}

	public List<Attachment> selectLessonFiles(int lessonNo) throws Exception {
		Connection conn = getConnetion();
		List<Attachment> fList = dao.selectLessonFiles(conn, lessonNo);
		close(conn);
		return fList;
	}

	public List<Attachment> selectThumbnailList(PageInfo pInfo) throws Exception {
		Connection conn = getConnetion();
		List<Attachment> fList = dao.selectThumbnailList(conn, pInfo);
		close(conn);
		return fList;
	}

	public Lesson updateView(int lessonNo) throws Exception {
		Connection conn = getConnetion();
		
		Lesson lesson = dao.selectLesson(conn, lessonNo);
		lesson.setLessonContent(lesson.getLessonContent().replaceAll("<br>", "\r\n"));
		
		close(conn);
		return lesson;
	}

	public int updateLesson(Map<String, Object> map) throws Exception {
		Connection conn = getConnetion();
		
		int result = 0;
		List<Attachment> deleteFiles = null;
		
		Lesson lesson = (Lesson) map.get("lesson");
		lesson.setLessonTitle(replaceParameter(lesson.getLessonTitle()));
		lesson.setLessonContent(replaceParameter(lesson.getLessonContent()));
		lesson.setLessonContent(lesson.getLessonContent().replaceAll("\r\n", "<br>"));
		map.put("lesson", lesson);

		try {
			result = dao.updateLesson(conn, map);
			
			// 삭제한 파일 제거
			String[] deletedImges = (String[]) map.get("deletedImges");
			List<Attachment> oldFileList = dao.selectLessonFiles(conn, lesson.getLessonNo());

			if( result > 0 && deletedImges != null) {
				deleteFiles = new ArrayList<Attachment>();
				for(Attachment oldFile : oldFileList) {
					for(String fileNo: deletedImges)
						if(oldFile.getFileNo() == Integer.parseInt(fileNo)) {
							deleteFiles.add(oldFile);
							result = dao.deleteAttachment(conn, oldFile.getFileNo());
							
							if(result == 0) 
								throw new FileInsertFailedException("파일 정보 삽입 또는 수정 실패");
						}
				}
			}
			
			// 새로 업로드된 파일 정보 삽입
			List<Attachment> newFileList = (List<Attachment>)map.get("fList"); 
			
			if(result > 0 && !newFileList.isEmpty()) {
				result = 0;
				
				for(Attachment newFile : newFileList) {
					result = dao.insertAttachment(conn, newFile);

					if(result == 0) 
						throw new FileInsertFailedException("파일 정보 삽입 또는 수정 실패");
				}
			}
		} catch (Exception e) {
			List<Attachment> fList = (List<Attachment>)map.get("fList");
			
			if(!fList.isEmpty())
				for(Attachment at: fList) {
					File deleteFile = new File(at.getFilePath() + at.getFileName());
					if(deleteFile.exists())
						deleteFile.delete();
				}
			throw e;
		}
		
		if(result > 0) {
			commit(conn);

			if(deleteFiles != null)
				for(Attachment at: deleteFiles) {
					File deleteFile = new File(at.getFilePath() + at.getFileName());
					
					if(deleteFile.exists())
						deleteFile.delete();
				}
		}
		else 
			rollback(conn);
		
		close(conn);
		return result;
	}
	
	public int updateLessonFL(int lessonNo) throws Exception {
		Connection conn = getConnetion();
		int result = dao.updateLessonFL(conn, lessonNo);
		
		if(result > 0) commit(conn);
		else rollback(conn);
		
		close(conn);
		return result;
	}

	public int updateStatus(int lessonNo) throws Exception {
		Connection conn = getConnetion();
		int result = dao.updateStatus(conn, lessonNo);
		
		if(result > 0) commit(conn);
		else rollback(conn);
		
		close(conn);
		return result;
	}

	public List<Lesson> selectLikesLesson(PageInfo pInfo, int memberNo) throws Exception {
		Connection conn = getConnetion();
		List<Lesson> lessonList = dao.selectLikesLesson(conn, pInfo, memberNo);
		close(conn);
		return lessonList;
	}

	public List<Attachment> selectLikesThumbnailList(PageInfo pInfo, int memberNo) throws Exception {
		Connection conn = getConnetion();
		List<Attachment> fList = dao.selectLikesThumbnailList(conn, pInfo, memberNo);
		close(conn);
		return fList;
	}

	public List<Attachment> selectMyThumbnailList(PageInfo pInfo, int memberNo) throws Exception {
		Connection conn = getConnetion();
		List<Attachment> fList = dao.selectMyThumbnailList(conn, pInfo, memberNo);
		close(conn);
		return fList;
	}

	public int selectSubjectCnt(int lessonNo) throws Exception {
		Connection conn = getConnetion();
		int result = dao.selectSubjectCnt(conn, lessonNo);
		close(conn);
		return result;
	}

	public PageInfo getMyPageInfo(String cp, int memberNo) throws Exception {
		Connection conn = getConnetion();
		
		int currentPage = (cp == null ? 1 : Integer.parseInt(cp));
		int listCount = dao.getMyListCount(conn, cp, memberNo);
		
		close(conn);
		
		return new PageInfo(currentPage, listCount);
	}

	public PageInfo getLikesPageInfo(String cp, int memberNo) throws Exception {
		Connection conn = getConnetion();
		
		int currentPage = (cp == null ? 1 : Integer.parseInt(cp));
		int listCount = dao.getLikesListCount(conn, cp, memberNo);
		
		close(conn);
		
		return new PageInfo(currentPage, listCount);
	}
}
