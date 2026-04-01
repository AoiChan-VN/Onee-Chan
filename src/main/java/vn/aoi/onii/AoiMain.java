package vn.aoi.onii;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import vn.aoi.onii.command.CommandManager;
import vn.aoi.onii.data.Database;
import vn.aoi.onii.listener.PlayerListener;
import vn.aoi.onii.player.PlayerManager;
import vn.aoi.onii.rank.RankManager;
import vn.aoi.onii.system.ExpService;
import vn.aoi.onii.system.ThienKiepService;

public final class AoiMain extends JavaPlugin {

    private static AoiMain instance;
    private Database database;
    private PlayerManager playerManager;
    private RankManager rankManager;
    private ExpService expService;
    private ThienKiepService thienKiepService;
    private CommandManager commandManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        saveResource("message.yml", false);

        database = new Database(this);
        database.init();

        rankManager = new RankManager(this);
        rankManager.load();

        playerManager = new PlayerManager(this);
        expService = new ExpService(this);
        thienKiepService = new ThienKiepService(this);

        commandManager = new CommandManager(this);
        commandManager.registerCommands();

        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
    }

    public static AoiMain get(){return instance;}
    public Database db(){return database;}
    public PlayerManager pm(){return playerManager;}
    public RankManager rm(){return rankManager;}
    public ExpService exp(){return expService;}
    public ThienKiepService tk(){return thienKiepService;}
}
