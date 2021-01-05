package com.future.sm.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.future.sm.common.vo.Node;
import com.future.sm.manager.pojo.ManMenu;
import com.future.sm.manager.service.ManMenuService;
import com.future.sm.manager.vo.JsonResult;

@Controller
@RequestMapping("/menu/")
public class ManMenuController {
	@Autowired
	private ManMenuService service;
	
	@ResponseBody
	@RequestMapping("doFindObjects")
	public JsonResult findObjects() {
		Object o = service.findObjects();
		return new JsonResult(o);
	}
	@RequestMapping("doDeleteObject")
	@ResponseBody
	public JsonResult deleteObject(Integer id) {
		service.deleteObject(id);
		return new JsonResult("delete OK!!!");
	}
	
	@RequestMapping("doUpdateObject")
	@ResponseBody
	public JsonResult updateObject(ManMenu menu) {
		service.updateObject(menu);		
		return new JsonResult("update successful!!!");		
	}
	
	@RequestMapping("doFindZtreeMenuNodes")
	@ResponseBody
	public JsonResult findZtreeMenuNodes() {
		
		return new JsonResult(service.findZtreeMenuNodes());
		
	}
	@ResponseBody
	@RequestMapping("doSaveObject")
	public JsonResult insertObject(ManMenu menu) {
		
		service.insertObject(menu);
		return new JsonResult("insert succesful!!!");
		
	}
	
}
