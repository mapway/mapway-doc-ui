package cn.mapway.document.test;

import cn.mapway.document.annotation.ApiField;
import cn.mapway.document.annotation.Code;
import cn.mapway.document.annotation.Codes;
import cn.mapway.document.annotation.Doc;

// TODO: Auto-generated Javadoc
/**
 * The Class Ret.
 */
@Doc(value = "RET对象定义", desc = "RET对象描述")
public class Ret {

	/**
	 * sads
	 */
	@ApiField(value = "double value", example = "35.4")
	@Codes({ @Code(value = "0", desc = "调用成功"),
			@Code(value = "123", desc = "调用成功"),
			@Code(value = "1200", desc = "参数校验失败") })
	public Double d;
}
