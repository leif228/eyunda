package com.hangyi.eyunda.service.notice;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.dao.PageHibernateDao;
import com.hangyi.eyunda.dao.YydNoticeDao;
import com.hangyi.eyunda.data.notice.NoticeData;
import com.hangyi.eyunda.domain.YydNotice;
import com.hangyi.eyunda.domain.enumeric.ImgTypeCode;
import com.hangyi.eyunda.domain.enumeric.NoticeTopCode;
import com.hangyi.eyunda.domain.enumeric.NtcColumnCode;
import com.hangyi.eyunda.domain.enumeric.ReleaseStatusCode;
import com.hangyi.eyunda.service.BaseService;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.Constants;
import com.hangyi.eyunda.util.CopyUtil;
import com.hangyi.eyunda.util.MultipartUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class NoticeService extends BaseService<YydNotice, Long> {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private YydNoticeDao noticeDao;

	@Override
	public PageHibernateDao<YydNotice, Long> getDao() {
		return (PageHibernateDao<YydNotice, Long>) noticeDao;
	}

	public Page<NoticeData> getHomePageNotices(int pageSize, int pageNo) {

		Page<NoticeData> pageData = new Page<NoticeData>();
		Page<YydNotice> page = noticeDao.findNotices(pageSize, pageNo, NtcColumnCode.bulletin, NtcColumnCode.help);

		List<NoticeData> noticeDatas = new ArrayList<NoticeData>();

		for (YydNotice yydNotice : page.getResult()) {
			NoticeData noticeData = this.getNoticeData(yydNotice);

			String content = noticeData.getContent();
			content = htmlspecialchars(content);
			if (content.length() > 250)
				noticeData.setContent(content.substring(0, 249) + "...");
			else
				noticeData.setContent(content);

			noticeDatas.add(noticeData);
		}
		CopyUtil.copyProperties(pageData, page);
		pageData.setResult(noticeDatas);

		return pageData;
	}

	private String htmlspecialchars(String str) {
		String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
		String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
		String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

		Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
		Matcher m_script = p_script.matcher(str);
		str = m_script.replaceAll(""); // 过滤script标签

		Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
		Matcher m_style = p_style.matcher(str);
		str = m_style.replaceAll(""); // 过滤style标签

		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(str);
		str = m_html.replaceAll(""); // 过滤html标签

		return str.trim(); // 返回文本字符串
	}

	public Page<NoticeData> getManagePageNotices(int pageSize, int pageNo, NtcColumnCode selectCode) {

		Page<NoticeData> pageData = new Page<NoticeData>();
		Page<YydNotice> page = noticeDao.findManageNotices(pageSize, pageNo, selectCode);

		List<NoticeData> noticeDatas = new ArrayList<NoticeData>();

		for (YydNotice yydNotice : page.getResult()) {
			NoticeData noticeData = this.getNoticeData(yydNotice);

			String content = noticeData.getContent();
			content = htmlspecialchars(content);
			noticeData.setContent(content);

			noticeDatas.add(noticeData);
		}
		CopyUtil.copyProperties(pageData, page);
		pageData.setResult(noticeDatas);

		return pageData;
	}

	public NoticeData getNoticeData(YydNotice yydNotice) {
		NoticeData noticeData = new NoticeData();
		CopyUtil.copyProperties(noticeData, yydNotice);

		noticeData.setCreateTime(CalendarUtil.toYYYY_MM_DD(yydNotice.getCreateTime()));
		noticeData.setPublishTime(CalendarUtil.toYYYY_MM_DD(yydNotice.getPublishTime()));

		return noticeData;
	}

	public NoticeData getNoticeDataById(Long noticeId) {
		YydNotice yydNotice = noticeDao.get(noticeId);
		if (yydNotice != null) {
			yydNotice.setPointNum(yydNotice.getPointNum() + 1);
			noticeDao.save(yydNotice);

			NoticeData noticeData = this.getNoticeData(yydNotice);
			return noticeData;
		}
		return null;
	}

	public NoticeData getManageNoticeDataById(Long noticeId) {
		YydNotice yydNotice = noticeDao.get(noticeId);
		if (yydNotice != null) {

			NoticeData noticeData = this.getNoticeData(yydNotice);
			return noticeData;
		}
		return null;
	}

	public boolean unpublish(Long id) {
		YydNotice yydNotice = noticeDao.get(id);
		if (yydNotice != null) {
			if (yydNotice.getReleaseStatus() == ReleaseStatusCode.publish) {
				yydNotice.setReleaseStatus(ReleaseStatusCode.unpublish);
				noticeDao.save(yydNotice);
				return true;
			}
		}
		return false;
	}

	public boolean publish(Long id) {
		YydNotice yydNotice = noticeDao.get(id);
		if (yydNotice != null) {
			if (yydNotice.getReleaseStatus() == ReleaseStatusCode.unpublish) {
				yydNotice.setReleaseStatus(ReleaseStatusCode.publish);
				yydNotice.setPublishTime(Calendar.getInstance());
				noticeDao.save(yydNotice);
				return true;
			}
		}
		return false;
	}

	public void saveOrUpdate(NoticeData noticeData) throws Exception {
		if (noticeData.getContent().getBytes().length > 16000)
			throw new Exception("错误！内容过长或文件过大！");

		YydNotice notice = noticeDao.get(noticeData.getId());
		if (notice == null)
			notice = new YydNotice();

		notice.setTitle(noticeData.getTitle());
		notice.setContent(noticeData.getContent());
		notice.setReleaseStatus(ReleaseStatusCode.unpublish);
		notice.setCreateTime(Calendar.getInstance());
		notice.setTop(noticeData.getTop());
		notice.setNtcColumn(noticeData.getNtcColumn());

		// 图
		MultipartFile bigFile = noticeData.getSourceImg();
		if (bigFile != null && !bigFile.isEmpty()) {
			String ext = FilenameUtils.getExtension(bigFile.getOriginalFilename());
			if (ImgTypeCode.contains(ext)) {
				// 拷贝新文件到指定位置
				String realPath = Constants.SHARE_DIR;
				String relativePath = "/notice";
				String prefix = "noticeImage";
				String url = MultipartUtil.uploadFile(bigFile, realPath, relativePath, prefix);
				// 修改数据库中文件路径
				notice.setSource(url);
			} else {
				throw new Exception("错误！上传文件的类型不符合要求！");
			}
		}
		noticeDao.save(notice);
	}

	public String saveImage(MultipartFile imgFile) throws Exception {
		if (imgFile != null && !imgFile.isEmpty()) {
			String ext = FilenameUtils.getExtension(imgFile.getOriginalFilename());
			if (ImgTypeCode.contains(ext)) {
				// 拷贝新文件到指定位置
				String realPath = Constants.SHARE_DIR;
				String relativePath = "/notice";
				String prefix = "noticeImage";
				String url = MultipartUtil.uploadFile(imgFile, realPath, relativePath, prefix);

				return url;
			} else
				throw new Exception("错误！上传文件的类型不符合要求！");
		} else
			throw new Exception("上传文件为空！");

	}

	public boolean top(Long id) {
		YydNotice yydNotice = noticeDao.get(id);
		if (yydNotice != null) {
			yydNotice.setTop(NoticeTopCode.yes);
			noticeDao.save(yydNotice);
			return true;
		}
		return false;
	}

	public boolean untop(Long id) {
		YydNotice yydNotice = noticeDao.get(id);
		if (yydNotice != null) {
			yydNotice.setTop(NoticeTopCode.no);
			noticeDao.save(yydNotice);
			return true;
		}
		return false;
	}
}
