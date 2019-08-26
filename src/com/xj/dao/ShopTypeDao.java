package com.xj.dao;

import java.util.List;

import com.xj.bean.ShopType;

public interface ShopTypeDao extends MyBaseDao {
	public List<ShopType> getTypeList() throws Exception;

	public String getShopTypeName(String shopType) throws Exception;
}
