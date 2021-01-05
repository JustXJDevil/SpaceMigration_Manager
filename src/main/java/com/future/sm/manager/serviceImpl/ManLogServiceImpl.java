package com.future.sm.manager.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.future.sm.common.exception.ServiceException;
import com.future.sm.manager.dao.ManLogDao;
import com.future.sm.manager.pojo.ManLog;
import com.future.sm.manager.service.ManLogService;
import com.future.sm.manager.vo.PageObject;
@Service
public class ManLogServiceImpl implements ManLogService {
	@Autowired
	private ManLogDao dao;
	@Override
	public PageObject<ManLog> findPageObject(String name,Integer pageCurrent) {
		//数据验证
//		if(name == null || name.length()==0) {
//			throw new ServiceException("请输入正确的用户名");
//		}
		//设置页面大小为3
		int pageSize = 3;
		PageObject<ManLog> po = new PageObject();
		//查询总记录数
		Integer counts = dao.getRowCounts();
		//验证总记录数
		if(counts==0)
			throw new ServiceException("无记录");
		//查询并封装当前页记录
		po.setRecords(dao.findPageObject(
				name, 
				(pageCurrent-1)*pageSize, 
				pageSize));
		po.setPageSize(pageSize);
		po.setRowCount(counts);
		po.setPageCurrent(pageCurrent);
		po.setPageCount((counts-1)/3+1);	//总页数
		return po;
	}
	@Override
	public int doDeleteObjects(Integer... ids) {
		if(ids==null || ids.length == 0) {
			throw new ServiceException("Pls choose at least one!!");
		}
		int rows;
		try {
			rows = dao.doDeleteObjects(ids);
		}catch(Throwable t){
			throw new ServiceException("something is wrong!!");
			//通知运维（微信、短信）
		}
		System.out.println("rows = "+rows);
		if(rows == 0)
			throw new ServiceException("maybe they are not exist already!!");
		return rows;
	}
}
