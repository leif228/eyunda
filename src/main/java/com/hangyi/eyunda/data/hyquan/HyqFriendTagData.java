package com.hangyi.eyunda.data.hyquan;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hangyi.eyunda.data.BaseData;

public class HyqFriendTagData extends BaseData {
	private static final long serialVersionUID = -1L;

	private Long id = 0L; // ID

	private Long userId = 0L; // 用户ID
	private String tag = ""; // 标签
	private Integer no = 0; // 序号
	private String createTime = ""; // 建立时间

	private List<HyqUserData> userDatas = new ArrayList<HyqUserData>(); // 好友集合

	public HyqFriendTagData() {
		super();
	}

	public HyqFriendTagData(Map<String, Object> params) {
		super();
		if (params != null && !params.isEmpty()) {
			this.id = ((Double) params.get("id")).longValue();
			this.userId = ((Double) params.get("userId")).longValue();
			this.tag = (String) params.get("tag");
			this.no = ((Double) params.get("no")).intValue();
			this.createTime = (String) params.get("createTime");
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> userDatasMap = (List<Map<String, Object>>) params.get("userDatas");
			if (userDatasMap != null && userDatasMap.size() > 0) {
				for (Map<String, Object> map : userDatasMap) {
					HyqUserData data = new HyqUserData(map);
					this.userDatas.add(data);
				}
			}
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public List<HyqUserData> getUserDatas() {
		return userDatas;
	}

	public void setUserDatas(List<HyqUserData> userDatas) {
		this.userDatas = userDatas;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
