package kuchtastefan.characters.hero;

import kuchtastefan.ability.Ability;
import kuchtastefan.characters.GameCharacter;
import kuchtastefan.characters.hero.inventory.HeroInventory;
import kuchtastefan.characters.spell.Spell;
import kuchtastefan.constant.Constant;
import kuchtastefan.items.Item;
import kuchtastefan.items.ItemsLists;
import kuchtastefan.items.wearableItem.WearableItem;
import kuchtastefan.items.wearableItem.WearableItemQuality;
import kuchtastefan.items.wearableItem.WearableItemType;
import kuchtastefan.quest.Quest;
import kuchtastefan.quest.questObjectives.QuestBringItemObjective;
import kuchtastefan.quest.questObjectives.QuestKillObjective;
import kuchtastefan.quest.questObjectives.QuestObjective;
import kuchtastefan.regions.locations.Location;
import kuchtastefan.service.ExperiencePointsService;
import kuchtastefan.utility.PrintUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private final HeroInventory heroInventory;
    private final ExperiencePointsService experiencePointsService;
    private final Map<Integer, Quest> listOfAcceptedQuests;
    private final Map<Integer, Spell> learnedSpells;
    private final Map<Integer, Location> discoveredLocationList;


    public Hero(String name) {
        super(name, new HashMap<>());
        this.abilities = this.getInitialAbilityPoints();
        this.currentAbilities = this.getInitialAbilityPoints();
        this.unspentAbilityPoints = Constant.INITIAL_ABILITY_POINTS;
        this.wearingItemAbilityPoints = getItemsInitialAbilityPoints();
        this.equippedItem = initialEquip();
        this.heroInventory = new HeroInventory();
        this.heroGold = Constant.INITIAL_HERO_GOLD;
        this.experiencePoints = Constant.INITIAL_EXPERIENCE_POINT;
        this.experiencePointsService = new ExperiencePointsService();
        this.listOfAcceptedQuests = new HashMap<>();
        this.learnedSpells = new HashMap<>();
        this.discoveredLocationList = new HashMap<>();
    }

    public void equipItem(WearableItem wearableItem) {
        if (this.level < wearableItem.getItemLevel()) {
            PrintUtil.printLongDivider();
            System.out.println("\tYou don't meet minimal level requirement to wear this item!");
            PrintUtil.printLongDivider();
        } else {
            PrintUtil.printDivider();
            System.out.println("\tYou equipped " + wearableItem.getName());
            PrintUtil.printDivider();
            this.equippedItem.put(wearableItem.getWearableItemType(), wearableItem);
        }
        updateWearingItemAbilityPoints();
    }

    /**
     * If dismantled or sold item is wearing by hero, item will be wear down and ability stats will be updated
     *
     * @param wearableItem item checked for wear down
     */
    public void unEquipItem(WearableItem wearableItem) {
        if (this.equippedItem.get(wearableItem.getWearableItemType()).equals(wearableItem)) {
            this.equippedItem.putAll(returnNoItemToEquippedMap(wearableItem.getWearableItemType()));
        }

        updateWearingItemAbilityPoints();
    }

    /**
     * Set all items to NoItem and set all wearingItemAbilityPoints to 0 for each ability.
     */
    public void wearDownAllEquippedItems() {
        this.equippedItem = initialEquip();
        this.updateWearingItemAbilityPoints();
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
                this.wearingItemAbilityPoints.put(
                        ability,
                        wearingItem.getValue().getAbilities().get(ability)
                                + this.wearingItemAbilityPoints.get(ability));
            }
        }

        this.setHeroMaxAbilities();
        this.updateCurrentCharacterStateDependsOnActiveActionsAndIncreaseTurn(null);
    }

    /**
     * Method is responsible for setHeroMax abilities depending on abilities + wearing item abilities
     * Call this method whenever you change your equip (best in updateWearingAbilityPoints),
     * or update basic ability points
     */
    private void setHeroMaxAbilities() {
        for (Ability ability : Ability.values()) {
            this.maxAbilities.put(ability, this.abilities.get(ability)
                    + this.wearingItemAbilityPoints.get(ability));
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

        if ((this.abilities.get(ability) + pointsToChange) < minimumPoints) {
            System.out.println("You don't have enough points!");
        } else {
            if ((this.abilities.get(ability) + pointsToChange) < this.abilities.get(ability)) {
                System.out.println("You have removed 1 point from " + ability.name());
            }
            if (ability.equals(Ability.HEALTH)) {
                this.abilities.put(ability, this.abilities.get(ability) + pointsToChange * Constant.HEALTH_OF_ONE_POINT);
            } else if (ability.equals(Ability.MANA)) {
                this.abilities.put(ability, this.abilities.get(ability) + pointsToChange * Constant.MANA_OF_ONE_POINT);
            } else {
                this.abilities.put(ability, this.abilities.get(ability) + pointsToChange);
            }

            this.setHeroMaxAbilities();
            this.resetCurrentAbilitiesToMaxAbilities(true);
            this.updateCurrentCharacterStateDependsOnActiveActionsAndIncreaseTurn(null);
            updateAbilityPoints(heroAvailablePointsChange);
        }
    }

    public void updateAbilityPoints(int numberOfPoints) {
        this.unspentAbilityPoints += numberOfPoints;
    }

    public void setAbilityValue(Ability ability, int abilityValueToSet) {
        this.abilities.put(ability, abilityValueToSet);
    }

    /**
     * Method takes care of hero experience points. If you have enough experience points, you gain a new level
     * and experience points will be set to new value. After each new level you will gain 2 ability points
     *
     * @param experiencePointsGained from kill, quest, discover location,
     */
    public void gainExperiencePoints(double experiencePointsGained) {
        this.experiencePointsService.setNeededExperiencePointsForNewLevel(this.level);
        this.experiencePoints += experiencePointsGained;

        if (this.experiencePointsService.gainedNewLevel(this.experiencePoints)) {
            this.level++;
            this.updateAbilityPoints(2);

            PrintUtil.printDivider();
            System.out.println("\tYou reached a new level! Your level is " + this.level + "!");
            PrintUtil.printDivider();

            this.experiencePoints -= experiencePointsService.getNeededExperiencePointsForNewLevel();
            this.experiencePointsService.setNeededExperiencePointsForNewLevel(this.level);
        }

        PrintUtil.printLongDivider();
        System.out.println("\t\tYou gained " + (int) experiencePointsGained
                + "xp\t\t\t\tExperience points: " + (int) this.experiencePoints + "xp / "
                + (int) this.experiencePointsService.getNeededExperiencePointsForNewLevel() + "xp");
        PrintUtil.printLongDivider();
    }

    /**
     * check if enemy killed in CombatEvent belongs to some of accepted Quest.
     * If yes increase current count progress in questObjective
     * and print QuestObjectiveAssignment with QuestObjective progress.
     * If you need to use this method, Use it always before checkIfQuestObjectivesAndQuestIsCompleted() method.
     */
    public void checkQuestProgress(Integer questEnemyId) {

//        for (Quest quest : this.listOfAcceptedQuests) {
        for (Map.Entry<Integer, Quest> questMap : this.listOfAcceptedQuests.entrySet()) {
            for (QuestObjective questObjective : questMap.getValue().getQuestObjectives()) {
                if (!questObjective.isCompleted()) {
                    if (questObjective instanceof QuestKillObjective
                            && ((QuestKillObjective) questObjective).getQuestEnemyId().equals(questEnemyId)) {

                        ((QuestKillObjective) questObjective).increaseCurrentCountEnemyProgress();
                        questObjective.printQuestObjectiveAssignment(this);
                    }

                    if (questObjective instanceof QuestBringItemObjective
                            && ((QuestBringItemObjective) questObjective).checkEnemy(questEnemyId)) {

                        Item questItem = ItemsLists.getItemMapIdItem().get(
                                ((QuestBringItemObjective) questObjective).getObjectiveItemId());
                        System.out.println("\t-- You loot " + (questItem.getName() + " --"));

                        this.heroInventory.addItemWithNewCopyToItemList((questItem));
                        questObjective.printQuestObjectiveAssignment(this);
                    }
                }
            }
        }
    }

    /**
     * Check if quest or quest objective is completed.
     * Add this method at the end of each event which can complete quest or quest objective
     */
    public void checkIfQuestObjectivesAndQuestIsCompleted() {
//        for (Quest quest : this.listOfAcceptedQuests) {
        for (Map.Entry<Integer, Quest> questMap : this.listOfAcceptedQuests.entrySet()) {
            if (!questMap.getValue().isTurnedIn()) {
                for (QuestObjective questObjective : questMap.getValue().getQuestObjectives()) {
                    questObjective.checkIfQuestObjectiveIsCompleted(this);
                }
                questMap.getValue().checkIfQuestIsCompleted();
            }
        }
    }

    public void checkHeroGoldsAndSubtractIfTrue(double goldNeeded) {
        if (this.heroGold >= goldNeeded) {
            this.heroGold -= goldNeeded;
//            return true;
        } else {
            this.heroGold = 0;
//            return false;
        }

//        System.out.println("\tYou don't have enough golds");
//        return false;
    }

    public void addGolds(double golds) {
        this.heroGold += golds;
    }

    private Map<Ability, Integer> getInitialAbilityPoints() {
        return new HashMap<>(Map.of(
                Ability.ATTACK, 1,
                Ability.RESIST_DAMAGE, 1,
                Ability.STRENGTH, 1,
                Ability.INTELLECT, 1,
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
                Ability.HASTE, 0,
                Ability.CRITICAL_HIT_CHANCE, 0,
                Ability.HEALTH, 0,
                Ability.MANA, 0,
                Ability.ABSORB_DAMAGE, 0
        ));
    }
}
