# AOIENGINE SPEC PRO

Premium Minecraft MMORPG Engine Specification

---

# 1. Project Identity

Project: **AoiEngine**
Type: **Premium MMORPG Engine Framework**
Server: **Paper 1.21.1+**
Language: **Java 21**

AoiEngine is the **core engine** responsible for powering a modular MMORPG ecosystem.

External gameplay plugins include:

* AoiStats
* AoiClass
* AoiSkill
* AoiGem
* AoiCultivation
* AoiWorld

AoiEngine provides the core infrastructure:

* Player data engine
* Stat system
* Skill runtime
* Combat pipeline
* Module loader
* Event bus
* Database layer
* Async scheduler
* Plugin API

---

# 2. Mandatory Development Rules

These rules are **strict requirements**.

1. Always send **FULL FILES**
2. Always include **folder paths**
3. Code must contain **clear comments**
4. No skeleton or placeholder code
5. Validate **structure and compilation**
6. If a file is removed or modified, it must be reported

Example format:

File path:

```
src/main/java/aoi/aoichan/core/EngineBootstrap.java
```

Then send the **entire file content**.

---

# 3. Base Package

```
aoi.aoichan
```

All engine classes must remain within this namespace.

---

# 4. Plugin Ecosystem

Server plugin structure:

```
plugins/

AoiEngine.jar
AoiStats.jar
AoiClass.jar
AoiSkill.jar
AoiGem.jar
AoiCultivation.jar
AoiWorld.jar
```

Role definitions:

| Plugin         | Role                   |
| -------------- | ---------------------- |
| AoiEngine      | Core engine            |
| AoiStats       | Stat calculation       |
| AoiClass       | Player class system    |
| AoiSkill       | Skill execution        |
| AoiGem         | Equipment gem system   |
| AoiCultivation | Progression system     |
| AoiWorld       | World scaling and mobs |

---

# 5. Engine Architecture

AoiEngine contains **12 major subsystems**.

---

## Core System

Handles engine startup and lifecycle.

Components:

* EngineBootstrap
* EngineLifecycle
* EngineContext
* EngineThreadPool

---

## Module System

Loads gameplay modules.

Components:

* Module
* ModuleLoader
* ModuleManager
* ModuleDescriptor
* ModuleDependency

Modules define metadata using:

```
module.yml
```

Example:

```
name: AoiStats
version: 1.0
main: aoi.stats.AoiStatsModule
depend:
 - AoiEngine
```

---

## Service Registry

Dependency injection container.

Components:

* ServiceRegistry
* ServiceProvider
* ServicePriority

Example usage:

```
StatService stat = EngineAPI.services().get(StatService.class);
```

---

## Player System

Runtime player management.

Components:

* PlayerManager
* PlayerSession
* PlayerProfile
* PlayerCache

PlayerProfile fields:

```
uuid
level
exp
stats
skills
class
cultivation
gemSlots
cooldowns
```

---

## Data Layer

Handles storage and persistence.

Components:

* DataManager
* StorageAdapter
* SQLStorage
* MongoStorage
* RedisCache
* DataSerializer

All storage operations must be **asynchronous**.

---

## Event System

Custom event engine.

Components:

* EngineEventBus
* EngineListener

Core events:

* PlayerLevelUpEvent
* PlayerStatChangeEvent
* PlayerSkillCastEvent
* CombatDamageEvent

---

## Scheduler System

Task management layer.

Components:

* AsyncScheduler
* SyncScheduler
* TaskManager
* EngineTask

Used for:

* database operations
* skill runtime
* cooldown timers
* combat calculations

---

## Stat Engine

Manages player attributes.

Components:

* StatManager
* StatType
* StatContainer
* StatCalculator

Example stats:

```
strength
agility
vitality
intelligence
critChance
critDamage
attackPower
defense
```

---

## Combat Engine

Handles damage calculations.

Components:

* DamageEngine
* DamageCalculator
* DamageType
* CombatPipeline

Damage pipeline:

```
BaseDamage
 → StatModifier
 → SkillModifier
 → ArmorReduction
 → FinalDamage
```

---

## Skill Engine

Skill runtime framework.

Components:

* SkillManager
* SkillExecutor
* SkillContext
* SkillCooldownManager

SkillContext contains:

```
caster
target
manaCost
cooldown
damage
```

---

## Hook System

Integration with external plugins.

Supported integrations:

* Vault
* PlaceholderAPI
* ProtocolLib
* WorldGuard

Components:

* PluginHookManager
* VaultHook
* PlaceholderAPIHook
* ProtocolLibHook

---

## Config System

Configuration and language files.

Components:

* ConfigManager
* LangManager
* ModuleConfig

Supports **hot reload**.

---

# 6. Engine Package Structure

```
aoi.aoichan
│
├── bootstrap
├── core
├── module
├── service
├── api
├── player
├── data
├── event
├── scheduler
├── stat
├── combat
├── skill
├── hook
├── command
├── config
└── util
```

This structure is **locked** and must not change.

---

# 7. Database Model

Example SQL player table:

```
players
------
uuid
level
exp
class
cultivation
mana
stamina
```

Stats table:

```
player_stats
------------
uuid
strength
agility
vitality
intelligence
```

Skills table:

```
player_skills
-------------
uuid
skill_id
level
cooldown
```

---

# 8. Combat Formula Example

Basic damage formula:

```
Damage =
(BaseAttack + Strength × 1.5)
× SkillMultiplier
× CritMultiplier
- DefenseReduction
```

Critical chance:

```
critChance = agility × 0.02
```

---

# 9. Skill Runtime Model

Skill definition example:

```
skill:
  id: fireball
  mana: 20
  cooldown: 5
  damage: 45
  scaling: intelligence
```

Skill execution pipeline:

```
SkillCast
 → ManaCheck
 → CooldownCheck
 → TargetAcquire
 → DamageEngine
 → EventDispatch
```

---

# 10. Build Environment

Tools required:

```
Java 21
Maven
Paper API 1.21.1
GitHub Actions
```

Build output:

```
target/AoiEngine.jar
```

---

# 11. Expected Code Scale

AoiEngine production scale:

| Metric        | Value   |
| ------------- | ------- |
| Classes       | 120–200 |
| Packages      | 15      |
| Subsystems    | 12      |
| Lines of code | 20k+    |

Comparable to premium Spigot plugins.

---

# 12. Development Starting Point

Engine development begins with the **Core Layer**.

First components to implement:

```
EngineBootstrap
EngineLifecycle
ServiceRegistry
ModuleManager
EngineThreadPool
EngineAPI
```

These form the **foundation of the engine**.

---

# 13. Important Reminder

This project aims to build a **production-level MMORPG engine**.

Therefore:

* No skeleton code
* No partial implementations
* All systems must be expandable
* Code must be production ready
 
