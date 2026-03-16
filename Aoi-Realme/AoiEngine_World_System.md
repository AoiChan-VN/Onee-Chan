# AOIENGINE WORLD SYSTEM

This document defines the **world scaling and mob system**.

The world system controls:

* mob difficulty
* region scaling
* dungeon scaling
* spawn systems
* world progression

---

# World Architecture

Components:

WorldManager
RegionManager
MobScalingEngine
SpawnManager
DungeonManager

Responsibilities:

WorldManager → global world logic
RegionManager → region configs
MobScalingEngine → mob stat scaling
SpawnManager → mob spawning
DungeonManager → dungeon instances

---

# Region System

World divided into regions.

Example regions:

starter_zone
forest_of_trials
crystal_caverns
dragon_peak

Each region defines difficulty.

Example:

region:

id: starter_zone
levelMin: 1
levelMax: 10
mobMultiplier: 1.0

---

# Mob Scaling

Mob stats scale with region difficulty.

Example formula:

mobHealth = baseHealth × regionMultiplier

mobDamage = baseDamage × regionMultiplier

---

# Player Scaling

Optional dynamic scaling.

Example:

mobLevel = playerLevel ± randomRange

Example:

playerLevel = 20
mobLevel = 18–22

---

# Spawn System

Mob spawn configuration.

Example:

spawn:

mob: goblin
count: 6
respawn: 30
region: forest_of_trials

Properties:

mob
count
respawn
region
spawnRadius

---

# Dungeon System

Dungeons are instanced worlds.

Example dungeon:

dungeon:

id: shadow_catacomb
minPlayers: 1
maxPlayers: 5
levelRequirement: 15

Dungeon contains:

waves
boss
loot tables

---

# Boss Scaling

Boss stats scale with players.

Example:

bossHealth = baseHealth × playerCount

bossDamage = baseDamage × difficultyMultiplier

---

# Loot System

Loot tables per mob.

Example:

loot:

mob: goblin
drops:

```
- item: gold_coin
  chance: 0.8

- item: goblin_dagger
  chance: 0.05
```

---

# World Progression

Regions unlock progressively.

Example order:

starter_zone
forest_of_trials
crystal_caverns
dragon_peak

Each region increases difficulty.

---

# Future Systems

Planned upgrades:

dynamic events
world bosses
territory control
raid instances
procedural dungeons
 
