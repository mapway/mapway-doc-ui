package cn.mapway.document.ui.client.rpc;

import cn.mapway.document.ui.client.main.storage.LocalStorage;
import cn.mapway.document.ui.client.test.TestPanel;

// TODO: Auto-generated Javadoc
/**
 * RPC调用的上下文.
 *
 * @author zhangjianshe
 */
public class RpcContext {

  /** The context. */
  private static RpcContext context = null;

  /** The enn custom token. */
  public String KEY = "";

  /** The enn gateway token. */
  public String VALUE = "";

  /**
   * Gets the.
   *
   * @return the rpc context
   */
  public final static RpcContext get() {
    if (context == null) {
      context = new RpcContext();
      context.KEY = LocalStorage.val(TestPanel.GWT_USER_HEADER);
      if (context.KEY != null) {
        context.VALUE = LocalStorage.val(context.KEY);
      }
    }
    return context;
  }
}
