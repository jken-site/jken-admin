package jken.support.thymeleaf;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface ModuleExpressionObject {
    @AliasFor(
            annotation = Component.class
    )
    String value() default "";

    String objectName();
}
