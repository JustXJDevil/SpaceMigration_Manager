package com.future.sm.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/log/")
public class LogController {
	@RequestMapping("log_list")
	public String logList() {
		return "sys/log_list";
	}
	
	@RequestMapping("page")
	public String loadPage() {
		return "common/page";
	}
}
