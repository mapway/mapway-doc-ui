package cn.mapway.document.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.mapway.document.annotation.Doc;

// TODO: Auto-generated Javadoc
/**
 * The Class TestController2.
 */
@Doc(value = "ABCD", group = "/泛能", desc = "ABCD Specail")
@Controller
@RequestMapping(value = "doc/456/")
public class TestController2 {

	/**
	 * Gets the name 3.
	 *
	 * @param req the req
	 * @return the name 3
	 */
	@Doc(value = "touch3", group = "/Style")
	@RequestMapping("/touch3")
	public Ret getname3(Req req) {
		return new Ret();
	}

	/**
	 * Gets the name 456.
	 *
	 * @param req the req
	 * @return the name 456
	 */
	@Doc(value = "touch4", group = "/Css")
	@RequestMapping("/touch3")
	public Ret getname456(Req req) {
		return new Ret();
	}
}
