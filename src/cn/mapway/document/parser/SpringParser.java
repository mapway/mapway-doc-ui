package cn.mapway.document.parser;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.nutz.castor.Castors;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.lang.Mirror;
import org.nutz.lang.Strings;
import org.nutz.log.Logs;
import org.nutz.resource.Scans;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.mapway.document.annotation.ApiField;
import cn.mapway.document.annotation.Code;
import cn.mapway.document.annotation.Codes;
import cn.mapway.document.annotation.DevelopmentState;
import cn.mapway.document.annotation.Doc;
import cn.mapway.document.module.ApiDoc;
import cn.mapway.document.module.Entry;
import cn.mapway.document.module.FieldCode;
import cn.mapway.document.module.Group;
import cn.mapway.document.module.ObjectInfo;
import cn.mapway.document.ver.MapwayDocVer;


// TODO: Auto-generated Javadoc
/**
 * 解析Spring 注解.
 *
 * @author zhangjianshe@gmail.com
 */
public class SpringParser {

	/** The Constant log. */
	private final static org.nutz.log.Log log = Logs.getLog(SpringParser.class);

	/** The m context. */
	GenContext mContext;

	/**
	 * 解析包中的类.
	 *
	 * @param context
	 *            the context
	 * @param packageNames
	 *            包名
	 * @return the api doc
	 * @throws IllegalArgumentException
	 *             the illegal argument exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws InstantiationException
	 *             the instantiation exception
	 */
	public ApiDoc parse(GenContext context, String... packageNames)
			throws IllegalArgumentException, IllegalAccessException,
			InstantiationException {

		mContext = context;
		if (packageNames.length == 0) {
			return new ApiDoc();
		}
		ArrayList<Class<?>> clzs = new ArrayList<Class<?>>();

		for (String pk : packageNames) {
			List<Class<?>> clz = Scans.me().scanPackage(pk);
			clzs.addAll(clz);
		}

		ApiDoc doc = new ApiDoc();
		doc.author = context.getAuthor();
		doc.basePath = context.getBasepath();
		doc.title = context.getDocTitle();
		doc.wordUrl = context.getWordURL();
		doc.summary = context.getSubtitle();
		doc.domain = context.getDomain();
		doc.copyright = context.getCopyright();

		doc.copyright = doc.copyright + "-" + MapwayDocVer.version();

		for (Class<?> clz : clzs) {
			if (clz.getAnnotation(Controller.class) != null
					|| clz.getAnnotation(RestController.class) != null) {
				parseClass(doc, clz);
			}
		}
		doc.root.name = doc.title;
		doc.root.fullName = "/" + doc.title;
		doc.root.summary = "";
		doc.sort();
		return doc;
	}

	/**
	 * 解析某个类.
	 *
	 * @param document
	 *            the document
	 * @param clz
	 *            the clz
	 * @throws IllegalArgumentException
	 *             the illegal argument exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws InstantiationException
	 *             the instantiation exception
	 */
	private void parseClass(ApiDoc document, Class<?> clz)
			throws IllegalArgumentException, IllegalAccessException,
			InstantiationException {

		Doc doc = clz.getAnnotation(Doc.class);

		if (doc == null) {
			return;
		}

		populateGroup(document, clz);
	}

	/**
	 * 填充Group信息.
	 *
	 * @param document
	 *            the document
	 * @param c
	 *            the c
	 * @throws IllegalArgumentException
	 *             the illegal argument exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws InstantiationException
	 *             the instantiation exception
	 */
	private void populateGroup(ApiDoc document, Class<?> c)
			throws IllegalArgumentException, IllegalAccessException,
			InstantiationException {

		log.debug("process " + c.getName());

		// 类Doc
		Doc doc = c.getAnnotation(Doc.class);
		String group_base_path = doc.group();

		Group apigroup = document.findGroup(group_base_path);

		apigroup.summary += doc.desc().length() > 0 ? doc.desc() : "";
		apigroup.order = doc.order();

		String basepath = "";

		RequestMapping rm = c.getAnnotation(RequestMapping.class);
		if (rm != null) {
			String[] paths = rm.value();
			if (paths == null || paths.length == 0) {

			} else {
				basepath = paths[0];
			}
		}

		Method[] methods = c.getDeclaredMethods();
		List<Method> list = new ArrayList<Method>();
		for (int i = 0; i < methods.length; i++) {
			Method m = methods[i];
			RequestMapping rm1 = m.getAnnotation(RequestMapping.class);
			if (rm1 != null) {
				list.add(m);
			}
		}

		for (int i = 0; i < list.size(); i++) {
			Method m = list.get(i);

			Entry entry = handleMethod(document, group_base_path, m);
			entry.parentClassName = c.getName();

			if (entry != null) {
				entry.relativePath = basepath + entry.relativePath;
				entry.url = mContext.getBasepath() + entry.relativePath;

			}
		}
	}

