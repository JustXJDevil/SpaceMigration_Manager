package com.future.sm.manager.service;

import java.util.List;
import java.util.Map;

import com.future.sm.common.vo.Node;
import com.future.sm.manager.pojo.ManMenu;

public interface ManMenuService {
	List<Map<String,Object>> findObjects();
	
	int deleteObject(Integer id);
	
	int updateObject(ManMenu menu);
	
	List<Node> findZtreeMenuNodes();
	
	void insertObject(ManMenu menu);
}
