package cn.mapway.document.helper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.nutz.lang.Strings;

import cn.mapway.document.module.ApiDoc;
import cn.mapway.document.module.Entry;
import cn.mapway.document.module.Group;
import cn.mapway.document.module.ObjectInfo;
import cn.mapway.document.resource.Template;

// TODO: Auto-generated Javadoc
/**
 * 输出Java Connect 代码.
 *
 * @author zhangjianshe
 */
public class GwtConnextorExport {

	/** The modules. */
	Map<String, ObjectInfo> modules;

	/**
	 * Instantiates a new gwt connextor export.
	 */
	public GwtConnextorExport() {
		modules = new HashMap<String, ObjectInfo>();
	}

	/**
	 * 输出API 对应的 Java Connector
	 * 
	 * public resp doSomthing(Req req) {
	 * 
	 * }.
	 *
	 * @param api the api
	 * @param packageName the package name
	 * @param className the class name
	 * @return the string
	 */
	public String export(ApiDoc api, String packageName, String className) {

		StringBuilder imports = new StringBuilder();
		StringBuilder sb = new StringBuilder();
		StringBuilder modulesBody = new StringBuilder();

		modules = new HashMap<String, ObjectInfo>();
		Group root = api.root;

		recurse(sb, root, modules);

		String template;
		try {
			template = Template
					.readTemplate("/cn/mapway/document/resource/gwtconnect.txt");
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}

		processModuleMody(modules, modulesBody);

		Map<String, String> map = new HashMap<String, String>();
		map.put("package", packageName);
		map.put("name", className);
		map.put("basepath", "");
		map.put("importModules", imports.toString());
		map.put("apis", sb.toString());
		map.put("modules", modulesBody.toString());

		return replace(template, map);

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
	 * Process module mody.
	 *
	 * @param ms the ms
	 * @param modulesBody the modules body
	 */
	private void processModuleMody(Map<String, ObjectInfo> ms,
			StringBuilder modulesBody) {
		for (String key : ms.keySet()) {

			ObjectInfo oi = ms.get(key);

			modulesBody.append("\r\n/**");
			modulesBody.append("  " + oi.title + "\r\n");
			modulesBody.append("  " + oi.summary);
			modulesBody.append("*/\r\n");
		
			modulesBody.append("public static class "
					+ getType(unwrapType(oi.type))
					+ " extends JavaScriptObject{\r\n");
			modulesBody.append("protected "+getType(unwrapType(oi.type))+"(){}\r\n");
			modulesBody.append("public final static native "+getType(unwrapType(oi.type))+" create()/*-{return {}}-*/;\r\n");
			
			for (ObjectInfo fi : oi.fields) {

				modulesBody.append("/**\r\n  获取" + fi.title + "\r\n "
						+ fi.summary + "\r\n */\r\n");
				modulesBody.append(" public final  native "
						+ getType(fi.type) + " " + fi.name + "() /*-{\r\n"
						+ "return this." + fi.name + ";}-*/;\r\n");
				
				modulesBody.append("/**\r\n  " + fi.title + "\r\n "
						+ fi.summary + "\r\n */\r\n");
				modulesBody.append(" public final  native void "
						 + fi.name + "("+ getType(fi.type) + " v) /*-{\r\n"
						+ "this." + fi.name + "=v;}-*/;\r\n");
			}
			modulesBody.append("}\r\n");
		}

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
				return "JsArrayString";
			} else if (subtype.equals("java.lang.Boolean")
					|| subtype.equals("bool")) {
				return "JsArrayBoolean";
			} else if (subtype.equals("int")
					|| subtype.equals("java.lang.Integer")
					|| subtype.equals("java.lang.Long")
					|| subtype.equals("long")
					|| subtype.equals("java.lang.Byte")
					|| subtype.equals("byte")
					|| subtype.equals("java.lang.Short")
					|| subtype.equals("short")) {
				return "JsArrayInteger";
			} else if (subtype.equals("double")
					|| subtype.equals("java.lang.Double")
					|| subtype.equals("float")
					|| subtype.equals("java.lang.Float")) {
				return "JsArrayNumber";
			}
			if (subtype.equals("java.util.Map")) {
				return "JsArray";
			}

			return "JsArray<" + getType(subtype) + ">";
		}

		int index = type.lastIndexOf(".");
		if (index > 0) {
			return type.substring(index + 1);
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
	private void recurse(StringBuilder sb, Group root,
			Map<String, ObjectInfo> modules2) {

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
	private void processEntry(Entry e, StringBuilder sb,
			Map<String, ObjectInfo> modules2) {

		for (ObjectInfo info : e.input) {
			processModule(info, modules2);
		}
		processModule(e.output, modules2);

		processEntryBodyAsync(e, sb);

	}

	/**
	 * Process entry body async.
	 *
	 * @param e the e
	 * @param sb the sb
	 */
	private void processEntryBodyAsync(Entry e, StringBuilder sb) {
		if (e.input.size() == 0) {
			// TODO 处理无参数的情况
			return;
		}
		sb.append("/**\r\n");
		sb.append("  *" + e.title + "\r\n");
		sb.append("  *" + Strings.sBlank(e.summary) + "\r\n");
		sb.append("*/" + "\r\n");

		sb.append("public void " + e.methodName + "(final "
				+ getType(unwrapType(e.input.get(0).type))
				+ " req ,final ICallback<" + getType(unwrapType(e.output.type))
				+ "> handler) throws Exception{\r\n");

		sb.append("\tif (handler == null) {\r\n");
		sb.append("\treturn;\r\n}\r\n");

		String type = getType(unwrapType(e.output.type));

		if (e.invokeMethod.compareToIgnoreCase("post") == 0) {

			if (type.equals("String")) {
				sb.append("doPostString(\"" + e.relativePath
						+ "\", JsonUtils.stringify(req), handler);");
			} else {
				sb.append("doPost(\"" + e.relativePath
						+ "\", JsonUtils.stringify(req), handler);");
			}
		} else {
			if (type.equals("String")) {
				sb.append("doGetString(\"" + e.relativePath
						+ "\", JsonUtils.stringify(req), handler);");
			} else {
				sb.append("doGet(\"" + e.relativePath
						+ "\", JsonUtils.stringify(req), handler);");
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
	private void checkDoubleName(Map<String, ObjectInfo> modules2,
			ObjectInfo info) {

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
	private static String[] ps = { "int", "Integer", "float", "Float",
			"Double", "double", "long", "Long", "Date", "DateTime", "String",
			"boolean", "Boolean", "char", "short", "byte", "Timestamp", "Map" };

	/**
	 * Checks if is primitive.
	 *
	 * @param type
	 *            the type
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
