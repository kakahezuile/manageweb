package com.xj.service.welfare;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xj.bean.welfare.Welfare;
import com.xj.dao.welfare.WelfareDao;

/**
 * 福利业务服务
 * @author 王利东
 * 2015-09-16
 */
@Service("welfareService")
public class WelfareService {

	@Autowired
	private WelfareDao welfareDao;

	/**
	 * 添加福利
	 * @param welfare
	 * @return
	 * @throws Exception
	 */
	public Integer addWelfare(Welfare welfare) throws Exception {
		if (null == welfare) {
			throw new NullPointerException("福利对象为空!");
		}
		if (null != welfare.getWelfareId()) {
			throw new IllegalArgumentException("福利ID已存在!");
		}
		
		return welfareDao.addWelfare(welfare);
	}

	/**
	 * 更新福利信息
	 * @param welfare
	 * @return
	 * @throws Exception
	 */
	public boolean updateWelfare(Welfare welfare) throws Exception {
		if (null == welfare) {
			throw new NullPointerException("福利对象为空!");
		}
		if (null == welfare.getWelfareId() || welfare.getWelfareId().intValue() < 1) {
			throw new IllegalArgumentException("福利ID不存在!");
		}
		
		return welfareDao.updateWelfare(welfare);
	}

	/**
	 * 获取福利信息，结果已排序，进行中的在前，结束时间晚的在前
	 * @param communityId
	 * @return
	 * @throws Exception
	 */
	public List<Welfare> getWelfares(Integer communityId) throws Exception {
		if (null == communityId) {
			throw new NullPointerException("请提供参数:communityId");
		}
		
		return welfareDao.getWelfares(communityId);
	}

	/**
	 * 获取福利信息
	 * @param welfareId
	 * @return
	 * @throws Exception
	 */
	public Welfare getWelfare(Integer welfareId) throws Exception {
		if (null == welfareId) {
			throw new NullPointerException("福利ID为空!");
		}
		
		return welfareDao.getWelfare(welfareId);
	}

	/**
	 * 福利上线
	 * @param communityId
	 * @param welfareId
	 * @return
	 */
	public String online(Integer communityId, Integer welfareId) {
		if (null == communityId) {
			throw new NullPointerException("小区ID为空!");
		}
		if (null == welfareId) {
			throw new NullPointerException("福利ID为空!");
		}
		
		return welfareDao.online(communityId, welfareId);
	}

	/**
	 * 福利下线
	 * @param welfareId
	 * @return
	 * @throws Exception
	 */
	public boolean offline(Integer welfareId) throws Exception {
		if (null == welfareId) {
			throw new NullPointerException("福利ID为空!");
		}
		
		return welfareDao.offline(welfareId);
	}

	/**
	 * 标识福利成功了
	 * @param welfareId
	 * @return
	 * @throws Exception
	 */
	public boolean succeed(Integer welfareId) throws Exception {
		if (null == welfareId) {
			throw new NullPointerException("福利ID为空!");
		}
		
		return welfareDao.succeed(welfareId);
	}

	/**
	 * 标识福利失败了
	 * @param welfareId
	 * @return
	 * @throws Exception
	 */
	public boolean fail(Integer welfareId, String reason) throws Exception {
		if (null == welfareId) {
			throw new NullPointerException("福利ID为空!");
		}
		if (StringUtils.isBlank(reason)) {
			throw new NullPointerException("失败原因为空!");
		}
		
		return welfareDao.fail(welfareId, reason);
	}

	/**
	 * 删除福利
	 * @param welfareId
	 * @return
	 * @throws Exception
	 */
	public boolean delete(Integer welfareId) throws Exception {
		if (null == welfareId) {
			throw new NullPointerException("福利ID为空!");
		}
		
		return welfareDao.deleteWelfare(welfareId);
	}
}