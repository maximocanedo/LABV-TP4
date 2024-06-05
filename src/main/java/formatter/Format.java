package formatter;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import formatter.TextBlock.Alignment;

@Retention(RetentionPolicy.RUNTIME) 
@Target(METHOD)
public @interface Format {
	int order() default -1;
	boolean ignore() default false;
	String label() default "";
	Alignment align() default Alignment.LEFT;
	boolean omitLabel() default false;
	String prefix() default "";
	String suffix() default "";
	String whenTrue() default "true";
	String whenFalse() default "false";
}
