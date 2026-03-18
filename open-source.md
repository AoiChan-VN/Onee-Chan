# 🌌 AoiCore - Architecture Design (Premium Edition)

## 🎯 Mục tiêu

AoiCore là core engine cho server Minecraft (Spigot/Paper) với mục tiêu:

* Hiệu năng cao (no lag, không block main thread)
* Dễ mở rộng (plug-and-play modules)
* Không chứa gameplay
* Là nền tảng cho hệ plugin:

  * AoiWorld
  * AoiCrystal
  * AoiClass
  * AoiCrystal

## Cấu trúc tổng thể
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
│   ├── TickScheduler.java
│   ├── AsyncExecutor.java
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
│
│   ├── module/
│   │   ├── ModuleService.java
│   │   ├── ModuleLoader.java
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
│   │   ├── StorageType.java
│   │   ├── MySQLStorage.java
│   │   ├── SQLiteStorage.java
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
* Tuyệt đối không truy cập DB trên main thread
* Core không phụ thuộc plugin con
* Mở rộng plugin 👉 Không cần sửa Core
---

## 🧱 Tiêu chuẩn "Premium"

* Không block main thread
* không spam load, save,...
* Không phụ thuộc gameplay
* HOT-Reload không restart
* PlayerData System ( SQLite / MySQL )
* Module Auto Save

---

## ❌ Những điều cần tránh

* Hardcode data vào PlayerData 
* Query DB liên tục
* Sync IO trên main thread
* Coupling giữa plugins
---
 
## Quan trọng:
• No Code test, lỗi, bug ẩn
• File + Code Comment // 【❅】:

