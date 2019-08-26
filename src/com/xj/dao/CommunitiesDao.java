package com.xj.dao;






import java.util.List;
import java.util.Map;

import com.xj.bean.Communities;
import com.xj.bean.CommunityAndCity;
import com.xj.bean.CommunityFeature;
import com.xj.bean.IdCount;
import com.xj.bean.PartnerPermission;
import com.xj.bean.PublicizePhotos;
import com.xj.bean.PublishPrice;
import com.xj.bean.tvstatistics.CommunitiesSequence;


/**
 * 小区信息dao
 * @author Administrator
 *
 */
public interface CommunitiesDao extends MyBaseDao {
	/**
	 * 根据小区id获取内容
	 * @param communityId
	 * @return
	 */
    public Communities getCommunities(Integer communityId) throws Exception;
    
    /**
     * 修改小区信息
     * @param communities
     * @return
     * @throws Exception
     */
    public boolean updateCommunities(Communities communities) throws Exception;
    
    
    public boolean deleteCommunities(Integer communityId) throws Exception;
    
    public int addCommunities(Communities communities) throws Exception;
    
    public List<Communities> getCommunitiesByCityId(Integer cityId) throws Exception;
    
    public List<Communities> getCommunityList() throws Exception;
    
    public CommunityAndCity getCommunityAndCity(Integer userId) throws Exception;

	public List<Communities> getListCommunity(Integer communityId ) throws Exception;

	/**
	 * 获取小区属性
	 * @param communityId
	 */
	public List<CommunityFeature> getCommunityFeature(Integer communityId)throws Exception;

	public List<Communities> getListCommunityEmobId(String emobId)throws Exception;

	public List<Communities> getListCommunityQ()throws Exception;

	public Integer addPartnerPermission(PartnerPermission partnerPermission)throws Exception;

	public Integer delPartnerPermission(String emobId, Integer communityId)throws Exception;

	public boolean addPublicizePhotos(PublicizePhotos publicizePhotos)throws Exception;

	public PublicizePhotos getPublicizePhotos(Integer communityId)throws Exception;

	public List<CommunitiesSequence> communitiesSequence()throws Exception;

	public Integer addPublishPrice(PublishPrice publishPrice)throws Exception;

	public PublishPrice getPublishPrice(Integer communityId)throws Exception;

	public Map<String, Integer> getCities();

	public List<Communities> getCityCommunities(Integer cityId) throws Exception;
    
	/**
	 * 获取所有的小区id
	 * @return
	 */
	public List<Integer> getCommunityIdList();
	
	//获取小区更新消息
	public List<IdCount> getCommunityUpdate(Integer lastQuitTime) throws Exception;
	
	/**
	 * 根据小区id获取小区坐标
	 * @param communityId
	 * @return
	 */
	public Map<String, Float> getCommunityLocation(Integer communityId);
	
	
	/**
	 * 根据小区id获取 首页背景图
	 * @param communityId
	 * @return
	 */
	public String getHomePic(Integer communityId);

	/**
	 * 保存或则更新首页背景图
	 * @param communityId
	 * @param posterFile
	 */
	public void saveOrUpdateHomePic(Integer communityId, String posterFile);

}
