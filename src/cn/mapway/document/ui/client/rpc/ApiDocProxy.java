package cn.mapway.document.ui.client.rpc;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;

public class ApiDocProxy {

	/**
	 * 向服务器请求接口调用
	 * 
	 * @param url
	 * @param jsonData
	 * @param method
	 * @param handler
	 * @throws RequestException
	 */
	public static void fetchString(final String url, String jsonData,
			String contextType, String method, final IOnData<String> handler)
			throws RequestException {

		RequestBuilder builder = new RequestBuilder(
				method.equalsIgnoreCase("post") ? RequestBuilder.POST
						: RequestBuilder.GET, URL.encode(url));
		if (contextType == null || contextType.length() == 0) {
			contextType = "application/json;charset=UTF-8";
		}
		builder.setHeader("Content-type", contextType);
		if (RpcContext.get().ENN_CUSTOM_TOKEN.length() > 0) {
			builder.setHeader("ENN-CUSTOM-TOKEN",
					RpcContext.get().ENN_CUSTOM_TOKEN);
		}
		if (RpcContext.get().ENN_GATEWAY_TOKEN.length() > 0) {
			builder.setHeader("ENN-GATEWAY-TOKEN",
					RpcContext.get().ENN_GATEWAY_TOKEN);
		}
		Request request = builder.sendRequest(jsonData, new RequestCallback() {
			public void onError(Request request, Throwable exception) {
				handler.onError(url,exception.getMessage());
			}

			@Override
			public void onResponseReceived(Request request, Response response) {
				if (200 == response.getStatusCode()) {
					String data = response.getText();
					handler.onSuccess(url,data);
				} else {
					handler.onError(url,response.getStatusText());
				}
			}
		});
	}

	/**
	 * 向服务器请求接口调用
	 * 
	 * @param url
	 * @param jsonData
	 * @param method
	 * @param handler
	 * @throws RequestException
	 */
	public static <T extends JavaScriptObject> void fetch(final String url,
			String jsonData, String method, final IOnData<T> handler)
			throws RequestException {

		RequestBuilder builder = new RequestBuilder(
				method.equalsIgnoreCase("post") ? RequestBuilder.POST
						: RequestBuilder.GET, URL.encode(url));
		builder.setHeader("ENN-CUSTOM-TOKEN", RpcContext.get().ENN_CUSTOM_TOKEN);
		builder.setHeader("ENN-GATEWAY-TOKEN",
				RpcContext.get().ENN_GATEWAY_TOKEN);

		Request request = builder.sendRequest(jsonData, new RequestCallback() {
			public void onError(Request request, Throwable exception) {
				handler.onError(url, exception.getMessage());
			}

			@Override
			public void onResponseReceived(Request request, Response response) {
				if (200 == response.getStatusCode()) {
					String data = response.getText();
					T obj = JsonUtils.unsafeEval(data);
					handler.onSuccess(url, obj);
				} else {
					handler.onError(url, response.getStatusText());
				}
			}
		});
	}

	public static <T extends JavaScriptObject> void fetchData(
			final String target, final IOnData<T> handler) {

		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET,
				URL.encode(target));

		try {
			Request request = builder.sendRequest(null, new RequestCallback() {
				public void onError(Request request, Throwable exception) {
					handler.onError(target, exception.getMessage());
				}

				@Override
				public void onResponseReceived(Request request,
						Response response) {
					if (200 == response.getStatusCode()) {
						String data = response.getText();
						T obj = JsonUtils.unsafeEval(data);
						handler.onSuccess(target, obj);
					} else {
						handler.onError(target, response.getStatusText());
					}
				}
			});
		} catch (RequestException e) {
			handler.onError(target, e.getMessage());
		}
	}

}
