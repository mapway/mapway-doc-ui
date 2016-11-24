package cn.mapway.document.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import cn.mapway.document.helper.DocHelper;
import cn.mapway.document.helper.JavaConnextorExport;
import cn.mapway.document.helper.ParseType;
import cn.mapway.document.module.ApiDoc;
import cn.mapway.document.parser.GenContext;

/**
 * 
 * @author zhangjianshe
 *
 */
public class MapwayDocServlet extends HttpServlet {
	private static Log log = Logs.getLog(MapwayDocServlet.class);

	private GenContext context;
	private String packageNames;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public MapwayDocServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		String contype = "html";
		contype = request.getParameter("contype");

		if (contype == null || contype.length() == 0) {
			contype = "html";
		}

		PrintWriter out = response.getWriter();
		DocHelper helper = new DocHelper();
		ApiDoc doc = helper.toDoc(ParseType.PT_SPRING, context, packageNames);

		if (contype.equals("html")) {
			System.out.println(Json.toJson(doc, JsonFormat.full()));

		} else if (contype.equals("java")) {
			JavaConnextorExport export = new JavaConnextorExport();
			out.println("<pre>"
					+ Strings.escapeHtml(export.export(doc,
							"cn.mapway.document.connector", "TestConnector"))
					+ "</pre>");
		}
		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		context = new GenContext();
		context.setAuthor(this.getInitParameter("author"));
		context.setDocTitle(this.getInitParameter("title"));
		context.setDomain(this.getInitParameter("domain"));
		context.setBasepath(this.getInitParameter("basePath"));
		context.setWordURL("http://www.baidu.com/");
		context.setSubtitle("Hello" + new Date());
		packageNames = this.getInitParameter("packages");
	}

}
