package com.hangyi.eyunda.chat.mina.client;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import org.apache.mina.core.future.CloseFuture;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LogLevel;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.SocketConnector;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.hangyi.eyunda.chat.mina.codec.TextLineCodecFactory;

public class MinaClientConn {

	private SocketConnector connector = null;
	private IoSession session = null;

	public MinaClientConn() {
	}

	public boolean connect() {
		try {
			connector = new NioSocketConnector();
			connector.setConnectTimeoutMillis(3000);

			ProtocolCodecFilter codecFilter = new ProtocolCodecFilter(new TextLineCodecFactory());
			connector.getFilterChain().addLast("codec", codecFilter);// 配置CodecFactory

			LoggingFilter log = new LoggingFilter();
			log.setMessageReceivedLogLevel(LogLevel.INFO);
			connector.getFilterChain().addLast("logger", log);// 配置Log

			connector.setHandler(new MinaClientHandle());

			ConnectFuture future = connector.connect(new InetSocketAddress("127.0.0.1", 10168));
			future.awaitUninterruptibly();
			session = future.getSession();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public void setAttribute(Object key, Object value) {
		session.setAttribute(key, value);
	}

	public void sentMsg(String message) throws InterruptedException {
		WriteFuture wf = session.write(message);
		wf.await(3L, TimeUnit.SECONDS);
		// 发送成功，处理发送状态
	}

	boolean close() {
		CloseFuture future = session.getCloseFuture();
		future.awaitUninterruptibly(1000);
		connector.dispose();
		return true;
	}

	public SocketConnector getConnector() {
		return connector;
	}

	public IoSession getSession() {
		return session;
	}

}