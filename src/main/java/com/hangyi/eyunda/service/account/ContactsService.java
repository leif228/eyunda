package com.hangyi.eyunda.service.account;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hangyi.eyunda.dao.YydChatRoomDao;
import com.hangyi.eyunda.dao.YydChatRoomStatusDao;
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.domain.YydChatRoom;
import com.hangyi.eyunda.domain.YydChatRoomStatus;
import com.hangyi.eyunda.service.portal.login.UserService;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class ContactsService {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	public final static Integer CHAT_PAGESIZE = 15;

	@Autowired
	private YydChatRoomDao roomDao;
	@Autowired
	private YydChatRoomStatusDao chatRoomStatusDao;

	@Autowired
	private UserService userService;

	private List<UserData> getUserDatas(Set<Long> uids) {
		List<UserData> uds = new ArrayList<UserData>();
		for (Long uid : uids) {
			UserData ud = userService.getUserData(uid);
			uds.add(ud);
		}
		return uds;
	}

	private Set<Long> getFriendIds(Long userId) {
		Set<Long> friendIds = new HashSet<Long>();// 去除重复ID
		List<YydChatRoom> rooms = roomDao.getByUserId(userId);
		for (YydChatRoom room : rooms) {
			List<YydChatRoomStatus> statuss = chatRoomStatusDao.getByRoomId(room.getId());
			for (YydChatRoomStatus status : statuss) {
				if (!status.getUserId().equals(userId)) {
					friendIds.add(status.getUserId());
				}
			}
		}

		return friendIds;
	}

	public List<UserData> getFriends(Long userId) {
		Set<Long> friendIds = this.getFriendIds(userId);
		List<UserData> friends = this.getUserDatas(friendIds);

		return friends;
	}

}
