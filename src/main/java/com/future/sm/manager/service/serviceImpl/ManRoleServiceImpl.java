package com.future.sm.manager.service.serviceImpl;

import com.future.sm.common.annotation.RequiredLog;
import com.future.sm.common.exception.ServiceException;
import com.future.sm.common.vo.CheckBox;
import com.future.sm.common.vo.RoleMenuVo;
import com.future.sm.manager.dao.ManRoleDao;
import com.future.sm.manager.dao.ManRoleMenuDao;
import com.future.sm.manager.dao.ManUserRoleDao;
import com.future.sm.manager.pojo.ManRole;
import com.future.sm.manager.service.ManRoleService;
import com.future.sm.manager.vo.PageObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class ManRoleServiceImpl implements ManRoleService {
    @Autowired
    private ManRoleDao dao;
    @Autowired
    private ManRoleMenuDao rmdao;
    @Autowired
    private ManUserRoleDao manUserRoleDao;
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

    @Override
    @RequiredLog("保存角色")
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void doSaveObject(ManRole manRole, Integer... menuIds) {
        //参数校检
        if (manRole == null || manRole.getName() == null || manRole.getNote() == null) {
            throw new ServiceException("pleas enter information!!");
        }
        if (menuIds == null || menuIds.length==0) {
            throw new ServiceException("pleas choose some menu");
        }

        //保存role
        dao.doSaveObject(manRole);
        //获取刚才插入的数据的id
        int roleId = dao.getMaxId();
        //保存role和menu的关系
        rmdao.doSaveObjects(roleId,menuIds);


    }

    @Override
    public RoleMenuVo findObjectById(Integer id) {
        //校检
        if (id == null) {
            throw new ServiceException("pleas choose one to update!");
        }
        //获取role的信息封装在RoleMenuVo中
        RoleMenuVo roleMenuVo = dao.findObjectById(id);
        //校检是否为空
        if (roleMenuVo == null) {
            throw new ServiceException("The selected role doesn't exist!");
        }
        //获取与所选role相关的menuIds
        List<Integer> menuIds = rmdao.findObjectByRoleId(id);
        System.out.println(roleMenuVo);
        roleMenuVo.setMenuIds(menuIds);
        System.out.println(roleMenuVo);
        return roleMenuVo;
    }

    @Override
    @RequiredLog("更新角色")
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void updateObject(ManRole manRole, Integer... menuIds) {
        //校检
        if (manRole == null) {
            throw new ServiceException("pleas enter information!");
        }
        if (menuIds == null || menuIds.length==0) {
            throw new ServiceException("pleas choose at least one menu!");
        }

        //修改role
        int row = dao.updateObject(manRole);
        if (row == 0) {
            throw new ServiceException("the select role doesn't exist!");
        }
        //修改role_menu
        int row2 = rmdao.deleteObjectsByRoleId(manRole.getId());
        int row3 = rmdao.doSaveObjects(manRole.getId(),menuIds);
        if (row2 == 0 || row3==0) {
            throw new ServiceException("update failed!");
        }
    }

    @Override
    @RequiredLog("删除角色")
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void deleteObjectById(Integer id) {
        //校检
        if (id == null || id<0) {
            throw new ServiceException("pleas choose one!");
        }

        //删除role
        int row1 = dao.deleteObjectById(id);
        //删除role_menus
        int row2 = rmdao.deleteObjectsByRoleId(id);
        //删除user_roles
        int row3 = manUserRoleDao.deleteObjectsByRoleId(id);

//        if (row1 == 0 || row2==0||row3==0) {
//            throw new ServiceException("delete failed");
//        }
        if (row1 == 0) {
            throw new ServiceException("delete failed");
        }
    }

    @Override
    public List<CheckBox> findRoles() {
        return dao.findRoles();
    }
}
