package cn.mapway.document.ui.client.test;

import java.util.Date;

import cn.mapway.document.ui.client.main.JsonPanel;
import cn.mapway.document.ui.client.main.storage.LocalStorage;
import cn.mapway.document.ui.client.module.Entry;
import cn.mapway.document.ui.client.module.LoginResp;
import cn.mapway.document.ui.client.resource.SysResource;
import cn.mapway.document.ui.client.rpc.ApiDocProxy;
import cn.mapway.document.ui.client.rpc.IOnData;
import cn.mapway.document.ui.client.rpc.RpcContext;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.HasCloseHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * 测试面板
 * 
 * @author zhangjianshe
 *
 */
public class TestPanel extends Composite implements HasCloseHandlers<Void> {

	private static TestPanelUiBinder uiBinder = GWT
			.create(TestPanelUiBinder.class);

	interface TestPanelUiBinder extends UiBinder<Widget, TestPanel> {
	}

	public TestPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		btnExecute.setStyleName(SysResource.INSTANCE.getCss().btn());
		btnClose.setStyleName(SysResource.INSTANCE.getCss().btn());
		imgLoadding.setUrl(SysResource.INSTANCE.loading().getSafeUri());
		btnHistory.setUrl(SysResource.INSTANCE.history().getSafeUri());
		txtCustomToken.addBlurHandler(new BlurHandler() {

			@Override
			public void onBlur(BlurEvent event) {
				if (txtCustomToken.getValue().length() > 0) {
					RpcContext.get().ENN_CUSTOM_TOKEN = txtCustomToken
							.getValue();
				}
			}
		});
		txtGatewayToken.addBlurHandler(new BlurHandler() {

			@Override
			public void onBlur(BlurEvent event) {
				if (txtGatewayToken.getValue().length() > 0) {
					RpcContext.get().ENN_CUSTOM_TOKEN = txtCustomToken
							.getValue();
				}
			}
		});
		txtCustomToken.setValue(RpcContext.get().ENN_CUSTOM_TOKEN);
	}

	@UiField
	Label lbTitle;
	@UiField
	Label lbUrl;

	@UiField
	TextArea txtInput;
	@UiField
	JsonPanel txtOutput;

	Entry mEntry;

	public void invoke(Entry entry) {
		mEntry = entry;
		lbTitle.setText(entry.title());
		lbUrl.setText(entry.relativePath());
		String his = readHistory();
		if (his.length() == 0) {
			his = entry.input().get(0).json();
		}
		txtInput.setValue(his);
		txtOutput.setString("");
	}

	private String readHistory() {
		String r = "";
		String v = LocalStorage.val(mEntry.relativePath());
		if (v == null || v.length() == 0) {
			return "";
		}

		String[] vs = v.split("\\|");

		if (vs.length > 0) {
			String[] itemdata = vs[0].split("`");
			r = itemdata[1];
			return r;
		}

		return r;
	}

	@UiField
	Button btnExecute;

	@UiField
	Button btnClose;

	@UiField
	Image imgLoadding;

	@UiField
	Image btnHistory;

	@UiHandler("btnExecute")
	void onExecute(ClickEvent ev) {
		imgLoadding.setVisible(true);
		txtOutput.setText("");
		// 保存到本地
		String v = LocalStorage.val(mEntry.relativePath());
		Date d = new Date();
		String key = (d.getYear() + 1900) + "-" + (d.getMonth() + 1) + "-"
				+ d.getDate() + " " + d.getHours() + ":" + d.getMinutes() + ":"
				+ d.getSeconds();
		String va = txtInput.getValue();

		if (v == null || v.length() == 0) {
			LocalStorage.save(mEntry.relativePath(), key + "`" + va);
		} else {
			LocalStorage.save(mEntry.relativePath(), key + "`" + va + "|" + v);
		}
		try {
			ApiDocProxy.fetchString(mEntry.relativePath(), txtInput.getValue(),
					"", mEntry.invokeMethod(), new IOnData<String>() {
						@Override
						public void onError(String url, String error) {
							txtOutput.setString(error);
							imgLoadding.setVisible(false);

						}

						public void onSuccess(String url, String data) {
							JavaScriptObject jso = JsonUtils.unsafeEval(data);
							txtOutput.setJson(JsonUtils.stringify(jso, "   "));
							imgLoadding.setVisible(false);
							processToken(url, data);
						};
					});
		} catch (RequestException e) {
			txtOutput.setString(e.getMessage());
			imgLoadding.setVisible(false);
		}
	}

	/**
	 * 处理TOKEN
	 * 
	 * @param url
	 * @param data
	 */
	protected void processToken(String url, String data) {

		if (url.endsWith("/app/custom/login")) {
			LoginResp resp = JsonUtils.unsafeEval(data);
			if (0 == resp.getRetCode()) {
				RpcContext.get().ENN_CUSTOM_TOKEN = resp.getUToken();
				txtCustomToken.setValue(resp.getUToken());
				LocalStorage.save("enn_custom_token", resp.getUToken());
			}
		}
	}

	@UiField
	TextBox txtCustomToken;

	@UiField
	TextBox txtGatewayToken;

	@UiHandler("btnClose")
	void onClose(ClickEvent ev) {
		CloseEvent.fire(this, null);
	}

	@Override
	public HandlerRegistration addCloseHandler(CloseHandler<Void> handler) {
		return addHandler(handler, CloseEvent.getType());
	}

	PopupPanel pop = null;
	InputHistoryPanel historyPanel;
	private CloseHandler<HistoryData> itemSelected = new CloseHandler<HistoryData>() {

		@Override
		public void onClose(CloseEvent<HistoryData> event) {
			txtInput.setValue(event.getTarget().value);
			pop.hide();
		}
	};

	/**
	 * 显示历史记录
	 * 
	 * @param e
	 */
	@UiHandler("btnHistory")
	void onHistory(ClickEvent e) {
		if (pop == null) {
			pop = new PopupPanel(true, true);
			historyPanel = new InputHistoryPanel();
			historyPanel.addCloseHandler(itemSelected);
			pop.add(historyPanel);
		}
		pop.showRelativeTo(btnHistory);
		historyPanel.render(mEntry.relativePath());
	}
}