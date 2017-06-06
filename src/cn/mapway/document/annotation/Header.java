package cn.mapway.document.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Header {
  /**
   * 用于Header中的 KEY值 例如 ENN-TOKEN
   *
   * @return the string
   */
  public String key() default "";

  /**
   * HEader中的VALUE值
   *
   * @return the int
   */
  public String value() default "";
}
