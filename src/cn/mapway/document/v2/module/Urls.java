package cn.mapway.document.v2.module;

import java.util.ArrayList;
import java.util.List;


// TODO: Auto-generated Javadoc
/**
 * Url 列表，并提供列表的操作 转换，解析等操作.
 * @author zhangjianshe
 *
 */
public class Urls {
	
	/** The items. */
	List<UrlItem> items;
	
	/**
	 * Instantiates a new urls.
	 */
	public Urls() {
		items=new ArrayList<UrlItem>();
	}
	
	/**
	 * Adds the.
	 *
	 * @param item the item
	 */
	public void add(UrlItem item)
	{
		items.add(item);
	}
	
	/**
	 * Clear.
	 */
	public void clear()
	{
		items.clear();
	}
	
}
