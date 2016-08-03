package cn.mapway.document.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.mapway.document.annotation.Doc;

@Doc(value = "ABCD", group = "/Test")
@Controller
public class TestController {
	
	@Doc(value="Name fetch")
	@RequestMapping("/touch")
	public Ret getname(Req req)
	{
		return null;
	}
}
