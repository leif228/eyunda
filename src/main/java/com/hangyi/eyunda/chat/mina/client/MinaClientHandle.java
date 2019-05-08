package com.hangyi.eyunda.chat.mina.client;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MinaClientHandle extends IoHandlerAdapter {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	MinaClientHandle() {
	}

	public void messageReceived(IoSession session, Object message) throws Exception {
		logger.info("============== 客户端接收一个信息：" + message.toString());
	}

	public void messageSent(IoSession session, Object message) throws Exception {
		logger.info("============== 客户端发了一个信息：" + message.toString());
	}

}
