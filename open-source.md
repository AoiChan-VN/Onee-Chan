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

---

* Tuyệt đối không truy cập DB trên main thread
* Core không phụ thuộc plugin con

---

## 🧩 Kiến trúc tổng thể
có thể bổ sung thêm nếu cần
```
AoiCore
├── bootstrap/
│   └── AoiMain.java
├── lifecycle/
│   └── LifecycleManager.java
├── player/
│   ├── PlayerManager.java
│   ├── PlayerSession.java
│   ├── PlayerState.java
│   ├── PlayerPipeline.java
├── data/
│   ├── DataContainer.java
│   ├── DataRegistry.java
├── pool/
│   ├── PacketPool
│   ├── DataContainerPool
├── cache/
│   ├── CacheManager.java
│   ├── CacheEntry.java
│   ├── CachePolicy.java
├── database/
│   ├── DatabaseProvider.java
│   ├── AsyncQueue.java
│   ├── driver/
│        ├── SQLiteDriver.java
│        ├── MySQLDriver.java
│        ├── RedisDriver.java
├── scheduler/
│   ├── SyncScheduler.java
│   ├── AsyncScheduler.java
├──metrics/
│   ├── TPSMonitor
│   ├── CacheStats
│   ├── DBStats
├── event/
│   ├── AoiEventBus.java
│   ├── events/
│       ├── PlayerDataLoadEvent
│       ├── PlayerDataSaveEvent
├── hook/
├── util/
```

### 🔐 Type-safe nâng cao
•Key system (namespace)
•Versioning
•Serializer strategy

### Format JSON

## 🔄 Batch Save | Save System | Auto Save Scheduler
## ⏱️ Scheduler System
## ⚙️ Hot Reload Engine
---

🧠 Advanced Pack

DataRegistry auto register container
Hook auto scan (reflection)
Event system (load/save event)
SQLite (main) / MySQL / Redis tùy chỉnh sử dụng trong config
Packet-level optimization

⚡ Hardcore Optimization

Async write queue (non-blocking IO)
Binary serialize (MessagePack)
Cache metrics (TPS impact monitor

→ Cho phép plugin hook vào lifecycle

## 🚀 Mở rộng plugin

👉 Không cần sửa Core

---

## 🧱 Tiêu chuẩn "Premium"

* Không block main thread
* Không phụ thuộc gameplay
* Reload không restart

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
