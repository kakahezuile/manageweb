package com.xj.dao;

import java.util.List;

import com.xj.bean.XjBankCard;

public interface XjBankCardDao extends MyBaseDao{
	
	public Integer addXjBankCard(XjBankCard xjBankCard) throws Exception;
	
	public boolean updateXjBankCard(XjBankCard xjBankCard) throws Exception;
	
	public List<XjBankCard> getBankCardList(String emobId) throws Exception;
	
	public XjBankCard getBankCard(String emobId , String cardNo) throws Exception;
} 
