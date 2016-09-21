package cn.mapway.document.helper;

import java.io.FileOutputStream;
import java.io.IOException;

import org.nutz.json.Json;
import org.nutz.json.JsonFormat;

import cn.mapway.document.module.ApiDoc;
import cn.mapway.document.parser.GenContext;
import cn.mapway.document.parser.SpringParser;
import cn.mapway.document.resource.Template;
import cn.mapway.document.word.WordExport;

import com.google.gwt.dom.client.Style.VerticalAlign;
import com.google.gwt.user.client.ui.ValueBoxBase.TextAlignment;

// TODO: Auto-generated Javadoc
/**
 * 辅助类.
 *
 * @author zhangjianshe
 */
public class DocHelper {

	/**
	 * 生成WORD文档.
	 *
	 * @param api the api
	 * @param fileName the file name
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void toWord(ApiDoc api, String fileName) throws IOException {
		WordExport export = new WordExport();
		export.toWord(api, fileName);
	}

	/**
	 * 生成接口文档JSON格式.
	 *
	 * @param pt the pt
	 * @param context the context
	 * @param packages the packages
	 * @return the api doc
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
	 * 生成HTML文档.
	 *
	 * @param pt the pt
	 * @param context the context
	 * @param packages the packages
	 * @return the string
	 */
	public String genHTML(ParseType pt, GenContext context, String... packages) {
		ApiDoc doc = toDoc(pt, context, packages);
		String template;
		try {
			template = Template
					.readTemplate("/cn/mapway/document/resource/doctemplate.html");
			String ui = Template
					.readTemplate("/cn/mapway/document/resource/apidoc.nocache.js");
			String json = Json.toJson(doc, JsonFormat.tidy());
			template = template.replace("API_DOC_DATA", json);
			template = template.replace("API_DOC_UI", ui);
			return template;
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	/** The gifdata. */
	private static byte[] gifdata = { 0x47, 0x49, 0x46, 0x38, 0x39, 0x61, 0x01,
			0x00, 0x01, 0x00, (byte) 0x80, 0x00, 0x00, (byte) 0xff,
			(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
			0x21, (byte) 0xf9, 0x04, 0x01, 0x0a, 0x00, 0x01, 0x00, 0x2c, 0x00,
			0x00, 0x00, 0x00, 0x01, 0x00, 0x01, 0x00, 0x00, 0x02, 0x02, 0x4c,
			0x01, 0x00, 0x3b };

	/**
	 * 获取透明 1X1 像素的GIF图片数据.
	 *
	 * @return the clear gif data
	 */
	public static byte[] getClearGifData() {
		return gifdata;
	}

	/**
	 * 生成Word File.
	 *
	 * @param pt the pt
	 * @param context the context
	 * @param packages the packages
	 * @return the string
	 */
	public String genDoc(ParseType pt, GenContext context, String... packages) {
		ApiDoc doc = toDoc(pt, context, packages);
		String wordFile = "";

		return wordFile;
	}
}
