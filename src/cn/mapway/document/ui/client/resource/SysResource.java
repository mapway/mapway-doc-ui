package cn.mapway.document.ui.client.resource;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.TextResource;
import com.google.gwt.user.client.ui.Tree;

// TODO: Auto-generated Javadoc
/**
 * 系统资源.
 *
 * @author zhangjianshe
 */
public interface SysResource extends ClientBundle {
	
	/** The Constant INSTANCE. */
	public final static SysResource INSTANCE = GWT.create(SysResource.class);

	/**
	 * Gets the css.
	 *
	 * @return the css
	 */
	@Source("style.css")
	@CssResource.NotStrict
	CssStyle getCss();

	/**
	 * Logo.
	 *
	 * @return the image resource
	 */
	@Source("image/logo.png")
	ImageResource logo();

	/**
	 * Loading.
	 *
	 * @return the image resource
	 */
	@Source("image/loadding.gif")
	ImageResource loading();

	/**
	 * Arrow left white.
	 *
	 * @return the image resource
	 */
	@Source("image/arrow_left_white.png")
	ImageResource arrow_left_white();

	/**
	 * Plus.
	 *
	 * @return the image resource
	 */
	@Source("image/plus.png")
	ImageResource plus();

	/**
	 * Minus.
	 *
	 * @return the image resource
	 */
	@Source("image/minus.png")
	ImageResource minus();

	/**
	 * Inter.
	 *
	 * @return the image resource
	 */
	@Source("image/inter.png")
	ImageResource inter();

	/**
	 * Tree open.
	 *
	 * @return the image resource
	 */
	@Source("image/treeOpen.png")
	ImageResource treeOpen();

	/**
	 * Tree close.
	 *
	 * @return the image resource
	 */
	@Source("image/treeClose.png")
	ImageResource treeClose();

	/**
	 * History.
	 *
	 * @return the image resource
	 */
	@Source("image/history.png")
	ImageResource history();

	/**
	 * Main css.
	 *
	 * @return the text resource
	 */
	@Source("main.css")
	TextResource mainCss();

	/**
	 * Word.
	 *
	 * @return the image resource
	 */
	@Source("image/word.png")
	ImageResource word();

	/**
	 * Jsondata.
	 *
	 * @return the text resource
	 */
	@Source("data.txt")
	TextResource jsondata();

}
