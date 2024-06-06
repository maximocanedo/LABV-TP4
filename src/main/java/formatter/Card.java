package formatter;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) 
@Target(TYPE)
public @interface Card {
	
	/**
	 * Nombre amigable de la entidad. Aparece al imprimir en la esquina superior derecha de la tarjeta.
	 */
	String name() default "";
	
	/**
	 * Ancho de la tarjeta al imprimirse.
	 */
	int size() default 48;
}
