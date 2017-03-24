package cn.mapway.tools;

import java.util.LinkedList;
import java.util.List;

import org.nutz.lang.Strings;
import org.nutz.lang.random.R;

/**
 * 打印表格
 * 
 * @author zhangjianshe
 *
 */
public class TableBuilder {
	
	public static String[][] UTF8TABLE={
		{"┌","─","┬","─","┐"},
		{"│"," ","┼"," ","│"},
		{"├","─","┼","─","┤"},
		{"│"," ","┼"," ","│"},
		{"└","─","┴","─","┘"}
	};
	public static String[][] ASICIITABLE={
		{"+","-","+","-","+"},
		{"+","-","+","-","+"},
		{"+","-","+","-","+"},
		{"+","-","+","-","+"},
		{"+","-","+","-","+"}
	};
	
	public String[][] TABLE=ASICIITABLE;
	
	int padding = 1;

	public void setPadding(int padding) {
		this.padding = padding;
	}

	List<String[]> rows = new LinkedList<String[]>();
	int columns = 0;

	/**
	 * 添加一行
	 * 
	 * @param cols
	 */
	public void addRow(String... cols) {
		rows.add(cols);
	}

	/**
	 * 列的宽度
	 * 
	 * @return
	 */
	private int[] colWidths() {

		for (String[] row : rows)
			columns = Math.max(columns, row.length);

		// 获取最大的列数

		int[] widths = new int[columns];

		for (String[] row : rows) {
			for (int colNum = 0; colNum < row.length; colNum++) {
				widths[colNum] = Math.max(widths[colNum],
						Strings.charLength(row[colNum]));
			}
		}

		return widths;
	}

	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();

		int[] colWidths = colWidths();

		// 输出头部
		printTopLine(buf, colWidths);
		

		for (int index = 0; index < rows.size(); index++) {
			String[] row = rows.get(index);
			for (int colNum = 0; colNum < row.length; colNum++) {
				if (colNum == 0) {
					buf.append(TABLE[3][0]);
				}

				int colwidth = colWidths[colNum];
				buf.append(Strings.dup(' ', padding));
				buf.append(row[colNum]);
				buf.append(Strings.dup(' ', colwidth - row[colNum].length()));
				buf.append(Strings.dup(' ', padding));
				if (colNum == colWidths.length - 1) {
					buf.append('│');
				} else {
					buf.append('│');
				}
			}
			buf.append('\n');

			if (index == rows.size() - 1) {
				printBottomLine(buf, colWidths, row);
			} else {
				printMiddleLine(buf, colWidths, row);

			}

		}

		return buf.toString();
	}

	private void printTopLine(StringBuilder buf, int[] colWidths) {
		for (int colNum = 0; colNum < colWidths.length; colNum++) {
			if (colNum == 0) {
				buf.append(TABLE[0][0]);
			}
			int colwidth = colWidths[colNum];
			buf.append(Strings.dup(TABLE[0][1], colwidth + padding * 2));
			if (colNum == colWidths.length - 1) {
				buf.append(TABLE[0][4]);
			} else {
				buf.append(TABLE[0][2]);
			}
		}
		buf.append('\n');
	}

	private void printMiddleLine(StringBuilder buf, int[] colWidths,
			String[] row) {
		for (int colNum = 0; colNum < colWidths.length; colNum++) {
			if (colNum == 0) {
				buf.append(TABLE[1][0]);
			}

			int colwidth = colWidths[colNum];

			buf.append(Strings.dup(TABLE[1][1], colwidth + padding * 2));

			if (colNum == colWidths.length - 1) {
				buf.append(TABLE[1][2]);
			} else {
				buf.append(TABLE[1][1]);
			}
			
		}
		buf.append('\n');
	}

	private void printBottomLine(StringBuilder buf, int[] colWidths,
			String[] row) {
		for (int colNum = 0; colNum < colWidths.length; colNum++) {
			if (colNum == 0) {
				buf.append(TABLE[2][0]);
			}
			
			int colwidth = colWidths[colNum];

			buf.append(Strings.dup(TABLE[2][1], colwidth + padding * 2));

			if (colNum == colWidths.length - 1) {
				buf.append(TABLE[1][2]);
			} else {

				buf.append(TABLE[2][2]);
			}
		}
		buf.append('\n');
	}

	public static void main(String[] args) {
		TableBuilder tb = new TableBuilder();
		tb.addRow("title", "url", "special");
		for (int i = 0; i < 10; i++) {
			tb.addRow(R.sg(4, 25).next(), "http://www.baidu.com/" + i);
		}
		tb.addRow("","","Hello","touch");
		System.out.println(tb);
	}
}