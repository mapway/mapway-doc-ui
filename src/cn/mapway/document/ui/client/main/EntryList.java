package cn.mapway.document.ui.client.main;

import cn.mapway.document.ui.client.module.Entry;
import cn.mapway.document.ui.client.module.Group;
import cn.mapway.document.ui.client.resource.SysResource;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;

/**
 * 接口列表
 * 
 * @author zhangjianshe
 *
 */
public class EntryList extends Grid {

	public EntryList() {
		init();
	}

	private void init() {
		Element e = getElement();
		e.setAttribute("borderCollapse", "collapse");
		e.setAttribute("cellPadding", "10px");
		e.setAttribute("cellSpacing", "1px");

		this.resize(1, 5);
		int row = 0;

		// 名称 类型 长度 选项 解释
		Label l;
		int col = 0;

//		l = new Label("分组");
//		l.setStyleName(SysResource.INSTANCE.getCss().tableHeader());
//		setWidget(row, col++, l);

		l = new Label("序号");
		l.setStyleName(SysResource.INSTANCE.getCss().tableHeader());
		setWidget(row, col++, l);

		l = new Label("接口名称");
		l.setStyleName(SysResource.INSTANCE.getCss().tableHeader());
		setWidget(row, col++, l);

		l = new Label("映射网址");
		l.setStyleName(SysResource.INSTANCE.getCss().tableHeader());
		setWidget(row, col++, l);

		l = new Label("开发状态");
		l.setStyleName(SysResource.INSTANCE.getCss().tableHeader());
		setWidget(row, col++, l);

		l = new Label("开发者");
		l.setStyleName(SysResource.INSTANCE.getCss().tableHeader());
		setWidget(row, col++, l);

		getCellFormatter().setHorizontalAlignment(row, col - 1,
				HasHorizontalAlignment.ALIGN_RIGHT);

		RowFormatter rf = getRowFormatter();
		rf.setStylePrimaryName(row, SysResource.INSTANCE.getCss().rowh());

	}

	int row = 0;

	public void parse(Group group) {

		row = 1;
		int count = findCount(group);

		this.resize(count + 1, 5);

		parseGroup(group);

	}

	private void parseGroup(Group group) {

		for (int i = 0; i < group.entries().length(); i++) {
			Entry e = group.entries().get(i);
			int column = 0;

			//this.setWidget(row, column++, new Label(group.fullName()));
			this.setWidget(row, column++, new Label((row) + ""));

			this.setWidget(row, column++, new Label(e.title()));
			this.setWidget(row, column++, new Label(e.relativePath()));
			this.setWidget(row, column++, new Label(e.state()));
			this.setWidget(row, column++, new Label(e.author()));
			getCellFormatter().setHorizontalAlignment(row, column - 1,
					HasHorizontalAlignment.ALIGN_RIGHT);
			row++;
		}

		for (int i = 0; i < group.subGroups().length(); i++) {
			parseGroup(group.subGroups().get(i));
		}
	}

	private int findCount(Group group) {
		Integer count = 0;

		count = count + group.entries().length();

		for (int i = 0; i < group.subGroups().length(); i++) {
			count = count + findCount(group.subGroups().get(i));
		}
		return count;
	}

}
