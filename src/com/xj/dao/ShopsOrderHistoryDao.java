package com.xj.dao;

import java.util.List;

import com.xj.bean.BuyRanking;
import com.xj.bean.CommentsDetailList;
import com.xj.bean.CommentsStatistics;
import com.xj.bean.CommentsTop;
import com.xj.bean.ComplaintsDetailList;
import com.xj.bean.DeductMoney;
import com.xj.bean.OrderEmobId;
import com.xj.bean.Page;
import com.xj.bean.PayOff;
import com.xj.bean.ShopOrdersTime;
import com.xj.bean.ShopSupervision;
import com.xj.bean.ShopsClickDetail;
import com.xj.bean.ShopsOrderAmount;
import com.xj.bean.ShopsOrderDetailList;
import com.xj.bean.ShopsOrderHistory;
import com.xj.bean.ShopsStatisticsTop;
import com.xj.bean.TradingRecord;
import com.xj.bean.UserShops;

public interface ShopsOrderHistoryDao extends MyBaseDao {

	public Page<ShopsOrderHistory> getShopsOrdersHistoryList(String emobId,
			String sort, String action, Integer pageNum, Integer pageSize,
			Integer nvm) throws Exception;

	/**
	 * 快店统计 下单量列表
	 */
	public Page<ShopsOrderAmount> getQuickShopList(Integer communityId,
			String sort, Integer pageNum, Integer pageSize, Integer startTime,
			Integer endTime) throws Exception;

	/**
	 * 统计快店头部
	 */
	public ShopsStatisticsTop getQuickShopTop(Integer communityId, String sort,
			Integer startTime, Integer endTime) throws Exception;

	/**
	 * 快店统计 详情 下单量列表
	 */
	public Page<ShopsClickDetail> getQuickShopDetailList(String sort,
			Integer pageNum, Integer pageSize, Integer startTime,
			Integer endTime) throws Exception;

	/**
	 * 投诉 头部
	 */
	public CommentsTop getQuickShopCommentsTop(Integer communityId,
			String sort, Integer startTime, Integer endTime) throws Exception;

	/**
	 * 获取评价 列表
	 */
	public Page<CommentsStatistics> getQuickShopCommentsList(
			Integer communityId, String sort, Integer pageNum,
			Integer pageSize, Integer startTime, Integer endTime)
			throws Exception;

	/**
	 * 投诉详情页面
	 */
	public Page<CommentsDetailList> getQuickShopCommentsDetail(String shopId,
			Integer pageNum, Integer pageSize, Integer startTime,
			Integer endTime) throws Exception;

	/**
	 * 订单详情
	 */

	public Page<ShopsOrderDetailList> getQuickShopOrderDetail(String shopId,
			Integer pageNum, Integer pageSize, Integer startTimeInt,
			Integer endTimeInt) throws Exception;

	/**
	 * 投诉详情
	 */
	public Page<ComplaintsDetailList> getComplaintsDetailList(String shopId,
			Integer pageNum, Integer pageSize, Integer startTimeInt,
			Integer endTimeInt) throws Exception;

	/**
	 * 使用 量 时间段查询
	 */
	public Page<ShopsClickDetail> getUseAmountList(Integer communityId,
			String sort, Integer pageNum, Integer pageSize,
			Integer startTimeInt, Integer endTimeInt) throws Exception;

	/**
	 * 使用 量 时间段查询
	 */
	public Page<ShopsOrderDetailList> getTurnoverList(Integer communityId,
			String sort, Integer pageNum, Integer pageSize,
			Integer startTimeInt, Integer endTimeInt) throws Exception;

	/**
	 * 服务 时间段查询
	 */
	public Page<ShopOrdersTime> getTimeQuantumList(Integer communityId,
			String sort, Integer pageNum, Integer pageSize,
			Integer startTimeInt, Integer endTimeInt) throws Exception;

	/**
	 * 服务时间 头部
	 */
	public ShopOrdersTime getTimeQuantumTop(Integer communityId, String sort,
			Integer startTimeInt, Integer endTimeInt) throws Exception;

	///**
	// * 送水服务时间
	// */
	// public Page<ShopWaterTime> getWaterTime(String sort, Integer pageNum,
	// Integer pageSize, Integer startTime, Integer endTime)throws Exception;

	/**
	 * 结款
	 */
	public Page<PayOff> getPayOff(String sort, Integer pageNum,
			Integer pageSize, Integer startTime, Integer endTime, Integer myDate)
			throws Exception;

	/**
	 * 结款
	 */
	public Page<PayOff> getPayOff2(Integer communityId, String sort,
			Integer pageNum, Integer pageSize, Integer startTime,
			Integer endTime, Integer myDate) throws Exception;

	/**
	 * 扣款申请
	 */
	public Page<DeductMoney> getDeductMoney(String emobId, Integer pageNum,
			Integer pageSize, Integer startTime, Integer endTime)
			throws Exception;

	public Page<TradingRecord> getTradingRecord(String shopId, Integer pageNum,
			Integer pageSize, Integer startTimeInt, Integer endTimeInt)
			throws Exception;

	/**
	 * 结款历史
	 */
	public Page<TradingRecord> getReckoningHistory(Integer pageNum,
			Integer pageSize) throws Exception;

	public List<UserShops> getUserShop(String communityId, String sort,
			Integer startTimeInt, Integer endTimeInt) throws Exception;

	public List<OrderEmobId> getOrderEmobId(Integer communityId, String sort,
			Integer startTimeInt, Integer endTimeInt) throws Exception;

	public List<BuyRanking> getBuyRanking(Integer communityId, String type)
			throws Exception;

	public List<UserShops> getUserShopWU(String communityId,
			Integer startTimeInt, Integer endTimeInt) throws Exception;

	public ShopsStatisticsTop getQuickShopTopH(Integer communityId,
			String sort, Integer startTimeInt, Integer endTimeInt)
			throws Exception;

	public List<ShopSupervision> getShopDelie(Integer communityId, String type)
			throws Exception;

}
