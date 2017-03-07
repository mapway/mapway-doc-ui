package cn.mapway.document.v2.module;

// TODO: Auto-generated Javadoc
/**
 * 路径参数.
 * @author zhangjianshe
 *
 */
public class PathParameter {

	
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
	 * Gets the data type.
	 *
	 * @return the data type
	 */
	public String getDataType() {
		return dataType;
	}
	
	/**
	 * Sets the data type.
	 *
	 * @param dataType the new data type
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
	/**
	 * Gets the example.
	 *
	 * @return the example
	 */
	public String getExample() {
		return example;
	}
	
	/**
	 * Sets the example.
	 *
	 * @param example the new example
	 */
	public void setExample(String example) {
		this.example = example;
	}
	
	/** The name. */
	public String name;
	
	/** The description. */
	public String description;
	
	/** The data type. */
	public String dataType;
	
	/** The example. */
	public String example;
}
