package cn.mapway.document.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 描述代码的特定值及其含义
 * 
 * @author zhangjianshe
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface Code {
	/**
	 * 字段值
	 * 
	 * @return
	 */
	public String value();

	/**
	 * 字段含义
	 * 
	 * @return
	 */
	public String desc() default "";

	/**
	 * 字段定义的名称
	 * 
	 * @return
	 */
	public String name() default "";

	/**
	 * scope
	 * 
	 * @return
	 */
	public String scope() default "";
}
