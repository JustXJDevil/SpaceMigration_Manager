package com.future.sm.manager.controller;

import com.future.sm.manager.pojo.ManUser;
import com.future.sm.manager.service.ManUserService;
import com.future.sm.manager.vo.JsonResult;
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
}
