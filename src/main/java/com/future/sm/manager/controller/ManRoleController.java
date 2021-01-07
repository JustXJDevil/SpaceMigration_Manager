package com.future.sm.manager.controller;

import com.future.sm.common.vo.RoleMenuVo;
import com.future.sm.manager.pojo.ManRole;
import com.future.sm.manager.service.ManRoleService;
import com.future.sm.manager.vo.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/role/")
public class ManRoleController {
    @Autowired
    private ManRoleService service;

    @RequestMapping("doFindPageObjects")
    @ResponseBody
    public JsonResult doFindPageObjects(String name,Integer pageCurrent){

        return new JsonResult(service.findPageObjects(name,pageCurrent));
    }

    @ResponseBody
    @RequestMapping("doSaveObject")
    public JsonResult doSaveObject(ManRole manRole, Integer... menuIds){
        service.doSaveObject(manRole,menuIds);
        return new JsonResult("save ok!");
    }

    @ResponseBody
    @RequestMapping("doFindObjectById")
    public JsonResult doFindObjectById(Integer id){
        return new JsonResult(service.findObjectById(id));
    }

    @ResponseBody
    @RequestMapping("doUpdateObject")
    public JsonResult doUpdateObject(ManRole manRole,Integer... menuIds){
        service.updateObject(manRole,menuIds);
        return new JsonResult("update ok");
    }

    @ResponseBody
    @RequestMapping("doDeleteObject")
    public JsonResult doDeleteObject(Integer id){
        service.deleteObjectById(id);
        return new JsonResult("delete ok!");
    }
}
