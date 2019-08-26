package com.xj.dao;

import java.util.List;

/**
 * @author lence
 * @date 2015-7-15 下午03:51:17
 * @version 1.0
 */
public interface OrderDetailDao extends MyBaseDao {

	List<String> findOrderInfo(String serial) throws Exception;

}
