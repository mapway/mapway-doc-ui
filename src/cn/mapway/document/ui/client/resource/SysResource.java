package cn.mapway.document.ui.client.resource;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Tree;

/**
 * 系统资源
 * 
 * @author zhangjianshe
 *
 */
public interface SysResource extends ClientBundle {
	public final static SysResource INSTANCE = GWT.create(SysResource.class);

	@Source("style.css")
	@CssResource.NotStrict
	CssStyle getCss();

	@Source("image/logo.png")
	ImageResource logo();

	@Source("image/arrow_left_white.png")
	ImageResource arrow_left_white();

	@Source("image/plus.png")
	ImageResource plus();

	@Source("image/minus.png")
	ImageResource minus();

	@Source("image/inter.png")
	ImageResource inter();
}
