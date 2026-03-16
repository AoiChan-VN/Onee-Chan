# AOIENGINE DATABASE SCHEMA

Database systems supported:

MySQL
MongoDB
Redis Cache

---

## Players Table

players

uuid
username
level
exp
class
cultivation
mana
stamina

---

## Player Stats

player_stats

uuid
strength
agility
vitality
intelligence
critChance
critDamage
attackPower
defense

---

## Player Skills

player_skills

uuid
skill_id
level
cooldown

---

## Player Gems

player_gems

uuid
slot
gem_type
gem_level

---

## Redis Cache

Cached data:

player_profile
combat_state
cooldowns
online_players

Used to reduce database load.
 
