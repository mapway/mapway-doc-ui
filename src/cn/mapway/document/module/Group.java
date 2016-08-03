package cn.mapway.document.module;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * API 分组
 * 
 * @author zhangjianshe
 *
 */
public class Group implements Serializable{

	public Group() {
		entries = new ArrayList<Entry>();
		subGroups = new ArrayList<Group>();

		setParent(null);
	}

	/** 节点名称. */
	public String name;

	/** 节点说明. */
	public String summary;

	/**
	 * 父节点
	 */
	private Group parent;

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
}
