package cn.mapway.document.ui.client.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.mapway.document.ui.client.module.GenInfo;
import cn.mapway.document.ui.client.module.ObjectInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class OutputParameter extends Composite {

	private static OutputParameterUiBinder uiBinder = GWT
			.create(OutputParameterUiBinder.class);

	interface OutputParameterUiBinder extends UiBinder<Widget, OutputParameter> {
	}

	public OutputParameter() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	Label lbType;
	@UiField
	Label lbSummary;
	
	@UiField
	ObjectInfoPanel tbl;
	
	@UiField
	JsonPanel jsonPanel;
	
	@UiField
	HTMLPanel objInfoPanel;

	public void parse(ObjectInfo output) {
		gens =new ArrayList<GenInfo>();
		mapper=new HashMap<String, Anchor>();
		lbType.setText(output.type());
		lbSummary.setText(output.summary());
		
		if(tbl.isPrimitive(output.type()))
		{
			lbSummary.setVisible(true);
			tbl.setVisible(false);
			//原始數據類型
			objInfoPanel.setVisible(false);
			jsonPanel.setString(output.example());
		}
		else
		{
			//複雜類型
			tbl.setVisible(true);
			tbl.parse(output, gens, mapper);
			lbSummary.setVisible(false);
			
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

			jsonPanel.setJson(output.json());
		}
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

	/** The mapper. */
	Map<String, Anchor> mapper;

	/** The gens. */
	List<GenInfo> gens;
}
