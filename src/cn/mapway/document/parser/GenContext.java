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
	/** SUBTITLE URL. */
	private final static String SUBTITLE = "SUBTITLE";

	/** CopyRight. */
	private final static String COPYRIGHT = "COPYRIGHT";
	
	/** ENABLEJAVACONNECTOR. */
	private final static String ENABLEJAVACONNECTOR = "ENABLEJAVACONNECTOR";
	
	/** ENABLEGWTCONNECTOR. */
	private final static String ENABLEGWTCONNECTOR = "ENABLEGWTCONNECTOR";

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
	 * @param wordUrl
	 *            the new word URL
	 */
	public void setWordURL(String wordUrl) {
		put(WORDURL, wordUrl);
	}

	/**
	 * Gets the subtitle.
	 *
	 * @return the subtitle
	 */
	public String getSubtitle() {
		return getProperty(SUBTITLE);
	}

	/**
	 * Sets the subtitle.
	 *
	 * @param subtitle
	 *            the new subtitle
	 */
	public void setSubtitle(String subtitle) {
		put(SUBTITLE, subtitle);
	}

	/**
	 * Gets the copyright.
	 *
	 * @return the copyright
	 */
	public String getCopyright() {
		return getProperty(COPYRIGHT);
	}

	
	/**
	 * Sets the copyright.
	 *
	 * @param copyright the new copyright
	 */
	public void setCopyright(String copyright) {
		put(COPYRIGHT, copyright);
	}

	/**
	 * Sets the enable java connector.
	 *
	 * @param v the new enable java connector
	 */
	public void setEnableJavaConnector(String v) {
		put(ENABLEJAVACONNECTOR, v);
	}

	/**
	 * Sets the enable gwt connector.
	 *
	 * @param v the new enable gwt connector
	 */
	public void setEnableGwtConnector(String v) {
		put(ENABLEGWTCONNECTOR, v);
	}
	
	/**
	 * Gets the enable java connector.
	 *
	 * @return the enable java connector
	 */
	public Boolean getEnableJavaConnector() {
		return Boolean.valueOf(getProperty(ENABLEJAVACONNECTOR));
	}

	/**
	 * Gets the enable gwt connector.
	 *
	 * @return the enable gwt connector
	 */
	public Boolean getEnableGwtConnector() {
		return Boolean.valueOf(getProperty(ENABLEGWTCONNECTOR));
	}
}