	/**
	 * 解析方法，生成APIentry.
	 *
	 * @param document
	 *            the document
	 * @param group_base_path
	 *            the group base path
	 * @param m
	 *            the m
	 * @return the entry
	 * @throws IllegalArgumentException
	 *             the illegal argument exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws InstantiationException
	 *             the instantiation exception
	 */
	private Entry handleMethod(ApiDoc document, String group_base_path, Method m)
			throws IllegalArgumentException, IllegalAccessException,
			InstantiationException {
		Entry e = new Entry();

		RequestMapping rm = m.getAnnotation(RequestMapping.class);
		
		if (rm != null) {
			//TODO 增加对多路径的支持
			String[] paths = rm.value();
			if (paths == null || paths.length == 0) {

			} else {
				e.relativePath = paths[0];
			}

			//处理请求方法 支持对多种调用方式 POST GET PUT DELETE 等
			RequestMethod[] ms = rm.method();
			if (ms != null) {
				for (int i = 0; i < ms.length; i++) {
					RequestMethod rm0 = ms[i];
					e.invokeMethods.add(rm0.name());
				}
			}
			
		}
		if(e.invokeMethods.size()==0)
		{
			e.invokeMethods.add("GET");
		}

		if (e.relativePath.length() == 0) {
			return null;
		}

		e.methodName = m.getName();

		Doc summary = m.getAnnotation(Doc.class);
		if (summary != null) {
			e.title = summary.value();
			e.summary = summary.desc() == null ? "" : summary.desc();
			e.order = summary.order();
			e.author = summary.author();
			e.state = transState(summary.state());
		}

		
		
		Class<?>[] ps = m.getParameterTypes();
		Class<?> out = m.getReturnType();

		int i=0;
		for (Class<?> clz : ps) {
			
			String name = clz.getSimpleName();
			String pname = "";
			if(clz.getPackage()!=null)
				{
					pname=clz.getPackage().getName();
				}

			if (name.startsWith("Http") || pname.startsWith("org.")) {
				continue;
			} else {
				
				Annotation[][] parameterAnnotations = m.getParameterAnnotations();
				Annotation[] ass=parameterAnnotations[i];
				
				//处理每一个参数
				PathVariable pathVariable=null;
				RequestParam queryVariable=null;
				RequestBody isRequestBody=null;
				ApiField paraDoc=null;
				for(Annotation a:ass)
				{
					if(a instanceof PathVariable)
					{
						pathVariable=(PathVariable) a;
					}
					else if(a instanceof RequestParam)
					{
						queryVariable=(RequestParam) a;
					}
					else if(a instanceof ApiField)
					{
						paraDoc=(ApiField) a;
					}
					else if(a instanceof RequestBody)
					{
						isRequestBody=(RequestBody) a;
					}
				}
				ObjectInfo p = handleParameter(clz, name);
				
				if(p==null)
				{
					continue;
				}
				
				if(paraDoc!=null)
				{
					p.example=paraDoc.example();
					p.manditary=paraDoc.mandidate();
					p.title=paraDoc.value();
					p.summary=paraDoc.value();
				}
				
				if(pathVariable!=null)
				{
					//路径参数
					if(Strings.isBlank(pathVariable.value()))
					{
						p.name="未知";
					}
					else
					{
						p.name=pathVariable.value();
					}
					e.pathParas.add(p);
				}
				else if(queryVariable!=null)
				{
					p.name=queryVariable.value();
					e.queryParas.add(p);
				}
				else if(isRequestBody!=null){
					
					e.input.add(p);
				}
			i++;
			}
			
		}
		
		//处理返回值注解
		ApiField returnDoc=m.getAnnotation(ApiField.class);
		
		e.output = handleParameter(out, "out");
		if(returnDoc!=null)
		{
			//类型 解释 例子
			if(returnDoc.example()!=null &&returnDoc.example().length()>0)
			{
				e.output.example =returnDoc.example();
			}
		}
		
	
		String group_path = group_base_path + summary.group();
		Group apiGroup = document.findGroup(group_path);
		apiGroup.entries.add(e);

		return e;
	}

