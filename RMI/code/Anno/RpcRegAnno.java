package RMI.Anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Csh on 2019/1/14.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)

public @interface RpcRegAnno {

    Class<?> value();

    String version() default "";

}
