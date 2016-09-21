package cn.mapway.document.ui.client.main;

import cn.mapway.document.ui.client.resource.CssStyle;
import cn.mapway.document.ui.client.resource.SysResource;

import com.google.gwt.user.client.ui.HTML;

// TODO: Auto-generated Javadoc
/**
 * The Class JsonPanel.
 */
public class JsonPanel extends HTML {

	/**
	 * Instantiates a new json panel.
	 */
	public JsonPanel() {
	}

	/**
	 * Sets the json.
	 *
	 * @param json the new json
	 */
	public void setJson(String json) {
		CssStyle css = SysResource.INSTANCE.getCss();
		String html = highted(json, css.cssNumber(), css.cssKey(),
				css.cssString(), css.cssBoolean(), css.cssNull());
		this.setHTML("<pre class='" + css.pre() + "'>" + html + "</pre>");
	}

	/**
	 * Sets the string.
	 *
	 * @param text the new string
	 */
	public void setString(String text) {
		CssStyle css = SysResource.INSTANCE.getCss();
		this.setHTML("<pre class='" + css.pre() + "'>" + text + "</pre>");
	}

	/**
	 * Highted.
	 *
	 * @param json the json
	 * @param cssNumber the css number
	 * @param cssKey the css key
	 * @param cssString the css string
	 * @param cssBoolean the css boolean
	 * @param cssNull the css null
	 * @return the string
	 */
	private native String highted(String json, String cssNumber, String cssKey,
			String cssString, String cssBoolean, String cssNull)/*-{

		json = json.replace(/&/g, '&').replace(/</g, '<').replace(/>/g, '>');
		return json
				.replace(
						/("(\\u[a-zA-Z0-9]{4}|\\[^u]|[^\\"])*"(\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g,
						function(match) {
							var cls = cssNumber;
							if (/^"/.test(match)) {
								if (/:$/.test(match)) {
									cls = cssKey;
								} else {
									cls = cssString;
								}
							} else if (/true|false/.test(match)) {
								cls = cssBoolean;
							} else if (/null/.test(match)) {
								cls = cssNull;
							}
							return '<span class="' + cls + '">' + match
									+ '</span>';
						});
	}-*/;

}
