package com.hangyi.eyunda.chat;

import com.hangyi.eyunda.chat.data.OnlineUser;
import com.hangyi.eyunda.chat.event.LoginEvent;
import com.hangyi.eyunda.chat.event.LogoutEvent;
import com.hangyi.eyunda.chat.event.MessageEvent;
import com.hangyi.eyunda.chat.event.NotifyEvent;
import com.hangyi.eyunda.chat.event.ReportEvent;
import com.hangyi.eyunda.chat.event.StatusEvent;

public interface MessageListener {
	OnlineUser report(ReportEvent event) throws Exception;

	OnlineUser login(LoginEvent event) throws Exception;

	void logout(LogoutEvent event) throws Exception;

	void sendStatus(StatusEvent event) throws Exception;

	void sendNotify(final NotifyEvent event) throws Exception;

	void sendMessage(final MessageEvent event) throws Exception;
}
