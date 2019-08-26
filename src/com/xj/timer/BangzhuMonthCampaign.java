package com.xj.timer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.xj.bean.BonuscoinHistory;
import com.xj.bean.Users;
import com.xj.dao.CommunitiesDao;
import com.xj.dao.MyUserDao;
import com.xj.jodis.JodisUtils;
import com.xj.service.BonuscoinService;
import com.xj.utils.DateUtils;

import redis.clients.jedis.Jedis;

public class BangzhuMonthCampaign {

	@Autowired
	private MyUserDao myUserDao;

	@Autowired
	private CommunitiesDao communitiesDao;

	@Autowired
	private BonuscoinService bonuscoinService;

	public void execute() {
		List<Integer> list = communitiesDao.getCommunityIdList();
		for (Integer communityId : list) {
			List<Users> bangzhu = myUserDao.getCommunityCompeteBangzhuUser(communityId, 3, null, 1);// 获取小区中参与竞争帮主的用户
			List<Users> fubangzhu = myUserDao.getCommunityCompeteBangzhuUser(communityId, 1, bangzhu, 2);// 获取小区中参与竞争副帮主的用户
			
			List<Users> u = new ArrayList<Users>();
			u.addAll(bangzhu);
			u.addAll(fubangzhu);
			List<Users> zhanglao = myUserDao.getCommunityCompeteZhanglaoUser(communityId, u, 6);// 获取小区中参与竞争长老的用户
			
			// 获取现任帮主信息
			List<String> lastbangzhu = myUserDao.getCommunityBangzhuUser(communityId, "bangzhu");
			List<String> lastfubangzhu = myUserDao.getCommunityBangzhuUser(communityId, "fubangzhu");
			List<String> lastzhanglao = myUserDao.getCommunityBangzhuUser(communityId, "zhanglao");
			try (Jedis jedis = JodisUtils.getResource()) {
				String key = "bangzhu_change_info_" + communityId;
				int time = (int) (System.currentTimeMillis() / 1000l);
				
				if (lastbangzhu != null && lastbangzhu.size() > 0) {
					for (String emobId : lastbangzhu) {
						jedis.hset(key, emobId, time + "_您在本月帮主选举中失去了帮主宝座!");
						jedis.expireAt(key, DateUtils.getMonthEnd().getTime()/1000);
						BonuscoinHistory bonuscoinHistory = new BonuscoinHistory(time, 3000, "salary", emobId);
						bonuscoinService.addBonuscoinHistory(bonuscoinHistory);
					}
					myUserDao.updateUsersBonusCoin(lastbangzhu, 3000);// 给帮主发工资3000
				}
				
				if (lastfubangzhu != null && lastfubangzhu.size() > 0) {
					for (String emobId : lastfubangzhu) {
						jedis.hset(key, emobId, time + "_您在本月帮主选举中失去了副帮主宝座!");
						jedis.expireAt(key, DateUtils.getMonthEnd().getTime()/1000);
						BonuscoinHistory bonuscoinHistory = new BonuscoinHistory(time, 1000, "salary", emobId);
						bonuscoinService.addBonuscoinHistory(bonuscoinHistory);
					}
					myUserDao.updateUsersBonusCoin(lastfubangzhu, 1000);// 给副帮主发工资1000
				}
				
				if (lastzhanglao != null && lastzhanglao.size() > 0) {
					for (String emobId : lastzhanglao) {
						jedis.hset(key, emobId, time + "_您在本月帮主选举中失去了达人宝座!");
						jedis.expireAt(key, DateUtils.getMonthEnd().getTime()/1000);
						BonuscoinHistory bonuscoinHistory = new BonuscoinHistory(time, 100, "salary", emobId);
						bonuscoinService.addBonuscoinHistory(bonuscoinHistory);
					}
					myUserDao.updateUsersBonusCoin(lastzhanglao, 100);// 给长老发工资100
				}
				
				myUserDao.updateAllUserGrade(communityId, "bangzhong");// 将所有用户的等级设为普通帮众用户
				
				if (bangzhu != null && bangzhu.size() > 0) {
					for (Users user : bangzhu) {
						jedis.hset(key, user.getEmobId(), time + "_您在本月帮主选举中荣获帮主宝座!");
						String field = "bangzhu_community";
						jedis.hset(key, field, time + "_" + user.getNickname() + "成为本小区新任帮主");
						jedis.expireAt(key, DateUtils.getMonthEnd().getTime()/1000);
					}
					myUserDao.updateUsersGrade("bangzhu", bangzhu);// 更新用户角色为帮主
				}
				
				if (fubangzhu != null && fubangzhu.size() > 0) {
					for (Users user : fubangzhu) {
						jedis.hset(key, user.getEmobId(), time + "_您在本月帮主选举中荣获副帮主宝座!");
						jedis.expireAt(key, DateUtils.getMonthEnd().getTime()/1000);
					}
					myUserDao.updateUsersGrade("fubangzhu", fubangzhu);// 更新用户角色为副帮主
				}
				
				if (zhanglao != null && zhanglao.size() > 0) {
					for (Users user : zhanglao) {
						jedis.hset(key, user.getEmobId(), time + "_您在本月帮主选举中荣获达人宝座!");
						jedis.expireAt(key, DateUtils.getMonthEnd().getTime()/1000);
					}
					
					myUserDao.updateUsersGrade("zhanglao", zhanglao);// 更新用户角色为长老
				}
			}
		}
	}
}