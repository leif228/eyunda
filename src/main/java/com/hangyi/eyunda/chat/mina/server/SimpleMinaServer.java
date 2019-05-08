package com.hangyi.eyunda.chat.mina.server;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LogLevel;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.hangyi.eyunda.chat.mina.codec.TextLineCodecFactory;

public class SimpleMinaServer {
    
    SocketAcceptor acceptor = null;
    
    SimpleMinaServer(){
        acceptor = new NioSocketAcceptor();
    }
    
    public boolean bind(){
        acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(
                new TextLineCodecFactory())); //配置CodecFactory
        LoggingFilter log = new LoggingFilter();
        log.setMessageReceivedLogLevel(LogLevel.INFO);
        acceptor.getFilterChain().addLast("logger", log);
        acceptor.setHandler(new MinaServerHandle()); //配置handler
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 30);
        
        try {
            acceptor.bind(new InetSocketAddress(10168));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        
    }
    
    public static void main(String args[]){
        SimpleMinaServer server = new SimpleMinaServer();
        if(!server.bind()){
            System.out.println("服务器启动失败");
        }else{
            System.out.println("服务器启动成功");
        }
    }

}
