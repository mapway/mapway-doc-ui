package cn.mapway.document.ui.client.main;

import java.util.List;
import java.util.Map;

import cn.mapway.document.ui.client.component.CustomAnchor;
import cn.mapway.document.ui.client.module.GenInfo;
import cn.mapway.document.ui.client.module.ObjectInfo;
import cn.mapway.document.ui.client.resource.SysResource;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;

// TODO: Auto-generated Javadoc
/**
 * The Class ObjectInfoPanel.
 */
public class ObjectInfoPanel extends Grid implements HasSelectionHandlers<ObjectInfo>, ClickHandler {

  /** The lb title. */
  private Label lbTitle;

  /** The lb summary. */
  private HTML lbSummary;

  /*
   * (non-Javadoc)
   * 
   * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event
   * .dom.client.ClickEvent)
   */
  @Override
  public void onClick(ClickEvent arg0) {
    CustomAnchor anchor = (CustomAnchor) arg0.getSource();
    ObjectInfo info = (ObjectInfo) anchor.getData();

    SelectionEvent.fire(this, info);
  }

  /**
   * Instantiates a new object info panel.
   */
  public ObjectInfoPanel() {
    init();
    this.setStyleName(SysResource.INSTANCE.getCss().cssTable());
  }

  /**
   * Inits the.
   */
  void init() {
    Element e = getElement();
    e.setAttribute("borderCollapse", "collapse");
    e.setAttribute("cellPadding", "10px");
    e.setAttribute("cellSpacing", "1px");

    RowFormatter rf = getRowFormatter();

    lbTitle = new Label();
    lbTitle.setStyleName(SysResource.INSTANCE.getCss().title());
    lbSummary = new HTML();
    lbSummary.setStyleName(SysResource.INSTANCE.getCss().summary());
    HTMLPanel vp = new HTMLPanel("");
    vp.add(lbTitle);
    vp.add(lbSummary);

    this.resize(2, 5);
    int row = 0;
    rf.setStyleName(row, SysResource.INSTANCE.getCss().rowTitle());
    setWidget(row, 0, vp);
    getCellFormatter().getElement(0, 0).setAttribute("colspan", "5");

    this.removeCell(0, 1);
    this.removeCell(0, 1);
    this.removeCell(0, 1);
    this.removeCell(0, 1);

    // 名称 类型 长度 选项 解释
    Label l;
    int col = 0;

    row++;
    l = new Label("名称");
    l.setStyleName(SysResource.INSTANCE.getCss().tableHeader());
    setWidget(row, col++, l);

    l = new Label("类型");
    l.setStyleName(SysResource.INSTANCE.getCss().tableHeader());
    setWidget(row, col++, l);

    l = new Label("长度");
    l.setStyleName(SysResource.INSTANCE.getCss().tableHeader());
    setWidget(row, col++, l);

    l = new Label("选项");
    l.setStyleName(SysResource.INSTANCE.getCss().tableHeader());
    setWidget(row, col++, l);

    l = new Label("解释");
    l.setStyleName(SysResource.INSTANCE.getCss().tableHeader());
    setWidget(row, col++, l);

    rf.setStylePrimaryName(row, SysResource.INSTANCE.getCss().rowh());

    CellFormatter cf = getCellFormatter();
    cf.setWidth(row, 0, "200px");
    cf.setWidth(row, 1, "200px");
    cf.setWidth(row, 2, "80px");
    cf.setWidth(row, 3, "80px");
  }

