package com.hangyi.eyunda.service.complain;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.dao.PageHibernateDao;
import com.hangyi.eyunda.dao.YydComplainDao;
import com.hangyi.eyunda.data.complain.ComplainData;
import com.hangyi.eyunda.domain.YydComplain;
import com.hangyi.eyunda.domain.enumeric.YesNoCode;
import com.hangyi.eyunda.service.BaseService;
import com.hangyi.eyunda.service.portal.login.UserService;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.CopyUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class ComplainService extends BaseService<YydComplain, Long> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private YydComplainDao complainDao;

	@Autowired
	private UserService userService;

	@Override
	public PageHibernateDao<YydComplain, Long> getDao() {
		return (PageHibernateDao<YydComplain, Long>) complainDao;
	}

	// 取一条建议或者投诉
	public ComplainData getComplainData(Long id) {
		YydComplain complain = complainDao.get(id);
		
		if (complain != null) {
			ComplainData complainData = new ComplainData();
			CopyUtil.copyProperties(complainData, complain);

			complainData.setUserData(userService.getById(complainData.getUserId()));
			complainData.setCreateTime(CalendarUtil.toYYYY_MM_DD(complain.getCreateTime()));
			// 设置回复时间
			if (complainData.getStatus() == YesNoCode.no) {
				complainData.setReplyTime("");
			} else {
				complainData.setReplyTime(CalendarUtil.toYYYY_MM_DD(complain.getReplyTime()));
			}

			return complainData;
		}
		return null;
	}

	// 取所有建议或者投诉
	public Page<ComplainData> getComplainDatas(Page<ComplainData> pageData) {
		Page<YydComplain> page = complainDao.getList(pageData);
		
		List<ComplainData> complainDatas = new ArrayList<ComplainData>();
		for (YydComplain complain : page.getResult()) {
			ComplainData complainData = this.getComplainData(complain.getId());
			complainDatas.add(complainData);
		}
		pageData.setResult(complainDatas);
		
		return pageData;
	}
	
	// 取得用户所有投诉建议
	public Page<ComplainData> getComplainDatas(Long userId, Page<ComplainData> pageData) {
		Page<YydComplain> page = complainDao.getByUserId(pageData, userId);
		
		List<ComplainData> complainDatas = new ArrayList<ComplainData>();
		
		for (YydComplain complain : page.getResult()) {
			ComplainData complainData = this.getComplainData(complain.getId());
			complainDatas.add(complainData);
		}
		pageData.setResult(complainDatas);
		
		return pageData;
	}
	
	// 保存或修改一条建议投诉
	public boolean saveOrUpdate(ComplainData complainData) {
		YydComplain complain = complainDao.get(complainData.getId());
		
		if(complain == null){
			complain = new YydComplain();
			complain.setContent(complainData.getContent());
			complain.setCreateTime(CalendarUtil.now());
			complain.setOpinion(complainData.getOpinion());
			complain.setReply(complainData.getReply());
			complain.setReplyTime(CalendarUtil.now());
			complain.setStatus(YesNoCode.no);
			complain.setUserId(complainData.getUserId());
			complainDao.save(complain);
			return true;
		}
		
		if(complain != null && complain.getStatus() == YesNoCode.no){
			complain.setContent(complainData.getContent());
			complain.setCreateTime(CalendarUtil.now());
			complainDao.save(complain);
			return true;
		}
		
		return false;
	}

	// 回复一条投诉建议
	public boolean replyComplain(Long id, String reply) {
		YydComplain complain = complainDao.get(id);
		
		if(complain != null && complain.getStatus() == YesNoCode.no){
			complain.setReply(reply);
			complain.setReplyTime(CalendarUtil.now());
			complain.setStatus(YesNoCode.yes);
			complainDao.save(complain);
			
			return true;
		}
		
		return false;
	}

	// 删除一条投诉建议
	public void deleteComplain(Long id) {
		YydComplain complain = complainDao.get(id);
		if(complain != null)
			complainDao.delete(id);
	}

}
