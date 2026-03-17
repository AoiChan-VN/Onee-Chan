# 🌌 AoiCore - Architecture Design (Solo Premium Edition)

## 🎯 Mục tiêu
AoiCore là core engine cho server Minecraft (Spigot/Paper) với mục tiêu:
- Tối ưu hiệu năng (no lag, no blocking main thread)
- Dễ mở rộng (plug-and-play modules)
- Không chứa gameplay
- Dùng cho hệ plugin:
  - AoiWorld
  - AoiCrystal
  - AoiClass

---

## 🧠 Triết lý thiết kế

- RAM = nguồn dữ liệu runtime
- Database = lưu trữ lâu dài
- Không truy cập DB trên main thread
- Cache-first architecture
- Core không phụ thuộc plugin con

---

## 🧩 Kiến trúc tổng thể
 AoiChan
├── AoiMain
├── core/
│ ├── CoreAPI
│ ├── CoreProvider
│
├── player/
│ ├── PlayerData
│ ├── PlayerService
│
├── data/
│ ├── DataContainer
│ ├── DataRegistry
│
├── database/
│ ├── Database
│ ├── DataRepository
│
├── system/
│ ├── Scheduler
│ ├── Config
│
├── hook/
│ ├── CoreHook
│
├── util/


---

## 🧍 PlayerData Design

```java
class PlayerData {
    UUID uuid;
    Map<String, DataContainer> containers;
    boolean dirty;
}

Giải thích
containers: nơi plugin khác lưu data riêng
dirty: đánh dấu cần save

📦 DataContainer System (Quan trọng nhất)

class DataContainer {
    Map<String, Object> data;
}

Ví dụ:

AoiClass
key: "class"
data:
  level: 10
  exp: 2500

AoiCrystal
key: "crystal"
data:
  slots: 3
  gems: [...]

AoiWorld
key: "world"
data:
  bossDamage: 5000

🔌 Hook System

interface CoreHook {
    String getKey();
    DataContainer createDefault(UUID uuid);
}

Khi player load:
 • Core gọi tất cả hook
 • Tự động attach container vào PlayerData

⚡ Player Flow
Join
Async load DB
→ Deserialize JSON
→ Create PlayerData
→ Attach containers (hooks)
→ Put cache

Runtime
Plugin gọi API
→ Lấy PlayerData từ cache
→ Xử lý nhanh (RAM)

Quit
Mark dirty
→ Save async

🗃️ Database Design
Lưu dạng JSON
{
  "uuid": "player-uuid",
  "data": {
    "class": {...},
    "crystal": {...},
    "world": {...}
  }
}

Ưu điểm
• Không cần sửa schema khi thêm plugin
• Không migration phức tạp
• Dễ mở rộng

⚙️ Cache System
Map<UUID, PlayerData> cache;

Nguyên tắc:
 • Tất cả đọc/ghi runtime = cache
 • DB chỉ dùng khi load/save

🔄 Save System
Cách hoạt động
 • Khi data thay đổi → set dirty
 • Scheduler chạy mỗi X giây
 • Batch save các player dirty

Lợi ích
 • Giảm số lần ghi DB
 • Tăng hiệu năng

⏱️ Scheduler System

Wrapper cho BukkitScheduler:

runAsync()
runSync()
runLater()
runTimer()

⚙️ Config System
 • Load config.yml vào RAM
 • Có thể reload bằng command
 • Không cần restart server

📡 API Usage

CoreAPI api = CoreProvider.get();

PlayerData data = api.getPlayer(uuid);
DataContainer container = data.get("class");

int level = container.getInt("level");
container.set("level", level + 1);

🚀 Mở rộng plugin

Plugin mới chỉ cần:
 1. Implement CoreHook
 2. Register hook vào Core
 3. Dùng DataContainer
👉 Không cần sửa Core

Tiêu chuẩn "Premium"

• Không block main thread
• Async DB 100%
• Cache-first
• Dirty save system
• Không phụ thuộc gameplay
• Dễ mở rộng plugin
• Reload không restart

❌ Những thứ KHÔNG làm

• Không hardcode data vào PlayerData
• Không query DB liên tục
• Không sync IO trên main thread
• Không coupling giữa plugins
