package com.hangyi.eyunda.chat;

public final class MessageConstants {

	private MessageConstants() {
	}
	
	public static final String SERVER_QUEUE = "serverQueue:";

	public static final String ONLINE_USER_QUEUE = "onlineUserQueue";
	public static final String MINA_SESSION_QUEUE = "minaSessionQueue";
	public static final String DWR_SESSION_QUEUE = "dwrSessionQueue";
	
	public static final String CHAT_MESSAGE_QUEUE = "chatMessageQueue:";
	
	public static final String MINA_USERID_QUEUE = "minaUserIdQueue:";
	public static final String DWR_USERID_QUEUE = "dwrUserIdQueue:";

	public static final String REPORT_EVENT = "report";
	public static final String LOGIN_EVENT = "login";
	public static final String LOGOUT_EVENT = "logout";
	public static final String NOTIFY_EVENT = "notify";
	public static final String MESSAGE_EVENT = "message";
	public static final String STATUS_EVENT = "status";
	
	public static final String QCODE_QUEUE = "qcodeQueue";
	public static final String REGISTER_QUEUE = "registerQueue";
	public static final String CAPTCHA_QUEUE = "captchaQueue";
}
