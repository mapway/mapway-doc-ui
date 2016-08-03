package cn.mapway.document.ui.client;

import cn.mapway.document.ui.client.main.MainFrame;
import cn.mapway.document.ui.client.resource.SysResource;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootLayoutPanel;

public class ApiDocEntry implements EntryPoint {

	@Override
	public void onModuleLoad() {

		SysResource.INSTANCE.getCss().ensureInjected();
		MainFrame frame = new MainFrame();
		RootLayoutPanel.get().add(frame);

		String target = GWT.getModuleBaseURL() + "../doc";
		frame.init(target);
	}

}
