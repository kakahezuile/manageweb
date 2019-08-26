package com.xj.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xj.bean.ShopType;
import com.xj.dao.ShopTypeDao;

@Repository("shopTypeDao")
public class ShopTypeDaoImpl extends MyBaseDaoImpl implements ShopTypeDao{

	@Override
	public List<ShopType> getTypeList() throws Exception {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM shop_type";
		List<ShopType> list = this.getList(sql , null , ShopType.class);
		return list;
	}

	@Override
	public String getShopTypeName(String shopType) throws Exception {
		String sql = "SELECT * FROM shop_type t where t.shop_type_id = ?";
		List<ShopType> list = this.getList(sql , new Object[]{shopType} , ShopType.class);
		
		return list.get(0).getShopTypeName();
	}

}
