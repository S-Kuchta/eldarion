package kuchtastefan.item.specificItems.wearableItem;

import kuchtastefan.ability.Ability;
import kuchtastefan.character.hero.Hero;
import kuchtastefan.item.Item;
import kuchtastefan.item.ItemAndCount;
import kuchtastefan.item.ItemDB;
import kuchtastefan.item.itemFilter.ItemFilter;
import kuchtastefan.item.itemType.HaveType;
import kuchtastefan.item.specificItems.craftingItem.CraftingReagentItem;
import kuchtastefan.item.specificItems.craftingItem.CraftingReagentItemType;
import kuchtastefan.item.usableItem.UsableItem;
import kuchtastefan.utility.RandomNumberGenerator;
import kuchtastefan.utility.printUtil.PrintUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Objects;

@Getter
@Setter
public class WearableItem extends Item implements UsableItem, HaveType {

    private WearableItemType itemType;
    private final Map<Ability, Integer> abilities;
    private WearableItemQuality wearableItemQuality;

    public WearableItem(Integer itemId, String name, double price, int itemLevel,
                        WearableItemType itemType,
                        Map<Ability, Integer> abilities,
                        WearableItemQuality wearableItemQuality) {
        super(itemId, name, price, itemLevel);
        this.itemType = itemType;
        this.abilities = abilities;
        this.wearableItemQuality = wearableItemQuality;
    }


    @Override
    public void printItemDescription(Hero hero) {
        if (hero.getEquippedItem().containsValue(this)) {
            System.out.print("-- EQUIPPED -- ");
        }

        System.out.print(this.getItemType() + ": " + this.getName() + " (" + this.getWearableItemQuality() + "), iLevel: " + this.getItemLevel());
        System.out.print(", Item Price: " + this.getPrice() + "(Sell Value: " + this.returnSellItemPrice() + ")");

        if (!this.getName().equals("No item")) {
            System.out.print("\n\t\tItem stats: ");
        }

        for (Map.Entry<Ability, Integer> ability : this.getAbilities().entrySet()) {
            if (ability.getValue() != 0) {
                System.out.print(ability.getKey() + ": " + ability.getValue() + ", ");
            }
        }
        System.out.println();
    }

    @Override
    public boolean useItem(Hero hero) {
        hero.equipItem(this);
        return true;
    }

    public void refine() {
        this.setPrice(this.getPrice() * 2);
        this.setWearableItemQuality(WearableItemQuality.IMPROVED);

        for (Ability ability : Ability.values()) {
            if (this.getAbilities().get(ability) != 0) {
                this.getAbilities().put(ability, (this.getAbilities().get(ability) * 2));
            }
        }
    }

    public ItemAndCount reagentNeededToRefine() {
        Item reagent = ItemDB.getRandomItem(CraftingReagentItem.class, new ItemFilter(CraftingReagentItemType.BLACKSMITH_REAGENT, this.itemLevel));
        int count = this.itemLevel * 2;
        return new ItemAndCount(reagent, count);
    }

    public ItemAndCount dismantle() {
        Item reagent = ItemDB.getRandomItem(CraftingReagentItem.class, new ItemFilter(CraftingReagentItemType.BLACKSMITH_REAGENT, this.itemLevel));
        return new ItemAndCount(reagent, RandomNumberGenerator.getRandomNumber(2, 4) + this.itemLevel);
    }

//    @Override
//    public void printTypeSelection() {
//        for (WearableItemType wearableItemType : WearableItemType.values()) {
//            System.out.println(wearableItemType);
//        }
//
//        PrintUtil.printIndexAndText("A", "Weapon");
//        PrintUtil.printIndexAndText("B", "Body");
//        PrintUtil.printIndexAndText("C", "Boots");
//        PrintUtil.printIndexAndText("D", "Hands");
//        PrintUtil.printIndexAndText("E", "Head");
//    }
//
//    @Override
//    public ItemFilter returnItemType() {
//        return null;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        WearableItem that = (WearableItem) o;
        return Objects.equals(itemLevel, that.itemLevel) && itemType == that.itemType && Objects.equals(abilities, that.abilities) && wearableItemQuality == that.wearableItemQuality;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), itemType, abilities, itemLevel, wearableItemQuality);
    }
}
