package com.hangyi.eyunda.util;

import java.io.File;
import java.io.IOException;

import jodd.props.Props;
import jodd.typeconverter.Convert;

public class Constants {
	private static Props p;

	static {
		try {
			p = new Props();
			p.load(new File(Constants.class.getClassLoader().getResource("").getFile() + "config.properties"), "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getValue(String key) {
		return p.getValue(key);
	}

	public static Integer getIntegerValue(String key) {
		return Convert.toInteger(getValue(key));
	}

	public static Long getLongValue(String key) {
		return Convert.toLong(getValue(key));
	}

	public static Double getDoubleValue(String key) {
		return Convert.toDoubleValue(getValue(key));
	}

	public static final Integer ALL_SIZE = 1000;

	public static final String SMS_HEAD = Constants.getValue("SMS_HEAD");
	public static final String SMS_ACCOUNT = Constants.getValue("SMS_ACCOUNT");
	public static final String SMS_PASSWORD = Constants.getValue("SMS_PASSWORD");

	public static final String SHIP_APP_KEY = Constants.getValue("SHIP_APP_KEY");
	public static final String SHIP_SECRET = Constants.getValue("SHIP_SECRET");
	public static final String CARGO_APP_KEY = Constants.getValue("CARGO_APP_KEY");
	public static final String CARGO_SECRET = Constants.getValue("CARGO_SECRET");

	public static final Long FINANCE_USERID = Constants.getLongValue("FINANCE_USERID");
	public static final Long HUIYANG_USERID = Constants.getLongValue("HUIYANG_USERID");

	public static final String MESSAGE_SERVER_NAME = Constants.getValue("MESSAGE_SERVER_NAME");
	public static final Integer MINA_PORT = Constants.getIntegerValue("MINA_PORT");
	public static final Integer OFFLINE_TIME = Constants.getIntegerValue("OFFLINE_TIME");

	public static final String SCF_SALT_VALUE = Constants.getValue("SCF_SALT_VALUE");
	public static final String SALT_VALUE = Constants.getValue("SALT_VALUE");
	public static final String SHARE_DIR = Constants.getValue("SHARE_DIR");
	public static final String AVI_DIR = Constants.getValue("AVI_DIR");
	public static final String FLV_DIR = Constants.getValue("FLV_DIR");
	public static final String SHIP_IMAGE = Constants.getValue("SHIP_IMAGE");
	public static final String INDEX_DIR = Constants.getValue("INDEX_DIR");
	public static final String CARGO_INDEX_DIR = Constants.getValue("CARGO_INDEX_DIR");
	public static final String DOMAIN_NAME = Constants.getValue("DOMAIN_NAME");
	public static final String LOCALE_NAME = Constants.getValue("LOCALE_NAME");

	public static final Integer PERIOD_PERCENT = Constants.getIntegerValue("PERIOD_PERCENT");

	public static final String SELLEREMAIL = Constants.getValue("SELLEREMAIL");
	public static final String RECEIVERNAME = Constants.getValue("RECEIVERNAME");
	public static final String RECEIVERADDRESS = Constants.getValue("RECEIVERADDRESS");
	public static final String RECEIVERZIP = Constants.getValue("RECEIVERZIP");
	public static final String RECEIVERPHONE = Constants.getValue("RECEIVERPHONE");
	public static final String RECEIVERMOBILE = Constants.getValue("RECEIVERMOBILE");

	public static final String APPID = Constants.getValue("APPID");
	public static final String APPSECRET = Constants.getValue("APPSECRET");
	public static final String TOKEN = Constants.getValue("TOKEN");
	public static final String EncodingAESKey = Constants.getValue("EncodingAESKey");
	public static final String AisKey = Constants.getValue("AisKey");
	public static final String IconSize = Constants.getValue("IconSize");

	public static final String MailFrom = Constants.getValue("mail_from");

	public static final String CUSTOMER_NUM = Constants.getValue("CUSTOMER_NUM");
	public static final String HANGYI_ID = Constants.getValue("HANGYI_ID");

	public static final String NETPAY_URL = Constants.getValue("NETPAY_URL");
	public static final String NOBINDPAY_URL = Constants.getValue("NOBINDPAY_URL");
	public static final String BINDANDPAY_URL = Constants.getValue("BINDANDPAY_URL");
	public static final String BANKLIST_URL = Constants.getValue("BANKLIST_URL");
	public static final String REMOVEBIND_URL = Constants.getValue("REMOVEBIND_URL");
	public static final String SENDMESSAGE_URL = Constants.getValue("SENDMESSAGE_URL");
	public static final String BINDFASTPAY_URL = Constants.getValue("BINDFASTPAY_URL");
	public static final String KHREFUND_URL = Constants.getValue("KHREFUND_URL");
	public static final String KHQUERY_URL = Constants.getValue("KHQUERY_URL");

	public static final String OVERDUE_DAYS = Constants.getValue("OVERDUE_DAYS");

	public static final String ANGEL_ID = Constants.getValue("ANGEL_ID");
	public static final String INTERVAL_DAYS = Constants.getValue("INTERVAL_DAYS");

	// 企业代码
	public static final String QYDM = Constants.getValue("Qydm");
	// 资金汇总账号
	public static final String SUPACCTID = Constants.getValue("SupAcctId");
	// api服务IP
	public static final String SERVERIPADDRESS = Constants.getValue("ServerIPAddress");
	// api服务端口
	public static final Integer SERVERPORT = Constants.getIntegerValue("ServerPort");
	// 支付密码地址
	public static final String PWURL = Constants.getValue("pwUrl");
	// 交易网名称
	public static final String TRANWEBNAME = Constants.getValue("TranWebName");
	// 支付密码设置异步通知页面路径
	public static final Double PLAT_RATE = Constants.getDoubleValue("PLAT_RATE");
	public static final Double BROKER_RATE = Constants.getDoubleValue("BROKER_RATE");

}