	/**
	 * Trans state.
	 *
	 * @param state
	 *            the state
	 * @return the string
	 */
	private String transState(DevelopmentState state) {
		if (state == DevelopmentState.FINISH) {
			return "已完成";
		} else if (state == DevelopmentState.OBSOLETED) {
			return "已废弃";
		} else if (state == DevelopmentState.PROCESS) {
			return "开发中";
		} else if (state == DevelopmentState.UNSTART) {
			return "未开发";
		}
		return "";
	}

	/**
	 * 处理参数.
	 *
	 * @param clz
	 *            the clz
	 * @param name
	 *            the name
	 * @return the object info
	 * @throws IllegalArgumentException
	 *             the illegal argument exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws InstantiationException
	 *             the instantiation exception
	 */
	private ObjectInfo handleParameter(Class<?> clz, String name)
			throws IllegalArgumentException, IllegalAccessException,
			InstantiationException {

		ObjectInfo p = new ObjectInfo();
		Doc doc = clz.getAnnotation(Doc.class);

		p.name = name == null ? clz.getSimpleName() : name;

		p.title = doc == null ? "" : doc.value();
		

		if (isPrimitive(clz)) {
			p.type = clz.getSimpleName();
		} else {
			p.type = clz.getName();
		}
		String sum = doc == null ? "" :( Strings.isBlank(doc.desc()) ?doc.value():doc.desc());

		// 循环处理父类中的解释
		Class<?> superclazz = clz.getSuperclass();
		while (superclazz != null) {
			Doc summary1 = superclazz.getAnnotation(Doc.class);
			if (sum.length() > 0) {
				sum += "<br/>";
			}
			sum += summary1 == null ? "" : summary1.desc();
			superclazz = superclazz.getSuperclass();
		}
		p.summary = sum;

		deeps = new Deeps();
		deeps.push(clz.getName(), deeps.getLevel());
		Object instance = null;

		instance = newInstance(clz);

		for (Field f : clz.getFields()) {
			ObjectInfo fld = handleField(instance, f);
			if (fld != null) {
				p.fields.add(fld);
			}
		}

		if (instance != null)
			p.json = Json.toJson(instance, JsonFormat.full());
		else {
			p.json = "{}";
		}
		return p;
	}

	/** 类深度信息. */
	Deeps deeps;

