package cn.mapway.document.ui.client.module;

import com.google.gwt.core.client.JavaScriptObject;

// TODO: Auto-generated Javadoc
/**
 * The Class JarInfo.
 *
 * @author zhangjianshe
 */
public class JarInfo extends JavaScriptObject {

	/**
	 * Instantiates a new jar info.
	 */
	protected JarInfo() {
	}

	/**
	 * File name.
	 *
	 * @return the string
	 */
	public final native String fileName()/*-{
		return this.fileName;
	}-*/;

	/**
	 * Path.
	 *
	 * @return the string
	 */
	public final native String path()/*-{
		return this.path;
	}-*/;

	/**
	 * Size.
	 *
	 * @return the long
	 */
	public final native Long size()/*-{
		return this.size;
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
	 * Link.
	 *
	 * @return the string
	 */
	public final native String link()/*-{
		return this.link;
	}-*/;

	/**
	 * Link.
	 *
	 * @param url the url
	 */
	public final native void link(String url)/*-{
		return this.link = url;
	}-*/;

}
