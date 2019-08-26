package com.xj.bean;

import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

public class UserBulletin {
		@NotInsertAnnotation
		@NotUpdataAnnotation
		private Integer userBulletinId;
		private String emobId;
		
		private Integer bulletinId;

		public Integer getUserBulletinId() {
			return userBulletinId;
		}

		public void setUserBulletinId(Integer userBulletinId) {
			this.userBulletinId = userBulletinId;
		}

		public String getEmobId() {
			return emobId;
		}

		public void setEmobId(String emobId) {
			this.emobId = emobId;
		}

		public Integer getBulletinId() {
			return bulletinId;
		}

		public void setBulletinId(Integer bulletinId) {
			this.bulletinId = bulletinId;
		}

		
}
