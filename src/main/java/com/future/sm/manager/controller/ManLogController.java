package com.future.sm.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.future.sm.manager.pojo.ManLog;
import com.future.sm.manager.service.ManLogService;
import com.future.sm.manager.vo.JsonResult;
import com.future.sm.manager.vo.PageObject;

@Controller
@RequestMapping("/log/")
public class ManLogController {
	@Autowired
	private ManLogService service;
	//加载列表页面
	@RequestMapping("log_list")
	public String logList() {
		return "sys/log_list";
	}
	
	
	@RequestMapping("doFindPageObjects")
	@ResponseBody
	public JsonResult doFindPageObjects(
			String username,
			Integer pageCurrent) {
		PageObject<ManLog> po = service.findPageObject(username, pageCurrent);
		JsonResult res = new JsonResult();
		res.setData(po);
		return res;
	}
	
	@RequestMapping("doDeleteObjects")
	@ResponseBody
	public JsonResult doDeleteObjects(Integer... ids) {
		service.doDeleteObjects(ids);
		return new JsonResult("delete ok");
	}
}
