package cn.mapway.document.ui.client.main.storage;

import com.google.gwt.storage.client.Storage;

// TODO: Auto-generated Javadoc
/**
 * 本地存储.
 *
 * @author zhangjianshe
 */
public class LocalStorage {

  /** The Constant storage. */
  static final Storage storage = Storage.getLocalStorageIfSupported();

  /**
   * Save.
   *
   * @param key the key
   * @param data the data
   */
  public static void save(String key, String data) {
    if (storage == null) {
      return;
    }
    storage.setItem(key, data);
  }

  /**
   * Val.
   *
   * @param key the key
   * @return the string
   */
  public static String val(String key) {
    if (storage == null) {
      return null;
    }

    String data = storage.getItem(key);
    return data;
  }
}
