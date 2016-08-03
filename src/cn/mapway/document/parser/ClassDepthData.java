package cn.mapway.document.parser;

import java.util.ArrayList;

/**
 * 类深度数据
 * 
 * @author zhangjianshe
 *
 */
public class ClassDepthData {
	private static final int LEVEL = 100;
	public String typeName;
	private ArrayList<Integer> levelCount;

	public ClassDepthData() {
		levelCount = new ArrayList<Integer>();
		for (int i = 0; i < LEVEL; i++) {
			levelCount.add(0);
		}
	}

	public int increase(int level) {
		int count = levelCount.get(level);
		count++;
		levelCount.set(level, count);
		return count;
	}

	public int getCount(int level) {
		return levelCount.get(level);
	}

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
