package kuchtastefan.regions.events;

import kuchtastefan.domain.Hero;
import kuchtastefan.domain.vendor.ConsumableVendorCharacter;
import kuchtastefan.domain.vendor.CraftingReagentItemVendorCharacter;
import kuchtastefan.domain.vendor.WearableItemVendorCharacter;
import kuchtastefan.item.ItemsLists;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.RandomNumberGenerator;

public class MerchantEvent extends Event {

    private final ItemsLists itemsLists;

    public MerchantEvent(int eventLevel, ItemsLists itemsLists) {
        super(eventLevel);
        this.itemsLists = itemsLists;
    }

    @Override
    public void eventOccurs(Hero hero) {
        System.out.println("\tYou meet merchant caravan, what will you do?");
        System.out.println("\t0. Pass around");
        System.out.println("\t1. Talk");
        int choice = InputUtil.intScanner();
        switch (choice) {
            case 0 -> System.out.println("\tYou just walk around merchant and greets him");
            case 1 -> {
                int randomNumber = RandomNumberGenerator.getRandomNumber(0, 2);
                switch (randomNumber) {
                    case 0 -> {
                        WearableItemVendorCharacter wearableItemVendorCharacter = new WearableItemVendorCharacter("Random name", 8, this.itemsLists.returnWearableItemListByItemLevel(this.getEventLevel(), null));
                        wearableItemVendorCharacter.vendorMenu(hero);
                    }
                    case 1 -> {
                        CraftingReagentItemVendorCharacter craftingReagentItemVendorCharacter = new CraftingReagentItemVendorCharacter("Random name", 8, this.itemsLists.returnCraftingReagentItemListByItemLevel(this.getEventLevel(), 0));
                        craftingReagentItemVendorCharacter.vendorMenu(hero);
                    }
                    case 2 -> {
                        ConsumableVendorCharacter consumableVendorCharacter = new ConsumableVendorCharacter("Random name", 8, this.itemsLists.returnConsumableItemListByItemLevel(this.getEventLevel(), 0));
                        consumableVendorCharacter.vendorMenu(hero);
                    }
                }
            }
            default -> System.out.println("\tEnter valid input");
        }
    }
}
