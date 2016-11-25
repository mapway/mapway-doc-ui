package cn.mapway.document.word;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTString;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTVerticalJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STVerticalJc;

import cn.mapway.document.helper.DocHelper;
import cn.mapway.document.helper.ParseType;
import cn.mapway.document.module.ApiDoc;
import cn.mapway.document.module.Entry;
import cn.mapway.document.module.Group;
import cn.mapway.document.module.ObjectInfo;
import cn.mapway.document.parser.GenContext;

// TODO: Auto-generated Javadoc
/**
 * 输出WORD文档.
 *
 * @author zhangjianshe
 */
public class WordExport {

	/**
	 * 转成WORD文件格式.
	 *
	 * @param api
	 *            the api
	 * @param fileName
	 *            the file name
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void toWord(ApiDoc api, String fileName) throws IOException {
		XWPFDocument doc = new XWPFDocument();
		createCover(doc, api);
		createEntries(doc, api);
		FileOutputStream out = new FileOutputStream(fileName);
		doc.write(out);
		out.close();
		doc.close();
	}

	/**
	 * 输出接口信息.
	 *
	 * @param doc the doc
	 * @param api the api
	 */
	private void createEntries(XWPFDocument doc, ApiDoc api) {
		exportGroup(doc, api.root);
	}

	/**
	 * 输出Group.
	 *
	 * @param doc the doc
	 * @param root the root
	 */
	private void exportGroup(XWPFDocument doc, Group root) {
		for (Entry e : root.entries) {
			exportEntry(doc, e);
		}
		for (Group g : root.subGroups) {
			exportGroup(doc, g);
		}
	}

	/**
	 * 输出Entry.
	 *
	 * @param doc the doc
	 * @param e the e
	 */
	private void exportEntry(XWPFDocument doc, Entry e) {

		XWPFParagraph p = doc.createParagraph();
		p.setStyle("标题2");
		p.createRun().setText(e.title);

		doc.createParagraph();

		p = doc.createParagraph();
		p.setStyle("正文");
		XWPFRun r = p.createRun();
		r.setText(e.summary);

		if (e.input.size() > 0) {
			doc.createParagraph().createRun().setText("输入参数");
			descript(doc, e.input.get(0));
		}

		doc.createParagraph().createRun().setText("输出参数");
		descript(doc, e.output);

	}

	/**
	 * 描述.
	 *
	 * @param doc the doc
	 * @param objectInfo the object info
	 */
	private void descript(XWPFDocument doc, ObjectInfo objectInfo) {
		int nRows = objectInfo.fields.size() + 2;
		int nCols = 5;

		doc.createParagraph();
		XWPFTable table = doc.createTable(nRows, nCols);
		table.setCellMargins(5, 5, 5, 5);

		CTTblPr tblPr = table.getCTTbl().getTblPr();

		CTTblWidth w = tblPr.addNewTblW();
		w.setW(BigInteger.valueOf(5000));
		w.setType(STTblWidth.PCT);

		List<XWPFTableRow> rows = table.getRows();
		int row = 0;
		int col = 0;

		XWPFTableRow r = rows.get(row++);
		List<XWPFTableCell> cells = r.getTableCells();
		XWPFTableCell cell;

		cell = cells.get(col++);
		header(cell, "名称");

		cell = cells.get(col++);
		header(cell, "类型");

		cell = cells.get(col++);
		header(cell, "长度");

		cell = cells.get(col++);
		header(cell, "选项");

		cell = cells.get(col++);
		header(cell, "说明");

		for (ObjectInfo obj : objectInfo.fields) {
			col = 0;
			r = rows.get(row++);
			cells = r.getTableCells();

			cells.get(col++).setText(obj.name);
			cells.get(col++).setText(obj.type);
			cells.get(col++).setText(obj.length + "");
			cells.get(col++).setText(obj.manditary ? "必填" : "选填");
			cells.get(col++).setText(obj.title);
		}

		doc.createParagraph();
	}

	/**
	 * Header.
	 *
	 * @param cell the cell
	 * @param text the text
	 */
	private void header(XWPFTableCell cell, String text) {
		XWPFRun run = cell.addParagraph().createRun();
		run.setBold(true);
		run.setText(text);
		CTShd shd = cell.getCTTc().addNewTcPr().addNewShd();
		shd.setFill("F0F0F0");
		cell.removeParagraph(0);
	}

	/**
	 * Creates the cover.
	 *
	 * @param doc
	 *            the doc
	 * @param api
	 *            the api
	 */
	private void createCover(XWPFDocument doc, ApiDoc api) {

		for (int i = 0; i < 10; i++) {
			doc.createParagraph();
		}

		XWPFParagraph p1 = doc.createParagraph();

		p1.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun r1 = p1.createRun();
		r1.setBold(true);
		r1.setText(api.title + "API接口文档");
		r1.setBold(true);
		r1.setFontSize(34);
		r1.setFontFamily("黑体");
		r1.setTextPosition(100);

		doc.createParagraph();

		p1 = doc.createParagraph();
		p1.setAlignment(ParagraphAlignment.CENTER);
		r1 = p1.createRun();
		r1.setText("张建设");

		p1 = doc.createParagraph();
		p1.setAlignment(ParagraphAlignment.CENTER);
		r1 = p1.createRun();
		Date d = new Date();
		r1.setText(d.toLocaleString());

		r1.addBreak(BreakType.PAGE);
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void main(String[] args) throws IOException {

		WordExport export = new WordExport();
		DocHelper helper = new DocHelper();
		GenContext context = new GenContext();
		ApiDoc api = helper.toDoc(ParseType.PT_SPRING, context,
				"cn.mapway.document.test");
		export.toWord(api, "d:\\out\\1.docx");
	}

}
