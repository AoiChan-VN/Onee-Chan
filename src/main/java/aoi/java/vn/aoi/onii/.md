PROJECT SPECIFICATION: AOI-CHAN (MINECRAFT CULTIVATION PLUGIN)

1. TECHNICAL STACK & ENVIRONMENT

• Platform: Spigot / Paper 1.21.1 (Java 21).
• Architecture: Hexagonal / Service-Oriented Architecture (SOA).
• Database: SQLite with HikariCP Connection Pool.
• Concurrency: Non-blocking I/O, Caffeine Cache for Player Data, Asynchronous Database Operations.
• Libraries: Maven, Lombok, ACF (Aikar's Command Framework) or Reflection-based Auto-Registration.

2. CORE DOMAIN LOGIC

A. Realms & Levels System (Data-Driven)
• Realms (Cảnh giới): Load from config.yml.
• Phases (Tiểu cảnh giới): Sơ kỳ, Trung kỳ, Hậu kỳ, Viên mãn.
• Leveling:
 ◦Luyện Khí: 9 levels (1-3: Sơ, 4-6: Trung, 7-9: Hậu).
 ◦Other Realms: 4 phases (Sơ, Trung, Hậu, Viên mãn).
 ◦Auto-Level Up: Triggered immediately when EXP reaches exp-required.

B. Heavenly Tribulation (Thiên Kiếp)
• Trigger: When upgrading from "Độ Kiếp" to "Chân Tiên".
• Mechanics:
 ◦Start a 30-second BukkitRunnable.
 ◦Random lightning strikes (world.strikeLightning) within 3-5 blocks of the player.
 ◦Condition: If player dies or logs out -> Fail (Reset EXP). If survives -> Rank Up.

3. SYSTEM ARCHITECTURE (PACKAGE STRUCTURE)

AI MUST follow this structure strictly:
• vn.aoi.onii.api: Custom Events (PlayerExpGainEvent, PlayerLevelUpEvent).
• vn.aoi.onii.commands: Command base & Auto-tab completer.
• vn.aoi.onii.config: Config & Multi-lang (Message.yml) manager.
• vn.aoi.onii.database: SQLiteConnector.java, PlayerRepository.java (Async CRUD).
• vn.aoi.onii.manager: PlayerManager.java (Caffeine Cache), RealmManager.java (Config Loader).
• vn.aoi.onii.model: Cultivator.java (POJO), Realm.java.
• vn.aoi.onii.listener: MobKillListener.java, ConnectionListener.java.
• vn.aoi.onii.task: SaveTask.java, TribulationTask.java.

4. CODING STANDARDS (NO EXCEPTIONS)

• Thread Safety: Use ConcurrentHashMap for online players. Never run SQL on Main Thread.
• Performance: No onUpdate loops. Use Event-driven or scheduled tasks.
• Clean Code: Use Lombok (@Getter, @Setter, @Builder). No Spaghetti code.
• Security: Validate all inputs. Anti-dupe EXP logic.
• Expandability: Ensure other plugins can get a player's Realm via a Singleton API.

5. CONFIGURATION TEMPLATE (YAML)

yaml ( config, mobs, realm )
```
storage: sqlite
auto-save-interval: 300 # seconds
```
```
mobs-exp:
  ZOMBIE: 5
  DIAMOND_ZOMBIE: 50 # Custom metadata check
```
```
realms:
  "Phàm nhân":
    next-rank: "Luyện khí"
    max-level: 1
    exp-required: 10
  "Luyện khí":
    next-rank: "Trúc cơ"
    max-level: 9
    levels:
      1: { exp: 50, phase: "Sơ kỳ" }
      # ... (logic mapping as defined)
  "Độ kiếp":
    is-tribulation: true
    duration: 30
    next-rank: "Chân tiên"
```

6. OUTPUT REQUIREMENT

• Generate Full Code for each class.
• Include all Imports.
• Provide a pom.xml with necessary dependencies.
• Break down the response into parts if necessary to avoid code truncation.
