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

* RAM = dữ liệu runtime
* Database = lưu trữ lâu dài
* Tuyệt đối không truy cập DB trên main thread
* Cache-first architecture
* Core không phụ thuộc plugin con

---

## 🧩 Kiến trúc tổng thể

```
AoiCore
├── AoiMain
├── core/
│   ├── CoreAPI
│   ├── CoreProvider
│
├── player/
│   ├── PlayerData
│   ├── PlayerService
│
├── data/
│   ├── DataContainer
│   ├── DataRegistry
│   ├── DataKey (type-safe)
│
├── database/
│   ├── Database
│   ├── DataRepository
│
├── system/
│   ├── Scheduler
│   ├── Config
│
├── hook/
│   ├── CoreHook
│
├── event/
│   ├── PlayerDataLoadEvent
│   ├── PlayerDataSaveEvent
│
├── util/
```

## 🧍 PlayerData Design

## 📦 DataContainer System (Improved)

### 🔐 Type-safe nâng cao

## 🔌 Hook System

### Cơ chế hoạt động

Khi player load:

## ⚡ Player Flow

### Join

* Async load từ DB
* Deserialize JSON
* Tạo PlayerData
* Attach containers từ hooks
* Đưa vào cache

### Runtime

* Plugin gọi API
* Lấy PlayerData từ cache
* Xử lý hoàn toàn trên RAM

### Quit

* Mark dirty
* Save async
* Remove khỏi cache
* 
## 🗃️ Database Design

### Format JSON

### Ưu điểm

* Không cần migration schema
* Dễ mở rộng
* Linh hoạt plugin

## ⚙️ Cache System

## 🔄 Save System

### Cách hoạt động

* Khi data thay đổi → set dirty
* Scheduler chạy mỗi X giây
* Batch save các player dirty

### Bổ sung an toàn

* Save ngay khi player quit
* Flush toàn bộ khi server shutdown

## ⏱️ Scheduler System

## ⚙️ Config System

* Load config.yml vào RAM
* Có thể reload bằng command
* Không cần restart server

---

## 📡 API Usage

## 📡 Event System

→ Cho phép plugin hook vào lifecycle

## 🚀 Mở rộng plugin

Plugin mới chỉ cần:

1. Implement CoreHook
2. Register hook
3. Dùng DataContainer

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

## 🧪 Hướng nâng cấp tương lai

* Async write queue
* Metrics (cache size, DB latency)
* Module system
* Binary serialization (MessagePack/BSON)

---

## 🎯 Kết luận

AoiCore không chỉ là một plugin core.

Nó là nền tảng backend cho toàn bộ hệ sinh thái plugin:

* Mở rộng dễ dàng
* Hiệu năng cao
* Thiết kế sạch

👉 Sẵn sàng cho production server quy mô lớn 🚀
 
