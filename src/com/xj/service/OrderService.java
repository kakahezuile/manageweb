package com.xj.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xj.bean.OrderDetailBean;
import com.xj.bean.OrderResult;
import com.xj.bean.OrdersBean;
import com.xj.bean.ResultStatusBean;
import com.xj.bean.ShopItem;
import com.xj.bean.Shops;
import com.xj.dao.OrdersDao;
import com.xj.dao.ShopDao;
import com.xj.dao.ShopsDao;
import com.xj.exception.ShopItemException;

@Service("orderService")
public class OrderService {
	
	@Autowired
	private OrdersDao ordersDao;
	
	@Autowired
	private ShopDao shopDao;
	
	@Autowired
	private ShopsDao shopsDao;
	
	private ResultStatusBean resultStatusBean;
	

	public ResultStatusBean getResultStatusBean() {
		return resultStatusBean;
	}

	public void setResultStatusBean(ResultStatusBean resultStatusBean) {
		this.resultStatusBean = resultStatusBean;
	}
	
	/**
	 * 下单前的判断
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public ResultStatusBean isError(List<OrdersBean> list) throws Exception{
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		double tempPrice = 0;
		if(list != null && list.size() > 0){
			Iterator<OrdersBean> iterator = list.iterator();
			OrdersBean ordersBean = null;
			List<OrderDetailBean> detailBeans = null;
			Iterator<OrderDetailBean> iterator2 = null;
			OrderDetailBean orderDetailBean = null;
			int id = 0;
			int count = 0;
			ShopItem shopItem = null;
			int purchase = 0;
			StringBuilder sb = new StringBuilder("");
			while(iterator.hasNext()){
				ordersBean = iterator.next();
				detailBeans = ordersBean.getOrderDetailBeanList();
				iterator2 = detailBeans.iterator();
				while(iterator2.hasNext()){
					orderDetailBean = iterator2.next();
					id = orderDetailBean.getServiceId();
					count = orderDetailBean.getCount();
					shopItem = shopDao.getShopItemByServiceId(id);
					if(shopItem == null){
						
						if("".equals(sb.toString())){
							sb.append(orderDetailBean.getServiceName()).append(" 已下架");
						}else{
							sb.append(",").append(orderDetailBean.getServiceName()).append(" 已下架");
						}
					}else{
						purchase = shopItem.getPurchase();
						if(purchase != 0){
							if(count > purchase){
								if("".equals(sb.toString())){
									sb.append(orderDetailBean.getServiceName()).append("限购量为 ").append(purchase);
								}else{
									sb.append(",").append(orderDetailBean.getServiceName()).append("限购量为 ").append(purchase);
								}
							}
						}
						if(orderDetailBean.getPrice() != null && shopItem.getCurrentPrice() != null){
							DecimalFormat df = new DecimalFormat("0.00");
							double price = df.parse(orderDetailBean.getPrice()).doubleValue();
							tempPrice+=price*orderDetailBean.getCount();
							double currentPrice = df.parse(shopItem.getCurrentPrice()).doubleValue();
							if(price != currentPrice){
								if("".equals(sb.toString())){
									sb.append(orderDetailBean.getServiceName()).append(" 价格已变动，请删除后重新购买");
								}else{
									sb.append(",").append(orderDetailBean.getServiceName()).append(" 价格已变动，请删除后重新购买");
								}
							}
						}
						
					} 
				
				}
				
			}
			if(tempPrice < 20){
				resultStatusBean.setStatus("no");
				resultStatusBean.setMessage("金额出现异常了");
				return resultStatusBean;
				
			}
			if(!"".equals(sb.toString())){
				resultStatusBean.setStatus("no");
				resultStatusBean.setMessage(sb.toString());
			}
		}
		return resultStatusBean;
	}

	/**
	 * 下订单
	 * @param list
	 * @param communityId
	 * @param emobId
	 * @return
	 * @throws Exception
	 */
	public ResultStatusBean addOrder(List<OrdersBean> list , Integer communityId , String emobId) throws Exception{
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		resultStatusBean.setStatus("no");
		Iterator<OrdersBean> it = list.iterator();
		OrdersBean ordersBean = null;
		OrderResult orderResult = null;
		List<OrderResult> orderResults = new ArrayList<OrderResult>();
		while(it.hasNext()){
			ordersBean = it.next();
			ordersBean.setCommunityId(communityId);
			ordersBean.setEmobIdUser(emobId);
			List<OrderDetailBean> orderDetailBeans = ordersBean.getOrderDetailBeanList();
			if(orderDetailBeans != null){
//				int len = orderDetailBeans.size();
				int stock = 0;

				if(stock < 1){
					String resultId = ordersDao.addOrder(ordersBean);
					orderResult = new OrderResult();
					Shops shops = shopsDao.getShopsByEmobId(ordersBean.getEmobIdShop());
					orderResult.setShop(shops);
					orderResult.setOrderId(resultId);
					orderResults.add(orderResult);
//					resultStatusBean.setResultId(resultId);
					if(resultId != null){
						resultStatusBean.setStatus("yes");
					}else{
						resultStatusBean.setStatus("no");
						resultStatusBean.setMessage("添加订单失败");
						throw new ShopItemException("添加订单失败");
					}
				}else{
					try {
						Integer.parseInt("aa");
					} catch (Exception e) {
						resultStatusBean.setStatus("no");
						this.setResultStatusBean(resultStatusBean);
						throw e;
					}
				}
			}
		}
		
		resultStatusBean.setResultOrders(orderResults);
		return resultStatusBean;
	}


	/**
	 * 获取用户未完成订单
	 * @param communityId
	 * @param emobIdUser
	 * @param emobIdShop
	 * @return
	 * @throws Exception
	 */
	public List<OrdersBean> getNotEndOrders(int communityId, String emobIdUser, String emobIdShop) throws Exception {
		return ordersDao.getNotEndOrders(communityId,emobIdUser,emobIdShop);
	}
}
