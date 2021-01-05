package com.future.sm.manager.controller;

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
}
