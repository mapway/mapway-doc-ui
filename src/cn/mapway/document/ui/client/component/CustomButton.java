package cn.mapway.document.ui.client.component;

import cn.mapway.document.ui.client.resource.SysResource;

import com.google.gwt.user.client.ui.Button;

// TODO: Auto-generated Javadoc
/**
 * The Class CustomButton.
 */
public class CustomButton extends Button {

	/** The data. */
	private Object data;

	/**
	 * Instantiates a new custom button.
	 */
	public CustomButton() {
		this("");
	}

	/**
	 * Instantiates a new custom button.
	 *
	 * @param summary the summary
	 */
	public CustomButton(String summary) {
		super(summary);
		this.setStyleName(SysResource.INSTANCE.getCss().btn());
	}

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * Sets the data.
	 *
	 * @param data the new data
	 */
	public void setData(Object data) {
		this.data = data;
	}

}
