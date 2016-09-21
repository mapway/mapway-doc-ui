package cn.mapway.document.ui.client.module;


import com.google.gwt.core.client.JavaScriptObject;


// TODO: Auto-generated Javadoc
/**
 * JSON 对象.
 *
 * @author zhangjianshe
 */
public class ApiDoc extends JavaScriptObject {

	/**
	 * Instantiates a new api doc.
	 */
	protected  ApiDoc() {
	}

	/**
	 * 作者.
	 *
	 * @return the string
	 */
	public final native String author()/*-{
		return this.author;
	}-*/;

	/**
	 * 标题.
	 *
	 * @return the string
	 */
	public final native String title()/*-{
		return this.title;
	}-*/;

	/**
	 * API版本.
	 *
	 * @return the string
	 */
	public final native String version()/*-{
		return this.version;
	}-*/;

	/**
	 * API关联网址.
	 *
	 * @return the string
	 */
	public final native String link()/*-{
		return this.link;
	}-*/;

	/**
	 * API简介.
	 *
	 * @return the string
	 */
	public final native String summary()/*-{
		return this.summary;
	}-*/;;

	/**
	 * API 的整体说明文档.
	 *
	 * @return the string
	 */
	public final native String description()/*-{
		return this.description;
	}-*/;

	/**
	 * 接口访问的基地址.
	 *
	 * @return the string
	 */
	public final native String basePath()/*-{
		return this.basePath;
	}-*/;

	/**
	 * API分组的根节点.
	 *
	 * @return the group
	 */
	public final native Group root()/*-{
		return this.root;
	}-*/;
	
	/**
	 * WordUrl
	 *
	 * @return the group
	 */
	public final native String wordUrl()/*-{
		return this.wordUrl;
	}-*/;
}
