package com.hangyi.eyunda.controller.mobile;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.hangyi.eyunda.chat.MessageConstants;
import com.hangyi.eyunda.chat.data.OnlineUser;
import com.hangyi.eyunda.chat.redis.recorder.OnlineUserRecorder;
import com.hangyi.eyunda.controller.JsonResponser;
import com.hangyi.eyunda.data.account.AccountData;
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.domain.enumeric.PayStyleCode;
import com.hangyi.eyunda.domain.enumeric.QCodeResultCode;
import com.hangyi.eyunda.service.ShareDirService;
import com.hangyi.eyunda.service.account.AccountService;
import com.hangyi.eyunda.service.portal.login.UserService;
import com.hangyi.eyunda.service.qrcode.QrcodeService;
import com.hangyi.eyunda.util.Base64;
import com.hangyi.eyunda.util.Constants;
import com.hangyi.eyunda.util.DateUtils;
import com.hangyi.eyunda.util.MD5;
import com.hangyi.eyunda.util.MatrixToImageWriter;
import com.hangyi.eyunda.util.StringUtil;

@Controller
@RequestMapping("/mobile/qcode")
public class MblQcodeController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private OnlineUserRecorder onlineUserRecorder;
	@Autowired
	private JedisPool redisPool;
	@Autowired
	private UserService userService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private QrcodeService qrcodeService;	
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public void create(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String sessionId = ServletRequestUtils.getStringParameter(request, "sessionId", "");
			String contentMD5 = ServletRequestUtils.getStringParameter(request, JsonResponser.CONTENTMD5, "");

			OnlineUser ou = onlineUserRecorder.getOnlineUser(sessionId);
			if (ou == null)
				throw new Exception("session信息已丢失，正在重新连接，请稍后重试！");
			//接受请求参数
			Double num =  ServletRequestUtils.getDoubleParameter(request, "num");
			int useSure = ServletRequestUtils.getIntParameter(request, "useSure",0);
			String descipt = ServletRequestUtils.getStringParameter(request, "desc","");
			int sureDate = ServletRequestUtils.getIntParameter(request, "sureDate",0);

			//写入数据库并返回记录ID，加入下面字符串中
			String res = QCodeResultCode.fetch.name()+";"+ou.getId().toString()+";" + ou.getLoginName()+";"+ num.toString() +";"+ descipt +";"+ DateUtils.getTime("")+";"+useSure+";"+sureDate;
			String md5Str = MD5.toMD5(res) ;
			//md5Str+res 存入redis,供检查用
			Jedis jedis = redisPool.getResource();
			jedis.hset(MessageConstants.QCODE_QUEUE, md5Str, res);
			
			String newFileName = md5Str + ".jpg";
			String detail = Base64.encode(md5Str +";"+res);
			String realPath = Constants.SHARE_DIR;
			String relativePath = ShareDirService.getQcodeDir(ou.getId()) + ShareDirService.FILE_SEPA_SIGN;
			String path = realPath + relativePath  ;
			qrcodeService.encode(md5Str+"|"+StringUtil.getRandomString(32), path, newFileName, true, 800, 800, "jpg");
			Map<String, Object> content = new HashMap<String, Object>();
			content.put("img", relativePath+newFileName);
			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "二维码生成成功");
			map.put(JsonResponser.CONTENT, content);
			map.put(JsonResponser.CONTENTMD5, contentMD5);

		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
	}
	@RequestMapping(value = "/scan", method = RequestMethod.POST)
	public void scan(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		Jedis jedis = null;
		try {
			String sessionId = ServletRequestUtils.getStringParameter(request, "sessionId", "");
			String contentMD5 = ServletRequestUtils.getStringParameter(request, JsonResponser.CONTENTMD5, "");

			OnlineUser ou = onlineUserRecorder.getOnlineUser(sessionId);
			if (ou == null)
				throw new Exception("session信息已丢失，正在重新连接，请稍后重试！");
			//接受请求参数
			String key = ServletRequestUtils.getStringParameter(request, "id","");

			UserData ud = null;
			
			if(key.equals("")){
				throw new Exception("二维码信息不能为空");
			}else{
				key = key.substring(0,key.lastIndexOf("|"));
			}
			jedis = redisPool.getResource();
			String res = jedis.hget(MessageConstants.QCODE_QUEUE, key);
			if(res == null){
				throw new Exception("二维码信息不存在,请重新生成");
			}else{
				String[] resArr = res.split(";");
//				String[] resArr = result.split(";");
//				if(qc.equals(QCodeResultCode.fetch)){
//				    resArr[1]);//收款用户ID
//					resArr[2]);//收款人姓名
//					resArr[3]);//收款金额 
//					resArr[4]);//描述
//					nteger.parseInt(resArr[5]));//是否资金托管
//					Integer.parseInt(resArr[6]));//资金托管日期
//					resArr[0]);//支付类型
				
				//获取收款人账户信息
				Long id = Long.parseLong(resArr[1]);
				ud = userService.getById(id);
				
				if(ud == null){
					throw new Exception("收款人信息错误");
				}else{
					//读过就删除
					jedis.hdel(MessageConstants.QCODE_QUEUE, key);
					//删除图片文件
					String realPath = Constants.SHARE_DIR;
					String relativePath = ShareDirService.getQcodeDir(ou.getId()) + ShareDirService.FILE_SEPA_SIGN;
					String path = realPath + relativePath  ;
					String fileName = key + ".jpg";
					File file = new File(path+fileName);
					if (file.exists()) {
						file.delete();
					}
				}
			}

			Map<String, Object> content = new HashMap<String, Object>();
			content.put("result", res);
			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "成功");
			map.put(JsonResponser.CONTENT, content);
			map.put(JsonResponser.CONTENTMD5, contentMD5);

		}catch(JedisConnectionException jedisExcetion){
			redisPool.returnBrokenResource(jedis);
		}catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}finally {
			if(jedis != null)
				redisPool.returnResource(jedis);
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
	}
}
