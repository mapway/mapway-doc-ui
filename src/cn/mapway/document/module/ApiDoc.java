package cn.mapway.document.module;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.mapway.document.helper.JarInfo;

// TODO: Auto-generated Javadoc
/**
 * ApiDoc V2.
 *
 * @author zhangjianshe
 */
public class ApiDoc implements Serializable {

	/** 用户设置导出WordURL地址. */
	public String wordUrl = "";

	/** 作者. */
	public String author;

	/** 标题. */
	public String title;

	/** API版本. */
	public String version;

	/** API关联网址. */
	public String link;

	/** API简介. */
	public String summary;

	/** API 的整体说明文档. */
	public String description;

	/** API 的整体说明文档. */
	public String copyright;
	/** API 的整体说明文档. */
	public String domain;

	/** 接口访问的基地址. */
	public String basePath;

	/** API分组的根节点. */
	public Group root;

	/**
	 * Instantiates a new api doc.
	 */
	public ApiDoc() {
		root = new Group();
		downloads = new ArrayList<JarInfo>();
	}

	/**
	 * 根据路径查找Group,如果不存在这个路径的对象，就在树中创建这个路径.
	 *
	 * @param path
	 *            the path
	 * @return the group
	 */
	public Group findGroup(String path) {

		if (path == null || path.length() == 0 || path.equals("/")) {
			return root;
		}

		String[] paths = path.split("/");
		if (paths.length > 0) {
			String p = paths[0];
			if (p.length() == 0) {
				String[] temp = new String[paths.length - 1];
				for (int i = 0; i < temp.length; i++) {
					temp[i] = paths[i + 1];
				}
				paths = temp;
			}
		}
		Group g = root;

		for (int i = 0; i < paths.length; i++) {
			String p = paths[i];

			boolean find = false;
			for (Group sg : g.getChildGroups()) {
				if (sg.name.equals(p)) {// 找到节点
					g = sg;
					find = true;
				}
			}

			if (find == false) {
				// 没有找到节点 创建节点，并添加到节点树
				Group ng = new Group();
				ng.name = p;
				ng.summary = "";
				g.addChildGroup(ng);

				g = ng;
			}
		}
		return g;
	}

	/**
	 * Sort.
	 */
	public void sort() {
		processFullName();
		root.sort();
	}

	/**
	 * Process full name.
	 */
	public void processFullName() {
		processGroupFullName(root, "");
	}

	/**
	 * Process group full name.
	 *
	 * @param root
	 *            the root
	 * @param ppath
	 *            the ppath
	 */
	private void processGroupFullName(Group root, String ppath) {
		root.fullName = ppath + "/" + root.name;
		for (int i = 0; i < root.subGroups.size(); i++) {
			Group g = root.subGroups.get(i);
			processGroupFullName(g, root.fullName);
		}
	}

	/** The downloads. */
	private List<JarInfo> downloads;

	/**
	 * Gets the downloads.
	 *
	 * @return the downloads
	 */
	public List<JarInfo> getDownloads() {
		return downloads;
	}
}
