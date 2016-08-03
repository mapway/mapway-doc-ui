package cn.mapway.document.ui.client.module;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

/*
 * 类对象信息
 */
public class ObjectInfo extends JavaScriptObject {

	protected ObjectInfo() {
	}

	public final native String name()/*-{
		return this.name;
	}-*/;

	public final native String type()/*-{
		return this.type;
	}-*/;

	public final native String summary()/*-{
		return this.summary;
	}-*/;

	public final native String example()/*-{
		return this.example;
	}-*/;

	public final native boolean manditary()/*-{
		return this.manditary;
	}-*/;

	public final native int length()/*-{
		return this.length;
	}-*/;

	public final native JsArray<ObjectInfo> fields()/*-{
		return this.fields;
	}-*/;

	public final native String json() /*-{
		return this.json;
	}-*/;

}
