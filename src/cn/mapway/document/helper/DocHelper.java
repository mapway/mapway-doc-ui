package cn.mapway.document.helper;

import java.io.IOException;

import org.nutz.json.Json;
import org.nutz.json.JsonFormat;

import cn.mapway.document.module.ApiDoc;
import cn.mapway.document.parser.GenContext;
import cn.mapway.document.parser.SpringParser;
import cn.mapway.document.resource.Template;

/**
 * 辅助类
 * 
 * @author zhangjianshe
 *
 */
public class DocHelper {

	/**
	 * 生成接口文档JSON格式
	 * 
	 * @param pt
	 * @param context
	 * @param packages
	 * @return
	 */
	public ApiDoc toDoc(ParseType pt, GenContext context, String... packages) {
		if (pt == ParseType.PT_SPRING) {
			SpringParser parser = new SpringParser();
			ApiDoc doc = new ApiDoc();
			try {
				doc = parser.parse(context, packages);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return doc;
		}

		return new ApiDoc();
	}

	/**
	 * 生成HTML文档
	 * 
	 * @param context
	 * @param packages
	 * @return
	 */
	public String genHTML(ParseType pt, GenContext context, String... packages) {
		ApiDoc doc = toDoc(pt, context, packages);
		String template;
		try {
			template = Template
					.readTemplate("/cn/mapway/document/resource/doctemplate.html");
			String ui = Template
					.readTemplate("/cn/mapway/document/resource/apidoc.nocache.js");

			String json = Json.toJson(doc, JsonFormat.full());

			template = template.replace("API_DOC_DATA", json);
			template = template.replace("API_DOC_UI", ui);
			return template;
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}
}
