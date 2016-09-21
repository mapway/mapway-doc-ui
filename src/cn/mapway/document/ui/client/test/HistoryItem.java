package cn.mapway.document.ui.client.test;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class HistoryItem extends Composite implements HasClickHandlers {

	private static HistoryItemUiBinder uiBinder = GWT
			.create(HistoryItemUiBinder.class);

	interface HistoryItemUiBinder extends UiBinder<Widget, HistoryItem> {
	}

	public HistoryItem() {
		initWidget(uiBinder.createAndBindUi(this));
		Event.sinkEvents(this.getElement(), Event.ONCLICK);

	}

	@UiField
	Label lbTitle;

	HistoryData mData;

	public void render(HistoryData data) {
		mData = data;
		lbTitle.setText(data.title);
	}

	public HistoryData getData() {
		return mData;
	}

	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return addHandler(handler, ClickEvent.getType());
	}
}
