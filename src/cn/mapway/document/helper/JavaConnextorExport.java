package cn.mapway.document.helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.lang.Strings;

import cn.mapway.document.module.ApiDoc;
import cn.mapway.document.module.Entry;
import cn.mapway.document.module.Group;
import cn.mapway.document.module.ObjectInfo;
import cn.mapway.document.resource.Template;


/**
 * 输出Java Connect 代码.
 *
 * @author zhangjianshe
 */
public class JavaConnextorExport {

  /** The modules. */
  Map<String, ObjectInfo> modules;

  /**
   * Instantiates a new gwt connextor export.
   */
  public JavaConnextorExport() {
    modules = new HashMap<String, ObjectInfo>();
  }

  /**
   * 输出API 对应的 Java Connector 模型在独立的文件中
   * 
   * @param api
   * @param packageName
   * @param className
   * @return
   */
  public List<JavaClassContent> export2(ApiDoc api, String packageName, String className) {

    entries = new HashMap<String, Integer>();

    ArrayList<JavaClassContent> clazzs = new ArrayList<JavaClassContent>();

    StringBuilder imports = new StringBuilder();
    imports.append("import " + packageName + ".module.*;\r\n");
    StringBuilder sb = new StringBuilder();

    modules = new HashMap<String, ObjectInfo>();
    Group root = api.root;

    recurse(sb, root, modules);

    String template;
    try {
      template = Template.readTemplate("/cn/mapway/document/resource/connect.txt");
    } catch (IOException e) {
      e.printStackTrace();
      return clazzs;
    }

    processModuleMody(packageName, modules, clazzs);

    Map<String, String> map = new HashMap<String, String>();
    map.put("package", packageName);
    map.put("name", className);
    map.put("basepath", "");
    map.put("importModules", imports.toString());
    map.put("apis", sb.toString());
    map.put("modules", "");

    JavaClassContent jcc = new JavaClassContent();
    jcc.body = replace(template, map);
    jcc.className = className;
    jcc.packageName = packageName;
    clazzs.add(jcc);
    return clazzs;
  }

  private void processModuleMody(String packageName, Map<String, ObjectInfo> ms,
      ArrayList<JavaClassContent> clazzs) {


    for (String key : ms.keySet()) {

      ObjectInfo oi = ms.get(key);
      StringBuilder sb = new StringBuilder();

      sb.append("package " + packageName + ".module;\r\n\r\n");
      sb.append("\r\nimport java.util.*;\r\n");
      sb.append("\r\n/**\r\n");
      sb.append("  (" + oi.title + ").\r\n");
      sb.append("  " + oi.summary + "\r\n");
      sb.append("  */\r\n");
      sb.append("public  class " + getType(unwrapType(oi.type))
          + " implements java.io.Serializable,com.google.gwt.user.client.rpc.IsSerializable {\r\n");

      for (ObjectInfo fi : oi.fields) {
        sb.append("  private " + getType(fi.type) + "  " + fi.name + ";\r\n");

        sb.append("  /**\r\n  * 获取" + fi.title + " " + fi.summary + ".\r\n   */\r\n");
        sb.append("  public " + getType(fi.type) + " get" + Strings.upperFirst(fi.name)
            + "() {\r\n" + "        return this." + fi.name + ";\r\n  };\r\n");

        sb.append("  /**\r\n  " + fi.title + "\r\n " + fi.summary + "\r\n */\r\n");
        sb.append("  public  void set" + Strings.upperFirst(fi.name) + "(" + getType(fi.type)
            + " v) {\r\n" + "        this." + fi.name + " = v;\r\n  };\r\n");
      }
      sb.append("}\r\n");

      JavaClassContent jcc = new JavaClassContent();
      jcc.body = sb.toString();
      jcc.className = getType(unwrapType(oi.type));
      jcc.packageName = packageName + ".module";

      clazzs.add(jcc);

    }



  }



