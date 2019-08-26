package com.xj.dao;

import java.util.List;

import com.xj.bean.BankCategory;

public interface BankCategoryDao extends MyBaseDao{
	
	/**
	 * 添加绑定银行卡
	 * @param bankCategory
	 * @return
	 * @throws Exception
	 */
	public Integer addBankCategory(BankCategory bankCategory) throws Exception;
	/**
	 * 获取银行卡列表
	 * @return
	 * @throws Exception
	 */
	public List<BankCategory> getBankCategoryList() throws Exception;
}
