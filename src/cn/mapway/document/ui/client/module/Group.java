package cn.mapway.document.ui.client.module;


import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

/**
 * 接口组对象
 * 
 * @author zhangjianshe
 *
 */
public class Group extends JavaScriptObject {

	protected Group() {
	}

	/** 节点名称. */
	public  final native String name()/*-{
		return this.name;
	}-*/;

	/** 节点说明. */
	public  final native String summary()/*-{
		return this.summary;
	}-*/;

	/**
	 * 子节点
	 */
	public  final native JsArray<Entry> entries()/*-{
		return this.entries;
	}-*/;

	/**
	 * 子分组
	 */
	public final native JsArray<Group> subGroups()/*-{
		return this.subGroups;
	}-*/;
}
