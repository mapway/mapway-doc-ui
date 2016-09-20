package cn.mapway.document.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.mapway.document.annotation.Doc;

@Doc(value = "ABCD", group = "/泛能", desc = "ABCD Specail")
@Controller
@RequestMapping(value = "doc/456/")
public class TestController2 {

	@Doc(value = "touch3", group = "/Style")
	@RequestMapping("/touch3")
	public Ret getname3(Req req) {
		return new Ret();
	}

	@Doc(value = "touch4", group = "/Css")
	@RequestMapping("/touch3")
	public Ret getname456(Req req) {
		return new Ret();
	}
}
