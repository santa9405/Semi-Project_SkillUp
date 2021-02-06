package com.kh.skillup.mail;

import java.util.Random;
import java.util.Properties;
import java.io.IOException;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/sendMail.do")
public class SendMail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SendMail() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// mail server 설정
		String host = "smtp.gmail.com";
		String user = "sendmail834@gmail.com"; 
		String password = "sendmail123";
		
		// 메일 받을 주소
		String to_email = request.getParameter("email");
		
		// SMTP 서버 정보를 설정한다.
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", 465);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "true");
		
		// 인증 코드 생성
		StringBuffer code = new StringBuffer();
		Random rnd = new Random();
		for (int i = 0; i < 10; i++) {
			int rIndex = rnd.nextInt(3);
			switch (rIndex) {
			case 0: // a-z
				code.append((char) ((int) (rnd.nextInt(26)) + 97));
				break;
			case 1: // A-Z
				code.append((char) ((int) (rnd.nextInt(26)) + 65));
				break;
			case 2: // 0-9
				code.append((rnd.nextInt(10)));
				break;
			}
		}
		String AuthenticationKey = code.toString();
//		System.out.println(AuthenticationKey);
		
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});
		
		// email 전송
		try {
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(user, "Skill Up"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to_email));
			
			msg.setSubject("Skill Up 인증메일입니다."); // 메일 제목
			
			StringBuffer sb = new StringBuffer(); // 메일 내용
			sb.append("<!DOCTYPE html>\r\n" + 
					"<html lang=\"ko\">\r\n" + 
					"\r\n" + 
					"<head>\r\n" + 
					"    <meta charset=\"UTF-8\">\r\n" + 
					"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n" + 
					"    <title></title>\r\n" + 
					"</head>\r\n" + 
					"\r\n" + 
					"<body>\r\n" + 
					"\r\n" + 
					"    <body style=\"font-family: Arial, '맑은 고딕' , 'Malgun Gothic' , Dotum, '돋움' ,sans-serif, Helvetica;\r\n" + 
					"            font-size:12px; line-height:0; \">\r\n" + 
					"        <div style=\"width:700px; margin:0 auto; \">\r\n" + 
					"            <div style=\"padding:30px; \">\r\n" + 
					"                <p style=\"font-size:18px; font-weight:bold; margin-bottom:30px; \">SKILL UP 이메일 인증 코드</p>\r\n" + 
					"\r\n" + 
					"                <p style=\"line-height:1.6em; margin-bottom:5px;\">SKILL UP을 이용해주셔서 감사합니다.<br>\r\n" + 
					"                    아래의 코드를 입력하시면 가입이 완료됩니다.</p>\r\n" + 
					"\r\n" + 
					"                <p style=\"background-color:#ddd; padding:30px; font-size:18px; font-weight:bold; width: 400px;\">\r\n" + 
					code + 
					"                </p>\r\n" + 
					"            </div>\r\n" + 
					"        </div>\r\n" + 
					"    </body>\r\n" + 
					"\r\n" + 
					"</html>");
			msg.setContent(sb.toString(), "text/html; charset=EUC-KR");
			
			Transport.send(msg);
//			System.out.println("이메일 전송");
			response.getWriter().print(code);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		HttpSession saveKey = request.getSession();
		saveKey.setAttribute("AuthenticationKey", AuthenticationKey);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
    }
}
