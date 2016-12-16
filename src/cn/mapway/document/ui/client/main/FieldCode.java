package cn.mapway.document.ui.client.main;

import com.google.gwt.core.client.JavaScriptObject;

public class FieldCode extends JavaScriptObject {

	protected FieldCode() {
	}

	public final native String value()/*-{
		return this.value;
	}-*/;

	public final native String desc()/*-{
		return this.desc;
	}-*/;
}
