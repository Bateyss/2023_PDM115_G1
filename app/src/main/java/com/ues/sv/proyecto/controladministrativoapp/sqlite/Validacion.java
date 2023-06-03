package com.ues.sv.proyecto.controladministrativoapp.sqlite;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Validacion {

//    int length() default 100000;
//
//    int min() default Integer.MIN_VALUE;
//
//    int max() default Integer.MAX_VALUE;
    boolean notNull() default false;
}
