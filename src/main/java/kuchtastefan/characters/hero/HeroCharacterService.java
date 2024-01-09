package kuchtastefan.characters.hero;

import kuchtastefan.characters.hero.inventory.InventoryService;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;

public class HeroCharacterService {
    private final InventoryService inventoryService;
    private final HeroAbilityManager heroAbilityManager;

    public HeroCharacterService(HeroAbilityManager heroAbilityManager) {
        this.inventoryService = new InventoryService();
        this.heroAbilityManager = heroAbilityManager;
    }

    public void heroCharacterMenu(Hero hero) {
        System.out.println("\t0. Go back");
        System.out.println("\t1. Hero Info");
        System.out.println("\t2. Inventory");
        System.out.println("\t3. Abilities");
        final int choice = InputUtil.intScanner();
        switch (choice) {
            case 0 -> {
            }
            case 1 -> {
                showHeroInfo(hero);
                heroCharacterMenu(hero);
            }
            case 2 -> this.inventoryService.inventoryMenu(hero);
            case 3 -> this.upgradeAbilityMenu(hero);
        }
    }

    private void upgradeAbilityMenu(Hero hero) {
        System.out.println("\t0. Go Back");
        System.out.println("\t1. Spend points (" + hero.getUnspentAbilityPoints() + " points left)");
        System.out.println("\t2. Remove points");
        final int choice = InputUtil.intScanner();
        switch (choice) {
            case 0 -> heroCharacterMenu(hero);
            case 1 -> this.heroAbilityManager.spendAbilityPoints();
            case 2 -> this.heroAbilityManager.removeAbilityPoints();
        }
    }

    private void showHeroInfo(Hero hero) {
        PrintUtil.printCurrentAbilityPointsWithItems(hero);
        PrintUtil.printCurrentWearingArmor(hero);
    }

    public InventoryService getInventoryService() {
        return inventoryService;
    }

    public HeroAbilityManager getHeroAbilityManager() {
        return heroAbilityManager;
    }
}
