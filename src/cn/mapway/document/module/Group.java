package cn.mapway.document.module;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * API 分组
 * 
 * @author zhangjianshe
 *
 */
public class Group implements Serializable {

	public Group() {
		entries = new ArrayList<Entry>();
		subGroups = new ArrayList<Group>();

		setParent(null);
	}

	/** 节点名称. */
	public String name="";

	/** 节点说明. */
	public String summary;

	/**
	 * 父节点
	 */
	private Group parent;

	/**
	 * 节点全路径名称
	 */
	public String fullName="/";

	/**
	 * 子节点
	 */
	public List<Entry> entries;

	/**
	 * 子分组
	 */
	public List<Group> subGroups;

	/**
	 * 添加子節點.
	 *
	 * @param g
	 *            the g
	 */
	public void addChildGroup(Group g) {
		g.setParent(this);
		subGroups.add(g);
	}

	/**
	 * 清空子節點.
	 */
	public void clearChildGroup() {
		subGroups.clear();
	}

	/**
	 * 移除子节点.
	 *
	 * @param g
	 *            the g
	 */
	public void removeChildGroup(Group g) {
		subGroups.remove(g);
	}

	/**
	 * Gets the child groups.
	 *
	 * @return the child groups
	 */
	public List<Group> getChildGroups() {
		return subGroups;
	}

	/**
	 * 获取节点的全路径.
	 *
	 * @return the path
	 */
	public String getPath() {
		ArrayList<String> strArray = new ArrayList<String>();

		Group g = getParent();

		while (g != null) {
			strArray.add(g.name);
			g = g.getParent();
		}

		StringBuilder sb = new StringBuilder();
		for (int i = strArray.size() - 1; i >= 0; i--) {
			if (strArray.get(i).equals("/")) {
				continue;
			}
			sb.append("/" + strArray.get(i));
		}

		return sb.toString();
	}

	public Group getParent() {
		return parent;
	}

	public void setParent(Group parent) {
		this.parent = parent;
	}

	/**
	 * 排序
	 */
	public void sort() {

		Collections.sort(subGroups, new Comparator<Group>() {
			@Override
			public int compare(Group o1, Group o2) {
				return o1.name.compareTo(o2.name);
			}
		});

		Collections.sort(entries, new Comparator<Entry>() {
			@Override
			public int compare(Entry o1, Entry o2) {
				return o1.order > o2.order ? 1 : -1;
			}
		});

		for (Group g : subGroups) {
			g.sort();
		}

	}
}
