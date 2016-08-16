package cn.mapway.document.ui.client.main;

import cn.mapway.document.ui.client.module.Group;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class EntryListPanel extends Composite {

	private static EntryListPanelUiBinder uiBinder = GWT
			.create(EntryListPanelUiBinder.class);

	interface EntryListPanelUiBinder extends UiBinder<Widget, EntryListPanel> {
	}

	public EntryListPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		txtSEARCH.addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {
				search();
			}
		});
		txtSEARCH.addFocusHandler(new FocusHandler() {

			@Override
			public void onFocus(FocusEvent event) {
				if (txtSEARCH.getValue() != null
						&& txtSEARCH.getValue().length() > 0)
					txtSEARCH.setSelectionRange(0, txtSEARCH.getValue()
							.length());
			}
		});
	}

	@UiField
	Label lbTitle;

	@UiField
	EntryList list;
	Group mGroup;
	String searchText;
	@UiField
	HTML html;

	public void parse(Group group, String searchText) {
		if (group == null) {
			return;
		}

		this.searchText = searchText;
		txtSEARCH.setValue(searchText);
		mGroup = group;
		lbTitle.setText(group.name() == null ? "所有接口" : group.fullName());
		int count = list.findCount(group, "");
		String h = list.parse(group, searchText);
		if (count == 0) {
			entries.setVisible(false);
		} else {
			entries.setVisible(true);
		}
		html.setHTML(h);
	}

	@UiField
	VerticalPanel entries;

	@UiField
	TextBox txtSEARCH;

	@UiHandler("btnSearch")
	void onSearch(ClickEvent e) {
		search();
	}

	private void search() {
		String key = txtSEARCH.getValue();
		if (key == null || key.length() == 0) {
			parse(mGroup, "");
		}
		parse(mGroup, key);
	}
}
