package com.hangyi.eyunda.service.portal.login;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jodd.mail.Email;
import jodd.mail.EmailMessage;
import jodd.mail.SendMailSession;
import jodd.mail.SmtpServer;
import jodd.mail.SmtpSslServer;
import jodd.util.MimeTypes;

import com.hangyi.eyunda.util.Constants;

public class MailUtil {

	static ExecutorService executor; // 创建线程池.
	static String mail_ssl;
	static String mail_smtpServer;
	static int mail_port;
	static String mail_username;
	static String mail_password;
	static String mail_from;

	static {
		executor = Executors.newCachedThreadPool(); // 创建线程池.
		mail_ssl = Constants.getValue("mail_ssl");
		mail_smtpServer = Constants.getValue("mail_smtpServer");
		mail_port = Constants.getIntegerValue("mail_port");
		mail_username = Constants.getValue("mail_username");
		mail_password = Constants.getValue("mail_password");
		mail_from = Constants.getValue("mail_from");
	}

	public static String getMailEnterURL(String mailAddress) {
		StringBuffer url = new StringBuffer("http://");
		String domain = mailAddress.replaceAll(".*@", "");
		if (domain.startsWith("gmail")) {
			url.append("m.").append(domain);
		} else {
			url.append("mail.").append(domain);
		}
		return url.toString();
	}

	public static void sendMail(String subject, String message, String... tos) {
		Runnable sendThread = new SendThread(subject, message, tos);

		System.out.println("发送邮件..." + subject);
		executor.execute(sendThread);
	}

	static class SendThread implements Runnable {
		String subject;
		String message;
		String[] tos;

		public SendThread(String subject, String message, String... tos) {
			this.subject = subject;
			this.message = message;
			this.tos = tos;
		}

		@Override
		public void run() {
			try {
				// Thread.sleep(5000);
				Email email = new Email();
				email.setFrom(mail_from);
				email.setTo(tos);
				email.setSubject(subject);

				EmailMessage htmlMessage = new EmailMessage(message, MimeTypes.MIME_TEXT_HTML);
				email.addMessage(htmlMessage);
				SmtpServer smtpServer;
				if (mail_ssl.equals("0")) {
					smtpServer = new SmtpServer(mail_smtpServer, mail_port, mail_username, mail_password);
				} else {
					smtpServer = new SmtpSslServer(mail_smtpServer, mail_port, mail_username, mail_password);
				}

				SendMailSession session = smtpServer.createSession();
				session.open();
				session.sendMail(email);
				session.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
