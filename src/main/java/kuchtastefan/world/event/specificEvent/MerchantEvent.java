package kuchtastefan.world.event.specificEvent;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.character.npc.vendor.ShopService;
import kuchtastefan.character.npc.vendor.VendorDB;
import kuchtastefan.character.npc.vendor.VendorItemDB;
import kuchtastefan.character.npc.vendor.specificVendorCharacter.ConsumableVendorCharacter;
import kuchtastefan.character.npc.vendor.specificVendorCharacter.CraftingReagentItemVendorCharacter;
import kuchtastefan.character.npc.vendor.specificVendorCharacter.JunkVendorCharacter;
import kuchtastefan.character.npc.vendor.specificVendorCharacter.WearableItemVendorCharacter;
import kuchtastefan.item.ItemDB;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;
import kuchtastefan.utility.RandomNameGenerator;
import kuchtastefan.utility.RandomNumberGenerator;
import kuchtastefan.world.event.Event;

public class MerchantEvent extends Event {


    public MerchantEvent(int eventLevel) {
        super(eventLevel);
    }

    @Override
    public boolean eventOccurs(Hero hero) {
        ShopService shopService = new ShopService();
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


//                            shopService.vendorMenu(hero, VendorDB.returnRandomVendorCharacter());
//                            WearableItemVendorCharacter wearableItemVendorCharacter =
//                                    new WearableItemVendorCharacter(0, RandomNameGenerator.getRandomName(),
//                                            ItemDB.returnWearableItemListByItemLevel(this.getEventLevel(), null, false));

//                            shopService.vendorMenu(hero, wearableItemVendorCharacter);
                        }
                        case 1 -> {
//                            CraftingReagentItemVendorCharacter craftingReagentItemVendorCharacter =
//                                    new CraftingReagentItemVendorCharacter(0, RandomNameGenerator.getRandomName(),
//                                            ItemDB.returnCraftingReagentItemListByItemLevel(this.getEventLevel(), 0));
//
//                            shopService.vendorMenu(hero, craftingReagentItemVendorCharacter);
                        }
                        case 2 -> {
//                            ConsumableVendorCharacter consumableVendorCharacter =
//                                    new ConsumableVendorCharacter(0, RandomNameGenerator.getRandomName(),
//                                            ItemDB.returnConsumableItemListByItemLevel(this.getEventLevel(), 0));
//
//                            shopService.vendorMenu(hero, consumableVendorCharacter);
                        }
                        case 3 -> {
//                            JunkVendorCharacter junkVendorCharacter =
//                                    new JunkVendorCharacter(0, RandomNameGenerator.getRandomName(), ItemDB.getJunkItems());
//
//                            shopService.vendorMenu(hero, junkVendorCharacter);
                        }
                    }
                }
                default -> PrintUtil.printEnterValidInput();
            }
        }

        return true;
    }
}
