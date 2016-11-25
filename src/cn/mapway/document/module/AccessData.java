package cn.mapway.document.module;

/**
 * 记录访问次数
 * 
 * @author zhangjianshe
 *
 */
public class AccessData {

	public Long count;
	public Long last;

	private AccessData() {

	}

	public static AccessData gAccessData;

	public final static AccessData get() {
		if (gAccessData == null) {
			gAccessData = new AccessData();
		}
		return gAccessData;
	}
}
