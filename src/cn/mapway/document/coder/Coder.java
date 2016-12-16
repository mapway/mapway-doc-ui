package cn.mapway.document.coder;

/**
 * The Class Coder.
 */
public class Coder {

	/** The code. */
	public String code;

	/** The message. */
	public String message;

	/**
	 * Instantiates a new coder.
	 *
	 * @param code
	 *            the code
	 * @param message
	 *            the message
	 */
	public Coder(String code, String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * Bind.
	 *
	 * @param objects
	 *            the objects
	 * @return the coder
	 */
	public Coder bind(Object... objects) {
		if (objects == null) {
			return this;
		}

		String str = message;
		for (int i = 0; i < objects.length; i++) {
			Object o = objects[i];
			if (o == null) {
				o = "null";
			}
			str = str.replaceAll("\\$\\{" + i + "\\}", o.toString());
		}
		return new Coder(code, str);
	}

	/**
	 * 输出对象字符串
	 */
	@Override
	public String toString() {
		return "{\"code\":\"" + code + "\",\"" + message + "\"}";
	}
}
