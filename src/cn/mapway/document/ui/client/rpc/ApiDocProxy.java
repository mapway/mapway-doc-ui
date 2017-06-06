package cn.mapway.document.ui.client.rpc;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;


/**
 * The Class ApiDocProxy.
 */
public class ApiDocProxy {

  /**
   * 向服务器请求接口调用.
   *
   * @param url the url
   * @param jsonData the json data
   * @param contextType the context type
   * @param method the method
   * @param handler the handler
   * @throws RequestException the request exception
   */
  public static void fetchString(final String url, String jsonData, String contextType,
      String method, final IOnData<String> handler) throws RequestException {

    RequestBuilder builder =
        new RequestBuilder(method.equalsIgnoreCase("post") ? RequestBuilder.POST
            : RequestBuilder.GET, URL.encode(url));
    if (contextType == null || contextType.length() == 0) {
      contextType = "application/json;charset=UTF-8";
    }
    builder.setHeader("Content-type", contextType);
    RpcContext context = RpcContext.get();
    if (context.KEY != null && context.KEY.length() > 0 && context.VALUE != null) {
      builder.setHeader(context.KEY, context.VALUE);
    }
    Request request = builder.sendRequest(jsonData, new RequestCallback() {
      @Override
      public void onError(Request request, Throwable exception) {
        handler.onError(url, exception.getMessage());
      }

      @Override
      public void onResponseReceived(Request request, Response response) {
        if (200 == response.getStatusCode()) {
          String data = response.getText();
          handler.onSuccess(url, data);
        } else {
          handler.onError(url, response.getStatusText());
        }
      }
    });
  }

  /**
   * 向服务器请求接口调用.
   *
   * @param <T> the generic type
   * @param url the url
   * @param jsonData the json data
   * @param method the method
   * @param handler the handler
   * @throws RequestException the request exception
   */
  public static <T extends JavaScriptObject> void fetch(final String url, String jsonData,
      String method, final IOnData<T> handler) throws RequestException {

    RequestBuilder builder =
        new RequestBuilder(method.equalsIgnoreCase("post") ? RequestBuilder.POST
            : RequestBuilder.GET, URL.encode(url));
    RpcContext context = RpcContext.get();
    if (context.KEY != null && context.KEY.length() > 0 && context.VALUE != null) {
      builder.setHeader(context.KEY, context.VALUE);
    }
    Request request = builder.sendRequest(jsonData, new RequestCallback() {
      @Override
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

  /**
   * Fetch data.
   *
   * @param <T> the generic type
   * @param target the target
   * @param handler the handler
   */
  public static <T extends JavaScriptObject> void fetchData(final String target,
      final IOnData<T> handler) {

    RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL.encode(target));

    try {
      Request request = builder.sendRequest(null, new RequestCallback() {
        @Override
        public void onError(Request request, Throwable exception) {
          handler.onError(target, exception.getMessage());
        }

        @Override
        public void onResponseReceived(Request request, Response response) {
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
