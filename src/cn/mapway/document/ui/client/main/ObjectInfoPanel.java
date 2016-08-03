package cn.mapway.document.ui.client.main;

import java.util.List;

import cn.mapway.document.ui.client.component.CustomAnchor;
import cn.mapway.document.ui.client.module.GenInfo;
import cn.mapway.document.ui.client.module.ObjectInfo;
import cn.mapway.document.ui.client.resource.SysResource;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;

public class ObjectInfoPanel extends Grid implements
		HasSelectionHandlers<ObjectInfo>, ClickHandler {

	private Label lbTitle;

	@Override
	public void onClick(ClickEvent arg0) {
		CustomAnchor anchor = (CustomAnchor) arg0.getSource();
		ObjectInfo info = (ObjectInfo) anchor.getData();

		SelectionEvent.fire(this, info);
	}

	public ObjectInfoPanel() {
		init();
		this.setStyleName(SysResource.INSTANCE.getCss().cssTable());
	}

	void init() {
		Element e = getElement();
		e.setAttribute("borderCollapse", "collapse");
		e.setAttribute("cellPadding", "10px");
		e.setAttribute("cellSpacing", "1px");

		lbTitle = new Label();

		this.resize(2, 5);
		int row = 0;

		setWidget(row, 0, lbTitle);
		getCellFormatter().getElement(0, 0).setAttribute("colspan", "5");
		
		this.removeCell(0, 1);
		this.removeCell(0, 1);
		this.removeCell(0, 1);
		this.removeCell(0, 1);
		
		// 名称 类型 长度 选项 解释
		Label l;
		int col = 0;

		row++;
		l = new Label("名称");
		l.setStyleName(SysResource.INSTANCE.getCss().tableHeader());
		setWidget(row, col++, l);

		l = new Label("类型");
		l.setStyleName(SysResource.INSTANCE.getCss().tableHeader());
		setWidget(row, col++, l);

		l = new Label("长度");
		l.setStyleName(SysResource.INSTANCE.getCss().tableHeader());
		setWidget(row, col++, l);

		l = new Label("选项");
		l.setStyleName(SysResource.INSTANCE.getCss().tableHeader());
		setWidget(row, col++, l);

		l = new Label("解释");
		l.setStyleName(SysResource.INSTANCE.getCss().tableHeader());
		setWidget(row, col++, l);

		RowFormatter rf = getRowFormatter();
		rf.setStylePrimaryName(row, SysResource.INSTANCE.getCss().rowh());
	}

	/**
	 * 级联操作
	 * 
	 * @param obj
	 * @param objList
	 */
	public void parse(ObjectInfo obj, List<GenInfo> objList) {

		lbTitle.setText(obj.name() + "-(" + obj.type() + ")");

		this.resizeRows(obj.fields().length() + 2);

		RowFormatter rf = getRowFormatter();

		// 处理字段
		int row = 2;
		for (int i = 0; i < obj.fields().length(); i++) {
			ObjectInfo o = obj.fields().get(i);
			// 名称 类型 长度 选项 解释
			Label l;
			int col = 0;

			if (row % 2 == 0) {
				rf.setStyleName(row, SysResource.INSTANCE.getCss().row0());
			} else {
				rf.setStyleName(row, SysResource.INSTANCE.getCss().row1());
			}

			l = new Label(o.name());

			l.setStyleName(SysResource.INSTANCE.getCss().name());
			setWidget(row, col++, l);

			String type = o.type();
			if (isPrimitive(type)) {
				l = new Label(o.type());
				l.setStyleName(SysResource.INSTANCE.getCss().type());

				setWidget(row, col++, l);
			} else {
				CustomAnchor a = new CustomAnchor();
				a.setText(type);
				a.setData(o);
				a.setStyleName(SysResource.INSTANCE.getCss().typeLink());
				a.addClickHandler(this);
				setWidget(row, col++, a);

				boolean hasFind = findObj(type, objList);
				if (!hasFind) {
					GenInfo info = new GenInfo();
					info.type = type;
					info.obj = o;
					info.gen = false;
					objList.add(info);
				}
			}

			l = new Label(o.length() + "");
			l.setStyleName(SysResource.INSTANCE.getCss().text());
			setWidget(row, col++, l);

			l = new Label(o.manditary() == true ? "必须" : "可选");
			l.setStyleName(SysResource.INSTANCE.getCss().text());
			setWidget(row, col++, l);

			l = new Label(o.summary());
			l.setStyleName(SysResource.INSTANCE.getCss().summary());
			setWidget(row, col++, l);

			row++;
		}
	}

	private boolean findObj(String type, List<GenInfo> objList) {
		for (GenInfo info : objList) {
			if (info.type.equals(type)) {
				return true;
			}
		}
		return false;
	}

	private static String[] ps = { "int", "Integer", "float", "FLoat",
			"Double", "double", "long", "Long", "Date", "DateTime", "String",
			"boolean", "Boolean", "char", "short", "byte", "Timestamp" };

	private boolean isPrimitive(String type) {

		for (String s : ps) {
			if (type.contains(s)) {
				return true;
			}
		}
		return false;

	}

	@Override
	public HandlerRegistration addSelectionHandler(
			SelectionHandler<ObjectInfo> handler) {
		return addHandler(handler, SelectionEvent.getType());
	}
}