	/**
	 * 处理字段.
	 *
	 * @param instance
	 *            the instance
	 * @param f
	 *            the f
	 * @return the object info
	 * @throws IllegalArgumentException
	 *             the illegal argument exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws InstantiationException
	 *             the instantiation exception
	 */
	private ObjectInfo handleField(Object instance, Field f)
			throws IllegalArgumentException, IllegalAccessException,
			InstantiationException {
		deeps.incLevel();

		ApiField wf = f.getAnnotation(ApiField.class);
		if (wf != null) {

			ObjectInfo fi = new ObjectInfo();
			fi.manditary = wf.mandidate();
			fi.title = wf.value();
			fi.length = wf.length();
			fi.example = wf.example();
			fi.name = f.getName();
			fi.type = f.getType().getName();

			// 处理返回代码
			Codes codes = f.getAnnotation(Codes.class);
			if (codes != null) {
				for (Code code : codes.value()) {
					FieldCode fc = new FieldCode(code.value(), code.desc());
					fi.codes.add(fc);
				}
			}

			// 记录类型的循环次数

			deeps.push(f.getType().getName(), deeps.getLevel());

			// 处理字段
			if (isPrimitive(f.getType())) {
				// 原始数据类型 无需解析子类
				fi.type = f.getType().getSimpleName();

				if (instance != null) {
					// 处理例子
					f.set(instance,
							Castors.me().castTo(wf.example(), f.getType()));
				}
			} else if (isList(f)) {

				Type type = getGenericType(f);
				Class<?> c = (Class<?>) type;
				fi.type = "List<" + c.getName() + ">";

				ArrayList list = new ArrayList();
				if (instance != null) {
					// 处理例子
					f.set(instance, list);
				}

				int count = deeps.getPreLevelCount(c.getName(),
						deeps.getLevel());
				if (count > 2) {
					// 不处理了，油循环引用
					deeps.decLevel();
					return null;
				}

				Object cinstance = newInstance(c);
				// 处理 DOc fi.summary;

				// 读取List数组中对象的Doc注解
				Doc fdoc = c.getAnnotation(Doc.class);
				if (fdoc != null) {
					fi.summary = fdoc.desc();
				}

				// 列表添加2个例子
				list.add(cinstance);

				for (Field f1 : c.getFields()) {
					// 检查是否是循环引用
					ObjectInfo o = handleField(cinstance, f1);
					if (o != null) {
						fi.fields.add(o);
					}
				}

			} else {
				// 该字段是一个对象类，循环处理此类
				int count = deeps.getPreLevelCount(f.getType().getName(),
						deeps.getLevel());
				if (count > 2) {
					// 不处理了，油循环引用
					deeps.decLevel();
					return null;
				}

				Object cinstance = null;

				cinstance = newInstance(f.getType());
				f.set(instance, cinstance);

				// 读取List数组中对象的Doc注解
				Doc fdoc = cinstance.getClass().getAnnotation(Doc.class);
				if (fdoc != null) {
					fi.summary = fdoc.desc();
				}

				for (Field f1 : f.getType().getFields()) {
					ObjectInfo o = handleField(cinstance, f1);
					if (o != null) {
						fi.fields.add(o);
					}
				}
			}

			deeps.decLevel();
			return fi;
		}
		deeps.decLevel();
		return null;
	}

	/**
	 * New instance.
	 *
	 * @param c
	 *            the c
	 * @return the object
	 */
	private Object newInstance(Class<?> c) {
		// System.out.println(c.getName());
		Mirror<?> m = Mirror.me(c);
		if(m.isArray())
		{
			return new ArrayList();
		}
		if (m.isInt()) {
			return (Integer) 0;
		} else if (m.isMap()) {
			return new HashMap<Object, Object>();
		} else if (m.isDouble()) {
			return (Double) 0.0;
		} else if (m.isFloat()) {
			return (Float) 0.0f;
		} else if (m.isBoolean()) {
			return (Boolean) true;
		} else if (m.isByte()) {
			Byte b = new Byte("0");
			return b;
		} else if (m.isLong()) {
			return new Long(0l);
		} else
			return m.born();
	}

	/**
	 * Checks if is list.
	 *
	 * @param f
	 *            the f
	 * @return true, if is list
	 */
	private boolean isList(Field f) {
		if (f.getType().isAssignableFrom(List.class)) {
			return true;
		}
		return false;
	}

	/**
	 * Gets the generic type.
	 *
	 * @param f
	 *            the f
	 * @return the generic type
	 */
	private Type getGenericType(Field f) {
		ParameterizedType pt = (ParameterizedType) f.getGenericType();
		return pt.getActualTypeArguments()[0];
	}

	/**
	 * Checks if is primitive.
	 *
	 * @param c
	 *            the c
	 * @return true, if is primitive
	 */
	private boolean isPrimitive(Class<?> c) {
		String name = c.getName();

		String[] ps = { "int", "Integer", "float", "FLoat", "Double", "double",
				"long", "Long", "Date", "DateTime", "String", "boolean",
				"Boolean", "char", "short", "byte", "Timestamp" };
		for (String s : ps) {
			if (name.contains(s)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 * @throws IllegalArgumentException
	 *             the illegal argument exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws InstantiationException
	 *             the instantiation exception
	 */
	public static void main(String[] args) throws IllegalArgumentException,
			IllegalAccessException, InstantiationException {

		SpringParser p = new SpringParser();

		ApiDoc doc = p.parse(new GenContext(), "cn.mapway.doc2.test");
		System.out.println(Json.toJson(doc));
	}
}
