package cn.mapway.document.ui.client.main;

import java.util.List;
import java.util.Map;

import cn.mapway.document.ui.client.module.GenInfo;
import cn.mapway.document.ui.client.module.ObjectInfo;
import cn.mapway.document.ui.client.resource.SysResource;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
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
public class ParameterPanel extends Composite implements
		HasSelectionHandlers<ObjectInfo> {

	private static ParameterPanelUiBinder uiBinder = GWT
			.create(ParameterPanelUiBinder.class);

	interface ParameterPanelUiBinder extends UiBinder<Widget, ParameterPanel> {
	}

	private SelectionHandler<ObjectInfo> handler = new SelectionHandler<ObjectInfo>() {

		@Override
		public void onSelection(SelectionEvent<ObjectInfo> arg0) {
			SelectionEvent.fire(ParameterPanel.this, arg0.getSelectedItem());
		}
	};

	public ParameterPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		tbl.addSelectionHandler(handler);
	}

	@UiField
	Label lbTitle;

	@UiField
	Label lbSummary;

	ObjectInfo mObj;

	public void parse(ObjectInfo obj, String string, List<GenInfo> objList) {
		mObj = obj;

		lbTitle.setText(string + obj.type());
		lbSummary.setText(obj.summary());

		int rowcount = tbl.getRowCount();
		for (int index = obj.fields().length() + 1; index < rowcount; index++) {
			tbl.removeRow(tbl.getRowCount() - 1);
		}

		tbl.parse(mObj, objList);

		jsonPanel.setJson(obj.json());
	}

	@UiField
	ObjectInfoPanel tbl;

	@UiField
	JsonPanel jsonPanel;

	@Override
	public HandlerRegistration addSelectionHandler(
			SelectionHandler<ObjectInfo> arg0) {
		return addHandler(arg0, SelectionEvent.getType());
	}
}
