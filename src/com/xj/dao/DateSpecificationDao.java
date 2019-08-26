package com.xj.dao;

import com.xj.bean.DateSpecification;

public interface DateSpecificationDao extends MyBaseDao{
	
	public Integer addDateSpecification(DateSpecification dateSpecification) throws Exception;
	
	public DateSpecification getSpecification() throws Exception;
	
	public boolean updateDateSpecification(DateSpecification dateSpecification) throws Exception;
}
