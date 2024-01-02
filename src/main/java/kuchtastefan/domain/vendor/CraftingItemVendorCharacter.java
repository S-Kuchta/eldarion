package kuchtastefan.domain.vendor;

import kuchtastefan.ability.Ability;
import kuchtastefan.domain.Hero;
import kuchtastefan.item.Item;

import java.util.List;
import java.util.Map;

public class CraftingItemVendorCharacter extends VendorCharacter {
    public CraftingItemVendorCharacter(String name, Map<Ability, Integer> abilities, List<? extends Item> itemList) {
        super(name, abilities, itemList);
    }

    @Override
    public void vendorOffer(Hero hero) {

    }

    @Override
    public void successfullyItemBought(Hero hero, Item item) {

    }

    @Override
    public void printGreeting() {

    }

    @Override
    public void printItemsForSale(Hero hero) {

    }
}
