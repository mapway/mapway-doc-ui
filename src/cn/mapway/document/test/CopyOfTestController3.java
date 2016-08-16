package cn.mapway.document.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.mapway.document.annotation.Doc;

@Doc(value = "ABCD", group = "/泛能",desc="今天我们做对了吗?")
@Controller
@RequestMapping(value="doc/456/")
public class CopyOfTestController3 {
	
	
}
