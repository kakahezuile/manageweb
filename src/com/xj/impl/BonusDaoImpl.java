package com.xj.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xj.bean.Bonus;
import com.xj.dao.BonusDao;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("bonusDao")
public class BonusDaoImpl extends MyBaseDaoImpl implements BonusDao {

	@Override
	public Integer addBonus(Bonus bonus) throws Exception {
		// TODO Auto-generated method stub
		MyReturnKey key = new MyReturnKey();
		this.save(bonus, key);
		return key.getId();
	}

	@Override
	public List<Bonus> getBonusList() throws Exception {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM bonus ";
		List<Bonus> list = this.getList(sql, null, Bonus.class);
		return list;
	}

	@Override
	public Bonus getBonusById(Integer bonusId) throws Exception {
		// TODO Auto-generated method stub
		String sql = " SELECT * FROM bonus WHERE bonus_id = (SELECT bonus_id FROM user_bonus u WHERE user_bonus_id = ? ) ";
		List<Bonus> list = this.getList(sql, new Object[]{bonusId}, Bonus.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

}
