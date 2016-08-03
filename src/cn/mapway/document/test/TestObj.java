package cn.mapway.document.test;

import java.util.List;

import cn.mapway.document.annotation.ApiField;
import cn.mapway.document.annotation.Doc;

@Doc("sad")
public class TestObj {
	@ApiField("eq")
	public int a;
	@ApiField("eq")
	public Integer b;
	@ApiField("eq")
	public List<String> c;
	@ApiField("eq")
	public List<Req> d;

}
