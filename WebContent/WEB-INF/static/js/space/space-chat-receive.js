// 接收好友聊天消息
function messageEventReceive(data) {
	var roomId = data.roomId;
	var fromUserId = data.fromUserId;
	var fromUserName = data.fromUserName;
	var content = data.content;

	if (_openChatRoomId == roomId) {
		// 若消息来自于当前聊天室，聊天内容最后添加消息
		showOneMessage(fromUserId, fromUserName, null, content)
	}

	// 若消息来自于其他已存在的聊天室，修改该聊天室Logo及标题
	// 若消息来自于当前不存在的聊天室，聊天室列表最后增加该聊天室
	updateOneChatRoom(roomId, content);
}

// 接收好友通知
function notifyEventReceive(data) {
	var message = data.message;
	var fromUserId = data.fromUserId;
	var toUserId = data.toUserId;
	var fromTrueName = data.fromUserName;
	var toTrueName = data.toTrueName;
	// 推送消息
	notifyReceive(message, fromTrueName, fromUserId);
}

// 接收一个好友状态变化
function statusEventReceive(data) {
	var onlineStatus = data.onlineStatus;
	var fromUserId = data.fromUserId;
	var toUserId = data.toUserId;
	var fromTrueName = data.fromUserName;
	var toTrueName = data.toTrueName;

	// 更改当前聊天室成员列表中该成员的状态
	updateRoomMemberStatus(fromUserId, onlineStatus);
}
