package kuchtastefan.item.wearableItem;

import kuchtastefan.ability.Ability;
import kuchtastefan.character.hero.Hero;
import kuchtastefan.item.HaveType;
import kuchtastefan.item.Item;
import kuchtastefan.item.UsableItem;
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

        System.out.print(this.getItemType() + ": "
                + this.getName()
                + " (" + this.getWearableItemQuality() + "), iLevel: " + this.getItemLevel());

        System.out.print(", Item Price: " + this.getPrice()
                + "(Sell Value: " + this.returnSellItemPrice() + ")");

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

    public void increaseWearableItemAbilityValue(WearableItem wearableItem) {
        for (Ability ability : Ability.values()) {
            if (wearableItem.getAbilities().get(ability) != 0) {
                wearableItem.getAbilities().put(ability, (wearableItem.getAbilities().get(ability) * 2));
            }
        }
    }

    public void setItemQuality(WearableItemQuality wearableItemQuality) {
        this.wearableItemQuality = wearableItemQuality;
    }

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
