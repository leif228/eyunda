// 报到
function reportEventSend() {
	var map = {
		"messageType" : "report"
	};
	DwrChatService.chatTask(map);
}

// 每隔2分钟报道一次
window.setInterval("reportEventSend();", 2000 * 60);

// 登录
function loginEventSend() {
	var map = {
		"messageType" : "login"
	};
	DwrChatService.chatTask(map);
	statusEventSend("online");
}

// 登出
function logoutEventSend() {
	var map = {
		"messageType" : "logout"
	};
	DwrChatService.chatTask(map);
	statusEventSend("ofline");
}

// 发送好友聊天消息
function messageEventSend(content, roomId) {
	var map = {
		"messageType" : "message",
		"content" : content,
		"roomId" : roomId
	};
	DwrChatService.chatTask(map);
}

// 发送好友通知
function notifyEventSend(message, toUserId, toUserName) {
	var map = {
		"messageType" : "notify",
		"content" : content,
		"toUserId" : toUserId,
		"toUserName" : toUserName
	};
	DwrChatService.chatTask(map);
}

// 发送状态变化给所有好友
function statusEventSend(onlineStatus) {
	var map = {
		"messageType" : "status",
		"onlineStatus" : onlineStatus
	};
	DwrChatService.chatTask(map);
}
