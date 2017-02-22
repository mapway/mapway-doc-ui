package cn.mapway.document.module;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * 字段代码值.
 *
 * @author zhangjianshe
 */
public class FieldCode implements Serializable {
	
	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Gets the desc.
	 *
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * Sets the desc.
	 *
	 * @param desc the new desc
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/** The value. */
	public String value;
	
	/** The desc. */
	public String desc;

	/**
	 * Instantiates a new field code.
	 *
	 * @param v the v
	 * @param d the d
	 */
	public FieldCode(String v, String d) {
		setValue(v);
		setDesc(d);
	}
}
