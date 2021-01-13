package com.future.sm.manager.service.realm;

import com.future.sm.manager.dao.ManMenuDao;
import com.future.sm.manager.dao.ManRoleMenuDao;
import com.future.sm.manager.dao.ManUserDao;
import com.future.sm.manager.dao.ManUserRoleDao;
import com.future.sm.manager.pojo.ManUser;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ShiroUserRealm extends AuthorizingRealm {

    @Autowired
    private ManUserDao manUserDao;
    @Autowired
    private ManUserRoleDao manUserRoleDao;
    @Autowired
    private ManRoleMenuDao manRoleMenuDao;
    @Autowired
    private ManMenuDao manMenuDao;

    /**
     * 设置凭证匹配器(与用户添加操作使用相同的加密算法)
     */
    @Override
    public void setCredentialsMatcher(
            CredentialsMatcher credentialsMatcher) {
        //构建凭证匹配对象
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        //设置加密算法
        matcher.setHashAlgorithmName("MD5");
        //设置加密次数
        matcher.setHashIterations(1);
        super.setCredentialsMatcher(matcher);

    }
    /**
     * 通过此方法完成认证数据的获取及封装,系统
     * 底层会将认证数据传递认证管理器，由认证
     * 管理器完成认证操作。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken token)
            throws AuthenticationException {
        //1.获取用户名(用户页面输入)
        UsernamePasswordToken upToken = (UsernamePasswordToken)token;
        //2.基于用户名查询用户信息
        ManUser manUser = manUserDao.findUserByUsername(upToken.getUsername());
        //3.判定用户是否存在
        if (manUser == null) {
            throw new UnknownAccountException();
        }
        //4.判定用户是否已被禁用。
        if (manUser.getValid()==0)
            throw new LockedAccountException();

        //5.封装用户信息
        ByteSource credentialsSalt = new SimpleByteSource(manUser.getSalt());

        //记住：构建什么对象要看方法的返回值
        AuthenticationInfo info = new SimpleAuthenticationInfo(
                manUser,//Object principal当事人
                manUser.getPassword(),//Object hashedCredentials
                credentialsSalt,//ByteSource credentialsSalt
                "shiroUserRealm"//String realmName
        );
        //6.返回封装结果
        return info;//返回值会传递给认证管理器(后续
        //认证管理器会通过此信息完成认证操作)
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principalCollection) {

        System.out.println("getAuthorizationInfo: "+ "没走缓存");

        //1.获取登录用户信息，例如用户id.
        //(这里获取的主身份依据登陆时传入的值，也就是上面代码77行
        // AuthenticationInfo info = new SimpleAuthenticationInfo(
        //                manUser,//Object principal当事人
        //                manUser.getPassword(),//Object hashedCredentials
        //                credentialsSalt,//ByteSource credentialsSalt
        //                "shiroUserRealm"//String realmName)
        //也就是manUser
        ManUser manUser =
                (ManUser)principalCollection.getPrimaryPrincipal();
        int userId = manUser.getId();
        //2.基于用户id获取用户拥有的角色(sys_user_roles)
        List<Integer> roleIds =
                manUserRoleDao.findRoleIdsByUserId(userId);
        if(roleIds==null||roleIds.size()==0)
            throw new AuthorizationException();

        //3.基于角色id获取菜单id(sys_role_menus)
        Integer[] arr = {};
        System.out.println("arr.length : "+arr.length);
        List<Integer> menuIds =
                manRoleMenuDao.findMenuIdsByRoleIds(roleIds.toArray(arr));
        if (menuIds == null) {
            throw new AuthorizationException();
        }
        //4.基于菜单id获取权限标识(sys_menus)
        List<String> permissions =
                manMenuDao.findPermissionsByIds(menuIds.toArray(arr));
        //5.对权限标识信息进行封装并返回
        //为什么会用set呢？因为有些菜单的权限是重复的，用set可以去重
        Set<String> set = new HashSet<>();
        for (String permission :
                permissions) {
            set.add(permission);
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(set);
        return info;//返回给授权管理器

    }
}
