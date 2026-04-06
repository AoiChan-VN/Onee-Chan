package vn.aoi.onii.commands.context;

import co.aikar.commands.PaperCommandManager;
import org.bukkit.Bukkit;
import vn.aoi.onii.manager.RealmManager;

import java.util.stream.Collectors;

public class ACFCompletion {

    public static void register(PaperCommandManager manager, RealmManager realmManager) {

        // 👤 Online players
        manager.getCommandCompletions().registerCompletion("players",
                c -> Bukkit.getOnlinePlayers()
                        .stream()
                        .map(p -> p.getName())
                        .collect(Collectors.toList())
        );

        // 🌌 Realm dynamic
        manager.getCommandCompletions().registerCompletion("realms",
                c -> realmManager.getAllRealms()
                        .stream()
                        .collect(Collectors.toList())
        );
    }
}
