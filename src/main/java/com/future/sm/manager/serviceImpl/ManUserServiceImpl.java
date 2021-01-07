package com.future.sm.manager.serviceImpl;

import com.future.sm.common.exception.ServiceException;
import com.future.sm.common.vo.UserDeptVo;
import com.future.sm.manager.dao.ManUserDao;
import com.future.sm.manager.service.ManUserService;
import com.future.sm.manager.vo.PageObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManUserServiceImpl implements ManUserService {
    @Autowired
    protected ManUserDao manUserDao;
    @Override
    public PageObject findPageObjects(Integer pageCurrent, String username) {
        //校检
        if (pageCurrent <= 0) {
            throw new ServiceException("wrong page info");
        }
        //查询
        int pageSize = 3;
        int start = (pageCurrent-1)*pageSize;
        List<UserDeptVo> objs = manUserDao.findPageObjects(username,start,pageSize);
        //将数据封装到PageObject类中
        int rows = manUserDao.getRowCount();
        PageObject obj = new PageObject();
        obj.setRecords(objs);
        obj.setRowCount(rows);
        obj.setPageCurrent(pageCurrent);
        obj.setPageSize(pageSize);
        obj.setPageCount((rows-1)/pageSize+1);
        return obj;
    }
}
