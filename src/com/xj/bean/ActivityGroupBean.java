package com.xj.bean;

import java.util.List;

public class ActivityGroupBean {
	private String groupname; // 群组名称
	private String desc; // 群组的描述      *必须填写
	private Integer ispublic;  // 是否公开    *必须填写 1 == 是  0 --- 不是
 	private int maxusers; // 最大人数
	private Integer approval; //加入公开群是否需要批准, 没有这个属性的话默认是true, 此属性为可选的  1 == 是  0 --- 不是
	private String owner; //群组的管理员, 此属性为必须的
	private List<MemberBean> mumbers;  // 群组成员
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getMaxusers() {
		return maxusers;
	}
	public void setMaxusers(int maxusers) {
		this.maxusers = maxusers;
	}

	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public List<MemberBean> getMumbers() {
		return mumbers;
	}
	public void setMumbers(List<MemberBean> mumbers) {
		this.mumbers = mumbers;
	}
	public Integer getIspublic() {
		return ispublic;
	}
	public void setIspublic(Integer ispublic) {
		this.ispublic = ispublic;
	}
	public Integer getApproval() {
		return approval;
	}
	public void setApproval(Integer approval) {
		this.approval = approval;
	}
	
	

}
