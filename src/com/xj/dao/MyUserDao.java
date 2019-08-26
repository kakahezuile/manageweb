package com.xj.dao;

import java.util.List;
import java.util.Set;

import com.xj.bean.Messages;
import com.xj.bean.MoralStatistics;
import com.xj.bean.Page;
import com.xj.bean.TryOut;
import com.xj.bean.UserPartner;
import com.xj.bean.UserPercent;
import com.xj.bean.UserRegister;
import com.xj.bean.Users;
import com.xj.bean.UsersApp;
import com.xj.stat.bean.parameter.nearby.CrazySales;
import com.xj.stat.bean.resource.nearby.CrazySalesShop;

public interface MyUserDao extends MyBaseDao {
	
	/**
	 * 获取用户信息
	 * @param userName 用户名
	 * @return
	 * @throws Exception
	 */
	Users getNameByUser(String userName) throws Exception;
	
	/**
	 * 保存用户信息
	 * @param userBean
	 * @return
	 * @throws Exception
	 */
	Integer insert(Users userBean) throws Exception;
	
	/**
	 * 修改用户信息
	 * @param userBean
	 * @return
	 * @throws Exception
	 */
	boolean update(Users userBean) throws Exception;
	
	List<Users> getUserList(Integer communityId) throws Exception;
	
	List<Users> getUserList(Integer communityId,String rule) throws Exception;
	
	List<String> getEmobIdList(Integer communityId,String role) throws Exception;
	
	UsersApp getUserByEmobId(String emobId) throws Exception;
	
	Users getUserByUserId(Integer userId) throws Exception;
	
	List<Users> getShopList() throws Exception;
	
	Users getUserByPhone(String phone) throws Exception;
	
	Integer getUserCount() throws Exception;
	
	List<Users> getUserList() throws Exception;
	
	List<Users> getUsers(Integer communityId,String phone,String room,String userFloor)throws Exception;
	
	boolean updateUser(Users users) throws Exception;

	List<String> selectUserFloor(Integer communityId)throws Exception;

	List<String> selectUserNuit(Integer communityId, String userFloor)throws Exception;

	List<String> selectUserRoom(Integer communityId, String userFloor, String userNuit)throws Exception;

	List<Users> selectFloorUnitUser(Integer communityId, String userFloor, String userUnit, String room)throws Exception;

	UserRegister getUserRegister(Integer communityId)throws Exception;
	
	UserRegister getUserRegisterTime(Integer communityId,Integer sta,Integer end)throws Exception;

	List<UserRegister> getUserRegisterTimeList(Integer communityId,Integer startTimeInt, Integer endTimeInt)throws Exception;

	UserRegister getUserRegisterEndTime(Integer communityId, Integer endTimeInt)throws Exception;

	List<UserRegister> getUserRegisterTimeGroup(Integer communityId, Integer startTimeInt, Integer endTimeInt,List<Integer> endList)throws Exception;

	List<TryOut> getListTryOut()throws Exception;
	
	boolean updateUserByEmobId(Users users) throws Exception;
	
	UserPercent getUserPercent(Integer communityId , String emobId) throws Exception;

	MoralStatistics getMoralStatistics(Integer communityId)throws Exception;

	List<Users>  getMoralList(Integer communityId)throws Exception;
	
	List<Users> getUsersByEquipmentVersion(String version) throws Exception;

	List<Users> getListUsers(Integer communityId, Integer startTime, Integer endTime)throws Exception;

	List<Users> getListReg(Integer communityId)throws Exception;
	
	Users getUserByCommunityId(Integer communityId , String username) throws Exception;

	List<Users> registerList(Integer communityId)throws Exception;

	List<UserPartner>  getUserRegisterCommunity(String emobId)throws Exception;

	@SuppressWarnings("rawtypes")
	List<com.xj.stat.po.Users> getRegisterActiveUsersDay(Set set, Integer communityId)throws Exception;

