package com.xj.dao;

import java.util.List;

import com.xj.bean.Attribute;

public interface AttributeDao extends MyBaseDao{
	/**
	 * 添加产品属性
	 * @param attribute
	 * @return
	 * @throws Exception
	 */
	public Integer addAttribute(Attribute attribute) throws Exception;
	
	/**
	 * 根据商品id获取 商品属性id
	 * @param serviceId
	 * @return
	 * @throws Exception
	 */
	public List<Attribute> getAttributes(Integer serviceId) throws Exception;
	
	public boolean deleteAttribute(Attribute attribute) throws Exception;
} 
