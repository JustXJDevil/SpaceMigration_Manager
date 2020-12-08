package com.future.sm.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PageController {
	
	@RequestMapping("doIndexUI")
	public String doIndexUI() {
		return "starter";
	}
	
	//加载分页按钮
	@RequestMapping("doPageUI")
	public String loadPage() {
		return "common/page";
	}
}
