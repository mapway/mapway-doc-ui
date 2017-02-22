package cn.mapway.document.ui.client.component;

import com.google.gwt.user.client.Window;


/**
 * The Class Clients.
 */
public class Clients {

	/**
	 * 获取主机网络地址.
	 *
	 * @return the host port
	 */
	public static final String getHostPort() {
		String host = Window.Location.getHost();
		String protocol = Window.Location.getProtocol();
		String r = protocol + "//" + host;
		return r;
	}
}
