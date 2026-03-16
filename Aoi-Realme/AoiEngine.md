# AOIENGINE SPEC PRO

Premium Minecraft MMORPG Engine Specification

## Project Overview

Engine Name: **AoiEngine**
Type: **Modular MMORPG Engine**
Server: **Paper 1.21.1+**
Language: **Java 21**

AoiEngine is the **core engine** responsible for powering a modular MMORPG ecosystem.

Supported plugins:

* AoiStats
* AoiClass
* AoiSkill
* AoiGem
* AoiCultivation
* AoiWorld

The engine provides:

* Player data system
* Stat engine
* Combat pipeline
* Skill runtime
* Module loader
* Service registry
* Event bus
* Async task system
* Database abstraction
* Plugin API

---

## Development Rules

1. Always send **FULL FILES**
2. Always include **folder paths**
3. Include **detailed comments**
4. Never send skeleton code
5. Verify compilation
6. Report any removed or modified files

Example file path format:

src/main/java/aoi/aoichan/core/EngineBootstrap.java

---

## Base Package

aoi.aoichan

---

## Engine Architecture

Subsystems:

Core
Module System
Service Registry
Player System
Data Layer
Event System
Scheduler System
Stat Engine
Combat Engine
Skill Engine
Hook System
Config System

---

## Engine Package Structure

aoi.aoichan
bootstrap
core
module
service
api
player
data
event
scheduler
stat
combat
skill
hook
command
config
util

This structure is **locked and must not change**.

---

## Plugin Ecosystem

plugins/

AoiEngine.jar
AoiStats.jar
AoiClass.jar
AoiSkill.jar
AoiGem.jar
AoiCultivation.jar
AoiWorld.jar

---

## Code Scale Expectations

Classes: 120–200
Packages: ~15
Subsystems: 12
Lines of code: 20k+

Comparable to premium Spigot plugins.

---

## Development Starting Point

Initial core classes:

EngineBootstrap
EngineLifecycle
ServiceRegistry
ModuleManager
EngineThreadPool
EngineAPI
 
