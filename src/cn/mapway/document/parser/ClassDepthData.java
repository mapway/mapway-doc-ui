package cn.mapway.document.parser;

import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * 类深度数据.
 *
 * @author zhangjianshe
 */
public class ClassDepthData {
	
	/** The Constant LEVEL. */
	private static final int LEVEL = 100;
	
	/** The type name. */
	public String typeName;
	
	/** The level count. */
	private ArrayList<Integer> levelCount;

	/**
	 * Instantiates a new class depth data.
	 */
	public ClassDepthData() {
		levelCount = new ArrayList<Integer>();
		for (int i = 0; i < LEVEL; i++) {
			levelCount.add(0);
		}
	}

	/**
	 * Increase.
	 *
	 * @param level the level
	 * @return the int
	 */
	public int increase(int level) {
		int count = levelCount.get(level);
		count++;
		levelCount.set(level, count);
		return count;
	}

	/**
	 * Gets the count.
	 *
	 * @param level the level
	 * @return the count
	 */
	public int getCount(int level) {
		return levelCount.get(level);
	}

	/**
	 * Gets the pre level count.
	 *
	 * @param level2 the level 2
	 * @return the pre level count
	 */
	public int getPreLevelCount(int level2) {
		int count = 0;
		for (int i = 0; i < level2 - 1; i++) {
			if (levelCount.get(i) > 0) {
				count++;
			}
		}
		return count;
	}

}
