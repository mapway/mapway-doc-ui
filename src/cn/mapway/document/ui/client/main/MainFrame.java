package cn.mapway.document.ui.client.main;

import cn.mapway.document.ui.client.component.CustomButton;
import cn.mapway.document.ui.client.module.ApiDoc;
import cn.mapway.document.ui.client.module.Entry;
import cn.mapway.document.ui.client.module.Group;
import cn.mapway.document.ui.client.module.JarInfo;
import cn.mapway.document.ui.client.resource.SysResource;
import cn.mapway.document.ui.client.rpc.ApiDocProxy;
import cn.mapway.document.ui.client.rpc.IOnData;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;

// TODO: Auto-generated Javadoc
/**
 * The Class MainFrame.
 */
public class MainFrame extends Composite {

	/** The ui binder. */
	private static MainFrameUiBinder uiBinder = GWT
			.create(MainFrameUiBinder.class);

	/**
	 * The Interface MainFrameUiBinder.
	 */
	interface MainFrameUiBinder extends UiBinder<Widget, MainFrame> {
	}

	/** The current item. */
	TreeItem currentItem = null;

	/** The entry panel. */
	EntryPanel entryPanel;

	/** The tree select. */
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

	/**
	 * Handle item.
	 *
	 * @param item
	 *            the item
	 */
	private void handleItem(TreeItem item) {
		if (item.getTitle().length() == 0) {
			Group group = (Group) item.getUserObject();
			showEntryList(group);
		} else {
			Entry e = (Entry) item.getUserObject();
			showEntry(e);
		}

	}

	/**
	 * Show entry.
	 *
	 * @param e
	 *            the e
	 */
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

	/** The list. */
	EntryListPanel list;

	/**
	 * Show entry list.
	 *
	 * @param group
	 *            the group
	 */
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

	/**
	 * Instantiates a new main frame.
	 */
	public MainFrame() {
		initWidget(uiBinder.createAndBindUi(this));
		logo.setUrl(SysResource.INSTANCE.logo().getSafeUri());

		apiTree.addSelectionHandler(treeSelect);
	}

	/** The doc. */
	ApiDoc doc;

	/** The goto word handler. */
	private ClickHandler gotoWordHandler = new ClickHandler() {

		@Override
		public void onClick(ClickEvent event) {
			Window.open(doc.wordUrl(), "wordExport", "");
		}
	};

	private ClickHandler downloadLinkHandler = new ClickHandler() {

		@Override
		public void onClick(ClickEvent event) {
			CustomButton btn = (CustomButton) event.getSource();
			JarInfo jar = (JarInfo) btn.getData();
			Window.open(jar.link(), "Connecgtor 下载", "");
		}
	};

	/**
	 * 获取模板文件中的数据.
	 *
	 * @return the api doc
	 */
	private final native ApiDoc findDocData()/*-{
		if ($wnd.g_data != undefined) {
			return $wnd.g_data;
		}
		return null;
	}-*/;

	/**
	 * Inits the.
	 *
	 * @param target
	 *            the target
	 */
	public void init(String target) {

		ApiDoc doc = findDocData();
		if (doc == null) {
			ApiDocProxy.fetchData(target,
					new IOnData<cn.mapway.document.ui.client.module.ApiDoc>() {
						@Override
						public void onError(String url, String error) {
							Window.alert(error);
						}

						@Override
						public void onSuccess(String url,
								cn.mapway.document.ui.client.module.ApiDoc data) {
							parseData(data);
						}
					});
		} else {
			parseData(doc);
		}
	}

	/**
	 * Parses the data.
	 *
	 * @param doc
	 *            the doc
	 */
	void parseData(ApiDoc doc) {
		this.doc = doc;

		JsArray<JarInfo> jars = doc.getDownloads();

		if (jars.length() > 0) {
			for (int index = 0; index < jars.length(); index++) {
				JarInfo jar = jars.get(index);
				CustomButton btn = new CustomButton(jar.summary());
				btn.setData(jar);
				btn.addClickHandler(downloadLinkHandler);
				tools.add(btn);
			}
		}

		lbTitle.setText(doc.title());
		apiTree.parseData(doc);
		lbSubtitle.setText(doc.summary());
		handleItem(apiTree.getItem(0));
		if (doc.wordUrl().length() > 0) {
			CustomButton btn = new CustomButton("导出WORD文档");
			btn.addClickHandler(gotoWordHandler);
			tools.add(btn);
		}

	}

	/** The api tree. */
	@UiField
	ApiTree apiTree;

	/** The content. */
	@UiField
	ScrollPanel content;

	/** The lb title. */
	@UiField
	Label lbTitle;

	/** The logo. */
	@UiField
	Image logo;

	/** The tools. */
	@UiField
	HorizontalPanel tools;

	/** sub title. */
	@UiField
	Label lbSubtitle;

}
