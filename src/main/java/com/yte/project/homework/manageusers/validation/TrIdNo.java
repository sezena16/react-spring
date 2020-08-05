package com.yte.project.homework.manageusers.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TrIdNoValidator.class)
public @interface TrIdNo {

    String message() default "TR Id No format is not acceptable!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
