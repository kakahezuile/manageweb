package com.xj.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xj.bean.Activities;
import com.xj.bean.BonuscoinHistory;
import com.xj.bean.BonuscoinManager;
import com.xj.bean.CommunityId;
import com.xj.bean.DetailVO;
import com.xj.bean.IdCount;
import com.xj.bean.LifeCircle;
import com.xj.bean.LifeCircleBean;
import com.xj.bean.LifeCircleDetail;
import com.xj.bean.LifeCircleDetailList;
import com.xj.bean.LifeCircleGet;
import com.xj.bean.LifeCircleGetAll;
import com.xj.bean.LifeCircleHotTop;
import com.xj.bean.LifeCircleNumer;
import com.xj.bean.LifeCircleSelect;
import com.xj.bean.LifeCircleTips;
import com.xj.bean.LifeCircleTop;
import com.xj.bean.LifeCircleTopVO;
import com.xj.bean.LifeCircleVO;
import com.xj.bean.LifePhoto;
import com.xj.bean.LifePraise2;
import com.xj.bean.LifePraiseContent;
import com.xj.bean.LifeSensitive;
import com.xj.bean.NewTips;
import com.xj.bean.Page;
import com.xj.bean.ScanningTime;
import com.xj.bean.Shops;
import com.xj.bean.TipsUsers;
import com.xj.bean.UserPercent;
import com.xj.bean.UsersApp;
import com.xj.bean.life.Favorite;
import com.xj.dao.BonusCoinDao;
import com.xj.dao.BonuscoinManagerDao;
import com.xj.dao.LifeCircleDao;
import com.xj.dao.LifeCircleDetailDao;
import com.xj.dao.LifePhotoDao;
import com.xj.dao.LifePraiseDao;
import com.xj.dao.MyUserDao;
import com.xj.dao.MyUserDao2;
import com.xj.dao.ShopsDao;

@Service("lifeCircleService")
public class LifeCircleService {
	
	@Autowired
	private LifeCircleDao lifeCircleDao;
	
	@Autowired
	private LifePhotoDao lifePhotoDao;
	
	@Autowired
	private LifeCircleDetailDao lifeCircleDetailDao;
	
	@Autowired
	private ActivityService activityService;
	
	@Autowired
	private MyUserDao myUserDao;
	
	@Autowired
	private LifePraiseDao lifePraiseDao;
	
	@Autowired
	private BonuscoinManagerDao bonuscoinManagerDao;
	
	@Autowired
	private BonusCoinDao bonusCoinDao;
	
	@Autowired
	private  ShopsDao shopsDao;
	
	@Autowired
	private MyUserDao2 myUserDao2;
	
	public Integer getLifeCircleNum(Integer communityId , String emobId , Integer time) throws Exception{ // 获取生活圈最新推送数
		int result = 0;
		LifeCircleNumer lifeCircleNumer = lifeCircleDao.getLifeCircleNumber(communityId, time);
		if(lifeCircleNumer != null && lifeCircleNumer.getSum() != null){
			result = lifeCircleNumer.getSum();
		}
		LifeCircleNumer lifeCircleNumer2 = lifeCircleDetailDao.getLifeCircleNumber(emobId, time);
		if(lifeCircleNumer2 != null){
			if(lifeCircleNumer2.getSum() != null && lifeCircleNumer2.getSum() > result){
				result = lifeCircleNumer2.getSum();
			}
		}
		LifeCircleNumer lifeCircleNumer3 = lifePraiseDao.getLifeCircleNumber(emobId, time);
		if(lifeCircleNumer3 != null){
			if(lifeCircleNumer3.getSum() != null && lifeCircleNumer3.getSum() > result){
				result = lifeCircleNumer3.getSum();
			}
		}
		return result;
	}
	
