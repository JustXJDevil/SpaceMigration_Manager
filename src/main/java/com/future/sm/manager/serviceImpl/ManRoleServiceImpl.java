package com.future.sm.manager.serviceImpl;

import com.future.sm.common.exception.ServiceException;
import com.future.sm.manager.dao.ManRoleDao;
import com.future.sm.manager.pojo.ManRole;
import com.future.sm.manager.service.ManRoleService;
import com.future.sm.manager.vo.PageObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ManRoleServiceImpl implements ManRoleService {
    @Autowired
    private ManRoleDao dao;
    @Override
    public PageObject<ManRole> findPageObjects(String name, Integer pageCurrent) {
        //总行数
        int rows = dao.gerRowCounts(name);
        if (rows == 0) {
            throw new ServiceException("No record!");
        }

        int pageSize = 3;
        int start = (pageCurrent-1)*pageSize;
        //查询得到的本页记录
        List<ManRole> records = dao.findPageObjects(name,start,pageSize);
        if (records == null) {
            throw new ServiceException("Record is not exist1");
        }

        //计算得出总页数
        int pageCount = (rows-1)/pageSize + 1;
        PageObject<ManRole> obj = new PageObject();
        obj.setPageCount(pageCount);
        obj.setPageCurrent(pageCurrent);
        obj.setPageSize(pageSize);
        obj.setRowCount(rows);
        obj.setRecords(records);
        return obj;
    }
}
