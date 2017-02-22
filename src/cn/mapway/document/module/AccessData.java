package cn.mapway.document.module;

// TODO: Auto-generated Javadoc
/**
 * 记录访问次数.
 *
 * @author zhangjianshe
 */
public class AccessData {

	/** The count. */
	public Long count;
	
	/** The last. */
	public Long last;

	/**
	 * Instantiates a new access data.
	 */
	private AccessData() {

	}

	/** The g access data. */
	public static AccessData gAccessData;

	/**
	 * Gets the.
	 *
	 * @return the access data
	 */
	public final static AccessData get() {
		if (gAccessData == null) {
			gAccessData = new AccessData();
		}
		return gAccessData;
	}
}
