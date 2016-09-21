package cn.mapway.document.parser;

import java.util.Properties;

import org.nutz.lang.Strings;

// TODO: Auto-generated Javadoc
/**
 * 配置信息.
 *
 * @author zhangjianshe
 */
public class GenContext extends Properties {

	/** The Constant DOMAIN. */
	private final static String DOMAIN = "DOMAIN";

	/** The Constant BASEPATH. */
	private final static String BASEPATH = "BASEPATH";

	/** The Constant AUTHOR. */
	private final static String AUTHOR = "AUTHOR";

	/** The Constant DOCTITLE. */
	private final static String DOCTITLE = "DOCTITLE";

	/** The Constant NAMESPACE. */
	private final static String NAMESPACE = "NAMESPACE";

	/** WORD URL. */
	private final static String WORDURL = "WORDURL";

	/**
	 * Gets the domain.
	 *
	 * @return the domain
	 */
	public String getDomain() {
		return getProperty(DOMAIN);
	}

	/**
	 * Sets the domain.
	 *
	 * @param domain
	 *            the new domain
	 */
	public void setDomain(String domain) {
		if (Strings.isBlank(domain)) {
			domain = "mapway.cn";
		}
		put(DOMAIN, domain);
	}

	/**
	 * Gets the basepath.
	 *
	 * @return the basepath
	 */
	public String getBasepath() {
		return getProperty(BASEPATH);
	}

	/**
	 * Sets the basepath.
	 *
	 * @param basepath
	 *            the new basepath
	 */
	public void setBasepath(String basepath) {
		if (Strings.isBlank(basepath)) {
			basepath = "";
		}
		put(BASEPATH, basepath);
	}

	/**
	 * Sets the doc title.
	 *
	 * @param docTitle
	 *            the new doc title
	 */
	public void setDocTitle(String docTitle) {
		if (Strings.isBlank(docTitle)) {
			docTitle = "请设置接口文档标题";
		}
		put(DOCTITLE, docTitle);
	}

	/**
	 * Gets the doc title.
	 *
	 * @return the doc title
	 */
	public String getDocTitle() {
		return getProperty(DOCTITLE);
	}

	/**
	 * Sets the author.
	 *
	 * @param author
	 *            the new author
	 */
	public void setAuthor(String author) {
		if (Strings.isBlank(author)) {
			author = "zhangjianshe@gmail.com";
		}
		put(AUTHOR, author);
	}

	/**
	 * Gets the author.
	 *
	 * @return the author
	 */
	public String getAuthor() {
		return getProperty(AUTHOR);
	}

	/**
	 * Gets the name space.
	 *
	 * @return the name space
	 */
	public String getNameSpace() {
		return getProperty(NAMESPACE);
	}

	/**
	 * Sets the name space.
	 *
	 * @param namespace
	 *            the new name space
	 */
	public void setNameSpace(String namespace) {
		put(NAMESPACE, namespace);
	}

	/**
	 * Gets the name space.
	 *
	 * @return the name space
	 */
	public String getWordURL() {
		return getProperty(WORDURL);
	}

	/**
	 * Sets the name space.
	 *
	 * @param wordUrl the new word URL
	 */
	public void setWordURL(String wordUrl) {
		put(WORDURL, wordUrl);
	}
}
