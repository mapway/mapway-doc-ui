package cn.mapway.document.ui.client.module;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * 登录返回 { "userId" :"12346", "token" :"432432564tff45454543", "msg" :"错误内容",
 * "retCode" :1 }
 * 
 * @author zhangjianshe
 *
 */
public class LoginResp extends JavaScriptObject {

	protected LoginResp() {

	}

	public final native String getUserId()/*-{
		return this.userId;
	}-*/;

	public final native String getUToken()/*-{
		return this.token;
	}-*/;

	public final native String getMsg()/*-{
		return this.msg;
	}-*/;

	public final native int getRetCode()/*-{
		return this.retCode;
	}-*/;
}
