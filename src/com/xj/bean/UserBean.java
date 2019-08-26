package com.xj.bean;




import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import com.xj.utils.DateUtil;


public class UserBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int userId;
	private String phoneNumber;
	private String passWord;
	private String realName;
	private Date birthday;
	private int age;
	private int sex;
	private String job;
	private String address;
	private String type;
	private Timestamp createTime;
	private String salt;
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
		if(birthday!=null){
			this.age=DateUtil.getAge(birthday);
		}
	}
	
	public int getAge() {
		return age;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	@Override
	public String toString() {
		return "UserBean [userId=" + userId + ", phoneNumber=" + phoneNumber
				+ ", passWord=" + passWord + ", realName=" + realName
				+ ", birthday=" + birthday + ", age=" + age + ", sex=" + sex
				+ ", job=" + job + ", address=" + address + ", type=" + type
				+ ", createTime=" + createTime + "]";
	}
	
	
}
