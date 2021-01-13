package com.future.sm.manager.controller;

import com.future.sm.manager.pojo.ManUser;
import com.future.sm.manager.service.ManUserService;
import com.future.sm.manager.vo.JsonResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user/")
public class ManUserController {
    @Autowired
    private ManUserService manUserService;

    @RequestMapping("doFindPageObjects")
    @ResponseBody
    public JsonResult doFindPageObjects(Integer pageCurrent,String username){
        return new JsonResult(manUserService.findPageObjects(pageCurrent,username));
    }

    @ResponseBody
    @RequestMapping("doValidById")
    public JsonResult doValidById(Integer id,Integer valid){
        manUserService.updateValidById(id,valid,"admin");
        return new JsonResult("successful!");
    }

    @ResponseBody
    @RequestMapping("doSaveObject")
    public JsonResult doSaveObject(ManUser manUser,Integer... roleIds){
        manUserService.saveObject(manUser,roleIds);
        return new JsonResult("successful!!");
    }

    @ResponseBody
    @RequestMapping("doFindObjectById")
    public JsonResult doFindObjectById(Integer id){
        return new JsonResult(manUserService.findObjectById(id));
    }

    @ResponseBody
    @RequestMapping("doUpdateObject")
    public JsonResult doUpdateObject(ManUser manUser,Integer... roleIds){
        manUserService.updateObject(manUser,roleIds);
        return new JsonResult("successful!!");
    }

    @ResponseBody
    @RequestMapping("doLogin")
    public JsonResult doLogin(String username,
                              String password,
                              boolean isRememberMe){
        //1.获取Subject对象
        Subject subject = SecurityUtils.getSubject();
        //2.通过Subject提交用户信息,交给shiro框架进行认证操作
        //2.1对用户进行封装
        UsernamePasswordToken upToken =
                new UsernamePasswordToken(username, password);
        //2.1.1如果用户勾选了“记住我”,将勾选信息记录
        if(isRememberMe)
            upToken.setRememberMe(true);
        //2.2对用户信息进行身份认证
        subject.login(upToken);
        //分析:
        //1)token会传给shiro的SecurityManager
        //2)SecurityManager将token传递给认证管理器
        //3)认证管理器会将token传递给realm
        return new JsonResult("login ok");
    }
}
