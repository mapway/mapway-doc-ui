package cn.mapway.document.ui.client.main;

import cn.mapway.document.ui.client.module.Group;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class EntryListPanel extends Composite {

	private static EntryListPanelUiBinder uiBinder = GWT
			.create(EntryListPanelUiBinder.class);

	interface EntryListPanelUiBinder extends UiBinder<Widget, EntryListPanel> {
	}

	public EntryListPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	Label lbTitle;

	@UiField
	EntryList list;

	public void parse(Group group) {
		lbTitle.setText(group.name() == null ? "所有接口" : group.fullName());
		list.parse(group);
	}
}
