package cn.mapway.document.module;

import java.io.Serializable;

/**
 * 字段代码值
 * 
 * @author zhangjianshe
 *
 */
public class FieldCode implements Serializable {
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String value;
	public String desc;

	public FieldCode(String v, String d) {
		setValue(v);
		setDesc(d);
	}
}
