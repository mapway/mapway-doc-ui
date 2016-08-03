package cn.mapway.document.ui.client.main;

import cn.mapway.document.ui.client.module.Entry;
import cn.mapway.document.ui.client.module.Group;
import cn.mapway.document.ui.client.resource.CssStyle;
import cn.mapway.document.ui.client.resource.SysResource;
import cn.mapway.document.ui.client.resource.TreeResource;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;

/**
 * API接口树
 * 
 * @author zhangjianshe
 *
 */
public class ApiTree extends Tree {

	CssStyle css;

	public ApiTree() {
		super(new TreeResource(), false);

		css = SysResource.INSTANCE.getCss();
	}

	public void parseData(cn.mapway.document.ui.client.module.ApiDoc data) {
		this.clear();

		Group group = data.root();

		TreeItem root = new TreeItem();
		root.setStyleName(css.group());
		this.addItem(root);
		root.setUserObject(group);
		root.setText(data.title());
		parseGroup(root, group);

	}

	private void parseGroup(TreeItem root, Group group) {
		JsArray<Group> subs = group.subGroups();
		// 处理子节点
		for (int i = 0; i < subs.length(); i++) {
			Group g = subs.get(i);
			TreeItem item = new TreeItem();
			item.setStyleName(css.group());
			item.setUserObject(g);
			item.setText(g.name());
			parseGroup(item, g);
			root.addItem(item);
		}
		// 处理方法
		JsArray<cn.mapway.document.ui.client.module.Entry> entries = group
				.entries();
		for (int i = 0; i < entries.length(); i++) {
			Entry e = entries.get(i);
			TreeItem item = new TreeItem();
			item.setStyleName(css.entry());
			item.setText(e.title());
			item.setUserObject(e);
			item.setTitle("实现类:" + e.parentClassName() + "\r\n方法"
					+ e.methodName());
			root.addItem(item);
		}
		root.setState(true);
	}

}
