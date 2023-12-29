package kuchtastefan.domain;

import kuchtastefan.ability.Ability;
import kuchtastefan.constant.Constant;
import kuchtastefan.item.Item;
import kuchtastefan.item.ItemType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hero extends GameCharacter {
    private int unspentAbilityPoints;
    private Map<ItemType, Item> equippedItem;
    private final Map<Ability, Integer> wearingItemAbilityPoints;
    private final List<Item> heroInventory;
    private int heroGold;

    public Hero(String name) {
        super(name, new HashMap<>());
        this.abilities = this.getInitialAbilityPoints();
        this.unspentAbilityPoints = Constant.INITIAL_ABILITY_POINTS;
        this.wearingItemAbilityPoints = getItemsInitialAbilityPoints();
        this.equippedItem = initialEquip();
        this.heroInventory = new ArrayList<>();
        this.heroGold = Constant.INITIAL_HERO_GOLD;
    }

    public void equipItem(Item item) {
        if (this.level < item.getItemLevel()) {
            System.out.println("You don't meet minimal level requirement to wear this item!");
        } else {
            System.out.println("You equipped " + item.getName());
            this.equippedItem.put(item.getType(), item);
        }
        setWearingItemAbilityPoints();
    }

    public void setWearingItemAbilityPoints() {
        for (Ability ability : Ability.values()) {
            this.wearingItemAbilityPoints.put(ability, 0);
        }

        for (Map.Entry<ItemType, Item> wearingItem : this.equippedItem.entrySet()) {
            for (Ability ability : Ability.values()) {
                this.wearingItemAbilityPoints.put(
                        ability,
                        wearingItem.getValue().getAbilities().get(ability)
                                + this.wearingItemAbilityPoints.get(ability));
            }
        }
    }

//    public void wearDownAllItems() {
//        this.equippedItem = wearDownAllEquippedItems();
//        for (Ability ability : Ability.values()) {
//            this.wearingItemAbilityPoints.put(ability, 0);
//        }
//    }

    public void wearDownAllEquippedItems() {
        this.equippedItem = initialEquip();

        for (Ability ability : Ability.values()) {
            this.wearingItemAbilityPoints.put(ability, 0);
        }
    }

    private Map<ItemType, Item> initialEquip() {
        Map<ItemType, Item> itemMap = new HashMap<>();
        for (ItemType itemType : ItemType.values()) {
            itemMap.put(itemType, new Item("No item", itemType, getItemsInitialAbilityPoints(), 0));
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

    public void setNewAbilitiesPoints(Ability ability, int pointsToChange, int heroAvailablePointsChange) {
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

    public Map<ItemType, Item> getEquippedItem() {
        return equippedItem;
    }

    public List<Item> getHeroInventory() {
        return heroInventory;
    }

    public Map<Ability, Integer> getWearingItemAbilityPoints() {
        return wearingItemAbilityPoints;
    }

    public int getHeroGold() {
        return heroGold;
    }

    public void setHeroGold(int heroGold) {
        this.heroGold = heroGold;
    }
}
