package kuchtastefan.region.event;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.character.npc.vendor.ConsumableVendorCharacter;
import kuchtastefan.character.npc.vendor.CraftingReagentItemVendorCharacter;
import kuchtastefan.character.npc.vendor.JunkVendorCharacter;
import kuchtastefan.character.npc.vendor.WearableItemVendorCharacter;
import kuchtastefan.item.ItemDB;
import kuchtastefan.item.consumeableItem.ConsumableItem;
import kuchtastefan.item.craftingItem.CraftingReagentItem;
import kuchtastefan.item.junkItem.JunkItem;
import kuchtastefan.item.wearableItem.WearableItem;
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
                                            "Random name", 8, ItemDB.returnWearableItemListByItemLevel(this.getEventLevel(), null, false), WearableItem.class);
                            wearableItemVendorCharacter.vendorMenu(hero);
                        }
                        case 1 -> {
                            CraftingReagentItemVendorCharacter craftingReagentItemVendorCharacter =
                                    new CraftingReagentItemVendorCharacter(
                                            "Random name", 8, ItemDB.returnCraftingReagentItemListByItemLevel(this.getEventLevel(), 0), CraftingReagentItem.class);
                            craftingReagentItemVendorCharacter.vendorMenu(hero);
                        }
                        case 2 -> {
                            ConsumableVendorCharacter consumableVendorCharacter =
                                    new ConsumableVendorCharacter(
                                            "Random name", 8, ItemDB.returnConsumableItemListByItemLevel(this.getEventLevel(), 0), ConsumableItem.class);
                            consumableVendorCharacter.vendorMenu(hero);
                        }
                        case 3 -> {
                            JunkVendorCharacter junkVendorCharacter =
                                    new JunkVendorCharacter(
                                            "Random Name", 8, ItemDB.getJunkItems(), JunkItem.class);
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
