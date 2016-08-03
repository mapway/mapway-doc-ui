package cn.mapway.document.ui.client.rpc;

public interface IOnData<T> {
	public void onError(String error);

	public void onSuccess(T data);
}
