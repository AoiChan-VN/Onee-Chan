# AOIENGINE API SPECIFICATION

This document defines the public API used by other plugins.

Base namespace:

aoi.aoichan.api

---

## EngineAPI

Main entry point.

Example:

AoiEngine.getAPI()

Responsibilities:

* Access services
* Access player data
* Access stats
* Access skills
* Access combat engine

---

## PlayerAPI

Provides player access.

Methods:

getProfile(Player)

getLevel()

addExperience()

setClass()

---

## StatAPI

Handles player stats.

Methods:

getStat(player, statType)

addStat(player, statType, value)

calculateFinalStats(player)

Supported stat types:

strength
agility
vitality
intelligence
critChance
critDamage
attackPower
defense

---

## SkillAPI

Handles skill execution.

Methods:

castSkill(player, skillId)

getCooldown(player, skillId)

setCooldown(player, skillId)

---

## CombatAPI

Handles combat calculations.

Methods:

calculateDamage(attacker, target)

applyDamage(target, damage)

triggerCombatEvent()

---

## ServiceAPI

Provides service registry access.

Example:

StatService statService = EngineAPI.services().get(StatService.class);

Allows other plugins to register services.
 
