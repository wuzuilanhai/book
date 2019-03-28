package com.biubiu.auth.annotation;

import org.hibernate.validator.constraints.NotEmpty;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Haibiao.Zhang on 2019-03-28 09:07
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Role {

    @NotEmpty
    String[] roles();

}
