package cn.mapway.document.v2.module;

import java.util.ArrayList;
import java.util.List;

import cn.mapway.document.annotation.DevelopmentState;

// TODO: Auto-generated Javadoc
/**
 * URL 条目.
 * @author zhangjianshe
 *
 */
public class UrlItem {
	
	/** The path. */
	public String path;
	
	/** The name. */
	public String name;
	
	/** The description. */
	public String description;
	
	/** The group. */
	public String group;
	
	/** The author. */
	public String author;
	
	/** The state. */
	public DevelopmentState state;
	
	/** The methods. */
	public List<String> methods;
	
	/** The order. */
	public int order;
	
	/** The path parameters. */
	public List<PathParameter> pathParameters;
	
	/** The query parameters. */
	public List<QueryParameter> queryParameters;
	
	/** The body parameters. */
	public List<BodyParameter> bodyParameters;
	
	/** The return parameter. */
	public ReturnParameter returnParameter;
	
	/**
	 * Instantiates a new url item.
	 */
	public UrlItem() {
		methods=new ArrayList<String>();
		pathParameters=new ArrayList<PathParameter>();
		queryParameters=new ArrayList<QueryParameter>();
		bodyParameters =new ArrayList<BodyParameter>();
		returnParameter=new ReturnParameter();
		state =DevelopmentState.FINISH;
		group="/";
		author="";
		path="";
		name="";
		description="";
		order=0;
	}
	
	/**
	 * Gets the order.
	 *
	 * @return the order
	 */
	public int getOrder() {
		return order;
	}
	
	/**
	 * Sets the order.
	 *
	 * @param order the new order
	 */
	public void setOrder(int order) {
		this.order = order;
	}
	
	/**
	 * Gets the path.
	 *
	 * @return the path
	 */
	public String getPath() {
		return path;
	}
	
	/**
	 * Sets the path.
	 *
	 * @param path the new path
	 */
	public void setPath(String path) {
		this.path = path;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Gets the group.
	 *
	 * @return the group
	 */
	public String getGroup() {
		return group;
	}
	
	/**
	 * Sets the group.
	 *
	 * @param group the new group
	 */
	public void setGroup(String group) {
		this.group = group;
	}
	
	/**
	 * Gets the methods.
	 *
	 * @return the methods
	 */
	public List<String> getMethods() {
		return methods;
	}
	
	/**
	 * Sets the methods.
	 *
	 * @param methods the new methods
	 */
	public void setMethods(List<String> methods) {
		this.methods = methods;
	}
	
	/**
	 * Gets the author.
	 *
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}
	
	/**
	 * Sets the author.
	 *
	 * @param author the new author
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	
	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	public DevelopmentState getState() {
		return state;
	}
	
	/**
	 * Sets the state.
	 *
	 * @param state the new state
	 */
	public void setState(DevelopmentState state) {
		this.state = state;
	}

}
