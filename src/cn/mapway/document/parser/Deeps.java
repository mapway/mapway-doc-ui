package cn.mapway.document.parser;

import java.util.HashMap;

/**
 * 接口字段类型深度信息
 * 
 * @author zhangjianshe
 *
 */
public class Deeps {
	HashMap<String, ClassDepthData> map;

	private Integer level = 0;

	public Deeps() {
		map = new HashMap<String, ClassDepthData>();
		level = 0;
	}

	public int push(String type, int level) {
		ClassDepthData d = map.get(type);
		if (d == null) {
			d = new ClassDepthData();
			d.typeName = type;
			map.put(type, d);
			return d.increase(level);

		} else {
			return d.increase(level);
		}
	}

	public int getPreLevelCount(String type, int level) {
		ClassDepthData d = map.get(type);
		if (d == null) {
			return 0;
		}
		return d.getPreLevelCount(level);
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void incLevel() {
		this.level++;
	}

	public void decLevel() {
		this.level--;
	}
}
