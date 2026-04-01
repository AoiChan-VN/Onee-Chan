package vn.aoi.onii.commands;

import cloud.commandframework.annotations.AnnotationParser;
import cloud.commandframework.execution.CommandExecutionCoordinator;
import cloud.commandframework.meta.SimpleCommandMeta;
import cloud.commandframework.paper.PaperCommandManager;
import org.bukkit.command.CommandSender;
import vn.aoi.onii.Main;
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

        parser = new AnnotationParser<>(
                manager,
                CommandSender.class,
                parameters -> SimpleCommandMeta.empty()
        );

        registerCompletions(plugin);
        autoScanModules(plugin, "vn.aoi.onii.commands.modules");
    }

    private void autoScanModules(Main plugin, String packageName) {
        try {
            String path = packageName.replace('.', '/');
            Enumeration<URL> resources = plugin.getClassLoader().getResources(path);

            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                File folder = new File(resource.toURI());

                for (File file : Objects.requireNonNull(folder.listFiles())) {
                    if (!file.getName().endsWith(".class")) continue;

                    String className = packageName + "." + file.getName().replace(".class", "");
                    Class<?> clazz = Class.forName(className);

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

    private void registerCompletions(Main plugin) {

        manager.commandSuggestionProcessor().registerSuggestion(
                "players",
                (ctx, input) -> plugin.getServer().getOnlinePlayers()
                        .stream().map(p -> p.getName()).toList()
        );

        manager.commandSuggestionProcessor().registerSuggestion(
                "realms",
                (ctx, input) -> plugin.getRealmManager().getRealms().keySet()
        );
    }
}
