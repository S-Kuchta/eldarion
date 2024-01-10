package kuchtastefan.characters.hero;

import kuchtastefan.ability.Ability;
import kuchtastefan.characters.GameCharacter;
import kuchtastefan.characters.hero.inventory.HeroInventory;
import kuchtastefan.constant.Constant;
import kuchtastefan.items.wearableItem.WearableItem;
import kuchtastefan.items.wearableItem.WearableItemQuality;
import kuchtastefan.items.wearableItem.WearableItemType;
import kuchtastefan.service.ExperiencePointsService;
import kuchtastefan.utility.PrintUtil;

import java.util.HashMap;
import java.util.Map;

public class Hero extends GameCharacter {

    private int unspentAbilityPoints;
    private Map<WearableItemType, WearableItem> equippedItem;
    private final Map<Ability, Integer> wearingItemAbilityPoints;
    private final HeroInventory heroInventory;
    private double heroGold;
    private double experiencePoints;
    private final ExperiencePointsService experiencePointsService;


    public Hero(String name) {
        super(name, new HashMap<>());
        this.abilities = this.getInitialAbilityPoints();
        this.unspentAbilityPoints = Constant.INITIAL_ABILITY_POINTS;
        this.wearingItemAbilityPoints = getItemsInitialAbilityPoints();
        this.equippedItem = initialEquip();
        this.heroInventory = new HeroInventory();
        this.heroGold = Constant.INITIAL_HERO_GOLD;
        this.experiencePoints = Constant.INITIAL_EXPERIENCE_POINT;
        this.experiencePointsService = new ExperiencePointsService();
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

    public void unEquipItem(WearableItem wearableItem) {
        if (this.heroInventory.getHeroInventory().get(wearableItem) < 2) {
            this.equippedItem.putAll(returnNoItemToEquippedMap(wearableItem.getWearableItemType()));
        }

        updateWearingItemAbilityPoints();
    }

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
    }

    public void wearDownAllEquippedItems() {
        this.equippedItem = initialEquip();
        for (Ability ability : Ability.values()) {
            this.wearingItemAbilityPoints.put(ability, 0);
        }
    }

    private Map<WearableItemType, WearableItem> initialEquip() {
        Map<WearableItemType, WearableItem> itemMap = new HashMap<>();
        for (WearableItemType wearableItemType : WearableItemType.values()) {
            itemMap.putAll(returnNoItemToEquippedMap(wearableItemType));
        }
        return itemMap;
    }

    private Map<Ability, Integer> getInitialAbilityPoints() {
        return new HashMap<>(Map.of(
                Ability.ATTACK, 1,
                Ability.DEFENCE, 1,
                Ability.DEXTERITY, 1,
                Ability.SKILL, 1,
                Ability.LUCK, 1,
                Ability.HEALTH, 50
        ));
    }

    private Map<Ability, Integer> getItemsInitialAbilityPoints() {
        return new HashMap<>(Map.of(
                Ability.ATTACK, 0,
                Ability.DEFENCE, 0,
                Ability.DEXTERITY, 0,
                Ability.SKILL, 0,
                Ability.LUCK, 0,
                Ability.HEALTH, 0
        ));
    }

    private Map<WearableItemType, WearableItem> returnNoItemToEquippedMap(WearableItemType wearableItemType) {
        return new HashMap<>(Map.of(wearableItemType, new WearableItem("No item", 0, 0, wearableItemType, getItemsInitialAbilityPoints(), WearableItemQuality.BASIC)));
    }

    public void setNewAbilityPoint(Ability ability, int pointsToChange, int heroAvailablePointsChange) {
        int minimumPoints = 1;
        if (ability.equals(Ability.HEALTH)) {
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
            } else {
                this.abilities.put(ability, this.abilities.get(ability) + pointsToChange);
            }

            updateAbilityPoints(heroAvailablePointsChange);
        }
    }

    public void gainExperiencePoints(double experiencePointsGained) {
        this.experiencePointsService.setNeededExperiencePointsForNewLevel(this.level);
        this.experiencePoints = this.experiencePoints + experiencePointsGained;

        if (this.experiencePointsService.gainedNewLevel(this.experiencePoints)) {
            this.level++;
            this.updateAbilityPoints(2);

            PrintUtil.printDivider();
            System.out.println("\tYou reach a new level! Your level is " + this.level + "!");
            PrintUtil.printDivider();

            this.experiencePoints = this.experiencePoints - experiencePointsService.getNeededExperiencePointsForNewLevel();
            this.experiencePointsService.setNeededExperiencePointsForNewLevel(this.level);
        }

        PrintUtil.printLongDivider();
        System.out.println("\t\tYou gained " + (int) experiencePointsGained
                + "xp\t\t\t\tExperience points: " + (int) this.experiencePoints + "xp / "
                + (int) this.experiencePointsService.getNeededExperiencePointsForNewLevel() + "xp");
        PrintUtil.printLongDivider();
    }

    public boolean checkHeroGoldsAndRemoveIfTrue(double goldNeeded) {
        if (this.heroGold >= goldNeeded) {
            this.heroGold -= goldNeeded;
            return true;
        }

        System.out.println("\tYou don't have enough golds");
        return false;
    }

    public void addGolds(double golds) {
        this.heroGold += golds;
    }

    public void updateAbilityPoints(int numberOfPoints) {
        this.unspentAbilityPoints += numberOfPoints;
    }

    public void setAbility(Ability ability, int heroHealthBeforeBattle) {
        this.abilities.put(ability, heroHealthBeforeBattle);
    }

    public int getItemAbilityValue(Ability ability) {
        return this.wearingItemAbilityPoints.get(ability);
    }

    public int getUnspentAbilityPoints() {
        return unspentAbilityPoints;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<WearableItemType, WearableItem> getEquippedItem() {
        return equippedItem;
    }

    public Map<Ability, Integer> getWearingItemAbilityPoints() {
        return wearingItemAbilityPoints;
    }

    public double getHeroGold() {
        return heroGold;
    }

    public void setHeroGold(double heroGold) {
        this.heroGold = heroGold;
    }

    public HeroInventory getHeroInventory() {
        return heroInventory;
    }

}
