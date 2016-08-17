package cn.mapway.document.test;

import cn.mapway.document.annotation.ApiField;
import cn.mapway.document.annotation.Doc;

@Doc(value="touche返回值",desc="Hellorwrewrewr")
public class Ret {
	@ApiField(value="double value",example="35.4")
	public Double d;
}
