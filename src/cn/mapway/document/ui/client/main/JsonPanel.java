package cn.mapway.document.ui.client.main;

import cn.mapway.document.ui.client.resource.CssStyle;
import cn.mapway.document.ui.client.resource.SysResource;

import com.google.gwt.user.client.ui.HTML;

public class JsonPanel extends HTML {

	public JsonPanel() {
	}

	public void setJson(String json) {
		CssStyle css = SysResource.INSTANCE.getCss();
		String html = highted(json, css.cssNumber(), css.cssKey(), css.cssString(), css.cssBoolean(),
				css.cssNull());
		this.setHTML("<pre class='"+css.pre()+"'>"+html+"</pre>");
	}

	/**
	 * 
	 * @param json
	 * @param cssNumber
	 * @param cssKey
	 * @param cssString
	 * @param cssBoolean
	 * @param cssNull
	 * @return
	 */
	private native String highted(String json, String cssNumber, String cssKey,
			String cssString, String cssBoolean, String cssNull)/*-{
		
		json = json.replace(/&/g, '&').replace(/</g, '<').replace(/>/g, '>');
		return json.replace(/("(\\u[a-zA-Z0-9]{4}|\\[^u]|[^\\"])*"(\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g, 
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
		        return '<span class="' + cls + '">' + match + '</span>';
			});
	}-*/;

}
