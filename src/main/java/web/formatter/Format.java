package web.formatter;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import web.formatter.TextBlock.Alignment;

@Retention(RetentionPolicy.RUNTIME) 
@Target(METHOD)
public @interface Format {
	
	/**
	 * Indica el órden en el que aparecerá el campo al imprimirse. 
	 */
	int order() default -1;
	
	/**
	 * Indica si se debe ignorar el campo.
	 */
	boolean ignore() default false;
	
	/**
	 * Etiqueta del campo.
	 */
	String label() default "";
	
	/**
	 * Indica qué alineamiento debe respetar el campo al ser impreso.
	 */
	Alignment align() default Alignment.LEFT;
	
	/**
	 * Indica si se debe omitir la etiqueta.
	 */
	boolean omitLabel() default false;
	
	/**
	 * Prefijo del valor del campo.
	 */
	String prefix() default "";
	
	/**
	 * Sufijo del valor del campo.
	 */
	String suffix() default "";
	
	/**
	 * Si el campo es un booleano, texto que se mostrará cuando este sea true.
	 */
	String whenTrue() default "true";
	
	/**
	 * Si el campo es un booleano, texto que se mostrará cuando este sea false.
	 */
	String whenFalse() default "false";
}
