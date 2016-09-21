package cn.mapway.document.ui.client.test;

import cn.mapway.document.ui.client.main.storage.LocalStorage;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.HasCloseHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class InputHistoryPanel extends Composite implements
		HasCloseHandlers<HistoryData> {

	private static InputHistoryPanelUiBinder uiBinder = GWT
			.create(InputHistoryPanelUiBinder.class);

	interface InputHistoryPanelUiBinder extends
			UiBinder<Widget, InputHistoryPanel> {
	}

	public InputHistoryPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	HistoryItem current = null;

	@UiField
	HTMLPanel content;
	private ClickHandler itemClick = new ClickHandler() {

		@Override
		public void onClick(ClickEvent event) {
			HistoryItem item = (HistoryItem) event.getSource();
			CloseEvent.fire(InputHistoryPanel.this, item.getData());
		}
	};

	public void cleatContent() {
		content.clear();
	}

	public void addItem(String title, String value) {
		HistoryItem item = new HistoryItem();
		HistoryData hd = new HistoryData(title, value);

		item.render(hd);
		item.addClickHandler(itemClick);
		content.add(item);
	}

	@Override
	public HandlerRegistration addCloseHandler(CloseHandler<HistoryData> handler) {
		return addHandler(handler, CloseEvent.getType());
	}

	public void render(String relativePath) {
		content.clear();
		String v = LocalStorage.val(relativePath);
		if (v == null || v.length() == 0) {
			return;
		}

		String[] vs = v.split("\\|");

		for (int index = 0; index < vs.length; index++) {
			String[] itemdata = vs[index].split("`");
			addItem(itemdata[0], itemdata[1]);

		}
		// 保留最后的10个记录
		if (vs.length > 10) {
			String fulldata = "";
			for (int i = 0; i < 10; i++) {
				if (i == 0) {
					fulldata = vs[i];
				} else {
					fulldata = "|" + vs[i];
				}
			}
			LocalStorage.save(relativePath, fulldata);
		}

	}
}
