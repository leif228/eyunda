package com.hangyi.eyunda.service.hyquan;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hangyi.eyunda.dao.HyqMessageDao;
import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.data.hyquan.HyqMessageData;
import com.hangyi.eyunda.domain.HyqMessage;
import com.hangyi.eyunda.domain.enumeric.YesNoCode;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.Constants;
import com.hangyi.eyunda.util.CopyUtil;
import com.hangyi.eyunda.util.MD5Util;
import com.hangyi.eyunda.util.sms.SMSContent;
import com.hangyi.eyunda.util.sms.SMSResponse;
import com.hangyi.eyunda.util.sms.SMSSender;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class HyqMessageService {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private HyqMessageDao messageDao;

	public boolean sendCheckCodeTo(String mobile, String welcome, String checkCode) {
		String content = Constants.SMS_HEAD + welcome + "本次操作的验证码：" + checkCode;

		// 首先保存短信记录
		HyqMessage message = new HyqMessage();
		message.setMobile(mobile);
		message.setContent(content);
		message.setCreateTime(Calendar.getInstance());
		message.setStatus(YesNoCode.no);
		messageDao.save(message);

		// 其次调用短信平台接口发送验证码
		SMSContent smsDetail = new SMSContent();
		smsDetail.setAccount(Constants.SMS_ACCOUNT);
		smsDetail.setPassword(MD5Util.encrypt(Constants.SMS_PASSWORD).toUpperCase());
		smsDetail.setMobile(mobile);
		smsDetail.setContent(content);
		smsDetail.setExtno("");
		smsDetail.setRequestId(String.valueOf(System.nanoTime()));
		SMSResponse res = SMSSender.send(smsDetail);

		if ("10".equals(res.getStatus())) {
			message.setStatus(YesNoCode.yes);
			messageDao.save(message);

			return true;
		} else
			return false;
	}

	public boolean sendMessage(String mobile, String content) {
		content = Constants.SMS_HEAD + content;

		// 其次调用短信平台接口发送
		SMSContent smsDetail = new SMSContent();
		smsDetail.setAccount(Constants.SMS_ACCOUNT);
		smsDetail.setPassword(MD5Util.encrypt(Constants.SMS_PASSWORD).toUpperCase());
		smsDetail.setMobile(mobile);
		smsDetail.setContent(content);
		smsDetail.setExtno("");
		smsDetail.setRequestId(String.valueOf(System.nanoTime()));
		SMSResponse res = SMSSender.send(smsDetail);

		// 首先保存短信记录
		HyqMessage message = new HyqMessage();
		message.setMobile(mobile);
		message.setContent(content);
		message.setCreateTime(Calendar.getInstance());
		message.setStatus(YesNoCode.no);

		if ("10".equals(res.getStatus())) {
			message.setStatus(YesNoCode.yes);
			messageDao.save(message);
			return true;
		} else {
			messageDao.save(message);
			return false;
		}
	}

	public HyqMessageData getMessageData(HyqMessage message) {
		if (message != null) {
			HyqMessageData messageData = new HyqMessageData();
			CopyUtil.copyProperties(messageData, message);
			messageData.setCreateTime(CalendarUtil.toYYYY_MM_DD_HH_MM(message.getCreateTime()));

			return messageData;
		}
		return null;
	}

	public HyqMessageData getMessageData(Long messageId) {
		HyqMessage message = messageDao.get(messageId);
		if (message == null)
			return null;
		else
			return this.getMessageData(message);
	}

	public Page<HyqMessageData> getMessagePageData(Page<HyqMessageData> pageData) {
		List<HyqMessageData> messageDatas = new ArrayList<HyqMessageData>();

		Page<HyqMessage> page = messageDao.getMessagePage(pageData.getPageNo(), pageData.getPageSize(), null);

		if (!page.getResult().isEmpty()) {
			for (HyqMessage message : page.getResult()) {
				HyqMessageData messageData = this.getMessageData(message);
				messageDatas.add(messageData);
			}
		}

		CopyUtil.copyProperties(pageData, page);
		pageData.setResult(messageDatas);

		return pageData;
	}

	// 可能已经派单，不允许删除
	public boolean deleteMessage(Long messageId) {
		try {
			// if
			messageDao.delete(messageId);

			return true;
		} catch (Exception e) {
			logger.error(e.toString());
			return false;
		}
	}

}
