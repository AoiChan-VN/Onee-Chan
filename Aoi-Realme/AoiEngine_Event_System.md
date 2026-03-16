# AOIENGINE EVENT SYSTEM

This document describes the internal event system used by AoiEngine.

The event system allows communication between:

engine subsystems
plugins
modules

---

# Event Bus

Central component:

EngineEventBus

Responsibilities:

register listeners
dispatch events
handle priorities

---

# Event Flow

Event flow example:

PlayerAttack

→ CombatEngine
→ DamageCalculation
→ CombatDamageEvent
→ Listeners execute

---

# Core Events

Player Events

PlayerProfileLoadEvent
PlayerLevelUpEvent
PlayerStatChangeEvent

Skill Events

SkillCastEvent
SkillHitEvent
SkillCooldownStartEvent

Combat Events

CombatDamageEvent
CombatCriticalEvent
CombatKillEvent

World Events

MobSpawnEvent
DungeonStartEvent
BossSpawnEvent

---

# Event Priority

Supported priorities:

LOW
NORMAL
HIGH
MONITOR

Example usage:

@EventHandler(priority = EventPriority.HIGH)

---

# Event Cancellation

Certain events are cancellable.

Example:

SkillCastEvent

If cancelled:

skill execution stops.

---

# Custom Events

Plugins may create their own events.

Example:

GemUpgradeEvent

Custom events must extend:

EngineEvent

---

# Async Events

Heavy operations should use async events.

Example:

DatabaseSaveEvent

This prevents server lag.

---

# Event Debugging

Engine may log events in debug mode.

config:

debugEvents: true
 
