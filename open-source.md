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

## 🧠 Triết lý thiết kế

* Database = lưu trữ lâu dài
* Tuyệt đối không truy cập DB trên main thread
* Cache-first architecture
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
├── data/
│   ├── DataContainer.java
│   ├── DataRegistry.java
├── cache/
│   ├── CacheManager.java
├── database/
│   ├── DatabaseProvider.java
│   ├── AsyncQueue.java
├── scheduler/
│   ├── TaskScheduler.java
├── event/
├── hook/
├── util/
```

## 🧍 PlayerData Design
## 📦 DataContainer System (Improved)

### 🔐 Type-safe nâng cao
•Key system (namespace)
•Versioning
•Serializer strategy

## 🔌 Hook System
## ⚡ Player Flows
## 🗃️ Database Design

### Format JSON
### Ưu điểm
* Không cần migration schema
* Dễ mở rộng
* Linh hoạt plugin

## ⚙️ Cache System

## 🔄 Save System

### Bổ sung an toàn
* Save ngay khi player quit
* Flush toàn bộ khi server shutdown

## ⏱️ Scheduler System

## ⚙️ Config System
* Load config.yml vào RAM
* Có thể reload bằng command
* Không cần restart server

## Lifecycle
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
Cache metrics (TPS impact monitor)

## 📡 API Usage
## 📡 Event System

→ Cho phép plugin hook vào lifecycle

## 🚀 Mở rộng plugin

👉 Không cần sửa Core

---

## 🧱 Tiêu chuẩn "Premium"

* Không block main thread
* Async DB 100%
* Cache-first
* Dirty save system
* Type-safe data
* Event-driven
* Không phụ thuộc gameplay
* Reload không restart

---

## ❌ Những điều cần tránh

* Hardcode data vào PlayerData
* Query DB liên tục
* Sync IO trên main thread
* Coupling giữa plugins
---

## 🎯 Kết luận

AoiCore không chỉ là một plugin core.

Nó là nền tảng backend cho toàn bộ hệ sinh thái plugin:

* Mở rộng dễ dàng
* Hiệu năng cao
* Thiết kế sạch

👉 Sẵn sàng cho production server quy mô lớn 🚀
 
## Quan trọng:
• No Code test, lỗi, bug ẩn
• File + Code Comment // 【❅】:
