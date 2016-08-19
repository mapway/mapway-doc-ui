package cn.mapway.document.ui.client.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.mapway.document.ui.client.module.Entry;
import cn.mapway.document.ui.client.module.GenInfo;
import cn.mapway.document.ui.client.module.ObjectInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class EntryPanel extends Composite {

	private static EntryPanelUiBinder uiBinder = GWT
			.create(EntryPanelUiBinder.class);

	interface EntryPanelUiBinder extends UiBinder<Widget, EntryPanel> {
	}

	public EntryPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void parse(Entry e) {

		lbTITLE.setText(e.title());
		lbSUMMARY.setHTML(e.summary());
		lbURL.setText("接口网址:  " + e.relativePath());
		lbAUTHOR.setText("作者:" + e.author());
		lbINVOKE.setText("调用方法:" + e.invokeMethod());

		paraIn.parse(e.input().get(0), "传入参数");
		paraOut.parse(e.output(), "传出参数");

		String html = "<p>JAVA源码信息<br/><table cellpadding='5px'>";
		html += "<tr><td>控制类</td><td>" + e.parentClassName() + "</td></tr>";
		html += "<tr><td>控制方法</td><td>" + e.methodName() + "</td></tr>";

		if (e.input().length() > 0) {
			html += "<tr><td>输入参数 </td><td>" + e.input().get(0).type()
					+ "</td></tr>";
		}
		if (e.output() != null) {
			html += "<tr><td>输出参数</td><td>" + e.output().type() + "</td></tr>";
		}

		html += "</table>";
		javaSource.setHTML(html);
	}

	@UiField
	HTML javaSource;

	@UiField
	Label lbTITLE;

	@UiField
	HTML lbSUMMARY;

	@UiField
	Label lbURL;
	@UiField
	Label lbAUTHOR;
	@UiField
	Label lbINVOKE;

	@UiField
	ParameterPanel paraIn;

	@UiField
	ParameterPanel paraOut;

}