  /**
   * 级联操作.
   *
   * @param obj the obj
   * @param objList the obj list
   * @param mapper the mapper
   */
  public void parse(ObjectInfo obj, List<GenInfo> objList, Map<String, Anchor> mapper) {

    lbTitle.setText((obj.title() == null ? "" : obj.title()));
    lbSummary.setHTML(obj.summary() == null ? "" : obj.summary());

    this.resizeRows(obj.fields().length() + 2);

    RowFormatter rf = getRowFormatter();

    // 处理字段
    int row = 2;
    for (int i = 0; i < obj.fields().length(); i++) {
      ObjectInfo o = obj.fields().get(i);
      // 名称 类型 长度 选项 解释
      Label l;
      int col = 0;

      if (row % 2 == 0) {
        rf.setStyleName(row, SysResource.INSTANCE.getCss().row0());
      } else {
        rf.setStyleName(row, SysResource.INSTANCE.getCss().row1());
      }

      l = new Label(o.name());

      l.setStyleName(SysResource.INSTANCE.getCss().name());
      setWidget(row, col++, l);

      String type = o.type();
      if (isPrimitive(type)) {
        l = new Label(o.type());
        l.setStyleName(SysResource.INSTANCE.getCss().type());

        setWidget(row, col++, l);
      } else {
        CustomAnchor a = new CustomAnchor();
        a.setText(type);
        a.setData(o);
        a.setStyleName(SysResource.INSTANCE.getCss().typeLink());
        a.addClickHandler(this);
        setWidget(row, col++, a);

        boolean hasFind = findObj(type, objList);
        if (!hasFind) {
          GenInfo info = new GenInfo();
          info.type = type;
          info.obj = o;
          info.gen = false;
          objList.add(info);
        }
      }

      l = handleLengthConstrain(o);



      l.setStyleName(SysResource.INSTANCE.getCss().text());
      setWidget(row, col++, l);

      l = new Label(o.manditary() == true ? "必须" : "可选");
      l.setStyleName(SysResource.INSTANCE.getCss().text());
      setWidget(row, col++, l);

      FlowPanel fp = new FlowPanel();

      l = new Label(o.title());
      l.setStyleName(SysResource.INSTANCE.getCss().summary());
      fp.add(l);
      setWidget(row, col++, fp);

      // 处理返回值
      JsArray<FieldCode> codes = o.codes();
      if (codes != null && codes.length() > 0) {
        FlexTable g = new FlexTable();

        Element ele = g.getElement();
        ele.setAttribute("borderCollapse", "collapse");
        ele.setAttribute("rules", "rows");
        ele.setAttribute("border", "1px");
        ele.setAttribute("cellPadding", "3px");

        g.setWidget(0, 0, new Label("代码"));
        g.setWidget(0, 1, new Label("说明"));

        CellFormatter cf = g.getCellFormatter();
        cf.setAlignment(0, 0, HasHorizontalAlignment.ALIGN_RIGHT, HasVerticalAlignment.ALIGN_MIDDLE);

        for (int index = 0; index < codes.length(); index++) {
          FieldCode fc = codes.get(index);
          l = new Label(fc.value());
          l.setStyleName(SysResource.INSTANCE.getCss().type());
          g.setWidget(index + 1, 0, l);

          cf.setAlignment(index + 1, 0, HasHorizontalAlignment.ALIGN_RIGHT,
              HasVerticalAlignment.ALIGN_MIDDLE);

          l = new Label(fc.desc());
          l.setStyleName(SysResource.INSTANCE.getCss().summary());
          g.setWidget(index + 1, 1, l);
        }

        fp.add(g);
      }
      row++;
    }
  }

  /**
   * 处理长度约束.
   * 
   * @param o
   * @return
   */
  static Label handleLengthConstrain(ObjectInfo o) {
    Label l;
    if (isString(o)) {
      if (o.minLength() == 0 && o.maxLength() == 0) {
        l = new Label("不限长度");
      } else {
        l = new Label(o.minLength() + "-" + o.maxLength());
      }
    } else if (isNumber(o)) {
      if (o.min() != null && o.max() != null) {
        l = new Label(o.min() + "至" + o.max());
      } else if (o.min() != null) {
        l = new Label("最小为:" + o.min());
      } else if (o.max() != null) {
        l = new Label("最大为:" + o.min());
      } else {
        l = new Label("未设定范围");
      }
    } else {
      l = new Label("");
    }

    return l;
  }

  private static boolean isNumber(ObjectInfo o) {
    for (String s : numbers) {
      if (o.type().contains(s)) {
        return true;
      }
    }
    return false;
  }

  private static boolean isString(ObjectInfo o) {
    for (String s : strings) {
      if (o.type().contains(s)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Find obj.
   *
   * @param type the type
   * @param objList the obj list
   * @return true, if successful
   */
  private boolean findObj(String type, List<GenInfo> objList) {
    for (GenInfo info : objList) {
      if (info.type.equals(type)) {
        return true;
      }
    }
    return false;
  }

  /** The ps. */
  private static String[] numbers = {"int", "Integer", "float", "FLoat", "Double", "double",
      "long", "Long", "short", "byte"};
  /** The ps. */
  private static String[] strings = {"String"};
  /** The ps. */
  private static String[] ps = {"int", "Integer", "float", "FLoat", "Double", "double", "long",
      "Long", "Date", "DateTime", "String", "boolean", "Boolean", "char", "short", "byte",
      "Timestamp"};

  /**
   * Checks if is primitive.
   *
   * @param type the type
   * @return true, if is primitive
   */
  public boolean isPrimitive(String type) {

    for (String s : ps) {
      if (type.contains(s)) {
        return true;
      }
    }
    return false;

  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.gwt.event.logical.shared.HasSelectionHandlers#addSelectionHandler
   * (com.google.gwt.event.logical.shared.SelectionHandler)
   */
  @Override
  public HandlerRegistration addSelectionHandler(SelectionHandler<ObjectInfo> handler) {
    return addHandler(handler, SelectionEvent.getType());
  }
}
