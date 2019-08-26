package com.xj.timer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.xj.bean.LifeSensitive;
import com.xj.bean.SensitiveWords;
import com.xj.dao.SensitiveWordsDao;
import com.xj.service.LifeCircleService;

/**
 * 生活圈敏感词扫描
 */
public class SenstitiveLifeScan {
	
	@Autowired
	private LifeCircleService lifeCircleService;
	
	@Autowired
	private SensitiveWordsDao sensitiveWordsDao;
	
	public void execute() {
		try {
			List<LifeSensitive> l = lifeCircleService.selectLifeCircleListTime(lifeCircleService.selectLifeCirrleDetailId().getLifeCirrleDetailId());
			List<SensitiveWords> ls = sensitiveWordsDao.getSensitiveWordsList();
			for (LifeSensitive sensitive : l) {
				for (SensitiveWords sensitiveWords : ls) {
					if (sensitive.getDetailContent().indexOf(sensitiveWords.getSensitiveWords()) > -1) {
						lifeCircleService.addLifeSensitive(sensitive);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}