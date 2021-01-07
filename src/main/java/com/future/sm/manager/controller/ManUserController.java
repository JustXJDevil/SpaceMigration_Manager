package com.future.sm.manager.controller;

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
}
