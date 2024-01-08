package kuchtastefan.regions;

import kuchtastefan.domain.Hero;
import kuchtastefan.item.ItemsLists;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;


public class ForestRegionService extends Region {

    public ForestRegionService(String regionName, String regionDescription, ItemsLists itemsLists, Hero hero) {
        super(regionName, regionDescription, itemsLists, hero);
    }

    @Override
    public void adventuringAcrossTheRegion() {

        while (true) {
            System.out.println();
            PrintUtil.printLongDivider();
            System.out.println("\t\t" + getRegionName());
            PrintUtil.printLongDivider();

            System.out.println("\tYou are traveling across Magic forest called " + getRegionName());
            System.out.println("\t0. Go back to the city");
            System.out.println("\t1. Travel across region");
            int choice = InputUtil.intScanner();
            switch (choice) {
                case 0 -> {
                    return;
                }
                case 1 -> super.eventService.randomRegionEventGenerate(super.hero);
            }
        }
    }

}
