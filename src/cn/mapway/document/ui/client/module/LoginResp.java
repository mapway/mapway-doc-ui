package cn.mapway.document.ui.client.module;

import com.google.gwt.core.client.JavaScriptObject;

// TODO: Auto-generated Javadoc
/**
 * 登录返回 { "userId" :"12346", "token" :"432432564tff45454543", "msg" :"错误内容",
 * "retCode" :1 }.
 *
 * @author zhangjianshe
 */
public class LoginResp extends JavaScriptObject {

	/**
	 * Instantiates a new login resp.
	 */
	protected LoginResp() {

	}

	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public final native String getUserId()/*-{
		return this.userId;
	}-*/;

	/**
	 * Gets the u token.
	 *
	 * @return the u token
	 */
	public final native String getUToken()/*-{
		return this.token;
	}-*/;

	/**
	 * Gets the msg.
	 *
	 * @return the msg
	 */
	public final native String getMsg()/*-{
		return this.msg;
	}-*/;

	/**
	 * Gets the ret code.
	 *
	 * @return the ret code
	 */
	public final native int getRetCode()/*-{
		return this.retCode;
	}-*/;
}
