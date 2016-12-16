package cn.mapway.document.ui.client.module;

import cn.mapway.document.ui.client.main.FieldCode;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

// TODO: Auto-generated Javadoc
/**
 * The Class ObjectInfo.
 */
/*
 * 类对象信息
 */
public class ObjectInfo extends JavaScriptObject {

	/**
	 * Instantiates a new object info.
	 */
	protected ObjectInfo() {
	}

	/**
	 * Name.
	 *
	 * @return the string
	 */
	public final native String name()/*-{
		return this.name;
	}-*/;

	/**
	 * Type.
	 *
	 * @return the string
	 */
	public final native String type()/*-{
		return this.type;
	}-*/;

	/**
	 * Summary.
	 *
	 * @return the string
	 */
	public final native String summary()/*-{
		return this.summary;
	}-*/;

	/**
	 * Title.
	 *
	 * @return the string
	 */
	public final native String title()/*-{
		return this.title;
	}-*/;

	/**
	 * Example.
	 *
	 * @return the string
	 */
	public final native String example()/*-{
		return this.example;
	}-*/;

	/**
	 * Manditary.
	 *
	 * @return true, if successful
	 */
	public final native boolean manditary()/*-{
		return this.manditary;
	}-*/;

	/**
	 * Length.
	 *
	 * @return the int
	 */
	public final native int length()/*-{
		return this.length;
	}-*/;

	/**
	 * Fields.
	 *
	 * @return the js array
	 */
	public final native JsArray<ObjectInfo> fields()/*-{
		return this.fields || [];
	}-*/;

	/**
	 * Json.
	 *
	 * @return the string
	 */
	public final native String json() /*-{
		return this.json;
	}-*/;

	public final native JsArray<FieldCode> codes()/*-{
		return this.codes;
	}-*/;
}
