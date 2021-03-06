package com.future.sm.manager.service;

import com.future.sm.manager.pojo.ManLog;
import com.future.sm.manager.vo.PageObject;

import java.util.concurrent.Future;

public interface ManLogService {
	public PageObject<ManLog> findPageObject(String name, Integer pageCurrent);
	
	public int doDeleteObjects(Integer...ids);

	Future<Integer> insertObject(ManLog manLog);
}
