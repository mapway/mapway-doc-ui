package cn.mapway.document.ui.client.module;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

/**
 * 接口信息
 * 
 * @author zhangjianshe
 *
 */
public class Entry extends JavaScriptObject {

	protected Entry() {
	}

	/** 入口的函数名称. */
	public  final native String methodName()/*-{
		return this.methodName;
	}-*/;

	/** 接口名称. */
	public  final native String title()/*-{
		return this.title;
	}-*/;

	/** 接口说明. */
	public final  native String summary()/*-{
		return this.summary;
	}-*/;

	/** 接口相对路径. */
	public  final native String relativePath()/*-{
		return this.relativePath;
	}-*/;

	/** 调用类型. */
	public final native String invokeMethod()/*-{
		return this.invokeMethod;
	}-*/;

	/** 输入参数. */
	public final  native JsArray<ObjectInfo> input()/*-{
		return this.input;
	}-*/;

	/** 输出参数. */
	public  final native ObjectInfo output()/*-{
		return this.output;
	}-*/;

	/**
	 * 接口的排序值
	 */
	public  final native int order()/*-{
		return this.order;
	}-*/;

	/**
	 * 接口创建者
	 */
	public  final native String author()/*-{
		return this.author;
	}-*/;

	/**
	 * 方法所在的类
	 */
	public  final native String parentClassName()/*-{
		return this.parentClassName;
	}-*/;

	/**
	 * 开发状态
	 */
	// public DevelopmentState state;
}
