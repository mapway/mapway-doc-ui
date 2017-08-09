package cn.mapway.document.ui.client.main;

import cn.mapway.document.ui.client.component.Clients;
import cn.mapway.document.ui.client.module.Entry;
import cn.mapway.document.ui.client.module.ObjectInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class InputParameterPanel extends Composite {

  private static InputParameterPanelUiBinder uiBinder = GWT
      .create(InputParameterPanelUiBinder.class);

  interface InputParameterPanelUiBinder extends UiBinder<Widget, InputParameterPanel> {
  }

  public InputParameterPanel() {
    initWidget(uiBinder.createAndBindUi(this));
  }

  @UiField
  ObjectsInfoPanel paraPath;
  @UiField
  ObjectsInfoPanel paraQuery;
  @UiField
  ParameterPanel paraBody;

  @UiField
  Label lbUrl;
  @UiField
  Label lbNone;

  public void parseEntry(Entry entry) {

    boolean show = true;
    boolean urlShow = false;
    String url = entry.url();

    if (entry.pathParas().length() == 0) {
      paraPath.setVisible(false);
    } else {
      paraPath.setVisible(true);
      paraPath.parse(entry.pathParas(), "路径参数", "");
      show = false;
      urlShow = true;

      for (int i = 0; i < entry.pathParas().length(); i++) {
        ObjectInfo oi = entry.pathParas().get(i);
        url = url.replaceAll("\\{" + oi.name() + "\\}", oi.example() == null ? "" : oi.example());
      }
    }

    if (entry.queryParas().length() == 0) {
      paraQuery.setVisible(false);
    } else {
      paraQuery.setVisible(true);
      paraQuery.parse(entry.queryParas(), "查询参数", "");
      show = false;
      urlShow = true;

      url = url + "?";
      for (int i = 0; i < entry.queryParas().length(); i++) {
        ObjectInfo oi = entry.queryParas().get(i);
        if (i > 0) {
          url += "&";
        }
        url = url + oi.name() + "=" + (oi.example() == null ? "" : oi.example());
      }

    }



    lbUrl.setText("URL例子:" + Clients.getHostPort() + url);
    lbUrl.setVisible(urlShow);

    if (entry.input().length() == 0) {
      paraBody.setVisible(false);
    } else {
      paraBody.setVisible(true);
      ObjectInfo obj = entry.input().get(0);

      paraBody.parse(obj, "POST数据");
      show = false;
    }

    lbNone.setVisible(show);

  }

  public static void main(String[] args) {

  }

}
