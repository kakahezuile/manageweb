package com.xj.bean;

import java.util.List;

public class CommunityHome {
	
	private String userFloor;
	
	private List<FloorInRoom> list;
	 
	public String getUserFloor() {
		return userFloor;
	}

	public void setUserFloor(String userFloor) {
		this.userFloor = userFloor;
	}

	public List<FloorInRoom> getList() {
		return list;
	}

	public void setList(List<FloorInRoom> list) {
		this.list = list;
	}

	public class FloorInRoom{
		
		private String userUnit;
		
		private List<String> userRooms;

		public String getUserUnit() {
			return userUnit;
		}

		public void setUserUnit(String userUnit) {
			this.userUnit = userUnit;
		}

		public List<String> getUserRooms() {
			return userRooms;
		}

		public void setUserRooms(List<String> userRooms) {
			this.userRooms = userRooms;
		}

		

		
	}

}
