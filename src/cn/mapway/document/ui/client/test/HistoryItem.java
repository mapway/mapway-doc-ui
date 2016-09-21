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

// TODO: Auto-generated Javadoc
/**
 * The Class HistoryItem.
 */
public class HistoryItem extends Composite implements HasClickHandlers {

	/** The ui binder. */
	private static HistoryItemUiBinder uiBinder = GWT
			.create(HistoryItemUiBinder.class);

	/**
	 * The Interface HistoryItemUiBinder.
	 */
	interface HistoryItemUiBinder extends UiBinder<Widget, HistoryItem> {
	}

	/**
	 * Instantiates a new history item.
	 */
	public HistoryItem() {
		initWidget(uiBinder.createAndBindUi(this));
		Event.sinkEvents(this.getElement(), Event.ONCLICK);

	}

	/** The lb title. */
	@UiField
	Label lbTitle;

	/** The m data. */
	HistoryData mData;

	/**
	 * Render.
	 *
	 * @param data the data
	 */
	public void render(HistoryData data) {
		mData = data;
		lbTitle.setText(data.title);
	}

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public HistoryData getData() {
		return mData;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.HasClickHandlers#addClickHandler(com.google.gwt.event.dom.client.ClickHandler)
	 */
	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return addHandler(handler, ClickEvent.getType());
	}
}
