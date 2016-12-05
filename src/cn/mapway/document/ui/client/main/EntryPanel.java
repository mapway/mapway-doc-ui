package cn.mapway.document.ui.client.main;

import cn.mapway.document.ui.client.component.Clients;
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

// TODO: Auto-generated Javadoc
/**
 * The Class EntryPanel.
 */
public class EntryPanel extends Composite {

	/** The ui binder. */
	private static EntryPanelUiBinder uiBinder = GWT
			.create(EntryPanelUiBinder.class);

	/**
	 * The Interface EntryPanelUiBinder.
	 */
	interface EntryPanelUiBinder extends UiBinder<Widget, EntryPanel> {
	}

	/**
	 * Instantiates a new entry panel.
	 */
	public EntryPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		btnTest.setStyleName(SysResource.INSTANCE.getCss().btn());
	}

	/** The m entry. */
	Entry mEntry;

	/**
	 * Parses the.
	 *
	 * @param e
	 *            the e
	 */
	public void parse(Entry e) {
		mEntry = e;
		lbTITLE.setText(e.title());
		lbSUMMARY.setHTML(e.summary());
		lbURL.setText("接口网址:  " + Clients.getHostPort() + e.url());
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

	/** The java source. */
	@UiField
	HTML javaSource;

	/** The lb TITLE. */
	@UiField
	Label lbTITLE;

	/** The lb SUMMARY. */
	@UiField
	HTML lbSUMMARY;

	/** The lb URL. */
	@UiField
	Label lbURL;

	/** The lb AUTHOR. */
	@UiField
	Label lbAUTHOR;

	/** The lb INVOKE. */
	@UiField
	Label lbINVOKE;

	/** The para in. */
	@UiField
	ParameterPanel paraIn;

	/** The para out. */
	@UiField
	ParameterPanel paraOut;

	/** The dlg. */
	DialogBox dlg;

	/** The test panel. */
	TestPanel testPanel;

	/** The btn test. */
	@UiField
	Button btnTest;

	/**
	 * On test.
	 *
	 * @param e
	 *            the e
	 */
	@UiHandler("btnTest")
	void onTest(ClickEvent e) {
		if (dlg == null) {
			dlg = new DialogBox();
			dlg.setText("接口测试");
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
