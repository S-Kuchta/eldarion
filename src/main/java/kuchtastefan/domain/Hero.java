package kuchtastefan.domain;

import kuchtastefan.ability.Ability;
import kuchtastefan.constant.Constant;
import kuchtastefan.item.Item;
import kuchtastefan.item.ItemType;

import java.util.HashMap;
import java.util.Map;

public class Hero extends GameCharacter {
    private int unspentAbilityPoints;
    private Map<ItemType, Item> equippedItem;
    private final Map<Ability, Integer> wearingItemAbilityPoints;

    public Hero(String name) {
        super(name, new HashMap<>());
        this.abilities = this.getInitialAbilities();
        this.unspentAbilityPoints = Constant.INITIAL_ABILITY_POINTS;
        this.wearingItemAbilityPoints = getItemsInitialAbilities();
        this.equippedItem = wearDownEquippedItems();
    }

    public void equipItem(Item item) {
        this.equippedItem.put(item.getType(), item);
        setWearingItemAbPoints();
    }

    public void setWearingItemAbPoints() {
        for (Ability ability : Ability.values()) {
            this.wearingItemAbilityPoints.put(ability, 0);
        }

        for (Map.Entry<ItemType, Item> wearingItem : equippedItem.entrySet()) {
            for (Ability ability : Ability.values()) {
                this.wearingItemAbilityPoints.put(
                        ability,
                        wearingItem.getValue().getAbilities().get(ability) + this.wearingItemAbilityPoints.get(ability));
            }
        }
    }

    public void wearDownAllItems() {
        this.equippedItem = wearDownEquippedItems();
        for (Ability ability : Ability.values()) {
            this.wearingItemAbilityPoints.put(ability, 0);
        }
    }

    private Map<ItemType, Item> wearDownEquippedItems() {
        Map<ItemType, Item> itemMap = new HashMap<>();
        for (ItemType itemType : ItemType.values()) {
            itemMap.put(itemType, new Item("No item", itemType, Map.of(
                    Ability.ATTACK, 0,
                    Ability.DEFENCE, 0,
                    Ability.DEXTERITY, 0,
                    Ability.SKILL, 0,
                    Ability.LUCK, 0,
                    Ability.HEALTH, 0
            ), 0));
        }
        return itemMap;
    }

    private Map<Ability, Integer> getInitialAbilities() {
        return new HashMap<>(Map.of(
                Ability.ATTACK, 1,
                Ability.DEFENCE, 1,
                Ability.DEXTERITY, 1,
                Ability.SKILL, 1,
                Ability.LUCK, 1,
                Ability.HEALTH, 50
        ));
    }

    private Map<Ability, Integer> getItemsInitialAbilities() {
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

        int tempAbilityPoints;
        if (this.wearingItemAbilityPoints.get(ability) == null) {
            tempAbilityPoints = (this.abilities.get(ability) + pointsToChange);
        } else {
            tempAbilityPoints = (this.abilities.get(ability) + pointsToChange) - this.wearingItemAbilityPoints.get(ability);
        }

        if (tempAbilityPoints < minimumPoints) {
            System.out.println("You don't have enough points!");
        } else {
            if (tempAbilityPoints < this.abilities.get(ability)) {
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

    public Map<Ability, Integer> getWearingItemAbilityPoints() {
        return wearingItemAbilityPoints;
    }
}
