# Cultivation Blueprint Revision (Maven Conversion)

## Mục tiêu
Giữ nguyên toàn bộ blueprint gốc.

## Chỉnh sửa bắt buộc

### 1. Build System
Thay toàn bộ tham chiếu:

- build.gradle.kts
- settings.gradle.kts

Bằng:

- pom.xml

### 2. Đổi tiêu đề

Từ:

VII. CI/CD: build.gradle.kts SKELETON

Thành:

VII. CI/CD: pom.xml (Maven) SKELETON

### 3. Maven Plugins

Sử dụng:

- maven-compiler-plugin
- maven-shade-plugin
- maven-surefire-plugin

### 4. Maven Repositories

Giữ nguyên:

- PaperMC
- PlaceholderAPI
- JitPack
- Maven Central

### 5. Dependencies

Giữ nguyên:

- paper-api
- PlaceholderAPI
- VaultAPI
- sqlite-jdbc
- HikariCP
- bStats

### 6. Shade Relocation

Giữ nguyên relocation:

- org.sqlite -> vn.aoi.cultivation.libs.sqlite
- com.zaxxer.hikari -> vn.aoi.cultivation.libs.hikari
- org.bstats -> vn.aoi.cultivation.libs.bstats

### 7. Batch Order

Thay:

1. build.gradle.kts + settings.gradle.kts

Bằng:

1. pom.xml

### 8. GitHub Actions

Thay:

./gradlew build

Bằng:

mvn -B clean package

Artifact:

target/Cultivation-x.x.x.jar

## Không thay đổi

- 97 Java files
- 8 resource files
- SQLite + HikariCP
- DAO Layer
- Event Layer
- Manager Layer
- Tribulation System
- Sect System
- Skill System
- Async/Sync Bridge
- Edge Case Matrix
- File Generation Order

## Kết luận

Blueprint gốc chỉ cần chuyển từ Gradle sang Maven.
Toàn bộ kiến trúc còn lại có thể giữ nguyên.
