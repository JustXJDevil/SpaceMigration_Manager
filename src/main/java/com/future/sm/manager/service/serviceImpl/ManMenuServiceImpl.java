package com.future.sm.manager.service.serviceImpl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import com.future.sm.common.annotation.RequiredLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.future.sm.common.exception.ServiceException;
import com.future.sm.common.vo.Node;
import com.future.sm.manager.dao.ManMenuDao;
import com.future.sm.manager.dao.ManRoleMenuDao;
import com.future.sm.manager.pojo.ManMenu;
import com.future.sm.manager.pojo.ManRoleMenu;
import com.future.sm.manager.service.ManMenuService;
@Service
public class ManMenuServiceImpl implements ManMenuService {
	@Autowired
	private ManMenuDao dao;
	@Autowired
	private ManRoleMenuDao rmdao;
	private ReentrantLock lock;
	@Override
	public List<Map<String, Object>> findObjects() {
		List<Map<String,Object>> list = dao.findObjects();
		//Determine if the database contains relevant information
		if(list == null) {
			throw new ServiceException("there is no corresponding information");
		}
		return list;
	}
	@RequiredLog("删除菜单")
	@Override
	@Transactional(isolation = Isolation.REPEATABLE_READ)
	public int deleteObject(Integer id) {
		//verify data validity
		if(id==0 || id<0) {
			throw new ServiceException("please choose one");
		}
		//1.get the number of submenu
		int num = dao.getChildCount(id);
		if(num != 0) {
			throw new ServiceException("Cannot delete this munu");
		}
		//2.delete the selected menu 
		int rows = dao.deleteObject(id);
		if(rows == 0) {
			throw new ServiceException("the menu is not exist");
		}
		//3.delete the relevant role_menu
		int rows_rm = rmdao.deleteObjectsBymenuId(id);
		if(rows_rm == 0) {
			System.out.println("rows_rm = "+rows_rm);
			throw new ServiceException("delete failed");
		}
		return rows;
	}
	@RequiredLog("更新菜单")
	@Override
	public int updateObject(ManMenu menu) {
		//verify the validity
		if(menu == null) {
			throw new ServiceException("pls enter something");
		}
		int rows = dao.updateObject(menu);
		if(rows == 0) {
			throw new ServiceException("the data doesn't exist");
		}
		return rows;
	}
	
	@Override
	public List<Node> findZtreeMenuNodes() {
		List<Node> list = dao.findZtreeMenuNodes();
		//System.out.println("list: "+list);
		return list;
	}
	
	@Override
	@RequiredLog("添加菜单")
	@Transactional(isolation = Isolation.REPEATABLE_READ)
	public void insertObject(ManMenu menu) {
		lock = new ReentrantLock();
		if(menu==null) {
			throw new ServiceException("pls enter information");
		}
		
		
		try {		
			dao.insertObject(menu);			
			ManRoleMenu rm = new ManRoleMenu();
			rm.setRoleId(1);
			lock.lock();
			rm.setMenuId(dao.findMaxObjectId());
			System.out.println(rm);
			rmdao.insertObject(rm);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("insert failed!!!");
		}finally {
			lock.unlock();
		}
		
		
	}
	
}
