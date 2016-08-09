package cn.mapway.document.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.mapway.document.annotation.Doc;

@Doc(value = "ABCD", group = "/泛能云/APP接口/Tst", desc = "然后循环提取某个键值对的key和value，"
		+ "对于value我们可以直接使用之前的策略进行反序列化，因为这部分的结构也是固定的。")
@Controller
@RequestMapping(value = "doc/123/")
public class TestController {

	@Doc(value = "Name fetch", desc = "我们的意图是明确的，将返回的多个Person实体对象放到一个Map结构中。但是问题就在于返回的JSON中的keys是不固定的(比如上述JSON中的keys是人名)，这导致反序列化失败。毕竟默认配置下的ObjectMapper也没有聪明到这种程度，能够猜测你是想要将多个实体放到Map中。")
	@RequestMapping("/touch")
	public Ret getname(Req req) {
		return null;
	}
}
