package kuchtastefan.regions.events;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.character.vendor.ConsumableVendorCharacter;
import kuchtastefan.character.vendor.CraftingReagentItemVendorCharacter;
import kuchtastefan.character.vendor.JunkVendorCharacter;
import kuchtastefan.character.vendor.WearableItemVendorCharacter;
import kuchtastefan.items.ItemsLists;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;
import kuchtastefan.utility.RandomNumberGenerator;

public class MerchantEvent extends Event {


    public MerchantEvent(int eventLevel) {
        super(eventLevel);
    }

    @Override
    public boolean eventOccurs(Hero hero) {
        int randomNumberToMeet = RandomNumberGenerator.getRandomNumber(0, 1);

        if (randomNumberToMeet == 0) {
            System.out.println("\t--> The merchant was too fast for you, you couldn't catch up and talk to him <--");
        } else {
            System.out.println("\tYou meet merchant caravan, what will you do?");
            PrintUtil.printIndexAndText("0", "Pass around");
            System.out.println();
            PrintUtil.printIndexAndText("1", "Talk");
            System.out.println();

            int choice = InputUtil.intScanner();
            switch (choice) {
                case 0 -> System.out.println("\t--> You just walk around merchant and greets him <--");
                case 1 -> {
                    int randomNumber = RandomNumberGenerator.getRandomNumber(0, 3);
                    switch (randomNumber) {
                        case 0 -> {
                            WearableItemVendorCharacter wearableItemVendorCharacter =
                                    new WearableItemVendorCharacter(
                                            "Random name", 8, ItemsLists.returnWearableItemListByItemLevel(this.getEventLevel(), null, false));
                            wearableItemVendorCharacter.vendorMenu(hero);
                        }
                        case 1 -> {
                            CraftingReagentItemVendorCharacter craftingReagentItemVendorCharacter =
                                    new CraftingReagentItemVendorCharacter(
                                            "Random name", 8, ItemsLists.returnCraftingReagentItemListByItemLevel(this.getEventLevel(), 0));
                            craftingReagentItemVendorCharacter.vendorMenu(hero);
                        }
                        case 2 -> {
                            ConsumableVendorCharacter consumableVendorCharacter =
                                    new ConsumableVendorCharacter(
                                            "Random name", 8, ItemsLists.returnConsumableItemListByItemLevel(this.getEventLevel(), 0));
                            consumableVendorCharacter.vendorMenu(hero);
                        }
                        case 3 -> {
                            JunkVendorCharacter junkVendorCharacter =
                                    new JunkVendorCharacter(
                                            "Random Name", 8, ItemsLists.getJunkItems());
                            junkVendorCharacter.vendorMenu(hero);
                        }
                    }
                }
                default -> PrintUtil.printEnterValidInput();
            }
        }

        return true;
    }
}
