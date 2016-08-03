package cn.mapway.document.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.mapway.document.annotation.Doc;

@Doc(value = "ABCD", group = "/泛能/APP接口")
@Controller
@RequestMapping(value="doc/456/")
public class TestController2 {
	
	@Doc(value="touch3")
	@RequestMapping("/touch3")
	public Ret getname3(Req req)
	{
		return null;
	}
}