	@SuppressWarnings("rawtypes")
	List<com.xj.stat.po.Users> getRegisterActiveUsersDay2(Set set, Integer communityId)throws Exception;

	boolean upLiveness(Liveness liveness)throws Exception;

	Liveness getLiveness(Integer communityId)throws Exception;

	Page<CrazySalesShop> selectNearbyCrazySalesShop(CrazySales crazySales)throws Exception;

	void insertMessage(Messages ms)throws Exception;

	List<Users> getListTryOutUser(Integer communityId)throws Exception;

	List<Users> getSimpleUser(Integer communityId)throws Exception;
	
	/**
	 * 获取帮主
	 * @param integer
	 * @return
	 */
	List<Users> getCommunityCompeteBangzhuUser(Integer integer,Integer invitCount,List<Users> users,int limit);
	
	/**
	 * 获取小区的职位信息
	 * @param communityId
	 * @param string
	 * @return
	 */
	List<String> getCommunityBangzhuUser(Integer communityId, String string);
	
	/**
	 * 更新用户帮内级别
	 * @param communityId
	 */
	void updateAllUserGrade(Integer communityId,String grade);
	
	/**
	 * 更新用户角色
	 * @param string
	 * @param bangzhu
	 */
	int updateUsersGrade(String string, List<Users> bangzhu);
	
	/**
	 * 更新用户帮帮币信息，根据emobId
	 * @param lastbangzhu
	 * @param i
	 * @return 
	 */
	int updateUsersBonusCoin(List<String> emobIds, int i);
	
	/**
	 * 获取参与竞选的用户
	 * @param integer
	 * @param invitCount
	 * @param users
	 * @param limit
	 * @return
	 */
	List<Users> getCommunityCompeteBangzhuUserByEmobId(Integer integer, Integer invitCount, List<String> users, int limit);

	/**
	 * 参与竞争长老的用户
	 * @param communityId
	 * @param u
	 * @param j
	 * @return
	 */
	List<Users> getCommunityCompeteZhanglaoUser(Integer communityId, List<Users> u, int j);
	
	/**
	 * 获取剩余的长老职位
	 * @param communityId
	 * @param u2
	 * @param limit
	 * @return
	 */
	List<Users> getCommunityCompetezhanglaoUserByEmobId(Integer communityId, List<String> u2, int limit);
	
	/**
	 * 获取用户地址，包括小区名称，楼号，单元号，房间号
	 * @param emobId
	 * @return
	 */
	UsersApp getUserAddress(String emobId);
	
	/**
	 * 获取某小区在一个时间范围内的安装用户
	 * @param communityId
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	List<Users> getSetupUsersWithoutTest(Integer communityId, Integer startTime, Integer endTime) throws Exception;

	/**
	 * 分页获取用户
	 * @param communityId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	Page<Users> getUserByCommunityIdWithPage(Integer communityId,
			Integer pageNum, Integer pageSize);

	/**
	 * 根据条件查询用户
	 * @param communityId
	 * @param pageNum
	 * @param pageSize
	 * @param query
	 * @return
	 */
	Page<Users> getUserByCommunityIdWithPageAndCondition(Integer communityId,
			Integer pageNum, Integer pageSize, String query);
	
	/**
	 * @Title: getElectionWinners
	 * @Description: 获取各小区领导班子成员
	 * @param 
	 * @return List<Users>
	 * @throws 
	 * @author: 刘振
	 * @date: 2015-11-23
	 */
	List<Users> getElectionWinners(Integer communityId,String month,Integer limitStart,Integer limitEnd)throws Exception;

	
	List<Users> getIosUserEquipmentTokenByCommunity(Integer communityId);

	List<Users> getUserEquipmentAliasByCommunity(Integer communityId, String string);

	void updateUserDeviceTokenToNull(List<String> tokens);

	List<Users> getUsersByIds(List<Integer> userId);
}