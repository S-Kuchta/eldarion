package kuchtastefan.character.hero;

import kuchtastefan.ability.Ability;
import kuchtastefan.actions.ActionStatusEffect;
import kuchtastefan.actions.actionsWithDuration.ActionWithDuration;
import kuchtastefan.actions.actionsWithDuration.ActionWithDurationPerformedOnce;
import kuchtastefan.character.CharacterClass;
import kuchtastefan.character.GameCharacter;
import kuchtastefan.character.hero.inventory.HeroInventoryManager;
import kuchtastefan.character.hero.save.SaveGameEntities;
import kuchtastefan.character.npc.vendor.VendorCharacterDB;
import kuchtastefan.character.spell.Spell;
import kuchtastefan.character.spell.SpellDB;
import kuchtastefan.constant.Constant;
import kuchtastefan.item.ItemDB;
import kuchtastefan.item.specificItems.wearableItem.WearableItem;
import kuchtastefan.item.specificItems.wearableItem.WearableItemQuality;
import kuchtastefan.item.specificItems.wearableItem.WearableItemType;
import kuchtastefan.service.ExperiencePointsService;
import kuchtastefan.utility.ConsoleColor;
import kuchtastefan.utility.printUtil.PrintUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Hero extends GameCharacter {

    private CharacterClass characterClass;
    private int unspentAbilityPoints;
    private double heroGold;
    private double experiencePoints;
    private Map<WearableItemType, WearableItem> equippedItem;
    private final Map<Ability, Integer> wearingItemAbilityPoints;
    private final HeroInventoryManager heroInventoryManager;
    private final ExperiencePointsService experiencePointsService;
    private final SaveGameEntities saveGameEntities;
    private final Map<Integer, Spell> learnedSpells;
    private final EnemyKilled enemyKilled;
    private boolean inCombat;


    public Hero(String name) {
        super(name, new HashMap<>());
        this.enemyKilled = new EnemyKilled();
        this.baseAbilities = this.getInitialAbilityPoints();
        this.effectiveAbilities = this.getInitialAbilityPoints();
        this.unspentAbilityPoints = Constant.INITIAL_ABILITY_POINTS;
        this.wearingItemAbilityPoints = getItemsInitialAbilityPoints();
        this.equippedItem = initialEquip();
        this.experiencePointsService = new ExperiencePointsService();
        this.heroGold = Constant.INITIAL_HERO_GOLD;
        this.experiencePoints = Constant.INITIAL_EXPERIENCE_POINT;
        this.saveGameEntities = new SaveGameEntities();
        this.learnedSpells = new HashMap<>();
        this.inCombat = false;
        this.heroInventoryManager = new HeroInventoryManager(this);
    }

    public void equipItem(WearableItem wearableItem) {
        if (this.level < wearableItem.getItemLevel()) {
            System.out.println("\tYou don't meet minimal level requirement to wear this item!");
        } else {
            System.out.println("\tYou equipped " + wearableItem.getName());
            this.equippedItem.put(wearableItem.getItemType(), wearableItem);
        }

        updateWearingItemAbilityPoints();
    }

    /**
     * If dismantled or sold item is wearing by hero, item will be wear down and ability stats will be updated
     *
     * @param wearableItem item checked for wear down
     */
    public void unEquipItem(WearableItem wearableItem) {
        if (this.equippedItem.get(wearableItem.getItemType()).equals(wearableItem)) {
            this.equippedItem.putAll(returnNoItemToEquippedMap(wearableItem.getItemType()));
            System.out.println("\t" + wearableItem.getName() + " has been unequipped");
        }

        updateWearingItemAbilityPoints();
    }

    public boolean isItemEquipped(WearableItem wearableItem) {
        return this.getEquippedItem().get(wearableItem.getItemType()).equals(wearableItem);
    }

    /**
     * call this method when you want to update ability points of wearable items depending on current wearing armor
     */
    public void updateWearingItemAbilityPoints() {
        for (Ability ability : Ability.values()) {
            this.wearingItemAbilityPoints.put(ability, 0);
        }

        for (Map.Entry<WearableItemType, WearableItem> wearingItem : this.equippedItem.entrySet()) {
            for (Ability ability : Ability.values()) {
                this.wearingItemAbilityPoints.put(ability,
                        wearingItem.getValue().getAbilities().get(ability) + this.wearingItemAbilityPoints.get(ability));
            }
        }

        this.setHeroEnhancedAbilities();
        this.resetAbilitiesToMaxValues();
    }

    /**
     * Method is responsible for setHeroMax abilities depending on abilities + wearing item abilities
     * Call this method whenever you change your equip (best in updateWearingAbilityPoints),
     * or update basic ability points
     */
    private void setHeroEnhancedAbilities() {
        for (Ability ability : Ability.values()) {
            this.enhancedAbilities.put(ability, this.baseAbilities.get(ability) + this.wearingItemAbilityPoints.get(ability));
        }

        bonusFromAbility();
    }

    private void bonusFromAbility() {
        this.enhancedAbilities.put(Ability.MANA, getEnhancedAbilities().get(Ability.MANA) + getEnhancedAbilities().get(Ability.INTELLECT) * Constant.MANA_PER_POINT_OF_INTELLECT);
        this.enhancedAbilities.put(Ability.HEALTH, getEnhancedAbilities().get(Ability.HEALTH) + getEnhancedAbilities().get(Ability.STRENGTH) * Constant.HEALTH_PER_POINT_OF_STRENGTH);
        this.enhancedAbilities.put(Ability.CRITICAL_HIT_CHANCE, (int) (getEnhancedAbilities().get(Ability.CRITICAL_HIT_CHANCE) + (getEnhancedAbilities().get(Ability.ATTACK) * Constant.CRITICAL_PER_ATTACK)));
    }

    public void resetAbilitiesToMaxValues() {
        for (Ability ability : Ability.values()) {
            if (ability.equals(Ability.HEALTH) || ability.equals(Ability.MANA)) {
                this.effectiveAbilities.put(Ability.HEALTH, getEffectiveAbilityValue(Ability.HEALTH));
                this.effectiveAbilities.put(Ability.MANA, getEffectiveAbilityValue(Ability.MANA));
            } else {
                this.effectiveAbilities.put(ability, this.enhancedAbilities.get(ability));
            }
        }
    }

    /**
     * Set initial equip to No Item.
     * It also can be used when you want to set all WearableItemType to No Item.
     *
     * @return Map which contains NoItem for each WearableItemType.
     */
    private Map<WearableItemType, WearableItem> initialEquip() {
        Map<WearableItemType, WearableItem> itemMap = new HashMap<>();
        for (WearableItemType wearableItemType : WearableItemType.values()) {
            itemMap.putAll(returnNoItemToEquippedMap(wearableItemType));
        }
        return itemMap;
    }

    private Map<WearableItemType, WearableItem> returnNoItemToEquippedMap(WearableItemType wearableItemType) {
        return new HashMap<>(Map.of(wearableItemType, new WearableItem(null, "No item", 0, 0,
                wearableItemType, getItemsInitialAbilityPoints(), WearableItemQuality.BASIC)));
    }

    public void setNewAbilityPoint(Ability ability, int pointsToChange, int heroAvailablePointsChange) {
        int minimumPoints = 1;
        if (ability.equals(Ability.HEALTH) || ability.equals(Ability.MANA)) {
            minimumPoints = 50;
        }

        if ((this.baseAbilities.get(ability) + pointsToChange) < minimumPoints) {
            System.out.println("You don't have enough points!");
        } else {
            if ((this.baseAbilities.get(ability) + pointsToChange) < this.baseAbilities.get(ability)) {
                System.out.println("You have removed 1 point from " + ability.name());
            }

            if (ability.equals(Ability.HEALTH)) {
                this.baseAbilities.put(ability, this.baseAbilities.get(ability) + pointsToChange * Constant.HEALTH_OF_ONE_POINT);
            } else if (ability.equals(Ability.MANA)) {
                this.baseAbilities.put(ability, this.baseAbilities.get(ability) + pointsToChange * Constant.MANA_OF_ONE_POINT);
            } else {
                this.baseAbilities.put(ability, this.baseAbilities.get(ability) + pointsToChange);
            }

            this.setHeroEnhancedAbilities();
            this.resetAbilitiesToMaxValues();
            this.updateAbilityPoints(heroAvailablePointsChange);
        }
    }

    public void updateAbilityPoints(int numberOfPoints) {
        this.unspentAbilityPoints += numberOfPoints;
    }

    /**
     * Method takes care of hero experience points. If you have enough experience points, you gain a new level
     * and experience points will be set to new value. After each new level you will gain 2 ability points
     *
     * @param experiencePointsGained from kill, quest, discover location,
     */
    public void gainExperiencePoints(double experiencePointsGained) {

        if (this.level < Constant.MAX_LEVEL) {
            this.experiencePointsService.setNeededExperiencePointsForNewLevel(this.level);
            this.experiencePoints += experiencePointsGained;

            if (experiencePointsService.gainedNewLevel(this.experiencePoints)) {
                this.level++;
                this.updateAbilityPoints(Constant.INCREASE_ABILITY_POINTS_AFTER_LEVEL_UP);

                PrintUtil.printDivider();
                System.out.println("\tYou reached a new level! Your level is " + this.level + "!");
                PrintUtil.printDivider();

                this.experiencePoints -= this.experiencePointsService.getNeededExperiencePointsForNewLevel();
                this.experiencePointsService.setNeededExperiencePointsForNewLevel(this.level);
                VendorCharacterDB.setRandomCurrentVendorCharacterItemListId(this.level);
            }

            if (experiencePointsGained > 0) {
                System.out.println("\tYou gained " + ConsoleColor.YELLOW + (int) experiencePointsGained
                        + " Experience points" + ConsoleColor.RESET);
            }
        }
    }

    public void checkHeroGoldsAndSubtractIfHaveEnough(double goldNeeded) {
        if (this.heroGold >= goldNeeded) {
            this.heroGold -= goldNeeded;
        } else {
            this.heroGold = 0;
        }
    }

    public void addGolds(double golds) {
        this.heroGold += golds;
    }

    public void rest() {
        this.getEffectiveAbilities().put(Ability.HEALTH, this.getEnhancedAbilities().get(Ability.HEALTH));
        this.getEffectiveAbilities().put(Ability.MANA, this.getEnhancedAbilities().get(Ability.MANA));
        this.buffsAndDebuffs.removeIf(debuff -> debuff.getActionStatusEffect().equals(ActionStatusEffect.DEBUFF));
    }

    public void setInitialEquip() {
        this.heroInventoryManager.addItem(ItemDB.returnItemFromDB(200), 1);
        this.heroInventoryManager.addItem(ItemDB.returnItemFromDB(500), 1);
        this.heroInventoryManager.addItem(ItemDB.returnItemFromDB(600), 1);

        this.heroInventoryManager.addItem(ItemDB.returnItemFromDB(1001), 2);
        this.heroInventoryManager.addItem(ItemDB.returnItemFromDB(1002), 2);
        this.heroInventoryManager.addItem(ItemDB.returnItemFromDB(900), 2);
        this.heroInventoryManager.addItem(ItemDB.returnItemFromDB(902), 1);

        this.equipItem((WearableItem) ItemDB.returnItemFromDB(200));
        this.equipItem((WearableItem) ItemDB.returnItemFromDB(500));
        this.equipItem((WearableItem) ItemDB.returnItemFromDB(600));
    }

    @Override
    public boolean checkIfCharacterDies() {
        if (super.checkIfCharacterDies()) {
            int goldToRemove = Constant.GOLD_TO_REMOVE_PER_LEVEL_AFTER_DEAD * this.getLevel();
            this.checkHeroGoldsAndSubtractIfHaveEnough(goldToRemove);
            this.getEffectiveAbilities().put(Ability.HEALTH, this.getEnhancedAbilities().get(Ability.HEALTH));
            this.getEffectiveAbilities().put(Ability.MANA, this.getEnhancedAbilities().get(Ability.MANA));
            for (ActionWithDuration actionWithDuration : this.buffsAndDebuffs) {
                actionWithDuration.setCurrentActionTurn(100);
            }
            this.removeActionWithDuration();

            System.out.println("\n\t" + ConsoleColor.RED + "You have died!" + ConsoleColor.RESET);
            System.out.println("\tYou lost " + goldToRemove + " golds!");
            return true;
        }

        return false;
    }

    public void spellInit() {
        for (Spell spell : SpellDB.SPELL_LIST) {
            if (spell.getSpellLevel() == 0 && spell.getSpellClass().equals(this.getCharacterClass())) {
                this.getCharacterSpellList().add(spell);
            }
        }
    }

    private Map<Ability, Integer> getInitialAbilityPoints() {
        return new HashMap<>(Map.of(
                Ability.ATTACK, 1,
                Ability.RESIST_DAMAGE, 1,
                Ability.STRENGTH, 1,
                Ability.INTELLECT, 1,
                Ability.SPIRIT, 1,
                Ability.HASTE, 1,
                Ability.CRITICAL_HIT_CHANCE, 1,
                Ability.HEALTH, 50,
                Ability.MANA, 60,
                Ability.ABSORB_DAMAGE, 0
        ));
    }

    private Map<Ability, Integer> getItemsInitialAbilityPoints() {
        return new HashMap<>(Map.of(
                Ability.ATTACK, 0,
                Ability.RESIST_DAMAGE, 0,
                Ability.STRENGTH, 0,
                Ability.INTELLECT, 0,
                Ability.SPIRIT, 0,
                Ability.HASTE, 0,
                Ability.CRITICAL_HIT_CHANCE, 0,
                Ability.HEALTH, 0,
                Ability.MANA, 0,
                Ability.ABSORB_DAMAGE, 0
        ));
    }
}
