package cn.mapway.document.ui.client.main;

import cn.mapway.document.ui.client.module.Entry;
import cn.mapway.document.ui.client.module.Group;
import cn.mapway.document.ui.client.resource.SysResource;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;

// TODO: Auto-generated Javadoc
/**
 * 接口列表.
 *
 * @author zhangjianshe
 */
public class EntryList extends Grid {

	/**
	 * Instantiates a new entry list.
	 */
	public EntryList() {
		init();
	}

	/**
	 * Inits the.
	 */
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

	/** The row. */
	int row = 0;

	/**
	 * Parses the.
	 *
	 * @param group
	 *            the group
	 * @param searchText
	 *            the search text
	 * @return the string
	 */
	public String parse(Group group, String searchText) {

		row = 1;
		int count = findCount(group, searchText);

		this.resize(count + 1, 5);

		String html = parseGroup(group, searchText);
		return html;
	}

	/**
	 * Parses the group.
	 *
	 * @param group
	 *            the group
	 * @param searchText
	 *            the search text
	 * @return the string
	 */
	private String parseGroup(Group group, String searchText) {

		String html = "";
		if (group.summary() != null && (!group.summary().equals("null"))
				&& group.summary().length() > 0) {
			html = "<div class='" + SysResource.INSTANCE.getCss().entryTitle()
					+ "'>" + group.name() + "</div>" + "<div class='"
					+ SysResource.INSTANCE.getCss().desc() + "'>"
					+ group.summary() + "</div>";
		}
		for (int i = 0; i < group.entries().length(); i++) {
			Entry e = group.entries().get(i);

			boolean ok = false;

			if (searchText == null || searchText.length() == 0) {
				ok = true;
			} else if (e.title().contains(searchText)) {
				ok = true;
			} else {
				ok = false;
			}

			if (ok == false) {
				continue;
			}
			int column = 0;

			this.setWidget(row, column++, new Label((row) + ""));

			this.setWidget(row, column++, new Label(e.title()));
			this.setWidget(row, column++, new Label(e.url()));
			this.setWidget(row, column++, new Label(e.state()));
			this.setWidget(row, column++, new Label(e.author()));
			getCellFormatter().setHorizontalAlignment(row, column - 1,
					HasHorizontalAlignment.ALIGN_RIGHT);
			row++;
		}

		for (int i = 0; i < group.subGroups().length(); i++) {
			html += parseGroup(group.subGroups().get(i), searchText);
		}

		return html;
	}

	/**
	 * Find count.
	 *
	 * @param group
	 *            the group
	 * @param searchText
	 *            the search text
	 * @return the int
	 */
	public int findCount(Group group, String searchText) {
		Integer count = 0;
		for (int i = 0; i < group.entries().length(); i++) {
			boolean ok = false;
			Entry e = group.entries().get(i);
			if (searchText == null || searchText.length() == 0) {
				ok = true;
			} else if (e.title().contains(searchText)) {
				ok = true;
			} else {
				ok = false;
			}

			if (ok == false) {
				continue;
			}
			count = count + 1;
		}

		for (int i = 0; i < group.subGroups().length(); i++) {
			count = count + findCount(group.subGroups().get(i), searchText);
		}
		return count;
	}

}
