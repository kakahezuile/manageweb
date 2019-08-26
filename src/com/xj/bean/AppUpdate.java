package com.xj.bean;


public class AppUpdate {

	private String AppUpdateId;
	private String AppId;
	private String type;
	private String status;  //状态
	private String updateUser;//需要更新的用户    这里可以是单人的emobId  type=person    也可以是小区id  type = community    还可以是全部  all  type=all
	
	
	public String getAppUpdateId() {
		return AppUpdateId;
	}
	public void setAppUpdateId(String appUpdateId) {
		AppUpdateId = appUpdateId;
	}
	public String getAppId() {
		return AppId;
	}
	public void setAppId(String appId) {
		AppId = appId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
}
