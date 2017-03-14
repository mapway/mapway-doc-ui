package cn.mapway.document.helper;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.lang.Files;
import org.nutz.lang.Lang;

import cn.mapway.document.module.ApiDoc;
import cn.mapway.document.resource.Template;

/**
 * 输出Jar包工具.
 *
 * @author zhangjianshe
 */
public class JarHelper {

	/** The ant home. */
	private String antHome = "";

	/**
	 * Sets the ant home.
	 *
	 * @param home
	 *            the new ant home
	 */
	public void setAntHome(String home) {
		antHome = home;
	}

	/**
	 * 输出Jar包到指定的目录.
	 *
	 * @param api
	 *            the api
	 * @param path
	 *            the path
	 * @param packageName
	 *            the package name
	 * @param className
	 *            the class name
	 * @param libPath
	 *            the lib path
	 * @return the list
	 */
	public List<JarInfo> jar(ApiDoc api, String path, String packageName,
			String className, String libPath) {
		List<JarInfo> jars = new ArrayList<JarInfo>();

		String strpath = path;
		String packname = packageName;
		String clsName = className;
		String strlibPath = libPath;

		JarInfo jar = exportJavaConnector(api, strpath, packname, clsName,
				strlibPath);

		if (jar != null) {
			jars.add(jar);
		}

		jar = exportGwtConnector(api, path, packageName, className, libPath);

		if (jar != null) {
			jars.add(jar);
		}

		// 输出Gwt Connector
		Files.createDirIfNoExists(path + "/source/gwt");

		return jars;
	}

	/**
	 * 输出GWT连接器 Jar包.
	 *
	 * @param api
	 *            the api
	 * @param path
	 *            the path
	 * @param packageName
	 *            the package name
	 * @param className
	 *            the class name
	 * @param libPath
	 *            the lib path
	 * @return the jar info
	 */
	private JarInfo exportGwtConnector(ApiDoc api, String path,
			String packageName, String className, String libPath) {

		packageName = packageName + ".client";

		String fileName = className + "Gwt";
		Files.createDirIfNoExists(path);
		String javaConnectorPath = path + "/source/gwt";
		String javaConnectorSourcePath = javaConnectorPath + "/src";

		Files.createDirIfNoExists(javaConnectorSourcePath);

		GwtConnextorExport exportJava = new GwtConnextorExport();
		String code = exportJava.export(api, packageName, fileName);
		String p = makePathFile(javaConnectorSourcePath, packageName, fileName);
		Files.write(p, code);

		String template = "";

		// 输出模块定义文件
		try {
			template = Template
					.readTemplate("/cn/mapway/document/resource/gwtmodule.xml");
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		String gwtmoduleName = makeGwtModuleFile(javaConnectorSourcePath,
				packageName, fileName);
		Files.write(gwtmoduleName, template);

		try {
			template = Template
					.readTemplate("/cn/mapway/document/resource/buildgwt.xml");
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		javaConnectorPath = javaConnectorPath.replace('\\', '/');

		javaConnectorSourcePath = javaConnectorSourcePath.replace('\\', '/');
		libPath = libPath.replace('\\', '/');

		Map<String, String> vars = new HashMap<String, String>();
		vars.put("--SOURCE-PATH--", javaConnectorSourcePath);
		vars.put("--OUT-PATH--", javaConnectorPath + "/target");
		vars.put("--OUT-FILE-NAME--", fileName + ".jar");
		vars.put("--LIB-PATH--", libPath);

		String t = StrUtil.replace2(template, vars);

		String buildFile = javaConnectorPath + File.separator + "build.xml";
		Files.write(buildFile, t);

		String cmd = Lang.isWin() ? "\\bin\\ant.bat" : "/bin/ant";

		String exec = antHome + cmd;
		exec(exec, javaConnectorPath);

		JarInfo info = new JarInfo();
		info.fileName = fileName + ".jar";
		info.path = javaConnectorPath + "/target";
		File f = new File(info.path + "/" + info.fileName);
		info.size = f.length();
		info.summary = "Gwt访问API连接器";
		return info;
	}

	/**
	 * 输出Java连接器 Jar包.
	 *
	 * @param api
	 *            the api
	 * @param path
	 *            the path
	 * @param packageName
	 *            the package name
	 * @param className
	 *            the class name
	 * @param libPath
	 *            the lib path
	 * @return the jar info
	 */
	private JarInfo exportJavaConnector(ApiDoc api, String path,
			String packageName, String className, String libPath) {
		// 输出JavaConnector 源代码
		Files.createDirIfNoExists(path);
		String javaConnectorPath = path + "/source/java";
		String javaConnectorSourcePath = javaConnectorPath + "/src";
		String fileName = className + "Java";

		Files.createDirIfNoExists(javaConnectorSourcePath);

		JavaConnextorExport exportJava = new JavaConnextorExport();
		String code = exportJava.export(api, packageName, fileName);
		String p = makePathFile(javaConnectorSourcePath, packageName, className
				+ "Java");
		Files.write(p, code);

		String template = "";
		try {
			template = Template
					.readTemplate("/cn/mapway/document/resource/build.xml");
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		javaConnectorPath = javaConnectorPath.replace('\\', '/');

		javaConnectorSourcePath = javaConnectorSourcePath.replace('\\', '/');
		libPath = libPath.replace('\\', '/');

		Map<String, String> vars = new HashMap<String, String>();
		vars.put("--SOURCE-PATH--", javaConnectorSourcePath);
		vars.put("--OUT-PATH--", javaConnectorPath + "/target");
		vars.put("--OUT-FILE-NAME--", fileName + ".jar");
		vars.put("--LIB-PATH--", libPath);

		String t = StrUtil.replace2(template, vars);

		String buildFile = javaConnectorPath + File.separator + "build.xml";
		Files.write(buildFile, t);

		String cmd = Lang.isWin() ? "\\bin\\ant.bat" : "/bin/ant";

		String exec = antHome + cmd;
		exec(exec, javaConnectorPath);
		
		

		JarInfo info = new JarInfo();
		info.fileName = fileName + ".jar";
		info.path = javaConnectorPath + "/target";
		File f = new File(info.path + "/" + info.fileName);
		info.size = f.length();
		info.summary = "Java访问API连接器";
		return info;
	}

	/**
	 * Make gwt module file.
	 *
	 * @param javaConnectorSourcePath
	 *            the java connector source path
	 * @param packageName
	 *            the package name
	 * @param className
	 *            the class name
	 * @return the string
	 */
	private String makeGwtModuleFile(String javaConnectorSourcePath,
			String packageName, String className) {
		String p = packageName.replace(".", File.separator);

		return javaConnectorSourcePath + "/" + p + "/" + className + ".gwt.xml";
	}

	/**
	 * Make path file.
	 *
	 * @param javaConnectorSourcePath
	 *            the java connector source path
	 * @param packageName
	 *            the package name
	 * @param className
	 *            the class name
	 * @return the string
	 */
	private String makePathFile(String javaConnectorSourcePath,
			String packageName, String className) {
		String p = packageName.replace(".", File.separator);

		return javaConnectorSourcePath + File.separator + p + File.separator
				+ className + ".java";
	}


	/**
	 * Exec.
	 *
	 * @param cmd the cmd
	 * @param dir the dir
	 */
	private void exec(String cmd, String dir) {
		try {
			Runtime.getRuntime().exec(cmd, null, new File(dir));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
