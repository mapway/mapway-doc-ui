package cn.mapway.document.ui.client.main;

import cn.mapway.document.ui.client.module.Entry;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
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
	}

	public void parse(Entry e) {
		lbTITLE.setText(e.title() + "(" + e.parentClassName() + ")");
		lbSUMMARY.setHTML(e.summary());
		lbURL.setText("URL" + e.relativePath());
		lbAUTHOR.setText("作者:" + e.author());
		lbINVOKE.setText("调用方法:" + e.invokeMethod());

		paraIn.parse(e.input().get(0), "传入参数");
		paraOut.parse(e.output(), "传出参数");

	}

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
