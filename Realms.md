# 🌌 AoiCore — Core Engine (Spigot/Paper 1.21.x • Java 21)

> **Mục tiêu:** Một “bộ não trung ương” ổn định, không chứa gameplay, tối ưu cho production, không block main thread, cache-first, có API để plugin khác hook vào.

---

## 🎯 Phạm vi (Scope)

### ✅ AoiCore LÀM

* Player lifecycle: load → cache → save
* Cache layer (RAM-first)
* Database abstraction (MySQL / SQLite)
* Scheduler wrapper (async/sync)
* Config system (YAML, hot-reload)
* Debug & logging
* Public API + custom events

### ❌ AoiCore KHÔNG LÀM

* Gameplay (skills, damage, items, PvP…)
* Logic game cụ thể

---

## 🧱 Structure (Ổn định, không thay đổi giữa các chat)

```
AoiCore/
├── api/
│   ├── AoiCoreAPI.java
│   ├── AoiCoreProvider.java
│   ├── player/PlayerData.java
│   └── event/
│       ├── AoiPlayerLoadEvent.java
│       └── AoiPlayerSaveEvent.java
│
├── core/
│   ├── bootstrap/AoiCorePlugin.java
│   ├── player/PlayerManager.java
│   ├── cache/CacheService.java
│   ├── scheduler/TaskService.java
│   ├── config/ConfigService.java
│   └── debug/DebugService.java
│
├── database/
│   ├── driver/DatabaseDriver.java
│   ├── driver/mysql/MySQLDriver.java
│   ├── driver/sqlite/SQLiteDriver.java
│   ├── repository/PlayerRepository.java
│   └── migration/Schema.java
│
├── util/
│   ├── Async.java
│   └── Log.java
│
├── resources/
│   ├── plugin.yml
│   ├── config.yml
│   └── database.yml
│
└── pom.xml
```

---

## ⚙️ Maven (pom.xml)

```xml
<project>
  <modelVersion>4.0.0</modelVersion>
  <groupId>dev.aoi</groupId>
  <artifactId>AoiCore</artifactId>
  <version>1.0.0</version>

  <properties>
    <maven.compiler.source>21</maven.compiler.source>
    <maven.compiler.target>21</maven.compiler.target>
  </properties>

  <dependencies>
    <dependency>
      <groupId>io.papermc.paper</groupId>
      <artifactId>paper-api</artifactId>
      <version>1.21.1-R0.1-SNAPSHOT</version>
      <scope>provided</scope>
    </dependency>

    <dependency>
      <groupId>com.zaxxer</groupId>
      <artifactId>HikariCP</artifactId>
      <version>5.1.0</version>
    </dependency>
  </dependencies>
</project>
```

---

## 🤖 GitHub Actions (.github/workflows/build.yml)

```yaml
name: Build AoiCore

on:
  push:
    branches: [ "main" ]
  pull_request:

jobs:
  build:
    runs-on: ubuntu-latest

    steps:
      - uses: actions/checkout@v4

      - name: Setup Java 21
        uses: actions/setup-java@v4
        with:
          java-version: '21'
          distribution: 'temurin'

      - name: Build
        run: mvn -B clean package

      - name: Upload Artifact
        uses: actions/upload-artifact@v4
        with:
          name: AoiCore
          path: target/*.jar
```

---

## 🧠 API (Ổn định cho plugin khác)

```java
public interface AoiCoreAPI {

    PlayerData getPlayer(UUID uuid);

    void savePlayer(UUID uuid);

    void runAsync(Runnable task);

    void runSync(Runnable task);

    boolean isDebug();
}
```

```java
public final class AoiCoreProvider {
    private static AoiCoreAPI instance;

    public static void set(AoiCoreAPI api) {
        instance = api;
    }

    public static AoiCoreAPI get() {
        return instance;
    }
}
```

---

## 🧬 Player Data & Cache

```java
public class PlayerData {
    private final UUID uuid;
    private final Map<String, Object> data = new ConcurrentHashMap<>();

    public PlayerData(UUID uuid) {
        this.uuid = uuid;
    }

    public Object get(String key) {
        return data.get(key);
    }

    public void set(String key, Object value) {
        data.put(key, value);
    }
}
```

```java
public class CacheService {
    private final Map<UUID, PlayerData> cache = new ConcurrentHashMap<>();

    public PlayerData get(UUID uuid) {
        return cache.get(uuid);
    }

    public void put(UUID uuid, PlayerData data) {
        cache.put(uuid, data);
    }

    public void remove(UUID uuid) {
        cache.remove(uuid);
    }
}
```

---

## 🔄 Player Flow (Không lag)

```
JOIN
 → async load DB
 → put cache
 → fire AoiPlayerLoadEvent

RUNTIME
 → đọc 100% từ cache

QUIT / AUTOSAVE
 → async save DB
 → fire AoiPlayerSaveEvent
```

---

## 🗄️ Database Layer

```java
public interface DatabaseDriver {
    void connect();
    void disconnect();
    void execute(String sql);
}
```

```java
// Ví dụ MySQL (HikariCP)
HikariConfig cfg = new HikariConfig();
cfg.setJdbcUrl("jdbc:mysql://localhost:3306/aoicore");
cfg.setUsername("root");
cfg.setPassword("pass");
HikariDataSource ds = new HikariDataSource(cfg);
```

---

## ⚡ Scheduler Wrapper

```java
public class TaskService {

    private final JavaPlugin plugin;

    public TaskService(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void async(Runnable r) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, r);
    }

    public void sync(Runnable r) {
        Bukkit.getScheduler().runTask(plugin, r);
    }
}
```

---

## ⚙️ Config (YAML)

`config.yml`

```yaml
debug: false
autosave-interval: 300
```

`database.yml`

```yaml
type: SQLITE
mysql:
  url: jdbc:mysql://localhost:3306/aoicore
  user: root
  password: pass
```

---

## 🧪 Debug

* Cache hit/miss
* Query time
* Task duration
* Player load time

---

## 🔌 Custom Events

```java
public class AoiPlayerLoadEvent extends Event {
    private final PlayerData data;
}
```

```java
public class AoiPlayerSaveEvent extends Event {
    private final PlayerData data;
}
```

---

## 🚀 Performance Rules (BẮT BUỘC)

### ❌ Không làm

* Query DB trong event
* Chạy IO trên main thread
* Save từng action

### ✅ Phải làm

* Cache everything
* Async IO
* Batch save theo interval

---

## 🔌 Ví dụ plugin khác dùng API

```java
AoiCoreAPI api = AoiCoreProvider.get();
PlayerData data = api.getPlayer(player.getUniqueId());

data.set("mana", 100);
```

---

## 📦 plugin.yml

```yaml
name: AoiCore
main: dev.aoi.core.bootstrap.AoiCorePlugin
version: 1.0
api-version: 1.21
```

---

## 🎯 Kết luận

* AoiCore = **Engine / Brain**
* Gameplay = plugin bên ngoài
* Kiến trúc này đảm bảo:

  * ⚡ TPS ổn định
  * 🧠 Không lag khi đông người
  * 🔌 Dễ mở rộng vô hạn

> Một khi Core vững, mọi hệ thống Tu Tiên phía trên chỉ là “ghép module”.
 
