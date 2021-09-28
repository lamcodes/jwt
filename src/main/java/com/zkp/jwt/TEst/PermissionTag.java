package com.zkp.jwt.TEst;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionTag {
    boolean isPublic() default false;

    String[] tagDesc() default {};

    String[] excludeFilter() default {};

    String message() default "暂无权限!";

    String[] tagOr() default {};
}