package cn.mapway.document.test;

import java.util.List;

import cn.mapway.document.annotation.ApiField;
import cn.mapway.document.annotation.Doc;

// TODO: Auto-generated Javadoc
/**
 * The Class TestObj.
 */
@Doc(value="TestObject in TestObject",desc="1.场景ID 统一为 Long.2.定义场景结构由原来的一个场景下可以有多个执行单元 改为一个场景下只允许存在一个执行单元，这样结构简化为如下的形式：")
public class TestObj {
	
	/** The a. */
	@ApiField("eq")
	public int a;
	
	/** The b. */
	@ApiField("eq")
	public Integer b;
	
	/** The c. */
	@ApiField("eq")
	public List<String> c;
	
	/** The d. */
	@ApiField("eq")
	public List<Req> d;

}
