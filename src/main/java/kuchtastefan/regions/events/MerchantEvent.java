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
        System.out.println("You meet merchant caravan, what will you do?");
        System.out.println("0. Pass around");
        System.out.println("1. Talk");
        int choice = InputUtil.intScanner();
        switch (choice) {
            case 0 -> System.out.println("You just walk around merchant and greets him");
            case 1 -> {
                int randomNumber = RandomNumberGenerator.getRandomNumber(0, 2);
                switch (randomNumber) {
                    case 0 -> {
                        WearableItemVendorCharacter wearableItemVendorCharacter = new WearableItemVendorCharacter("Random name", 8, this.itemsLists.getWearableItemList());
                        wearableItemVendorCharacter.vendorMenu(hero);
                    }
                    case 1 -> {
                        CraftingReagentItemVendorCharacter craftingReagentItemVendorCharacter = new CraftingReagentItemVendorCharacter("Random name", 8, this.itemsLists.getCraftingReagentItems());
                        craftingReagentItemVendorCharacter.vendorMenu(hero);
                    }
                    case 2 -> {
                        ConsumableVendorCharacter consumableVendorCharacter = new ConsumableVendorCharacter("Random name", 8, this.itemsLists.getConsumableItems());
                        consumableVendorCharacter.vendorMenu(hero);
                    }
                }
            }
            default -> System.out.println("Enter valid input");
        }
    }
}
