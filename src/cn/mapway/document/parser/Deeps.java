package cn.mapway.document.parser;

import java.util.HashMap;

// TODO: Auto-generated Javadoc
/**
 * 接口字段类型深度信息.
 *
 * @author zhangjianshe
 */
public class Deeps {
	
	/** The map. */
	HashMap<String, ClassDepthData> map;

	/** The level. */
	private Integer level = 0;

	/**
	 * Instantiates a new deeps.
	 */
	public Deeps() {
		map = new HashMap<String, ClassDepthData>();
		level = 0;
	}

	/**
	 * Push.
	 *
	 * @param type the type
	 * @param level the level
	 * @return the int
	 */
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

	/**
	 * Gets the pre level count.
	 *
	 * @param type the type
	 * @param level the level
	 * @return the pre level count
	 */
	public int getPreLevelCount(String type, int level) {
		ClassDepthData d = map.get(type);
		if (d == null) {
			return 0;
		}
		return d.getPreLevelCount(level);
	}

	/**
	 * Gets the level.
	 *
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * Sets the level.
	 *
	 * @param level the new level
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * Inc level.
	 */
	public void incLevel() {
		this.level++;
	}

	/**
	 * Dec level.
	 */
	public void decLevel() {
		this.level--;
	}
}
