# AOIENGINE PLUGIN DEVELOPER GUIDE

This document explains how developers create plugins compatible with AoiEngine.

Plugins must follow this guide to avoid breaking the engine architecture.

---

# Plugin Base Requirements

Every plugin must depend on:

AoiEngine

Example plugin.yml

name: AoiStats
main: aoi.stats.AoiStatsPlugin
version: 1.0
depend:

* AoiEngine

---

# Accessing Engine API

Plugins must access the engine through EngineAPI.

Example:

EngineAPI api = AoiEngine.getAPI();

This provides access to:

players
stats
skills
combat
services

Plugins must not directly access internal engine classes.

---

# Accessing Player Data

Player data must always be accessed through PlayerAPI.

Example:

PlayerProfile profile = api.players().getProfile(player);

Plugins must never store player stats independently.

All persistent data must go through the engine DataManager.

---

# Registering Services

Plugins can register services in the ServiceRegistry.

Example:

api.services().register(StatService.class, new StatServiceImpl());

Other plugins may retrieve the service:

StatService service = api.services().get(StatService.class);

---

# Using Combat Engine

Plugins must use the combat engine for damage.

Incorrect example:

target.damage(20)

Correct example:

DamageEngine.calculateDamage(attacker, target)

This ensures:

crit calculation
defense calculation
combat events

---

# Skill Integration

Plugins creating skills must register them with SkillManager.

Example:

SkillManager.registerSkill(new FireballSkill());

---

# Event Listening

Plugins can listen to engine events.

Example:

@EventHandler
public void onSkillCast(SkillCastEvent event)

---

# Plugin Lifecycle

Recommended structure:

onEnable()

register services
register commands
register event listeners

onDisable()

cleanup tasks
unregister services

---

# Performance Rules

Plugins must:

avoid heavy sync tasks
use async scheduler when possible
avoid large loops per tick

This ensures TPS stability.
 
