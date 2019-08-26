package com.xj.dao;

import java.util.List;

import com.xj.bean.Bonus;

public interface BonusDao extends MyBaseDao {
	
	/**
	 * 添加帮帮券模板
	 * @param bonus
	 * @return
	 * @throws Exception
	 */
	public Integer addBonus(Bonus bonus) throws Exception;
	
	/**
	 * 获取所有帮帮券模板列表
	 * @return
	 * @throws Exception
	 */
	public List<Bonus> getBonusList() throws Exception;
	
	/**
	 * 根据模板id 获取帮帮券 模板
	 * @param bonusId
	 * @return
	 * @throws Exception
	 */
	public Bonus getBonusById(Integer bonusId) throws Exception;
}
