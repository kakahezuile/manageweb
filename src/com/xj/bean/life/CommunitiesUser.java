package com.xj.bean.life;

import java.util.List;

import com.xj.bean.Communities;
import com.xj.bean.Users;

public class CommunitiesUser {

	private Communities communities;
	private List<Users> listUser;
	public Communities getCommunities() {
		return communities;
	}
	public void setCommunities(Communities communities) {
		this.communities = communities;
	}
	public List<Users> getListUser() {
		return listUser;
	}
	public void setListUser(List<Users> listUser) {
		this.listUser = listUser;
	}
}
