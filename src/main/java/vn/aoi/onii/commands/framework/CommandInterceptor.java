package vn.aoi.onii.commands.framework;

import cloud.commandframework.context.CommandContext;
import cloud.commandframework.execution.preprocessor.CommandPreprocessor;
import org.bukkit.command.CommandSender;
import vn.aoi.onii.commands.framework.annotations.CommandCooldown;
import vn.aoi.onii.config.MessageManager;

import java.lang.reflect.Method;

public class CommandInterceptor implements CommandPreprocessor<CommandSender> {

    @Override
    public void accept(CommandContext<CommandSender> context) {

        try {
            Object instance = context.getCommand().getCommandExecutionHandler();
            Method method = context.getCommand().getCommandMethod();

            if (method == null) return;

            if (method.isAnnotationPresent(CommandCooldown.class)) {

                CommandCooldown cd = method.getAnnotation(CommandCooldown.class);

                String key = context.getSender().getName() + ":" + method.getName();

                if (CooldownManager.isOnCooldown(key, cd.seconds())) {
                    long remain = CooldownManager.getRemaining(key, cd.seconds());
                    context.getSender().sendMessage(
                            MessageManager.get("cooldown").replace("{time}", String.valueOf(remain))
                    );
                    context.setCancelled(true);
                    return;
                }

                CooldownManager.setCooldown(key);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 