	public LifeCircleHotTop getHotTop(Integer start , Integer end , String emobId , Integer communityId) throws Exception{ // 生活圈点赞排行榜
		LifeCircleHotTop lifeCircleHotTop = new LifeCircleHotTop();
		int userCharacterValues = lifePraiseDao.getUserCharacterValues(start, end, emobId , communityId);
		lifeCircleHotTop.setCharacterValues(userCharacterValues);
		int top = lifePraiseDao.getUserCount(start, end, userCharacterValues , communityId);
		List<LifeCircleTopVO> list = lifePraiseDao.getLifeTop(start, end , communityId);
		lifeCircleHotTop.setList(list);
		lifeCircleHotTop.setTop(top);
		return lifeCircleHotTop;
	}
	
	
	public Page<LifeCircle> getLifeCircles(Integer communityId , String emobId , Integer pageNum , Integer pageSize,int appVersion) throws Exception{ // 生活圈获取接口
		Page<LifeCircle> page;
		if(appVersion>=123){
			page = lifeCircleDao.getLifeCircles(communityId, emobId, pageNum, pageSize, 10);
		}else{
			 page = lifeCircleDao.getLifeCircles(communityId, emobId, pageNum, pageSize, 10,appVersion);
		}
		
		List<LifeCircle> list = page.getPageData();
		LifeCircle lifeCircle = null;
		Integer lifeCircleId = null;
		List<LifePhoto> lifePhotos = null;
		List<LifeCircleDetail> lifeCircleDetails = null;
		int len = list.size();
		for(int i = 0 ; i < len ; i++){
			lifeCircle = list.get(i);
			lifeCircleId = lifeCircle.getLifeCircleId();
			lifePhotos = lifePhotoDao.getLifephotos(lifeCircleId);
			list.get(i).setLifePhotos(lifePhotos);
			lifeCircleDetails = lifeCircleDetailDao.getLifeCircleDetails(lifeCircleId);
			list.get(i).setLifeCircleDetails(lifeCircleDetails);
		}
		return page;
	}
	
	public Page<LifeCircle> getLifeCircles2(String text , Integer pageNum , Integer pageSize) throws Exception{ // 生活圈获取接口
		Page<LifeCircle> page = lifeCircleDao.getLifeCircleByText(text, pageNum, pageSize, 10);
		List<LifeCircle> list = page.getPageData();
		LifeCircle lifeCircle = null;
		Integer lifeCircleId = null;
		List<LifePhoto> lifePhotos = null;
		List<LifeCircleDetail> lifeCircleDetails = null;
		int len = list.size();
		for(int i = 0 ; i < len ; i++){
			lifeCircle = list.get(i);
			lifeCircleId = lifeCircle.getLifeCircleId();
			lifePhotos = lifePhotoDao.getLifephotos(lifeCircleId);
			list.get(i).setLifePhotos(lifePhotos);
			lifeCircleDetails = lifeCircleDetailDao.getLifeCircleDetails(lifeCircleId);
			list.get(i).setLifeCircleDetails(lifeCircleDetails);
		}
		return page;
	}
	
	public boolean deleteLifeCircle(Integer lifeCircleId) throws Exception{
		boolean result = false;
		LifeCircle lifeCircle = new LifeCircle();
		lifeCircle.setStatus("delete");
		lifeCircle.setLifeCircleId(lifeCircleId);
		result = lifeCircleDao.updateLifeCircle(lifeCircle);
		return result;
	}
	
	/**
	 * 获取生活圈内容
	 * @param communityId
	 * @param lifeCircleId
	 * @return
	 * @throws Exception
	 */
	public LifeCircle getLifeCircle(Integer communityId , Integer lifeCircleId) throws Exception{
		LifeCircle lifeCircle = lifeCircleDao.getLifeCircle(lifeCircleId);
		if(lifeCircle != null){
			String emobId = lifeCircle.getEmobId();
			UserPercent userPercent = myUserDao.getUserPercent(communityId , emobId);
			if(userPercent != null){
				Double characterPercent = (double)userPercent.getCharacterCount() / (double)userPercent.getUserCount() * (double)100;
				if(characterPercent != null){
					if(userPercent.getUserCount() == 0){
						lifeCircle.setCharacterPercent(new Double(0));
					}else{
						lifeCircle.setCharacterPercent(characterPercent);
					}
				}
			}
		}
		
		List<LifePhoto> lifePhotos = null;
		List<LifeCircleDetail> lifeCircleDetails = null;
		if(lifeCircle != null){
			lifePhotos = lifePhotoDao.getLifephotos(lifeCircleId);
			lifeCircle.setLifePhotos(lifePhotos);
			lifeCircleDetails = lifeCircleDetailDao.getLifeCircleDetails(lifeCircleId);
			lifeCircle.setLifeCircleDetails(lifeCircleDetails);
			List<LifePraiseContent> lifePraises = lifePraiseDao.getLifePraises(lifeCircleId);
			lifeCircle.setLifePraises(lifePraises);
		}
		return lifeCircle;
	}
	
