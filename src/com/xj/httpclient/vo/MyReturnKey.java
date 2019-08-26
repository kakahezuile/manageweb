package com.xj.httpclient.vo;

import java.util.List;
import java.util.Map;

import com.xj.dao.ReturnKeyList;

public class MyReturnKey implements ReturnKeyList{
	
	private Integer id ;
	
	public Integer getId(){
		return id;
	}

	@Override
	public void getKeyList(List<Map<String, Object>> keyList) {
		// TODO Auto-generated method stub
		this.id =  ((Long)keyList.get(0).get("GENERATED_KEY")).intValue();
	}

}
