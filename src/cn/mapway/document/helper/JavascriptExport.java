package cn.mapway.document.helper;

import java.io.IOException;

import org.nutz.lang.random.R;

import cn.mapway.document.module.ApiDoc;
import cn.mapway.document.module.Entry;
import cn.mapway.document.module.Group;
import cn.mapway.document.resource.Template;

/**
 * The Class JavascriptHelper.
 *
 * @author zhangjianshe@navinfo.com
 */
public class JavascriptExport {

  /**
   * Export group.
   *
   * @param api the api
   * @param group the group
   * @param sb the sb
   * @param clsName
   */
  private void exportGroup(ApiDoc api, Group group, StringBuilder sb, String clsName) {
    for (Entry e : group.entries) {
      sb.append("/*\r\n");
      sb.append(" *" + e.methodName + "\r\n");
      sb.append("*/\r\n");

      sb.append(clsName + ".prototype." + e.methodName + "=function(data,ondata,onerror){\r\n");
      if (e.invokeMethods.contains("GET")) {
        sb.append("\t data=\\$.extend(data,{token:\\$.cookie('token')});\r\n");
        sb.append("\t return this.http_get(this.basepath+'" + e.relativePath
            + "',data,ondata,onerror);\r\n");
      } else if (e.invokeMethods.contains("POST")) {
        sb.append("\t data=\\$.extend(data,{token:\\$.cookie('token')});\r\n");
        sb.append("\t return this.http_post(this.basepath+'" + e.relativePath
            + "',data,ondata,onerror);\r\n");
      }
      sb.append("}\r\n");
    }
    for (Group g : group.getChildGroups()) {
      exportGroup(api, g, sb, clsName);
    }
  }

  /**
   * To javascript.
   *
   * @param api the api
   * @param basepath the basepath
   * @return the string
   */
  public final String toJavascript(ApiDoc api, String basepath, String exportName) {
    String template;
    try {
      template = Template.readTemplate("/cn/mapway/document/resource/javascript.txt");
    } catch (IOException e) {
      e.printStackTrace();
      return "";
    }

    StringBuilder sb = new StringBuilder();

    String clsName = "a" + R.UU16();
    exportGroup(api, api.root, sb, clsName);

    template = template.replaceAll("\\$\\{title\\}", api.title);
    template = template.replaceAll("\\$\\{name\\}", clsName);
    template = template.replaceAll("\\$\\{methods\\}", sb.toString());
    template = template.replaceAll("\\$\\{basepath\\}", basepath);
    template = template.replaceAll("\\$\\{exportName\\}", exportName);

    return template;
  }
}
