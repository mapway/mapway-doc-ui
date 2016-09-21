package cn.mapway.document.ui.client.rpc;

import cn.mapway.document.ui.client.main.storage.LocalStorage;

/**
 * RPC调用的上下文
 * 
 * @author zhangjianshe
 *
 */
public class RpcContext {
	private static RpcContext context = null;
	public String ENN_CUSTOM_TOKEN = "";
	public String ENN_GATEWAY_TOKEN = "";

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
