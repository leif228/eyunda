package com.hangyi.eyunda.service.hyquan;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hangyi.eyunda.dao.HyqFriendDao;
import com.hangyi.eyunda.dao.HyqFriendTagDao;
import com.hangyi.eyunda.data.hyquan.HyqFriendData;
import com.hangyi.eyunda.data.hyquan.HyqFriendTagData;
import com.hangyi.eyunda.data.hyquan.HyqUserData;
import com.hangyi.eyunda.domain.HyqFriend;
import com.hangyi.eyunda.domain.HyqFriendTag;
import com.hangyi.eyunda.domain.enumeric.ApplyStatusCode;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.CopyUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class HyqFriendService {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private HyqFriendTagDao friendTagDao;
	@Autowired
	private HyqFriendDao friendDao;

	@Autowired
	private HyqUserService userService;

	private List<HyqUserData> getTagFriendDatas(Long userId, Long tagId) {
		List<HyqUserData> userDatas = new ArrayList<HyqUserData>();

		List<HyqFriend> tagFriends = friendDao.getTagFriends(userId, tagId, ApplyStatusCode.approve);

		for (HyqFriend friend : tagFriends) {
			HyqUserData userData = userService.getById(friend.getFriendId());
			userDatas.add(userData);
		}

		return userDatas;
	}

	private HyqFriendTagData getFriendTagData(HyqFriendTag friendTag) {
		if (friendTag != null) {
			HyqFriendTagData friendTagData = new HyqFriendTagData();
			CopyUtil.copyProperties(friendTagData, friendTag);
			friendTagData.setCreateTime(CalendarUtil.toYYYY_MM_DD_HH_MM_SS(friendTag.getCreateTime()));

			List<HyqUserData> userDatas = this.getTagFriendDatas(friendTag.getUserId(), friendTag.getId());
			friendTagData.setUserDatas(userDatas);

			return friendTagData;
		}
		return null;
	}

	public List<HyqFriendTagData> getFriendTagDatas(Long userId) {
		List<HyqFriendTagData> friendTagDatas = new ArrayList<HyqFriendTagData>();
		try {
			List<HyqFriendTag> friendTags = friendTagDao.getByUserId(userId);
			for (HyqFriendTag friendTag : friendTags) {
				HyqFriendTagData friendTagData = this.getFriendTagData(friendTag);
				friendTagDatas.add(friendTagData);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return friendTagDatas;
	}

	public HyqFriendData getFriendData(HyqFriend friend) {
		if (friend != null) {
			HyqFriendData friendData = new HyqFriendData();
			CopyUtil.copyProperties(friendData, friend);
			friendData.setCreateTime(CalendarUtil.toYYYY_MM_DD_HH_MM_SS(friend.getCreateTime()));

			HyqFriend friend2 = friendDao.getFriend(friend.getFriendId(), friend.getUserId());

			friendData.setApplyId(friend.getId() < friend2.getId() ? friend.getUserId() : friend2.getUserId());

			HyqUserData ud = userService.getById(friend.getUserId());
			friendData.setUserData(ud);
			HyqUserData fd = userService.getById(friend.getFriendId());
			friendData.setFriendData(fd);

			return friendData;
		}
		return null;
	}

	public List<HyqFriendData> getMyFriendDatas(Long userId, ApplyStatusCode status) {
		List<HyqFriendData> friendDatas = new ArrayList<HyqFriendData>();
		try {
			List<HyqFriend> friends = friendDao.getMyFriends(userId, status);
			for (HyqFriend friend : friends) {
				HyqFriendData friendData = this.getFriendData(friend);
				friendDatas.add(friendData);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return friendDatas;
	}

	public void removeFriend(Long userId, Long friendId) throws Exception {
		HyqFriend friend = friendDao.getFriend(userId, friendId);
		if (friend != null)
			friendDao.delete(friend);

		HyqFriend friend2 = friendDao.getFriend(friendId, userId);
		if (friend2 != null)
			friendDao.delete(friend2);
	}

	public void applyFriend(Long userId, Long tagId, Long friendId) throws Exception {
		HyqFriend friend = friendDao.getFriend(userId, friendId);
		if (friend != null) {
			throw new Exception("错误！你已经添加了该好友，不可重复!");
		} else {
			// 我方
			friend = new HyqFriend();
			friend.setUserId(userId);
			friend.setTagId(tagId);
			friend.setFriendId(friendId);
			friend.setStatus(ApplyStatusCode.apply);
			friendDao.save(friend);
			// 对方
			HyqFriend friend2 = new HyqFriend();
			friend2.setUserId(friendId);
			friend2.setTagId(0L);
			friend2.setFriendId(userId);
			friend2.setStatus(ApplyStatusCode.apply);
			friendDao.save(friend2);
		}
	}

	public void approveFriend(Long userId, Long tagId, Long friendId, ApplyStatusCode approveCode) throws Exception {
		HyqFriend friend = friendDao.getFriend(userId, friendId);

		if (friend == null) {
			throw new Exception("错误！加为好友请求已经被删除!");

		} else {
			if (friend.getStatus() != ApplyStatusCode.apply)
				throw new Exception("错误！加为好友请求记录不是申请状态!");

			// 我方
			friend.setTagId(tagId);
			friend.setStatus(approveCode);// 同意或拒绝
			friendDao.save(friend);
			// 对方
			HyqFriend tagFriend2 = friendDao.getFriend(friendId, userId);
			tagFriend2.setStatus(approveCode);// 同意或拒绝
			friendDao.save(tagFriend2);
		}
	}

	public Long saveTag(Long userId, Long tagId, String tag, Integer no) throws Exception {
		HyqFriendTag friendTag = friendTagDao.get(tagId);

		if (friendTag == null) {
			HyqFriendTag ft = friendTagDao.getTag(userId, tag);
			if (ft != null)
				throw new Exception("错误！指定标签已经存在，不可重复!");
			// 新建
			friendTag = new HyqFriendTag();
			friendTag.setUserId(userId);
			friendTag.setTag(tag);
			friendTag.setNo(no);
			friendTagDao.save(friendTag);
		} else if (friendTag != null && friendTag.getUserId().equals(userId)) {
			// 改名或调序
			friendTag.setTag(tag);
			friendTag.setNo(no);
			friendTagDao.save(friendTag);
		} else {
			throw new Exception("错误！你不能修改别人的标签!");
		}
		return friendTag.getId();
	}

	public void deleteTag(Long userId, Long tagId) throws Exception {
		HyqFriendTag friendTag = friendTagDao.get(tagId);

		if (friendTag != null) {
			if (!friendTag.getUserId().equals(userId))
				throw new Exception("错误！你不能删除别人的标签!");

			List<HyqFriend> fs = friendDao.getTagFriends(userId, tagId, null);
			if (!fs.isEmpty())
				throw new Exception("错误！还有好友贴有指定标签，不可删除。");

			friendTagDao.delete(friendTag);
		} else {
			throw new Exception("错误！指定的标签没有找到。");
		}

		return;
	}

}
