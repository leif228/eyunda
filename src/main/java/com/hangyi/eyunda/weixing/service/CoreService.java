package com.hangyi.eyunda.weixing.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hangyi.eyunda.weixing.message.resp.Article;
import com.hangyi.eyunda.weixing.message.resp.NewsMessage;
import com.hangyi.eyunda.weixing.message.resp.TextMessage;
import com.hangyi.eyunda.weixing.util.MessageUtil;

/**
 * 核心服务类
 */
public class CoreService {
	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return
	 */
	public static String processRequest(HttpServletRequest request) {
		String respMessage = null;
		try {
			// 默认返回的文本消息内容
			String respContent = "功能正在开发中...请稍候尝试！";

			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);

			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");
			
			 // 默认回复此文本消息  
            TextMessage textMessage = new TextMessage();  
            textMessage.setToUserName(fromUserName);  
            textMessage.setFromUserName(toUserName);  
            textMessage.setCreateTime(new Date().getTime());  
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);  
            textMessage.setFuncFlag(0);  
            // 由于href属性值必须用双引号引起，这与字符串本身的双引号冲突，所以要转义  
            textMessage.setContent(respContent);  
            // 将文本消息对象转换成xml字符串  
            respMessage = MessageUtil.textMessageToXml(textMessage); 

			// 文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				respMessage = creatNewsMessage(fromUserName, toUserName);
			}
			// 图片消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				// respContent = "您发送的是图片消息！";
				return respMessage;
			}
			// 地理位置消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				// respContent = "您发送的是地理位置消息！";
				return respMessage;
			}
			// 链接消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				// respContent = "您发送的是链接消息！";
				return respMessage;
			}
			// 音频消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				// respContent = "您发送的是音频消息！";
				return respMessage;
			}
			// 事件推送
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");
				// 订阅
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					respMessage = creatNewsMessage(fromUserName, toUserName);
				}
				// 取消订阅
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
				}
				// 自定义菜单点击事件
				else if (eventType.equalsIgnoreCase(MessageUtil.EVENT_TYPE_CLICK)) {
					// 事件KEY值，与创建自定义菜单时指定的KEY值对应
					String eventKey = requestMap.get("EventKey");

					if (eventKey.equals("22")) {
						// respContent = "动态角色";
						respMessage = creatTextMessage(fromUserName, toUserName);
					} 
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return respMessage;
	}
	private static String creatTextMessage(String fromUserName,
			String toUserName){
		String respMessage;
		String content = "";
		
		StringBuffer buffer = new StringBuffer();  
	    buffer.append("平台用户共分3种角色：").append("\n\n");  
	    buffer.append("a  托运人：发布货物、管理子帐号。").append("\n");  
//	    buffer.append("b  船代：发布船舶、管理船舶、签定运输合同。").append("\n");  
	    buffer.append("b  承运人：发布船舶、管理子帐号。").append("\n");  
	    buffer.append("c  子帐号：包括上报子帐号和查看子帐号。").append("\n\n");  
	    buffer.append("回复“任意文字”显示主页");  
	    content = buffer.toString();
		
		TextMessage textMessage = new TextMessage();
		textMessage.setContent(content);
		textMessage.setCreateTime(new Date().getTime());
		textMessage.setToUserName(fromUserName);  
		textMessage.setFromUserName(toUserName); 
		textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);  
		textMessage.setFuncFlag(0); 
		respMessage = MessageUtil.textMessageToXml(textMessage);
		return respMessage;
	}

	private static String creatNewsMessage(String fromUserName,
			String toUserName) {
		String respMessage;
		List<Article> articleList = new ArrayList<Article>();
		 // 创建图文消息  
		NewsMessage newsMessage = new NewsMessage();  
		newsMessage.setToUserName(fromUserName);  
		newsMessage.setFromUserName(toUserName);  
		newsMessage.setCreateTime(new Date().getTime());  
		newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);  
		newsMessage.setFuncFlag(0); 
		Article article1 = new Article();
		article1.setTitle("易运达货运电商平台");
		article1.setDescription("");
		article1.setPicUrl("http://www.eyd98.com/img/wx/ship.jpg");
		article1.setUrl("http://www.eyd98.com/wx/appuse.html");

		Article article2 = new Article();
		article2.setTitle("易运达使用介绍");
		article2.setDescription("");
		article2.setPicUrl("http://www.eyd98.com/img/wx/appusehelp.jpg");
		article2.setUrl("http://www.eyd98.com/wx/appuse2.html");
		
		Article article3 = new Article();
		article3.setTitle("易运达App下载");
		article3.setDescription("");
		article3.setPicUrl("http://www.eyd98.com/img/wx/eyundaapp.png");
		article3.setUrl("http://www.eyd98.com/wx/appdown");

		articleList.add(article1);
		articleList.add(article2);
		articleList.add(article3);
		newsMessage.setArticleCount(articleList.size());
		newsMessage.setArticles(articleList);
		respMessage = MessageUtil.newsMessageToXml(newsMessage);
		return respMessage;
	}
}
