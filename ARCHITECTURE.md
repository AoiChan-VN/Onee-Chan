# 🌌 AoiCore - Architecture Design (Single Server Edition)

## 🎯 Mục tiêu

AoiCore là core engine cho server Minecraft (Spigot/Paper) được thiết kế cho:

* 1 server duy nhất (Skyblock / Survival RPG)
* Hiệu năng cao (no lag)
* Không block main thread
* Dễ mở rộng module
* Không chứa gameplay
* Là nền tảng cho:

  * AoiMobs
  * AoiItems
  * AoiCombat

---

## 🧱 Cấu trúc tổng thể

```
src/main/java/aoichan
│
├── AoiMain.java
│
├── bootstrap/
│   ├── Loader.java
│   ├── Shutdown.java
│
├── engine/
│   ├── Engine.java
│   ├── EngineState.java
│   ├── Lifecycle.java
│   ├── TickScheduler.java
│   ├── AsyncExecutor.java
│
├── thread/
│   ├── AsyncPool.java
│   ├── ThreadFactoryBuilder.java
│
├── service/
│   ├── ServiceManager.java
│   ├── IService.java
│
│   ├── player/
│   │   ├── PlayerService.java
│   │   ├── PlayerSession.java
│
│   ├── data/
│   │   ├── DataService.java
│   │   ├── DataCache.java
│   │   ├── SaveQueue.java
│   │   ├── DirtyFlag.java
│
│   ├── module/
│   │   ├── ModuleService.java
│   │   ├── ModuleLoader.java
│   │   ├── ModuleGraph.java
│
├── module/
│   ├── Module.java
│   ├── ModuleMeta.java
│   ├── ModuleState.java
│
├── data/
│   ├── repository/
│   │   ├── PlayerRepository.java
│   │   ├── AbstractRepository.java
│
│   ├── model/
│   │   ├── PlayerData.java
│
│   ├── storage/
│   │   ├── Storage.java
│   │   ├── SQLiteStorage.java
│
├── eventbus/
│   ├── EventBus.java
│   ├── EventListener.java
│   ├── Subscribe.java
│   ├── EventPriority.java
│
├── api/
│   ├── API.java
│   ├── event/
│   │   ├── Event.java
│   │   ├── PlayerDataLoadEvent.java
│   │   ├── PlayerDataSaveEvent.java
│
├── config/
│   ├── ConfigManager.java
│   ├── HotReloadService.java
│   ├── ConfigWatcher.java
│
├── autosave/
│   ├── AutoSaveService.java
│
├── util/
│   ├── Logger.java
│   ├── ThreadUtil.java
│   ├── TimeUtil.java
```

---

## 🧠 Triết lý thiết kế

```
• Đơn giản > Phức tạp
• Ổn định > Thông minh quá mức
• Hiệu năng > Linh hoạt
```

---

## ⚙️ Thread Model

| Thành phần | Thread      |
| ---------- | ----------- |
| Bukkit API | Main Thread |
| Data / DB  | Async       |
| Autosave   | Async       |
| Cache      | Mixed       |

---

## 💾 Data System

### 🔄 Flow Load

```
Player Join
 → Async Load (SQLite)
 → Cache (PlayerSession)
 → Attach vào Player
```

### 💿 Flow Save

```
DirtyFlag = true
 → SaveQueue
 → Batch Save (Async)
 → SQLite
```

---

## 🧠 PlayerSession

```
PlayerSession
 ├── PlayerData
 ├── DirtyFlag
 ├── LastSaveTime
 ├── State
```

---

## ⚡ EventBus

* Không dùng Bukkit Event
* Hỗ trợ:

  * Async event
  * Priority
  * Cancelable

---

## 🧩 Module System

* Tự động load module
* Có dependency graph
* Lifecycle:

```
LOAD → ENABLE → ACTIVE → DISABLE
```

---

## 🔄 Hot Reload

* Reload config không restart server
* Reload từng service
* Không reload toàn bộ plugin

---

## 💿 Autosave

* Không save từng player ngay lập tức
* Sử dụng queue + batch
* Giảm load SQLite

---

## ❌ Nguyên tắc cấm

```
❌ Không query DB trên main thread
❌ Không module truy cập DB trực tiếp
❌ Không save spam
❌ Không hardcode PlayerData
❌ Không coupling giữa module
```

---

## 🧱 Tiêu chuẩn Premium

```
✔ Async toàn bộ IO
✔ Dirty Flag System
✔ Autosave Queue
✔ EventBus riêng
✔ Module lifecycle
✔ Hot Reload
✔ Zero lag design
```

---
 
## Quan trọng khi Build:

• No Skeleton
• No Code test, lỗi, bug ẩn
• File + Code Comment // 【❅】:
