package com.xj.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.xj.bean.OrderDetailBean;
import com.xj.bean.OrderUpdateBean;
import com.xj.bean.Orders;
import com.xj.bean.OrdersBean;
import com.xj.bean.Page;
import com.xj.bean.UserBonus;
import com.xj.bean.XjPayHistory;
import com.xj.dao.OrdersDao;
import com.xj.httpclient.utils.DaoUtils;

@Repository("ordersDao")
public class OrdersDaoImpl extends MyBaseDaoImpl implements OrdersDao {

    private static Logger logger = Logger.getLogger(OrdersDaoImpl.class);
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

 
    public String addOrder(OrdersBean ordersBean) {
        int orderId = 0;
       // int orderDetailId = 0;
        int index = 0;
        Connection con = null;
        PreparedStatement statementOrder = null;
        PreparedStatement statementOrderDetail = null;
        ResultSet resultSet = null;
        int unixTime = 0;
        String num = "";
        String sqlOrder = "insert into orders(serial,emob_id_user,emob_id_shop,start_time , community_id,order_year,order_month,order_price,action,online,order_address) values(?,?,?,?,?,?,?,?,?,?,?)";
        try {
            //disable auto commit
            con = jdbcTemplate.getDataSource().getConnection();
            con.setAutoCommit(false);
            //insert order
            statementOrder = con.prepareStatement(sqlOrder, Statement.RETURN_GENERATED_KEYS);
            index = 1;
            num = getSixteenOrderNum();
            statementOrder.setString(index++, num);
            statementOrder.setString(index++, ordersBean.getEmobIdUser());
            statementOrder.setString(index++, ordersBean.getEmobIdShop());
         
            //get current time
            unixTime = (int) (System.currentTimeMillis() / 1000L);
            statementOrder.setInt(index++, unixTime);
            statementOrder.setInt(index++, ordersBean.getCommunityId()); 
            Calendar cal=Calendar.getInstance();   
            Integer year = cal.get(Calendar.YEAR);
            Integer month = cal.get(Calendar.MONTH) + 1;
            statementOrder.setInt(index++, year);
            statementOrder.setInt(index++, month);
            statementOrder.setString(index++, ordersBean.getOrderPrice());
            statementOrder.setString(index++, ordersBean.getAction());
            statementOrder.setString(index++, ordersBean.getOnline());
            statementOrder.setString(index++, ordersBean.getOrderAddress());
            logger.info("order insert sql is :" + statementOrder);
            statementOrder.executeUpdate();
            resultSet = statementOrder.getGeneratedKeys();
            if (resultSet.next()) {
                orderId = resultSet.getInt(1);
            }
            if(ordersBean.getEmobIdShop() == null || "".equals(ordersBean.getEmobIdShop())){
            	
            }else{
            	//insert order detail
                String sqlOrderDetail = "insert into order_detail(order_id,service_id,service_name,price,count,attr_id,attr_name) values(?,?,?,?,?,?,?)";

                statementOrderDetail = con.prepareStatement(sqlOrderDetail,Statement.RETURN_GENERATED_KEYS);
                for(OrderDetailBean orderDetailBean : ordersBean.getOrderDetailBeanList()){
                    statementOrderDetail.setInt(1,orderId);
                    statementOrderDetail.setInt(2,orderDetailBean.getServiceId());
                    statementOrderDetail.setString(3, orderDetailBean.getServiceName());
                    statementOrderDetail.setString(4, orderDetailBean.getPrice());
                    statementOrderDetail.setInt(5, orderDetailBean.getCount());
                    statementOrderDetail.setInt(6, orderDetailBean.getAttrId());
                    statementOrderDetail.setString(7, orderDetailBean.getAttrName());
                    logger.info("order detail sql insert is "+sqlOrderDetail);

                    statementOrderDetail.executeUpdate();
                    resultSet = statementOrderDetail.getGeneratedKeys();
                    if(resultSet.next()){
                       // orderDetailId = resultSet.getInt(1);
                    }
                }
                //commit
                con.commit();
            }
        } catch (Exception e) {
            logger.error(this, e);
            if (con != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    logger.info("roll back");
                    con.rollback();
                } catch(SQLException excep) {
                    logger.error(this,excep);
                }
            }
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statementOrder != null) {
                    statementOrder.close();
                }
                if (statementOrderDetail != null) {
                    statementOrderDetail.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                logger.error(this, e);
            }finally {
                try {
                    logger.info("enable auto commit in finally");
                    con.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return num;
    }

    public boolean modifyOrderState(String orderId, OrderUpdateBean orderUpdateBean) {
        boolean isUpdadeSucess = false;
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql;
        if(!orderUpdateBean.getStatus().equals("ended")){
            sql = "update orders set status = ? , start_time = ? where order_id = ?";
            try {
                con = jdbcTemplate.getDataSource().getConnection();
                preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                int index = 1;
                preparedStatement.setString(index++,orderUpdateBean.getStatus());
                preparedStatement.setInt(index++, (int)(System.currentTimeMillis() / 1000));
                preparedStatement.setString(index++, orderId);

                logger.info("sql is :" + preparedStatement);
                int count = preparedStatement.executeUpdate();
                if(count>0){
                    isUpdadeSucess = true;
                }
                logger.info("count of update query is :"+count);
            } catch (Exception e) {
                logger.error(this, e);
            } finally {
                try {
                    if (resultSet != null) {
                        resultSet.close();
                    }
                    if (preparedStatement != null) {
                        preparedStatement.close();
                    }
                    if (con != null) {
                        con.close();
                    }
                } catch (Exception e) {
                    logger.error(this, e);
                }
            }
        }else{
            sql = "update orders set end_time = ? , status = 'ended' where order_id = ?";
            try {
                con = jdbcTemplate.getDataSource().getConnection();
                preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                int index = 1;
                //get current time
                int unixTime = (int) (System.currentTimeMillis() / 1000L);
                preparedStatement.setInt(index++, unixTime);
                preparedStatement.setString(index++, orderId);

                logger.info("insert sql is :" + preparedStatement);
                int count = preparedStatement.executeUpdate();
                if(count>0){
                    isUpdadeSucess = true;
                }
                logger.info("count of update query is :"+count);
            } catch (Exception e) {
                logger.error(this, e);
            } finally {
                try {
                    if (resultSet != null) {
                        resultSet.close();
                    }
                    if (preparedStatement != null) {
                        preparedStatement.close();
                    }
                    if(con != null){
                        con.close();
                    }
                } catch (Exception e) {
                    logger.error(this, e);
                }
            }
        }

        return isUpdadeSucess;
    }
    
    private String getOrderNum() {
        return new SimpleDateFormat("yyMMddHHmmss").format(new java.util.Date());
    }
 
    /**
     * 产生随机的四位数
     * @return
     */
    private String getFourRandom(){
        Random rad=new Random();
        String strRan = rad.nextInt(10000)+"";
        switch (strRan.length()) {
        	case 1: strRan = "000" + strRan; break;
        	case 2: strRan = "00" + strRan;  break;
        	case 3: strRan = "0" + strRan;   break;
        	
        }
        return strRan;
    }
    /**
     * 16位的订单号
     * @return
     */
    private String getSixteenOrderNum() {
        return getOrderNum() + getFourRandom();
    }

	@Override
	public Orders getOrderById(Integer orderId) throws Exception {
		String sql = "SELECT * FROM orders WHERE order_id = ? ";
		List<Orders> list = this.getList(sql, new Integer[]{orderId}, Orders.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public Integer getCountByEmobIdShop(String emobIdShop) throws Exception {
		String sql = "SELECT COUNT(*) FROM orders WHERE emob_id_shop = ?";
		Number number = this.getValue(sql, Number.class, new Object[]{emobIdShop});
		return number.intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean updateOrders(Orders orders) throws Exception {
		String sql = " UPDATE orders SET " ;
		List<Object> list = new ArrayList<Object>();
		Object resultObject[] = DaoUtils.reflect(orders);
		if(resultObject != null && resultObject[1] != null && ((List<Object>)resultObject[1]).size() > 0){
			list = (List<Object>)resultObject[1];
			sql += (String) resultObject[0];
		}else{
			return false; 
		}
		sql += " WHERE serial = ? ";
		list.add(orders.getSerial());
		Integer result = this.updateData(sql, list, null);
		return result != null && result > 0 ? true : false;
	}

	@Override
	public Page<XjPayHistory> getXjPayHistory(String action, String emobId , Integer pageNum,
			Integer pageSize, Integer nvm) throws Exception {
		String sql = "SELECT serial , order_year , order_month , order_price , end_time , action " +
					 " FROM orders o where status = 'ended' and emob_id_user = ? and action in ('101','102','103','104','105') order by end_time desc ";
		Page<XjPayHistory> page = this.getData4Page(sql, new Object[]{ emobId}, pageNum, pageSize, nvm, XjPayHistory.class);
		return page;
	}

	@Override
	public String getOrdersNumbers(String emobId , Integer startTime , Integer endTime) throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = this.getJdbcTemplate().getDataSource().getConnection();
			
			preparedStatement = connection.prepareStatement("SELECT sum(order_price) FROM orders o WHERE emob_id_shop = ? AND status = 'ended' AND end_time >= ? AND end_time <= ?");
			preparedStatement.setString(1, emobId);
			preparedStatement.setInt(2, startTime);
			preparedStatement.setInt(3, endTime);
			
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getString(1);
			}
			return "";
		} finally {
			if (null != resultSet) {
				try {
					resultSet.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (null != preparedStatement) {
				try {
					preparedStatement.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (null != connection) {
				try {
					connection.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public Orders getOrderBySerail(String serial) throws Exception {
		String sql = "SELECT * FROM orders WHERE serial = ? ";
		List<Orders> list = this.getList(sql, new Object[]{serial}, Orders.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean updateOrdersByOrderId(Orders orders) throws Exception {
		String sql = " UPDATE orders SET " ;
		List<Object> list = new ArrayList<Object>();
		Object resultObject[] = DaoUtils.reflect(orders);
		if(resultObject != null && resultObject[1] != null && ((List<Object>)resultObject[1]).size() > 0){
			list = (List<Object>)resultObject[1];
			sql += (String) resultObject[0];
		}else{
			return false; 
		}
		sql += " WHERE order_id = ? ";
		list.add(orders.getOrderId());
		Integer result = this.updateData(sql, list, null);
		return result != null && result > 0 ? true : false;
	}

	@Override
	public int updateUsersShare(String serial) {
		String sql = "UPDATE orders o SET  o.share='no' where o.serial = ?";
		List<Object> params = new  ArrayList<Object>();
		params.add(serial);
		int updateData = this.updateData(sql, params, null);
		return updateData;
		
	}

	@Override
	public List<OrdersBean> getNotEndOrders(int communityId, String emobIdUser,
			String emobIdShop) throws Exception {
		String sql = "SELECT * from orders o  WHERE o.community_id=? AND o.emob_id_shop=? and o.emob_id_user=? AND o.`status` != 'ended' ORDER BY o.start_time DESC ";
		Object[] params = new Object[]{communityId,emobIdShop,emobIdUser};
	
		List<OrdersBean> list = this.getList(sql, params, OrdersBean.class);
		for (OrdersBean orders : list) {
			Integer orderId = orders.getOrderId();
			String serial = orders.getSerial();
			
			String sql3 = "SELECT * FROM user_bonus u INNER JOIN bonus  b on b.bonus_id = u.bonus_id  WHERE u.serial =? ";
			Object[] params3 = {serial};
			List<UserBonus> list3 = this.getList(sql3, params3, UserBonus.class);
			if(list3!=null && list3.size()>0){
				 orders.setUserBonus(list3.get(0));
			}
			
			
			String sql2 = "SELECT * from order_detail d INNER JOIN shop_item i  where d.service_id = i.service_id AND d.order_id=?";
			Object[] params2 = {orderId};
			List<OrderDetailBean> list2 = this.getList(sql2, params2, OrderDetailBean.class);
			orders.setOrderDetailBeanList(list2);
		}
		
		return list;
	}
}