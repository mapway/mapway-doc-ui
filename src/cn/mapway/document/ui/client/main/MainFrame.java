package cn.mapway.document.ui.client.main;

import cn.mapway.document.ui.client.module.ApiDoc;
import cn.mapway.document.ui.client.module.Entry;
import cn.mapway.document.ui.client.module.Group;
import cn.mapway.document.ui.client.resource.SysResource;
import cn.mapway.document.ui.client.rpc.ApiDocProxy;
import cn.mapway.document.ui.client.rpc.IOnData;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;

public class MainFrame extends Composite {

	private static MainFrameUiBinder uiBinder = GWT
			.create(MainFrameUiBinder.class);

	interface MainFrameUiBinder extends UiBinder<Widget, MainFrame> {
	}

	TreeItem currentItem = null;
	EntryPanel entryPanel;
	private SelectionHandler<TreeItem> treeSelect = new SelectionHandler<TreeItem>() {

		@Override
		public void onSelection(SelectionEvent<TreeItem> arg0) {
			TreeItem item = arg0.getSelectedItem();
			if (currentItem != null) {
				currentItem.removeStyleName(SysResource.INSTANCE.getCss()
						.entrySelected());
			}

			handleItem(item);

			currentItem = item;
			currentItem.addStyleName(SysResource.INSTANCE.getCss()
					.entrySelected());
		}

	};

	private void handleItem(TreeItem item) {
		if (item.getTitle().length() == 0) {
			Group group = (Group) item.getUserObject();
			showEntryList(group);
		} else {
			Entry e = (Entry) item.getUserObject();
			showEntry(e);
		}

	}

	private void showEntry(Entry e) {
		if (entryPanel == null) {
			entryPanel = new EntryPanel();

		}
		if (!entryPanel.isAttached()) {
			content.clear();
			content.add(entryPanel);
		}
		entryPanel.parse(e);
		content.scrollToTop();

	}

	EntryListPanel list;

	protected void showEntryList(Group group) {
		if (list == null) {
			list = new EntryListPanel();
		}
		if (!list.isAttached()) {
			content.clear();
			content.add(list);
		}
		list.parse(group, "");
		content.scrollToTop();
	}

	public MainFrame() {
		initWidget(uiBinder.createAndBindUi(this));
		logo.setUrl(SysResource.INSTANCE.logo().getSafeUri());

		apiTree.addSelectionHandler(treeSelect);
	}

	ApiDoc doc;

	/**
	 * 获取模板文件中的数据
	 * 
	 * @return
	 */
	private final native ApiDoc findDocData()/*-{
		if ($wnd.g_data != undefined) {
			return $wnd.g_data;
		}
		return null;
	}-*/;

	public void init(String target) {

		ApiDoc doc = findDocData();
		if (doc == null) {
			ApiDocProxy.fetchData(target,
					new IOnData<cn.mapway.document.ui.client.module.ApiDoc>() {
						@Override
						public void onError(String error) {
							Window.alert(error);
						}

						@Override
						public void onSuccess(
								cn.mapway.document.ui.client.module.ApiDoc data) {
							apiTree.parseData(data);
						}
					});
		} else {
			lbTitle.setText(doc.title());
			apiTree.parseData(doc);
			handleItem(apiTree.getItem(0));
		}
	}

	@UiField
	ApiTree apiTree;

	@UiField
	ScrollPanel content;

	@UiField
	Label lbTitle;

	@UiField
	Image logo;
}
