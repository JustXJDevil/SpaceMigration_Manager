package com.future.sm.manager.controller;

import com.future.sm.manager.dao.ManDeptDao;
import com.future.sm.manager.vo.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/dept/")
public class ManDeptController {
    @Autowired
    private ManDeptDao manDeptDao;

    @RequestMapping("doFindZTreeNodes")
    @ResponseBody
    public JsonResult doFindZTreeNodes(){
        return new JsonResult(manDeptDao.findZTreeNodes());
    }
}
