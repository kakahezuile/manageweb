package com.xj.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xj.bean.Clearing;
import com.xj.bean.Complaints;
import com.xj.bean.DeductMoney;
import com.xj.bean.Deduction;
import com.xj.bean.Page;
import com.xj.bean.ReckoningHistory;
import com.xj.bean.ShopsDeduction;
import com.xj.dao.ComplaintsDao;
import com.xj.dao.DeductionDao;
import com.xj.httpclient.utils.MyDateUtiles;

@Service("deductionService")
public class DeductionService {

	@Autowired
	private DeductionDao deductionDao;
	
	@Autowired
	private ComplaintsDao complaintsDao;
	
	public Integer addDeduction(Deduction deduction) throws Exception{ // 添加扣款记录
		Integer resultId = null;
		// 修改complaints表中的  投诉记录 改为 已完结
		Complaints com=new Complaints();
		com.setComplaintId(deduction.getComplaintId());
		com.setStatus("ended");
		complaintsDao.updateComplaint(com);
		
		resultId=deductionDao.addDeduction(deduction);
		//添加一条扣款记录   返回值 拿回来
		return resultId;
	}
	
	public boolean updateDeduction(Deduction deduction)throws Exception{ 
		
		boolean resultId = false;
		resultId=deductionDao.updateDeduction(deduction);
		//添加一条扣款记录   返回值 拿回来
		return resultId;
	}

	public Page<DeductMoney> getDeductMoney(String emobId, Integer pageNum,
			Integer pageSize, Integer startTime, Integer endTime) throws Exception {
		
		return deductionDao.getDeductMoney(emobId,pageNum,pageSize,startTime,endTime);
	}
	
	public List<ShopsDeduction> getShopsDeduction(String emobId) throws Exception{ // 获取扣款记录
		List<ShopsDeduction> list = deductionDao.getShopsDeduction(emobId);
		int len = list.size();
		if(list != null){
			for(int i = 0 ; i < len ; i++){
				ShopsDeduction shopsDeduction = list.get(i);
				String months = shopsDeduction.getMonths();
				Map<String, String> map = MyDateUtiles.getFirstday_Lastday_Month(months+"-01");
				shopsDeduction.setStartTime(map.get("first").replace("-", "."));
				shopsDeduction.setEndTime(map.get("last").replace("-", "."));
				List<Deduction> deductionList = deductionDao.getDeductionList(emobId, months);
				shopsDeduction.setList(deductionList);
			}
		}
		return list;
	}

	/**
	 *  结款历史
	 */
	public Page<ReckoningHistory> getReckoningHistory(Integer communityId,String sort,Integer pageNum,
			Integer pageSize) throws Exception{
		return deductionDao.getReckoningHistory(communityId,sort,pageNum,pageSize);
	}

	public Integer addClearing(Clearing clearing) throws Exception{
		Integer resultId = null;
		Date date=new Date();
		int time=(int) (date.getTime()/1000);
		clearing.setCreateTime(time);
		clearing.setClearingTime(time);
		clearing.setUpdateTime(time);
		resultId=deductionDao.addClearing(clearing);
		//添加一条扣款记录   返回值 拿回来
		return resultId;
	}
	
}
