package cn.mapway.document.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// TODO: Auto-generated Javadoc
/**
 * 注解.
 *
 * @author zhangjianshe
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface Codes {
	
	/**
	 * Code list.
	 *
	 * @return the code[]
	 */
	public Code[] value();

	/**
	 * scope.
	 *
	 * @return the string
	 */
	public String scope() default "";
}
