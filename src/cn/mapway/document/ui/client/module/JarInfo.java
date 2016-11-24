package cn.mapway.document.ui.client.module;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * 
 * @author zhangjianshe
 *
 */
public class JarInfo extends JavaScriptObject {

	protected JarInfo() {
	}

	public final native String fileName()/*-{
		return this.fileName;
	}-*/;

	public final native String path()/*-{
		return this.path;
	}-*/;

	public final native Long size()/*-{
		return this.size;
	}-*/;

	public final native String summary()/*-{
		return this.summary;
	}-*/;

	public final native String link()/*-{
		return this.link;
	}-*/;

	public final native void link(String url)/*-{
		return this.link = url;
	}-*/;

}
