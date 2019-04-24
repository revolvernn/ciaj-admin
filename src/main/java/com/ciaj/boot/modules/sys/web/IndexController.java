package com.ciaj.boot.modules.sys.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: Ciaj.
 * @Date: 2018/12/4 15:04
 * @Description:
 */
@Controller
public class IndexController {
	@RequestMapping("modules/{module}/{url}.html")
	public String module(@PathVariable("module") String module, @PathVariable("url") String url) {
		return "modules/" + module + "/" + url;
	}

	@RequestMapping(value = {"/", "index.html"})
	public String index() {
		return "index";
	}

	@RequestMapping(value = {"dashboard.html"})
	public String dashboard() {
		return "dashboard";
	}

	@RequestMapping("login.html")
	public String login() {
		return "login";
	}

	@RequestMapping("main.html")
	public String main() {
		return "main";
	}

	@RequestMapping("404.html")
	public String notFound() {
		return "error/404";
	}

}
