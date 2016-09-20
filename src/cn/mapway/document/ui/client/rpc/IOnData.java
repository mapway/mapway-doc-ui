package cn.mapway.document.ui.client.rpc;

public interface IOnData<T> {
	public void onError(String url,String error);

	public void onSuccess(String url,T data);
}