	/**
	 * 添加生活圈
	 * @param lifeCircle
	 * @return
	 * @throws Exception
	 */
	public Integer addLifeCircleService(LifeCircle lifeCircle) throws Exception{
		String emobGroupId = null;
		if(lifeCircle.getIsCreate() == 1){ // 创建群
			String title = lifeCircle.getLifeContent();
			if(title.length() > 14){
				title = title.substring(0,14);
			}
			Activities activities = new Activities();
			activities.setActivityTitle(title);
			activities.setCreateTime((int)(System.currentTimeMillis() / 1000));
			activities.setActivityTime((int)(System.currentTimeMillis() / 1000));
			activities.setActivityDetail(title);
			activities.setCommunityId(lifeCircle.getCommunityId());
			activities.setEmobIdOwner(lifeCircle.getEmobId());
			activities.setType("circle");
			activities.setStatus("ongoing");
			emobGroupId = activityService.addCircleActivity(activities);
		}
		lifeCircle.setEmobGroupId(emobGroupId);
		return lifeCircleDao.addLifeCircle(lifeCircle);
	}
	
	/**
	 * 分享朋友圈
	 * @param lifeCircle
	 * @return
	 * @throws Exception
	 */
	public boolean lifeCircleShare(String serial,LifeCircle lifeCircle) throws Exception{
		Integer id = addLifeCircleService(lifeCircle);
		List<LifePhoto> list = lifeCircle.getLifePhotos();
		if(list != null && list.size() > 0){
			for (LifePhoto lifePhoto2 : list) {
				lifePhoto2.setCreateTime((int)(System.currentTimeMillis()/1000));
				lifePhoto2.setLifeCircleId(id);
				lifePhotoDao.addLifePhoto(lifePhoto2);
			}
			
			/**
			 * 分享成功加帮帮币
			 */
			String emobIdShop = lifeCircle.getEmobIdShop();
			Shops shops = shopsDao.getShopsByEmobId(emobIdShop);
			BonuscoinManager bonuscoinManager = bonuscoinManagerDao.getBonuscoinManager(new  Integer(shops.getSort()));
			Integer bonuscoinCount = bonuscoinManager.getBonuscoinCount();
			myUserDao2.updateUserBonusCoin(lifeCircle.getEmobId(), bonuscoinCount);
			
			/**
			 * 添加历史信息
			 */
			BonuscoinHistory bonuscoinHistory= new BonuscoinHistory();
			bonuscoinHistory.setBonuscoinCount(bonuscoinCount);
			bonuscoinHistory.setCreateTime((int)(System.currentTimeMillis()/1000));
			bonuscoinHistory.setBonuscoinSource(shops.getSort());
			bonuscoinHistory.setEmobId(lifeCircle.getEmobId());
			
			bonusCoinDao.save(bonuscoinHistory, null);
			
//			ordersDao.updateUsersShare(serial);
			
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 添加生活圈明细
	 * @param lifeCircleDetail
	 * @return
	 * @throws Exception
	 */
	public Integer addLifeCircleDetail(LifeCircleDetail lifeCircleDetail) throws Exception{
		int resultId = lifeCircleDetailDao.addLifeCircleDetail(lifeCircleDetail);
		if(resultId > 0){
			boolean update = lifeCircleDao.updateContentSum(lifeCircleDetail.getLifeCircleId());
			if(!update){
				throw new Exception();
			}
		}
		return resultId;
	}
	
	/**
	 * 根据emobId获取生活圈内容明细
	 * @param communityId
	 * @param emobId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public LifeCircleBean getCircleSelf(Integer communityId , String emobId , Integer pageNum , Integer pageSize) throws Exception{
		LifeCircleBean lifeCircleBean = new LifeCircleBean();
		UserPercent userPercent = myUserDao.getUserPercent(communityId , emobId);
		if(userPercent != null){
			Double characterPercent = (double)userPercent.getCharacterCount() / (double)userPercent.getUserCount() * (double)100;
			if(characterPercent != null){
				if(userPercent.getUserCount() == 0){
					lifeCircleBean.setCharacterPercent(new Double(0));
				}else{
					lifeCircleBean.setCharacterPercent(characterPercent);
				}
			}
		}
		
		UsersApp users = myUserDao.getUserByEmobId(emobId);
		if(users != null){
			lifeCircleBean.setAvatar(users.getAvatar());
			lifeCircleBean.setCharacterValues(users.getCharacterValues());
			lifeCircleBean.setNickname(users.getNickname());
		}
		Page<LifeCircleVO> list = getLifeCircleVo(emobId, pageNum, pageSize);
		lifeCircleBean.setList(list);
		return lifeCircleBean;
	}
	
	public Page<LifeCircleVO> getLifeCircleVo(String emobId , Integer pageNum , Integer pageSize) throws Exception{
		Page<LifeCircleVO> page = lifeCircleDao.getLifeCircleVos(emobId, pageNum, pageSize, 10);
		List<LifeCircleVO> list = page.getPageData();
//		Map<String, List<LifeCircleVO>> map = new HashMap<String, List<LifeCircleVO>>();
////		Iterator<LifeCircleVO> iterator = list.iterator();
		LifeCircleVO lifeCircleVO = null;
//		String timestamp = "";
		Integer lifeCircleId = 0;
		int len = 0;
		if(list != null){
			len = list.size();
		}
		for(int i = 0 ; i < len ; i++){
			lifeCircleVO = list.get(i);
			lifeCircleId = lifeCircleVO.getLifeCircleId();
			List<LifePhoto> listPhotos = lifePhotoDao.getLifephotos(lifeCircleId);
			list.get(i).setListPhotos(listPhotos);
			
		}
		page.setPageData(list);
		return page;
	}
	
	public LifeCircleTips getTips(Integer communityId ,String emobId , Integer time) throws Exception{
		List<NewTips> tipsList = new ArrayList<NewTips>();
		LifeCircleTips lifeCircleTips = new LifeCircleTips();
		UsersApp users = myUserDao.getUserByEmobId(emobId);
		lifeCircleTips.setAvatar(users.getAvatar());
		lifeCircleTips.setNickname(users.getNickname());
		lifeCircleTips.setCharacterValues(users.getCharacterValues());
		lifeCircleTips.setEmobId(emobId);
		UserPercent userPercent = myUserDao.getUserPercent(communityId , emobId);
		if(userPercent != null){
			Double characterPercent = (double)userPercent.getCharacterCount() / (double)userPercent.getUserCount() * (double)100;
			if(characterPercent != null){
				if(userPercent.getUserCount() == 0){
					lifeCircleTips.setCharacterPercent(new Double(0));
				}else{
					lifeCircleTips.setCharacterPercent(characterPercent);
				}
			}
		}
		
		NewTips newTips = new NewTips();
		List<LifePraise2> lifePraises = lifePraiseDao.getLifePraises(emobId, time);
		Set<String> set = new HashSet<String>();
		Set<Integer> idSet = new HashSet<Integer>();
		List<DetailVO> details = lifeCircleDetailDao.getDetailVO(emobId, time);
		LifePraise2 lifePraise2 = null;
		DetailVO detailVO = null;
		int len = lifePraises.size();
		int length = details.size();
		
		if(lifePraises != null && len > 0){
			time = lifePraises.get(0).getCreateTime();
			for(int i = 0 ; i < len ;i++){
				lifePraise2 = lifePraises.get(i);
				Integer lifeCircleId = lifePraise2.getLifeCircleId();
				if(!idSet.contains(lifeCircleId)){
					newTips = new NewTips();
					if(newTips.getUsers() == null){
						List<TipsUsers> list = new ArrayList<TipsUsers>();
						newTips.setUsers(list);
						
					}
					newTips.setLifeCircleId(lifeCircleId);
					tipsList.add(newTips);
				}
				idSet.add(lifeCircleId);
			}
		}
		if(details != null && length > 0){
			if(details.get(length - 1).getCreateTime() > time){
				time = details.get(length - 1).getCreateTime();
			}
			for(int i = 0 ; i < length ; i++){
				detailVO = details.get(i);
				Integer lifeCircleId = detailVO.getLifeCircleId();
				if(!idSet.contains(lifeCircleId)){
					newTips = new NewTips();
					if(newTips.getUsers() == null){
						List<TipsUsers> list = new ArrayList<TipsUsers>();
						newTips.setUsers(list);
						
					}
					newTips.setLifeCircleId(lifeCircleId);
					tipsList.add(newTips);
				}
			}
		}
		lifeCircleTips.setTime(time);
		int len2 = tipsList.size();
		
		for(int i = 0 ; i < len2 ;i++){
			newTips = tipsList.get(i);
			set.clear();
			for(int j = 0 ; j < len ; j++){
				lifePraise2 = lifePraises.get(j);
				if(newTips.getLifeCircleId() == lifePraise2.getLifeCircleId()){
					if(newTips.getPraiseSum() == null){
						tipsList.get(i).setPraiseSum(1);
					}else{
						tipsList.get(i).setPraiseSum(newTips.getPraiseSum() + 1);
					}
					String nickname = lifePraise2.getNickname();
					if(!set.contains(nickname)){
						TipsUsers tipsUsers = new TipsUsers();
						tipsUsers.setAvatar(lifePraise2.getAvatar());
						tipsUsers.setNickname(nickname);
						tipsList.get(i).getUsers().add(tipsUsers);
					}
					set.add(nickname);
				}
			}
			
			for(int j = 0 ; j < length ; j++){
				detailVO = details.get(j);
				if(newTips.getLifeCircleId() == detailVO.getLifeCircleId()){
					if(newTips.getContentSum() == null){
						tipsList.get(i).setContentSum(1);
					}else{
						tipsList.get(i).setContentSum(newTips.getContentSum() + 1);
					}
					String nickname = detailVO.getNickname();
					if(!set.contains(nickname)){ 
						TipsUsers tipsUsers = new TipsUsers();
						tipsUsers.setAvatar(detailVO.getAvatar());
						tipsUsers.setNickname(nickname);
						tipsList.get(i).getUsers().add(tipsUsers);
					}
					set.add(nickname);
				}
			}
		}
		
		lifeCircleTips.setList(tipsList);
		return lifeCircleTips;
	}

	public LifeCircleTop getLifeCircleTop(Integer communityId, Integer startTimeInt, Integer endTimeInt) throws Exception{
		return lifeCircleDao.getLifeCircleTop(communityId,startTimeInt,endTimeInt);
	}

	public List<LifeCircleTop> getLifeCircleList(Integer communityId, Integer startTimeInt, Integer endTimeInt,List<Integer> sqlTime) throws Exception{
		return lifeCircleDao.getLifeCircleList(communityId,startTimeInt,endTimeInt,sqlTime);
	}

	public List<LifeCircleSelect> selectLifeCircleList(Integer communityId, Integer startTimeInt, Integer endTimeInt,String type) throws Exception{
		return lifeCircleDao.selectLifeCircleList(communityId,startTimeInt,endTimeInt,type);
	}
	
	public List<LifeCircleDetailList> getLifeCircleDetail(String emob_id_to) throws Exception{
		return lifeCircleDao.getLifeCircleDetail(emob_id_to);
	}
	
	public LifeCircleSelect getLifeCireDelit(String life_circle_id) throws Exception{
		return lifeCircleDao.getLifeCireDelit(life_circle_id);
	}

	public ScanningTime selectLifeCirrleDetailId() throws Exception{
		return lifeCircleDao.selectLifeCirrleDetailId();
	}

	public List<LifeSensitive> selectLifeCircleListTime(Integer lifeCirrleDetailId) throws Exception{
		return lifeCircleDao.selectLifeCircleListTime(lifeCirrleDetailId);
	}
	public Integer addLifeSensitive(LifeSensitive lifeSensitive) throws Exception{
		return lifeCircleDao.addLifeSensitive(lifeSensitive);
	}

	public Page<LifeSensitive> getSensitive(Integer communityId,Integer pageNum, Integer pageSize) throws Exception{
		return lifeCircleDao.getSensitive(communityId,pageNum,pageSize);
	}

	public boolean pingbi(Integer facilities)  throws Exception{
		return lifeCircleDao.pingbi(facilities);
	}

	public boolean upStatus(Integer id,String status)throws Exception{
		return lifeCircleDao.upStatus(id,status);
	}

	public Integer addLifePhoto(LifePhoto lifePhoto) throws Exception{
		return lifeCircleDao.addLifePhoto(lifePhoto);
	}

	public Page<LifeCircleGetAll> getLifeCircleList(Integer communityId,Integer pageNum, Integer pageSize) throws Exception{
		return lifeCircleDao.getLifeCircleList(communityId,pageNum,pageSize);
	}

	public Integer addFavorite(Favorite favorite)  throws Exception{
		return lifeCircleDao.addFavorite(favorite);
	}
	
	public Integer delFavorite(Integer favorite_id) throws Exception{
		return lifeCircleDao.delFavorite(favorite_id) ;
	}

	public Page<LifeCircleGet> getFavoriteLifeCire(Integer pageNum, Integer pageSize)  throws Exception{
		return lifeCircleDao.getFavoriteLifeCire(pageNum,pageSize);
	}

	public Integer delectLifePhoto(Integer life_photo_id) throws Exception{
		return lifeCircleDao.delectLifePhoto(life_photo_id) ;
	}
	
	public List<CommunityId>  getExisting(Integer lifeCircleId) throws Exception{
		return lifeCircleDao.getExisting(lifeCircleId);
	}
	
	public List<IdCount> getLifeCircleUpdateCount(Integer communityId,Integer lastQuitTime) throws Exception{
		return lifeCircleDao.getLifeCircleUpdateCount(communityId, lastQuitTime);
	}
	
	
}