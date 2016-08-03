package cn.mapway.document.ui.client.main;

import cn.mapway.document.ui.client.module.ObjectInfo;
import cn.mapway.document.ui.client.resource.SysResource;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.HTMLTable.RowFormatter;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

/**
 * 参数面板
 * 
 * @author zhangjianshe
 *
 */
public class ParameterPanel extends Composite {

	private static ParameterPanelUiBinder uiBinder = GWT
			.create(ParameterPanelUiBinder.class);

	interface ParameterPanelUiBinder extends UiBinder<Widget, ParameterPanel> {
	}

	public ParameterPanel() {
		initWidget(uiBinder.createAndBindUi(this));

		tbl.getElement().setAttribute("borderCollapse", "collapse");
		// 名称 类型 长度 选项 解释
		Label l;
		int col = 0;

		l = new Label("名称");
		l.setStyleName(SysResource.INSTANCE.getCss().tableHeader());
		tbl.setWidget(0, col++, l);

		l = new Label("类型");
		l.setStyleName(SysResource.INSTANCE.getCss().tableHeader());
		tbl.setWidget(0, col++, l);

		l = new Label("长度");
		l.setStyleName(SysResource.INSTANCE.getCss().tableHeader());
		tbl.setWidget(0, col++, l);

		l = new Label("选项");
		l.setStyleName(SysResource.INSTANCE.getCss().tableHeader());
		tbl.setWidget(0, col++, l);

		l = new Label("解释");
		l.setStyleName(SysResource.INSTANCE.getCss().tableHeader());
		tbl.setWidget(0, col++, l);

		RowFormatter rf = tbl.getRowFormatter();
		rf.setStylePrimaryName(0, SysResource.INSTANCE.getCss().rowh());
	}

	@UiField
	Label lbTitle;

	@UiField
	Label lbSummary;

	ObjectInfo mObj;

	public void parse(ObjectInfo obj, String string) {
		mObj = obj;

		lbTitle.setText(string + obj.type());
		lbSummary.setText(obj.summary());

		int rowcount = tbl.getRowCount();
		for (int index = obj.fields().length() + 1; index < rowcount; index++) {
			tbl.removeRow(tbl.getRowCount() - 1);
		}

		RowFormatter rf = tbl.getRowFormatter();

		// 处理字段
		int row = 1;
		for (int i = 0; i < obj.fields().length(); i++) {
			ObjectInfo o = obj.fields().get(i);
			// 名称 类型 长度 选项 解释
			Label l;
			int col = 0;

			if (row % 2 == 0) {
				rf.setStyleName(row, SysResource.INSTANCE.getCss().row0());
			} else {
				rf.setStyleName(row, SysResource.INSTANCE.getCss().row1());
			}

			l = new Label(o.name());

			l.setStyleName(SysResource.INSTANCE.getCss().name());
			tbl.setWidget(row, col++, l);

			l = new Label(o.type());
			l.setStyleName(SysResource.INSTANCE.getCss().type());
			tbl.setWidget(row, col++, l);

			l = new Label(o.length() + "");
			l.setStyleName(SysResource.INSTANCE.getCss().text());
			tbl.setWidget(row, col++, l);

			l = new Label(o.manditary() == true ? "必须" : "可选");
			l.setStyleName(SysResource.INSTANCE.getCss().text());
			tbl.setWidget(row, col++, l);

			l = new Label(o.summary());
			l.setStyleName(SysResource.INSTANCE.getCss().summary());
			tbl.setWidget(row, col++, l);

			row++;
		}

		jsonPanel.setJson(obj.json());
	}

	@UiField
	FlexTable tbl;

	@UiField
	JsonPanel jsonPanel;
}
