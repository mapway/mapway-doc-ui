package cn.mapway.document.test;

import cn.mapway.document.annotation.ApiField;
import cn.mapway.document.annotation.Doc;

@Doc(value="RET对象定义",desc="RET对象描述")
public class Ret {
	@ApiField(value="double value",example="35.4")
	public Double d;
}
