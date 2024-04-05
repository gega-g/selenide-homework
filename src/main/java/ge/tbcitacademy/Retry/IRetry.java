package ge.tbcitacademy.Retry;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface IRetry {
    int count() default 0;
}
