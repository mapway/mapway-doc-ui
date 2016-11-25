package cn.mapway.document.test;

import cn.mapway.document.annotation.ApiField;
import cn.mapway.document.annotation.Doc;

// TODO: Auto-generated Javadoc
/**
 * The Class Ret.
 */
@Doc(value="RET对象定义",desc="RET对象描述")
public class Ret {
	
	/** The d. */
	@ApiField(value="double value",example="35.4")
	public Double d;
}
