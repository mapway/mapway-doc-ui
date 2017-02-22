/******************************************************************************
<pre>

           =============================================================
           -   ____ _  _ ____ _  _ ____  _ _ ____ _  _ ____ _  _ ____  -
           -    __] |__| |__| |\ | | __  | | |__| |\ | [__  |__| |___  -
           -   [___ |  | |  | | \| |__| _| | |  | | \| ___] |  | |___  -
           -           http://hi.baidu.com/zhangjianshe                -
           =============================================================

</pre>
 *******************************************************************************/
package cn.mapway.document.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// TODO: Auto-generated Javadoc
/**
 * API 字段注解.
 *
 * @author zhangjianshe@navinfo.com
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD,ElementType.PARAMETER ,ElementType.METHOD})
public @interface ApiField {

	/**
	 * 描述.
	 *
	 * @return the string
	 */
	public String value();

	/**
	 * 长度.
	 *
	 * @return the int
	 */
	public int length() default 0;

	/**
	 * 给出一个字段值得例子.
	 *
	 * @return the string
	 */
	public String example() default "";

	/**
	 * 是否是强制类型.
	 *
	 * @return true, if successful
	 */
	public boolean mandidate() default true;

	/**
	 * 字段的排序.
	 *
	 * @return the int
	 */
	public int order() default 0;
	
}
