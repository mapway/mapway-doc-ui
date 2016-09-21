package cn.mapway.document.ui.client.rpc;

import cn.mapway.document.ui.client.main.storage.LocalStorage;

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
	public String ENN_CUSTOM_TOKEN = "";
	
	/** The enn gateway token. */
	public String ENN_GATEWAY_TOKEN = "";

	/**
	 * Gets the.
	 *
	 * @return the rpc context
	 */
	public final static RpcContext get() {
		if (context == null) {
			context = new RpcContext();
			String token = LocalStorage.val("enn_custom_token");
			if (token != null) {
				context.ENN_CUSTOM_TOKEN = token;
			}
		}
		return context;
	}
}
