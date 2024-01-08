package kuchtastefan.domain;

import com.google.gson.annotations.Expose;
import kuchtastefan.ability.Ability;
import kuchtastefan.constant.Constant;
import kuchtastefan.inventory.ItemInventoryList;
import kuchtastefan.item.Item;
import kuchtastefan.item.wearableItem.WearableItem;
import kuchtastefan.item.wearableItem.WearableItemQuality;
import kuchtastefan.item.wearableItem.WearableItemType;
import kuchtastefan.utility.PrintUtil;

import java.util.HashMap;
import java.util.Map;

public class Hero extends GameCharacter {

    private int unspentAbilityPoints;
    private Map<WearableItemType, WearableItem> equippedItem;
    private final Map<Ability, Integer> wearingItemAbilityPoints;
    private ItemInventoryList itemInventoryList;
    private double heroGold;

    public Hero(String name) {
        super(name, new HashMap<>());
        this.abilities = this.getInitialAbilityPoints();
        this.unspentAbilityPoints = Constant.INITIAL_ABILITY_POINTS;
        this.wearingItemAbilityPoints = getItemsInitialAbilityPoints();
        this.equippedItem = initialEquip();
        this.itemInventoryList = new ItemInventoryList();
        this.heroGold = Constant.INITIAL_HERO_GOLD;
    }

    public void testPrint() {
        for (Map.Entry<Item, Integer> itemMap : itemInventoryList.getHeroInventory().entrySet()) {
            System.out.println(itemMap.getKey().getName() + ", " + itemMap.getValue());
            System.out.println(itemMap.getKey().getClass());
        }
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
            itemMap.put(wearableItemType, new WearableItem("No item", 0, 0, wearableItemType, getItemsInitialAbilityPoints(), WearableItemQuality.BASIC));
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

    public ItemInventoryList getItemInventoryList() {
        return itemInventoryList;
    }

    public void setItemInventoryList(ItemInventoryList itemInventoryList) {
        this.itemInventoryList = itemInventoryList;
    }
}
