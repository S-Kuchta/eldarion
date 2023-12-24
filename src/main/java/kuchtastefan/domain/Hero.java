package kuchtastefan.domain;

import kuchtastefan.ability.Ability;
import kuchtastefan.constant.Constant;
import kuchtastefan.item.Item;
import kuchtastefan.item.ItemType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hero extends GameCharacter {

    private int unspentAbilityPoints;
//    private final Map<ItemType, String> equippedItem = new HashMap<>();
    private EquippedItems equippedItem;
    private final Map<Ability, Integer> wearingItemAbilityPoints = new HashMap<>();

    public Hero(String name) {
        super(name, new HashMap<>());
        this.abilities = this.getInitialAbilities();
        this.unspentAbilityPoints = Constant.INITIAL_ABILITY_POINTS;
        this.equippedItem = new EquippedItems();
        setAllEquippedItemsToNull();
    }

    public void equipItems(List<Item> itemList) {
//        this.removeEquippedItems(itemList);
//        this.equippedItem.getEquippedItem().put(ItemType.WEAPON, "Iron sword");
//        this.equippedItem.getEquippedItem().put(ItemType.BODY, "Medium armor");
//        this.equippedItem.getEquippedItem().put(ItemType.HEAD, "Basic helmet");
//        this.equippedItem.getEquippedItem().put(ItemType.HANDS, "Basic hands");
//        this.equippedItem.getEquippedItem().put(ItemType.BOOTS, "Basic boots");

        for (Item item : itemList) {
            for (ItemType itemType : ItemType.values()) {
                if (item.getName().equals(equippedItem.getEquippedItem().get(itemType))) {
                    for (Map.Entry<Ability, Integer> itemAbilityPoints : item.getAbilities().entrySet()) {
                        this.abilities.put(
                                itemAbilityPoints.getKey(),
                                itemAbilityPoints.getValue() + this.abilities.get(itemAbilityPoints.getKey()));
                        this.wearingItemAbilityPoints.put(itemAbilityPoints.getKey(), itemAbilityPoints.getValue());
                    }
                }
            }
        }
    }

    private void setAllEquippedItemsToNull() {
        for (ItemType itemType : ItemType.values()) {
            this.equippedItem.getEquippedItem().put(itemType, null);
        }
    }

//    public void removeEquippedItems(List<Item> itemList) {
//        for (Map.Entry<Ability, Integer> itemAbilityPoint : this.getWearingItemAbilityPoints().entrySet()) {
//            this.abilities.put(
//                    itemAbilityPoint.getKey(),
//                    (this.getAbilityValue(itemAbilityPoint.getKey()) - itemAbilityPoint.getValue()));
//            this.wearingItemAbilityPoints.put(itemAbilityPoint.getKey(), null);
//        }
//
//        setAllEquippedItemsToNull();
//    }

    public void removeEquippedItems(List<Item> itemList) {
        for (Item item : itemList) {
            for (ItemType itemType : ItemType.values()) {
                if (item.getName().equals(equippedItem.getEquippedItem().get(itemType))) {
                    for (Map.Entry<Ability, Integer> itemAbilityPoints : item.getAbilities().entrySet()) {
                        this.abilities.put(itemAbilityPoints.getKey(), this.abilities.get(itemAbilityPoints.getKey()) - itemAbilityPoints.getValue());
                        this.wearingItemAbilityPoints.put(itemAbilityPoints.getKey(), null);
                    }
                }
            }
        }

        setAllEquippedItemsToNull();
    }

    public Hero(String name, Map<Ability, Integer> abilities, int heroAvailablePoints) {
        super(name, abilities);
        this.unspentAbilityPoints = heroAvailablePoints;
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

    public int getUnspentAbilityPoints() {
        return unspentAbilityPoints;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<ItemType, String> getEquippedItem() {
        return equippedItem.getEquippedItem();
    }

    public EquippedItems getEquippedItems() {
        return this.equippedItem;
    }

    public void setEquippedItem(EquippedItems equippedItem) {
        this.equippedItem = equippedItem;
    }

    public Map<Ability, Integer> getWearingItemAbilityPoints() {
        return wearingItemAbilityPoints;
    }
}
