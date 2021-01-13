package com.future.sm.manager.service.serviceImpl;

import com.future.sm.common.annotation.RequiredLog;
import com.future.sm.common.exception.ServiceException;
import com.future.sm.common.vo.UserDeptVo;
import com.future.sm.manager.dao.ManUserDao;
import com.future.sm.manager.dao.ManUserRoleDao;
import com.future.sm.manager.pojo.ManUser;
import com.future.sm.manager.service.ManUserService;
import com.future.sm.manager.vo.PageObject;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ManUserServiceImpl implements ManUserService {
    @Autowired
    private ManUserDao manUserDao;
    @Autowired
    private ManUserRoleDao manUserRoleDao;

    @Transactional(readOnly = true)
    @RequiredLog("用户分页查询")
    @Override
    public PageObject findPageObjects(Integer pageCurrent, String username) {
        System.out.println("看见我表示没有走缓存");
        System.out.println("findPageObjects_t: "+Thread.currentThread().getName());
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

    @Override
    @RequiresPermissions("sys:user:update")
    @RequiredLog("禁用启用")
    public void updateValidById(Integer id, Integer valid, String modifiedUser) {
        //校检
        if (valid != 0 && valid != 1) {
            throw new ServiceException("wrong valid:"+valid);
        }
        if (modifiedUser == null) {
            throw new ServiceException("modifiedUser can't be null");
        }

        //修改valid
        int row = manUserDao.updateValidById(id,valid,modifiedUser);
        if (row == 0) {
            throw new ServiceException("Failed");
        }

    }

    @Override
    @RequiredLog("保存用户")
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void saveObject(ManUser manUser,Integer... roleIds) {
        //校检
        if (manUser.getUsername() == null) {
            throw new ServiceException("username can't be null");
        }
        if (manUser.getPassword() == null) {
            throw new ServiceException("password can't be null");
        }
        if (roleIds == null || roleIds.length==0) {
            throw new ServiceException("pleas choose at least one role");
        }

        //2.保存user
        //2.1 加密salt和password
        String salt = UUID.randomUUID().toString();
        String encryptedPWD = DigestUtils.md5DigestAsHex((salt+manUser.getPassword()).getBytes());
        /*  使用shiro加密
        SimpleHash simpleHash = new SimpleHash(
                "MD5",      //algorithmName 算法名
                manUser.getPassword(),    //source 待加密密码
                salt,                     //salt
                1);          //hashIterations 加密次数
        String encryptedPWD = simpleHash.toHex();
         */
        //2.2 将加密密码和salt封装
        manUser.setPassword(encryptedPWD);
        manUser.setSalt(salt);
        //2.3 持久化到数据库中
        int row1 = manUserDao.saveObject(manUser);
        //System.out.println("userID="+manUser.getId());
        //获取刚才insert的数据的id(已在mapper文件中解决)
        //int userId = manUserDao.getMaxId();
        //保存user_role
        int row2 = manUserRoleDao.saveObjects(manUser.getId(),roleIds);
    }

    /**
     * //用于告诉spring，此方法的返回结果进行cache
     * userCache是缓存名称
     * 缓存的key默认是方法参数的组合
     */
    @Cacheable(value = "userCache")
    @Override
    public Map<String, Object> findObjectById(Integer id) {
        //校检
        if (id == null || id<=0) {
            throw new ServiceException("wrong id");
        }
        //查询user
        UserDeptVo userDeptVo = manUserDao.findObjectById(id);
        if (userDeptVo == null) {
            throw new ServiceException("the selected user is not exist!");
        }
        //查询roleIds
        List<Integer> roleIds = manUserRoleDao.findRoleIdsByUserId(id);
        //封装信息到map
        Map<String,Object> map = new HashMap<>();
        map.put("user",userDeptVo);
        map.put("roleIds",roleIds);
        return map;
    }

    @Override
    @RequiredLog("更新用户")
    //清除缓存（注意key的格式）
    @CacheEvict(value = {"userCache"},key = "#manUser.id")
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void updateObject(ManUser manUser, Integer... roleIds) {
        //1.校检
        if (manUser.getUsername() == null) {
            throw new ServiceException("username can't be null");
        }
        if (roleIds == null || roleIds.length==0) {
            throw new ServiceException("pleas choose at least one role");
        }
        //2.更新user
        int row1 = manUserDao.updateObject(manUser);
        //跟新user_role
        int row2 = manUserRoleDao.deleteObjectsByUserId(manUser.getId());
        int row3 = manUserRoleDao.saveObjects(manUser.getId(),roleIds);
        if (row1==0 || row2==0 || row3==0)
            throw new ServiceException("failed1");
    }
}
