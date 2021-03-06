package szewek.mcflux.util;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(TYPE)
@Retention(RUNTIME)
@SuppressWarnings("unused")
public @interface InjectRegistry {
	InjectCond requires();
	String[] args();
}
