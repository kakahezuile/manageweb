package com.xj.stat.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xj.bean.Page;
import com.xj.bean.PushMessage;
import com.xj.bean.PushStatistics;
import com.xj.bean.StatPushMessageBean;
import com.xj.dao.MyUserDao2;
import com.xj.mongo.MyMessageMongo;
import com.xj.stat.dao.BullentinDao;
import com.xj.stat.po.Bulletin;
import com.xj.stat.po.UserVo;
import com.xj.stat.service.StatPushMessageService;
@Service
public class StatPushMessageServiceImpl implements StatPushMessageService {
	@Autowired
	private BullentinDao bullentinDao;
	
	@Autowired
	private MyMessageMongo MyMessageMongo;
	
	@Autowired
	private MyUserDao2 myUserDao2;
	
	@Override
	public List<StatPushMessageBean> notificationPushMessage(
			Integer communityId, Integer cmdCode, int pageNum) throws Exception {
		Page<StatPushMessageBean> page = new  Page<StatPushMessageBean>();
	
		List<PushStatistics> PushStatisticss = MyMessageMongo.statMessageByCommunityAndCode(communityId,cmdCode);
		
		Set<PushStatistics> set = new  TreeSet<PushStatistics>(new  Comparator<PushStatistics>() {

			@Override
			public int compare(PushStatistics o1, PushStatistics o2) {
				
				return (int) (-o1.getMessageId()+o2.getMessageId());
			}
			});
		
		set.addAll(PushStatisticss);
		PushStatistics[] array = set.toArray(new PushStatistics[]{});
		
		

		List<StatPushMessageBean> pbs = new  ArrayList<StatPushMessageBean>();
		
		int size = page.getPageSize();
		int startNum = (pageNum-1)*size;

		
			for (int i = startNum; i < size+startNum && array.length>=size+startNum; i++) {
				PushStatistics pushStatistics = array[i];
				StatPushMessageBean pb = new StatPushMessageBean(); 
				String content = pushStatistics.getContent();
				String title = pushStatistics.getTitle();
				int failNum = pushStatistics.getSum();
				int successNum = pushStatistics.getUnsum();
				Long messageId = pushStatistics.getMessageId();
				pb.setContext(title+":"+content);
				pb.setFailNum(failNum);
				pb.setSuccessNum(successNum);
				pb.setPushNum(failNum+successNum);
				pb.setMessageId(messageId);
				pb.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(messageId)));
				
				pbs.add(pb);
			}			
	/*	
		for (PushStatistics pushStatistics : set) {
			StatPushMessageBean pb = new StatPushMessageBean(); 
			String content = pushStatistics.getContent();
			String title = pushStatistics.getTitle();
			int failNum = pushStatistics.getSum();
			int successNum = pushStatistics.getUnsum();
			Long messageId = pushStatistics.getMessageId();
			pb.setContext(title+":"+content);
			pb.setFailNum(failNum);
			pb.setSuccessNum(successNum);
			pb.setPushNum(failNum+successNum);
			pb.setMessageId(messageId);
			pb.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(messageId)));
			
			pbs.add(pb);
			
			
		}*/
		return pbs;
	}


	@Override
	public Page<StatPushMessageBean> getAllBulletinByCommunityId(
			Integer communityId, int page, int pageSzie,Integer  cmdCode)  {
		
		
			int count = bullentinDao.getAllCountByCommunityId(communityId);
			Page<StatPushMessageBean> pb = new  Page<StatPushMessageBean>(page, count, pageSzie, 10);
			List<StatPushMessageBean> sbs = new ArrayList<StatPushMessageBean>();
			
			List<Bulletin> Bulletins =  bullentinDao.getAllBulletinByCommunityId(communityId,pb);
			for (Bulletin bulletin : Bulletins) {
				StatPushMessageBean sb = new StatPushMessageBean();
				Long messageId = bulletin.getMessageId();
				List<PushStatistics> pushStatistics = null;
				if(messageId!=null){
					pushStatistics = MyMessageMongo.getConsumeTimeByMessageId(messageId,cmdCode);
				}
				
				sb.setContext(bulletin.getTheme()+":"+ bulletin.getBulletinText());
				sb.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(bulletin.getCreateTime()*1000l)));
				
				if(pushStatistics!=null &&pushStatistics.size()>0){
					sb.setMessageId(messageId);
					PushStatistics pushStatistic = pushStatistics.get(0);
					sb.setPushNum(pushStatistic.getSum()+pushStatistic.getUnsum());
					sb.setSuccessNum(pushStatistic.getSum());
					sb.setFailNum(pushStatistic.getUnsum());
					
				}
				sbs.add(sb);
			}
			
			pb.setPageData(sbs);
	
		return pb;
	}
	
	
	
	/**
	 * 根据messageid查询,发送给了那些用户
	 * @param messageId
	 * @return
	 */
	public Page<UserVo> getBulletinsDetailsByMessageId(Integer communityId ,Long messageId,Integer page,Integer pageSize) {
		int start= (page-1)*pageSize;
		
		Map<String,Object> map = MyMessageMongo.getBulletinsDetailsByMessageId(communityId ,messageId,start,pageSize);
		
		Page<UserVo> pageUsers = new  Page<UserVo>(page, Integer.parseInt((String)map.get("count")), pageSize, 10);
		
		
		List<UserVo> users = new ArrayList<UserVo>();
		if(map!=null && map.size()>0){
			map.remove("count");
			Set<Entry<String,Object>> entrySet = map.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				String key = entry.getKey();
				Object[] value = (Object[]) entry.getValue();
				try {
					UserVo user = myUserDao2.getUserVoByEmobId(key);
					if(user!=null && value.length==4){
						user.setType((String)value[0]);
						user.setCount((Integer)value[1]);
						user.setTime(new Date((Long)value[2]*1000));
						user.setEquipment((String)value[3]);
						users.add(user);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
		pageUsers.setPageData(users);	
		return pageUsers;
	}


	@Override
	public Page<UserVo> getPushMessagesUsers(Integer communityId, int page,
			int pageSize,Integer  cmdCode) {
		
		List<PushStatistics> PushStatistics = MyMessageMongo.getUsersByCommunityId(communityId,page,pageSize,cmdCode);
		Page<UserVo> pageUsers = new  Page<UserVo>(page, PushStatistics.size(), pageSize, 10);
		List<UserVo> list = new  ArrayList<UserVo>();
		for (int i = pageUsers.getStartRow(); i < pageUsers.getPageSize()+pageUsers.getStartRow(); i++) {
			PushStatistics ps = PushStatistics.get(i);
			String emobId = ps.getEmobId();
			
			
			try {
				UserVo user = myUserDao2.getUserVoByEmobId(emobId);
				user.setSuccessNum(ps.getSum());
				user.setFailNmu(ps.getUnsum());
				list.add(user);
			} catch (Exception e) {
			
				e.printStackTrace();
			}
			
			
		}
		pageUsers.setPageData(list);
		return pageUsers;
	}


	@Override
	public Page<PushMessage> getPushUserDetail(Integer communityId, String emobId,
			int page, int pageSize,Integer  cmdCode) {
		Page<PushMessage> pushMessages = MyMessageMongo.getBulletinsDetailsEmobId(communityId,emobId, page, pageSize,cmdCode);
		
		return pushMessages;
	}




}
