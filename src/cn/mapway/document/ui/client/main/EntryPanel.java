package cn.mapway.document.ui.client.main;

import cn.mapway.document.ui.client.module.Entry;
import cn.mapway.document.ui.client.resource.SysResource;
import cn.mapway.document.ui.client.test.TestPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class EntryPanel extends Composite {

	private static EntryPanelUiBinder uiBinder = GWT
			.create(EntryPanelUiBinder.class);

	interface EntryPanelUiBinder extends UiBinder<Widget, EntryPanel> {
	}

	public EntryPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		btnTest.setStyleName(SysResource.INSTANCE.getCss().btn());
	}

	Entry mEntry;

	public void parse(Entry e) {
		mEntry = e;
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

	DialogBox dlg;
	TestPanel testPanel;

	@UiField
	Button btnTest;

	@UiHandler("btnTest")
	void onTest(ClickEvent e) {
		if (dlg == null) {
			dlg = new DialogBox();
			testPanel = new TestPanel();
			testPanel.addCloseHandler(new CloseHandler<Void>() {

				@Override
				public void onClose(CloseEvent<Void> event) {
					dlg.hide();
				}
			});
			dlg.setWidget(testPanel);
			dlg.setGlassEnabled(true);
			dlg.setAutoHideEnabled(false);
			dlg.setStyleName(SysResource.INSTANCE.getCss().dlg());
		}
		dlg.show();
		dlg.center();
		testPanel.invoke(mEntry);
	}

}
