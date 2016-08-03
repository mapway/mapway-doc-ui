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

	public static <T extends JavaScriptObject> void fetchData(String target,
			final IOnData<T> handler) {

		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET,
				URL.encode(target));

		try {
			Request request = builder.sendRequest(null, new RequestCallback() {
				public void onError(Request request, Throwable exception) {
					// Couldn't connect to server (could be timeout, SOP
					// violation, etc.)
					handler.onError(exception.getMessage());
				}

				@Override
				public void onResponseReceived(Request request,
						Response response) {
					if (200 == response.getStatusCode()) {
						// Process the response in response.getText()
						String data = response.getText();
						T obj = JsonUtils.unsafeEval(data);
						handler.onSuccess(obj);
					} else {
						// Handle the error. Can get the status text from
						// response.getStatusText()
						handler.onError(response.getStatusText());
					}
				}
			});
		} catch (RequestException e) {
			// Couldn't connect to server
			handler.onError(e.getMessage());
		}
	}

}
