package cn.mapway.document.ui.client.main;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.HTMLTable.RowFormatter;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

// TODO: Auto-generated Javadoc
/**
 * 参数面板.
 *
 * @author zhangjianshe
 */
public class ParameterPanel extends Composite implements
		HasSelectionHandlers<ObjectInfo> {

	/** The ui binder. */
	private static ParameterPanelUiBinder uiBinder = GWT
			.create(ParameterPanelUiBinder.class);

	/**
	 * The Interface ParameterPanelUiBinder.
	 */
	interface ParameterPanelUiBinder extends UiBinder<Widget, ParameterPanel> {
	}

	/**
	 * Instantiates a new parameter panel.
	 */
	public ParameterPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		tbl.addSelectionHandler(fieldTypeSelectionHandler);
	}

	/** The lb title. */
	@UiField
	Label lbTitle;

	/** The m obj. */
	ObjectInfo mObj;

	/** The mapper. */
	Map<String, Anchor> mapper;

	/** The gens. */
	List<GenInfo> gens;

	/** The field type selection handler. */
	private SelectionHandler<ObjectInfo> fieldTypeSelectionHandler = new SelectionHandler<ObjectInfo>() {

		@Override
		public void onSelection(SelectionEvent<ObjectInfo> arg0) {
			if (mapper != null) {
				ObjectInfo info = arg0.getSelectedItem();
				Anchor a = mapper.get(info.type());
				if (a != null) {
					a.getElement().scrollIntoView();
				}
			}
		}
	};

	/**
	 * Parses the.
	 *
	 * @param obj the obj
	 * @param string the string
	 */
	public void parse(ObjectInfo obj, String string) {
		mObj = obj;
		gens = new ArrayList<GenInfo>();
		mapper = new HashMap<String, Anchor>();

		lbTitle.setText(string);
		tbl.parse(mObj, gens, mapper);

		objInfoPanel.clear();
		while (needContinue(gens)) {
			List<GenInfo> gens2 = new ArrayList<GenInfo>();
			for (GenInfo info : gens) {
				if (info.gen == false) {

					ObjectInfoPanel p = new ObjectInfoPanel();
					p.addSelectionHandler(fieldTypeSelectionHandler);
					p.parse(info.obj, gens2, mapper);
					objInfoPanel.add(p);
					info.gen = true;
					
					Anchor a = new Anchor();
					a.setName(info.type);
					mapper.put(info.type, a);
					objInfoPanel.add(a);
				}
			}
			merge(gens, gens2);
		}

		jsonPanel.setJson(obj.json());
	}

	/**
	 * Merge.
	 *
	 * @param gens the gens
	 * @param gens2 the gens 2
	 */
	private void merge(List<GenInfo> gens, List<GenInfo> gens2) {
		for (GenInfo info : gens2) {
			boolean find = false;
			for (GenInfo gen : gens) {
				if (gen.type.equals(info.type)) {
					find = true;
					break;
				}
			}

			if (find == false) {
				gens.add(info);
			}
		}
	}

	/**
	 * Need continue.
	 *
	 * @param gens2 the gens 2
	 * @return true, if successful
	 */
	private boolean needContinue(List<GenInfo> gens2) {
		for (GenInfo info : gens2) {
			if (info.gen == false) {
				return true;
			}
		}
		return false;
	}

	/** The tbl. */
	@UiField
	ObjectInfoPanel tbl;

	/** The json panel. */
	@UiField
	JsonPanel jsonPanel;

	/* (non-Javadoc)
	 * @see com.google.gwt.event.logical.shared.HasSelectionHandlers#addSelectionHandler(com.google.gwt.event.logical.shared.SelectionHandler)
	 */
	@Override
	public HandlerRegistration addSelectionHandler(
			SelectionHandler<ObjectInfo> arg0) {
		return addHandler(arg0, SelectionEvent.getType());
	}

	/** The obj info panel. */
	@UiField
	HTMLPanel objInfoPanel;
}