  /**
   * Replace.
   *
   * @param str the str
   * @param data the data
   * @return the string
   */
  public String replace(String str, Map<String, String> data) {
    try {
      return StrUtil.composeMessage(str, data);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "";
  }



  /**
   * Gets the type.
   *
   * @param type the type
   * @return the type
   */
  private String getType(String type) {
    if (type.startsWith("List")) {
      String subtype = type.substring(5, type.length() - 1);
      if (subtype.equals("java.lang.String")) {
        return "List<String>";
      } else if (subtype.equals("java.lang.Boolean") || subtype.equals("bool")) {
        return "Boolean";
      } else if (subtype.equals("int") || subtype.equals("java.lang.Integer")
          || subtype.equals("java.lang.Long") || subtype.equals("long")
          || subtype.equals("java.lang.Byte") || subtype.equals("byte")
          || subtype.equals("java.lang.Short") || subtype.equals("short")) {
        return "List<Integer>";
      } else if (subtype.equals("double") || subtype.equals("java.lang.Double")
          || subtype.equals("float") || subtype.equals("java.lang.Float")) {
        return "List<Double>";
      }
      if (subtype.equals("java.util.Map")) {
        return "List<>";
      }

      return "List<" + getType(subtype) + ">";
    }

    String t = type;

    int index = type.lastIndexOf(".");
    if (index > 0) {
      return type.substring(index + 1);
    }
    return changeTypeFromObject2Native(t);
  }

  /**
   * 将 Java对象类型转化为原生类型 "int", "Integer", "float", "Float", "Double", "double", "long", "Long",
   * "Date", "DateTime", "String", "boolean", "Boolean", "char", "short", "byte", "Timestamp", "Map"
   * 
   * @param type
   * @return
   */
  private String changeTypeFromObject2Native(String type) {

    if ("Integer".equals(type)) {
      return "int";
    }
    if ("Double".equals(type)) {
      return "double";
    }
    if ("Boolean".equals(type)) {
      return "boolean";
    }
    if ("Char".equals(type)) {
      return "char";
    }
    if ("Short".equals(type)) {
      return "int";
    }
    if ("Long".equals(type)) {
      return "int";
    }
    if ("long".equals(type)) {
      return "int";
    }
    if ("Float".equals(type)) {
      return "double";
    }
    if ("float".equals(type)) {
      return "double";
    }



    return type;
  }

  /**
   * 循环处理接口.
   *
   * @param sb the sb
   * @param root the root
   * @param modules2 the modules 2
   */
  private void recurse(StringBuilder sb, Group root, Map<String, ObjectInfo> modules2) {

    for (Entry e : root.entries) {
      processEntry(e, sb, modules2);
    }

    for (Group g : root.subGroups) {
      recurse(sb, g, modules2);
    }
  }

  /**
   * Process entry.
   *
   * @param e the e
   * @param sb the sb
   * @param modules2 the modules 2
   */
  private void processEntry(Entry e, StringBuilder sb, Map<String, ObjectInfo> modules2) {

    for (ObjectInfo info : e.input) {
      processModule(info, modules2);
    }
    processModule(e.output, modules2);

    processEntryBodySync(e, sb);

  }

  HashMap<String, Integer> entries = new HashMap<String, Integer>();


  /**
   * Process entry body async.
   *
   * @param e the e
   * @param sb the sb
   */
  private void processEntryBodySync(Entry e, StringBuilder sb) {
    if (e.input.size() == 0) {
      // TODO 处理无参数的情况
      return;
    }

    int count = 0;
    if (entries.containsKey(e.methodName)) {
      count = entries.get(e.methodName);
      count++;
      entries.put(e.methodName, count);
    } else {
      count = 0;
      entries.put(e.methodName, 0);
    }

    sb.append("/**\r\n");
    sb.append("  *" + e.title + "\r\n");
    sb.append("  *" + Strings.sBlank(e.summary) + "\r\n");
    sb.append("*/" + "\r\n");

    sb.append("public " + getType(unwrapType(e.output.type)) + " "
        + (e.methodName + (count == 0 ? "" : ("" + count))) + "( "
        + getType(unwrapType(e.input.get(0).type)) + " req " + ") throws Exception{\r\n");


    String type = getType(unwrapType(e.output.type));

    // TODO 解析更多 invoke method
    String invokeMethod = e.invokeMethods.get(0);

    if (invokeMethod.compareToIgnoreCase("post") == 0) {

      if (type.equals("String")) {
        sb.append(" return webpost(apiBase+\"" + e.relativePath
            + "\", Json.toJson(req), String.class);");
      } else {
        sb.append(" return    webpost(apiBase+\"" + e.relativePath + "\", Json.toJson(req)," + type
            + ".class);");
      }
    } else {
      if (type.equals("String")) {
        sb.append(" return    webget(apiBase+\"" + e.relativePath
            + "\",Json.toJson(req), String.class);");
      } else {
        sb.append(" return    webget(apiBase+\"" + e.relativePath + "\", Json.toJson(req), " + type
            + ".class);");
      }
    }

    sb.append("\t\r\n");
    sb.append("}\r\n");
  }

  /**
   * List<?> ===> ?.
   *
   * @param t the t
   * @return the string
   */
  String unwrapType(String t) {
    if (t.startsWith("List")) {
      return t.substring(5, t.length() - 1);
    }
    return t;
  }

  /**
   * Process module.
   *
   * @param info the info
   * @param modules2 the modules 2
   */
  private void processModule(ObjectInfo info, Map<String, ObjectInfo> modules2) {

    String t = unwrapType(info.type);

    if (isPrimitive(t)) {
      return;
    }

    if (!modules2.containsKey(t)) {

      // 检查参数类型是否有重名的

      checkDoubleName(modules2, info);

      modules2.put(t, info);
    }

    for (ObjectInfo oi : info.fields) {
      processModule(oi, modules2);
    }
  }

  /**
   * Check double name.
   *
   * @param modules2 the modules 2
   * @param info the info
   */
  private void checkDoubleName(Map<String, ObjectInfo> modules2, ObjectInfo info) {

    String t = getType(unwrapType(info.type));

    for (String key : modules2.keySet()) {
      String t1 = getType(unwrapType(key));
      if (t.equals(t1)) {
        // 有重名
        info.type = info.type.replaceAll("\\.", "");
        break;
      }
    }

  }

  /** The ps. */
  private static String[] ps = {"int", "Integer", "float", "Float", "Double", "double", "long",
      "Long", "Date", "DateTime", "String", "boolean", "Boolean", "char", "short", "byte",
      "Timestamp", "Map"};

  /**
   * Checks if is primitive.
   *
   * @param type the type
   * @return true, if is primitive
   */
  private boolean isPrimitive(String type) {

    for (String s : ps) {
      if (type.contains(s)) {
        return true;
      }
    }
    return false;

  }
}
