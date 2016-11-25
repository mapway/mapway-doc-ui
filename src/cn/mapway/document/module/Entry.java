package cn.mapway.document.module;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * 接口信息.
 *
 * @author zhangjianshe
 */
public class Entry implements Serializable {

	/**
	 * Instantiates a new entry.
	 */
	public Entry() {
	}

	/** 入口的函数名称. */
	public String methodName;

	/** 接口名称. */
	public String title;

	/** 接口说明. */
	public String summary = "";

	/** 接口相对路径. */
	public String relativePath = "";

	/** 接口路径. */
	public String url = "";

	/** 调用类型. */
	public String invokeMethod = "";

	/** 输入参数. */
	public List<ObjectInfo> input = new ArrayList<ObjectInfo>();

	/** 输出参数. */
	public ObjectInfo output;

	/** 接口的排序值. */
	public int order;

	/** 接口创建者. */
	public String author;

	/** 方法所在的类. */
	public String parentClassName;

	/** 开发状态. */
	public String state;
}
