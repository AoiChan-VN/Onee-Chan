package vn.aoi.onii.commands;

import cloud.commandframework.annotations.AnnotationParser;
import cloud.commandframework.execution.CommandExecutionCoordinator;
import cloud.commandframework.meta.SimpleCommandMeta;
import cloud.commandframework.paper.PaperCommandManager;
import org.bukkit.command.CommandSender;
import vn.aoi.onii.Main;
import vn.aoi.onii.commands.framework.CommandInterceptor;
import vn.aoi.onii.commands.framework.ICommandModule;

import java.io.File;
import java.net.URL;
import java.util.*;

public class CommandManager {

    private final PaperCommandManager<CommandSender> manager;
    private final AnnotationParser<CommandSender> parser;

    public CommandManager(Main plugin) throws Exception {

        manager = new PaperCommandManager<>(
                plugin,
                CommandExecutionCoordinator.simpleCoordinator(),
                sender -> sender,
                sender -> sender
        );

        manager.registerCommandPreProcessor(new CommandInterceptor());

        parser = new AnnotationParser<>(
                manager,
                CommandSender.class,
                parameters -> SimpleCommandMeta.empty()
        );

        autoScan(plugin);
    }

    private void autoScan(Main plugin) {
        try {
            String pkg = "vn.aoi.onii.commands.modules";
            String path = pkg.replace('.', '/');

            Enumeration<URL> resources = plugin.getClassLoader().getResources(path);

            while (resources.hasMoreElements()) {
                File folder = new File(resources.nextElement().toURI());

                for (File file : Objects.requireNonNull(folder.listFiles())) {

                    if (!file.getName().endsWith(".class")) continue;

                    String name = pkg + "." + file.getName().replace(".class", "");

                    Class<?> clazz = Class.forName(name);

                    if (ICommandModule.class.isAssignableFrom(clazz)) {
                        ICommandModule module = (ICommandModule)
                                clazz.getDeclaredConstructor(Main.class).newInstance(plugin);

                        module.register(parser);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
