package com.future.sm.manager.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.future.sm.common.vo.Node;
import com.future.sm.manager.pojo.ManMenu;
@Mapper
public interface ManMenuDao {
	//one row of record corresponds to a map
	List<Map<String,Object>> findObjects();
	//query the number of submenus
	int getChildCount(Integer id);
	//delete menu
	int deleteObject(Integer id);
	//updata
	int updateObject(ManMenu menu);
	//get Ztree information
	List<Node> findZtreeMenuNodes();
	//insert object
	void insertObject(ManMenu menu);
	
	int findMaxObjectId();
}
