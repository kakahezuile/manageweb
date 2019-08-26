package com.xj.bean;

public class HomeOwnerExcel {
	private String id;
	private String roomNum;
	private String name;

	public HomeOwnerExcel() {
	}

	public HomeOwnerExcel(String id, String roomNum, String name) {

		this.id = id;
		this.roomNum = roomNum;
		this.name = name;
	}

	@Override
	public String toString() {
		return "HomeOwner [id=" + id + ", roomNum=" + roomNum + ", name="
				+ name + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(String roomNum) {
		this.roomNum = roomNum;
	}

}
