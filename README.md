# Gladiatus
A Street of Code Java Tutorial Assignment.<br>

> [!IMPORTANT]
> ## Actual game status
> - Fixing som bugs, creating new Locations, enemies etc.<br>
> - You need to wait for short time to see game ready to release. But release is almost there!<br>
> - I am doing my maximum :slightly_smiling_face:

# How to Run the Project
1.	Clone the git repository with your IDE - https://github.com/S-Kuchta/gladiatus.git<br>
  or
2.	Download the zip and open it in your IDE.
3.	Run Main.class in src/main/java/.

# How to Play

I recommend to maximize the console window for better playing experience.<br>
When there is a MAGENTA - colored number or letter followed by a dot and text, you can enter this number or letter into the console, and the action will be performed.<br>
To start, enter your name, select a hero class, and set your initial ability points.<br>
You begin the game in the city with 10 free ability points, which you can spend on abilities of your choice.<br>
After this, you embark on your journey in the city. In the city, you can accept quests, visit vendors, buy or sell items, etc.<br>
You can change some of the game settings. 


# Highlights

# How game work
Slightly described every aspect in the game

## Characters
In the game, there are playable characters (Heroes) and non-player characters (NPCs).<br>
Only one playable character is allowed.

#### Hero
- The player character.
- Players can choose from various Hero classes, each with unique spells.
- Hero classes include:
  - Warrior
  - Mage
  - Warlock
  - Shaman
- Once chosen, the class cannot be changed.
- Initially, the hero has 10 unspent ability points.
- Upon leveling up, the hero receives 4 additional ability points.
- Ability points can be allocated randomly.
> [!NOTE]
> Ability points can also be reallocated, so players can change their decisions.

#### Vendor
- Buys and sells items.

#### Enemy
- Enemies are one of the main part of the game.
- Each enemy has different ability points based on their rarity and level.
- Enemy ability points are calculated as a multiplier of the maximum achievable hero ability points at the current level.
- Enemy Rarity:
  - COMMON - Ability point multipliers range from 0.4 to 0.65.
  - RARE - Ability point multipliers range from 0.65 to 0.9.
  - ELITE - Ability point multipliers range from 0.9 to 1.2.
  - BOSS - Ability point multiplier is 1.4.
- Enemies possess their own spells to use in battle.
- Enemies spawn randomly during travel across region or are pre-scripted in specific locations.
- Defeating enemies grants rewards, including gold, items, and experience points.

#### Creature type
- There are six types of creatures, each with its own characteristics. However, currently, there is no functional difference between them.
- Creature types: Beast, Demon, Elemental, Humanoid, Undead, Totem
> [!NOTE]
> Humanoids drop gold upon defeat, unlike other creature types.

#### Leveling
- To achieve a higher level, you need to accumulate a sufficient amount of experience points required for the next level.
- The hero can earn experience points by defeating enemies, completing quests, or discovering new locations.

## Spells and Actions

#### Spell
- Every character in the game has their own set of spells.
- Casting a spell consumes Mana.
- Spells may have a CoolDown, requiring the character to wait for a certain number of rounds before casting the spell again.
- Players can choose which spell to cast, provided they meet the requirements (have enough mana and the spell is not on CoolDown).
- Enemies utilize a simple decision-making model based on priority points.
- Each action has a specific number of priority points, and the spell with the highest accumulated priority points is cast.
- Each spell can consist of one or more Actions.

#### Action
- There are multiple possible actions, such as dealing damage, increasing ability points, absorbing damage, etc.
- Actions are executed after casting a spell or using a consumable item.

> [!NOTE]
> Spells can miss if the defending character has more HASTE Ability points than the attacking character.<br>
> For example: If the defending character has 10 more haste points than the attacking character, there is a 10% chance that the spell will miss.

## World
Region -> Location (Event) -> Location Stage -> Event 

#### Region
- While traveling across regions, you may encounter random events (meeting a merchant caravan, random battles, gathering or mining resources needed for crafting, or discovering locations). 

#### Location
- Locations can be discovered only when their level is equal to or lower than the hero's level. Once discovered, you can enter the location (if you have the key, if needed). 
- In locations, there are location stages.
- Some are visible immediately, while others become visible after clearing the previous stage. Each stage forces an event, and a stage is cleared when the event is successfully completed (returns true). 

#### Location Stage
- There are multiple possible location stages (combat, find item, quest giver, use item, and find treasure), each starting a specific event.

## Quest
- Quest Giver -> Quest -> Quest Objectives -> Quest Reward
- You can obtain quests from quest givers.
- Quests can consist of multiple objectives. To complete a quest, you must fulfill each of its objectives.
- Each quest consists of objectives that must be completed to finish the quest.
> [!NOTE]
> If you forgot to accept the quest with the objective to Clear the Location, and you've already cleared the location beforehand, don't worry; the quest objective will still be completed after Quest is be accepted.

> [!WARNING]
> Be aware:<br>
> If you defeat 2 enemies required for the quest but lose the battle, the enemies will not be counted as completed since you did not finish the event.

## Items
You can encounter various types of items.

#### Wearable items
- increases hero ability points. There are five slots that can be obtained - Body, Boots, Hands, Head and Weapon.
- On each slot you can have only one item.
- During game is possible to change wearable items with no cost. Old item will not be removed.
- Wearable items can have 4 types of quality:
  - Basic - the lowest item quality. Basic items randomly drops from enemy or can be bought from vendors.
  - Improved - to achieve Improved item quality you need to reforge your item with basic quality - for this you need to have specific crafting reagent items
  - Quest Reward - You can get these items for completing the quests
  - Special - Best items in the game. Can be found only in locations.
> [!TIP]
> Wearable items can be dismantled to obtain Blacksmith reagents.<br>
> Items with basic quality can be refined to enhance their quality.

#### Consumeable items
- Consumeable items could improve hero abilities, restore health or mana.
- Can be found in locations, bought from vendors or create them.
- There are two types of consumeable items.
  - Food (drink) - used only out of combat.
  - Potion - can be used during combat.
> [!WARNING]
> Create potions or food is not available yet.<br>

#### Crafting reagent items
- Are used for craft potions, food, wearable items or to improve Basic quality wearable items.
- Can be obtained during travelling across region, bought from vendor or find them in locations.
- There are two types of Crafting reagent items:
  - Alchemy Reagent - Used for craft new potion or food.
  - Blacksmith reagent - Used for refinement wearable item or craft new wearable item.

#### Quest items
- Are used to complete quests.
- Also included keys to unlock certain locations or location stages.

#### Junk items
- Good only for sell and make som extra golds. Can be obtained randomly from enemies.

## Battle
Battle is one of the most crucial aspects of the game.<br>
The course of the battle is calculated in turns, where each turn involves every character executing one attack or consuming one potion.<br>
During battle, you may face multiple enemies. You have the option to choose which enemy to target with your attacks.<br>
The battle ends when there are no enemies remaining, or when the hero is defeated.<br>
In case of defeat, the hero loses a certain amount of gold.

## Hints
During the game, players will receive hint for the current situation.<br>
Each hint will be shown only once.<br>
Hints can be reset.

## Constraints

## What's next? 
