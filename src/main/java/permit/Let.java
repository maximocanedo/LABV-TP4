package permit;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Target;

import entity.Permit;

@Deprecated
@Target(METHOD)
public @interface Let {
	public Permit[] value();
}
