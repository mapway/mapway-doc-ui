package cn.mapway.document.test;

import java.util.List;

import cn.mapway.document.annotation.ApiField;
import cn.mapway.document.annotation.Doc;

@Doc("请求参数积累")
public class Req {

	@ApiField("int field")
	public int f1;

	@ApiField("booelan field")
	public Boolean booleaField;

	@ApiField("List test")
	public List<String> names;

	@ApiField("List test2")
	public List<Ret> rets;

	@ApiField("ApiDoc")
	public TestObj testObj;

}
