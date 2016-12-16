package cn.mapway.document.processor;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic.Kind;
import javax.tools.JavaFileObject;

import cn.mapway.document.annotation.Code;
import cn.mapway.document.annotation.Codes;

/**
 * 错误代码生成的注解处理器
 * 
 * @author zhangjianshe
 *
 */
@SupportedAnnotationTypes({ "cn.mapway.document.annotation.Codes",
		"cn.mapway.document.annotation.Code" })
@SupportedSourceVersion(SourceVersion.RELEASE_5)
public class CodesGeneratorProcessor extends AbstractProcessor {

	/**
	 * 辅助工具
	 */
	Elements utils;

	/**
	 * 初始化处理器
	 */
	@Override
	public synchronized void init(ProcessingEnvironment processingEnv) {
		super.init(processingEnv);
		// 元素操作的辅助类
		utils = processingEnv.getElementUtils();

	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations,
			RoundEnvironment roundEnv) {

		Map<String, Code> codes = new HashMap<String, Code>();

		Set<? extends Element> listCodes = roundEnv
				.getElementsAnnotatedWith(Codes.class);

		Set<? extends Element> listCode = roundEnv
				.getElementsAnnotatedWith(Code.class);

		for (Element e : listCodes) {
			Codes cs = e.getAnnotation(Codes.class);
			String scope = cs.scope();

			if (scope == null) {
				scope = "";
			}

			for (Code c : cs.value()) {
				processCoder(codes, c, "");
			}
		}
		for (Element e : listCode) {
			Code c = e.getAnnotation(Code.class);
			processCoder(codes, c, "");
		}

		String className = this.processingEnv.getOptions().get("className");
		if (className == null || className.length() == 0) {
			className = "Coders";
		}

		String packageName = this.processingEnv.getOptions().get("packageName");

		PackageElement pkgElement = null;
		if (packageName != null && packageName.length() > 0) {
			pkgElement = this.processingEnv.getElementUtils()
					.getPackageElement(packageName);
		}

		String data = generateCodes(codes, pkgElement, className);

		writeToFile(data, pkgElement, className);
		return true;
	}

	/**
	 * 处理代码
	 * 
	 * @param codes
	 * @param c
	 * @param scope
	 */
	private void processCoder(Map<String, Code> codes, Code c, String scope) {
		if (c.name() == null || c.name().length() == 0) {
			return;
		}
		String key = buildKey(scope, c);

		if (codes.get(key) == null) {
			codes.put(key, c);
		} else {
			this.processingEnv.getMessager().printMessage(Kind.NOTE,
					"repeat coder " + c.name());
		}
	}

	private String buildKey(String scope, Code c) {
		String key = "";

		if (c.scope() == null || c.scope().length() == 0) {
			key = c.name();
		} else {
			key = c.scope() + "_" + c.name();
		}

		if (scope != null && scope.length() > 0) {
			key = scope + "_" + key;
		}
		return key;
	}

	private void writeToFile(String data, PackageElement pe, String className) {

		JavaFileObject generationForPath;
		try {
			generationForPath = processingEnv.getFiler().createSourceFile(
					pe.getQualifiedName() + "." + className);
			Writer writer = generationForPath.openWriter();
			writer.write(data);
			writer.close();
		} catch (IOException e1) {

			e1.printStackTrace();
		}

	}

	/**
	 * 生成代码
	 * 
	 * @param codes
	 * @param className
	 */
	private String generateCodes(Map<String, Code> codes,
			PackageElement pkgElement, String className) {
		StringBuilder sb = new StringBuilder();

		sb.append("/**\r\n  *编译器自动生成此代码，请勿编辑此文件 contact to :15910868680@163.com\r\n*/\r\n");

		if (pkgElement != null) {
			sb.append("package " + pkgElement.getQualifiedName() + ";\r\n");
		}
		sb.append("import cn.mapway.document.coder.Coder;\r\n");

		sb.append("/**\r\n");
		sb.append("* 自动生成的代码类\r\n");
		sb.append("*/\r\n");
		sb.append("public class " + className + "{\r\n");
		for (String key : codes.keySet()) {
			Code c = codes.get(key);
			if (c.name().length() > 0)
				sb.append("\t public final static Coder " + key
						+ " = new Coder(\"" + c.value() + "\",\"" + c.desc()
						+ "\");\r\n");
		}
		sb.append("}\r\n");

		return sb.toString();
	}

}
