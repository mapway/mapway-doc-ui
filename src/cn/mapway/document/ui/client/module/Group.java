package cn.mapway.document.ui.client.module;


import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

// TODO: Auto-generated Javadoc
/**
 * 接口组对象.
 *
 * @author zhangjianshe
 */
public class Group extends JavaScriptObject {

	/**
	 * Instantiates a new group.
	 */
	protected Group() {
	}

	/**
	 *  节点名称.
	 *
	 * @return the string
	 */
	public  final native String name()/*-{
		return this.name;
	}-*/;

	/**
	 *  节点说明.
	 *
	 * @return the string
	 */
	public  final native String summary()/*-{
		return this.summary;
	}-*/;

	/**
	 * 子节点.
	 *
	 * @return the js array
	 */
	public  final native JsArray<Entry> entries()/*-{
		return this.entries;
	}-*/;

	/**
	 * 子分组.
	 *
	 * @return the js array
	 */
	public final native JsArray<Group> subGroups()/*-{
		return this.subGroups;
	}-*/;
}
