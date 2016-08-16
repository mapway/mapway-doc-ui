package cn.mapway.document.ui.client.main.storage;

import com.google.gwt.storage.client.Storage;

/**
 * 本地存储
 * 
 * @author zhangjianshe
 *
 */
public class LocalStorage {

	static final Storage storage = Storage.getLocalStorageIfSupported();

	public static void save(String key, String data) {
		if (storage == null) {
			return;
		}
		storage.setItem(key, data);
	}

	public static String val(String key) {
		if (storage == null) {
			return null;
		}

		String data = storage.getItem(key);
		return data;
	}
}
