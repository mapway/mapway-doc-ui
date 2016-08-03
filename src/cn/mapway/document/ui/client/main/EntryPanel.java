package cn.mapway.document.ui.client.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.mapway.document.ui.client.module.Entry;
import cn.mapway.document.ui.client.module.GenInfo;
import cn.mapway.document.ui.client.module.ObjectInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class EntryPanel extends Composite {

	private static EntryPanelUiBinder uiBinder = GWT
			.create(EntryPanelUiBinder.class);

	interface EntryPanelUiBinder extends UiBinder<Widget, EntryPanel> {
	}

	public EntryPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		paraIn.addSelectionHandler(fieldTypeSelectionHandler);
		paraOut.addSelectionHandler(fieldTypeSelectionHandler);
	}

	List<GenInfo> gens;

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

	Map<String, Anchor> mapper;

	public void parse(Entry e) {

		mapper = new HashMap<String, Anchor>();
		gens = new ArrayList<GenInfo>();
		lbTITLE.setText(e.title() + "   " + e.parentClassName() + "::"
				+ e.methodName());
		lbSUMMARY.setHTML(e.summary());
		lbURL.setText("接口网址:  " + e.relativePath());
		lbAUTHOR.setText("作者:" + e.author());
		lbINVOKE.setText("调用方法:" + e.invokeMethod());

		paraIn.parse(e.input().get(0), "传入参数", gens);
		paraOut.parse(e.output(), "传出参数", gens);

		objInfoList.clear();
		while (needContinue(gens)) {
			List<GenInfo> gens2 = new ArrayList();
			for (GenInfo info : gens) {
				if (info.gen == false) {

					Anchor a = new Anchor();
					a.setName(info.type);
					mapper.put(info.type, a);
					objInfoList.add(a);

					ObjectInfoPanel p = new ObjectInfoPanel();
					p.addSelectionHandler(fieldTypeSelectionHandler);
					p.parse(info.obj, gens2);
					objInfoList.add(p);
					info.gen = true;
				}
			}
			merge(gens, gens2);
		}

	}

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

	private boolean needContinue(List<GenInfo> gens2) {
		for (GenInfo info : gens2) {
			if (info.gen == false) {
				return true;
			}
		}
		return false;
	}

	@UiField
	Label lbTITLE;

	@UiField
	HTML lbSUMMARY;

	@UiField
	Label lbURL;
	@UiField
	Label lbAUTHOR;
	@UiField
	Label lbINVOKE;

	@UiField
	ParameterPanel paraIn;

	@UiField
	ParameterPanel paraOut;

	@UiField
	HTMLPanel objInfoList;

}
