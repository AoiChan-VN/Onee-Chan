package vn.aoi.onii.commands.framework.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CommandCooldown {
    int seconds();
} 
