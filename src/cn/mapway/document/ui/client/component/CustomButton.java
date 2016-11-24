package cn.mapway.document.ui.client.component;

import cn.mapway.document.ui.client.resource.SysResource;

import com.google.gwt.user.client.ui.Button;

public class CustomButton extends Button {

	private Object data;

	public CustomButton() {
		this("");
	}

	public CustomButton(String summary) {
		super(summary);
		this.setStyleName(SysResource.INSTANCE.getCss().btn());
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
