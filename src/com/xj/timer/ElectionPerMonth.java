package com.xj.timer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.xj.bean.BonuscoinHistory;
import com.xj.bean.Users;
import com.xj.dao.CommunitiesDao;
import com.xj.dao.MyUserDao;
import com.xj.httpclient.apidemo.EasemobChatMessage;
import com.xj.service.BonuscoinService;
import com.xj.service.UserService;
import com.xj.utils.SystemProperties;

public class ElectionPerMonth {

	@Autowired
	private CommunitiesDao communitiesDao;

	@Autowired
	private MyUserDao myUserDao;

	@Autowired
	private BonuscoinService bonuscoinService;

	@Autowired
	private UserService userService;

	public void execute() {
		List<Integer> list = communitiesDao.getCommunityIdList();
		int time = (int) (System.currentTimeMillis() / 1000l);
		SystemProperties properties = SystemProperties.getInstance();
		String username = properties.get("election.username");
		try {
			Users toupiaoxiaoxi = userService.getUserByName(username);// 获取投票消息用户
			for (Integer communityId : list) {
				List<String> lastbangzhu = myUserDao.getCommunityBangzhuUser(communityId, "bangzhu");
				List<String> lastfubangzhu = myUserDao.getCommunityBangzhuUser(communityId, "fubangzhu");
				List<String> lastzhanglao = myUserDao.getCommunityBangzhuUser(communityId, "zhanglao");
				if (lastbangzhu != null && lastbangzhu.size() > 0) {
					for (String emobId : lastbangzhu) {
						BonuscoinHistory bonuscoinHistory = new BonuscoinHistory(time, 3000, "salary", emobId);
						bonuscoinService.addBonuscoinHistory(bonuscoinHistory);
					}
					myUserDao.updateUsersBonusCoin(lastbangzhu, 3000);// 给帮主发工资3000
				}
				
				if (lastfubangzhu != null && lastfubangzhu.size() > 0) {
					for (String emobId : lastfubangzhu) {
						BonuscoinHistory bonuscoinHistory = new BonuscoinHistory(time, 1000, "salary", emobId);
						bonuscoinService.addBonuscoinHistory(bonuscoinHistory);
					}
					myUserDao.updateUsersBonusCoin(lastfubangzhu, 1000);// 给副帮主发工资1000
				}
				
				if (lastzhanglao != null && lastzhanglao.size() > 0) {
					for (String emobId : lastzhanglao) {
						BonuscoinHistory bonuscoinHistory = new BonuscoinHistory(time, 100, "salary", emobId);
						bonuscoinService.addBonuscoinHistory(bonuscoinHistory);
					}
					myUserDao.updateUsersBonusCoin(lastzhanglao, 100);// 给堂主发工资100
				}
				
				myUserDao.updateAllUserGrade(communityId, "bangzhong");// 将所有用户的等级设为普通帮众用户
				
				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(time * 1000l);
				cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
				String timeMonth = sdf.format(cal.getTimeInMillis());
				
				List<Users> bangzhu = myUserDao.getElectionWinners(communityId,timeMonth, 0, 1);
				List<Users> fubangzhu = myUserDao.getElectionWinners(communityId,timeMonth, 1, 2);
				List<Users> tangzhu = myUserDao.getElectionWinners(communityId,timeMonth, 3, 6);
				
				myUserDao.updateUsersGrade("bangzhu", bangzhu);// 更新用户角色为帮主
				myUserDao.updateUsersGrade("fubangzhu", fubangzhu);// 更新用户角色为副帮主
				myUserDao.updateUsersGrade("zhanglao", tangzhu);// 更新用户角色为堂主
				
				addElectionResultMessage(toupiaoxiaoxi, communityId, bangzhu, fubangzhu);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addElectionResultMessage(Users toupiaoxiaoxi,Integer communityId, List<Users> bangzhu, List<Users> fubangzhu){
		try {
			List<String> usersList = userService.getEmobIdList(communityId,"owner");
		// 根据小区id获取该小区所有用户，即为发送消息对象
		String[] emobIds = new String[usersList.size()];
		for(int i=0;i<usersList.size();i++){
			emobIds[i]=usersList.get(i);
		}
		Map<String, Object> ext = new HashMap<String, Object>();
		ext.put("timestamp", (int) (System.currentTimeMillis() / 1000l)+"");
		ext.put("avatar", toupiaoxiaoxi.getAvatar());
		ext.put("nickname", toupiaoxiaoxi.getNickname());
		ext.put("CMD_CODE", 701);
		ext.put("title", "新任帮主（班底）上任了");
		ext.put("content", getBangzhuAndFubangzhuNicknames(bangzhu, fubangzhu));
		
		EasemobChatMessage.sendChatMessage(emobIds, toupiaoxiaoxi.getNickname(), "新任帮主（班底）上任了", ext);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private  String getBangzhuAndFubangzhuNicknames(List<Users> bangzhu, List<Users> fubangzhu) {
		String result = "{";
		result += getListNicknames(bangzhu, "bangzhu");
		result += ",";
		result += getListNicknames(fubangzhu, "fubangzhu");
		result += "}";
		return result;
	}

	private String getListNicknames(List<Users> list, String str) {
		String result = "";
		if(CollectionUtils.isEmpty(list)){
			return result;
		}
		for (int i = 0; i < list.size() - 1; i++) {
			result += "\"" + str +(i+1)+ "\":\"" + list.get(i).getNickname() + "\",";
		}
		result += "\"" + str +((list.size() - 1)==0?"":list.size())+ "\":\"" + list.get(list.size() - 1).getNickname() + "\"";
		return result;
	}
	
	public void startMessage(){
		List<Integer> list = communitiesDao.getCommunityIdList();
		SystemProperties properties = SystemProperties.getInstance();
		String username = properties.get("election.username");
		try {
			Users toupiaoxiaoxi = userService.getUserByName(username);// 获取投票消息用户
			for (Integer communityId : list) {
				List<String> usersList = userService.getEmobIdList(communityId,"owner");// 根据小区id获取该小区所有用户，即为发送消息对象
				String[] emobIds = new String[usersList.size()];
				for(int i=0;i<usersList.size();i++){
					emobIds[i]=usersList.get(i);
				}
			Map<String, Object> ext = new HashMap<String, Object>();
			ext.put("timestamp", (int) (System.currentTimeMillis() / 1000l));
			ext.put("avatar", toupiaoxiaoxi.getAvatar());
			ext.put("nickname", toupiaoxiaoxi.getNickname());
			ext.put("CMD_CODE", 702);
			ext.put("title", "下月帮主竞选开始了");
			ext.put("content", "下月新的帮主已经开始竞选啦，赶快去拉票或者投票吧。");
			
			EasemobChatMessage.sendChatMessage(emobIds, toupiaoxiaoxi.getNickname(), "下月帮主竞选开始了", ext);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}