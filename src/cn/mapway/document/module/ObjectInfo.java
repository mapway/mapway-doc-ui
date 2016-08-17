package cn.mapway.document.module;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*
 * 类对象信息
 */
public class ObjectInfo implements Serializable {

	public ObjectInfo() {
	}

	public String name;
	public String type;
	public String summary;
	public String title;
	public String example;
	public boolean manditary;
	public int length = 0;
	public List<ObjectInfo> fields = new ArrayList<ObjectInfo>();

	/**
	 * 对象的JSON例子
	 */
	public String json;
}
