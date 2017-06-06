package cn.mapway.document.module;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class ObjectInfo. 类对象信息
 */
public class ObjectInfo implements Serializable {

  /**
   * Instantiates a new object info.
   */
  public ObjectInfo() {}

  /** The name. */
  public String name;

  /** The type. */
  public String type;

  /** The summary. */
  public String summary;

  /** The title. */
  public String title;

  /** The example. */
  public String example;

  /** The manditary. */
  public boolean manditary;

  /** The length. */
  public int length = 0;

  /** The fields. */
  public List<ObjectInfo> fields = new ArrayList<ObjectInfo>();

  /** 对象的JSON例子. */
  public String json;

  /** The codes. */
  public List<FieldCode> codes = new ArrayList<FieldCode>();
}
