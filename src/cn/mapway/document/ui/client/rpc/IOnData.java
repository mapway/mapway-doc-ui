package cn.mapway.document.ui.client.rpc;

// TODO: Auto-generated Javadoc
/**
 * The Interface IOnData.
 *
 * @param <T> the generic type
 */
public interface IOnData<T> {
	
	/**
	 * On error.
	 *
	 * @param url the url
	 * @param error the error
	 */
	public void onError(String url,String error);

	/**
	 * On success.
	 *
	 * @param url the url
	 * @param data the data
	 */
	public void onSuccess(String url,T data);
}
