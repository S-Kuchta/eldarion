package kuchtastefan.characters.hero;

import kuchtastefan.characters.hero.inventory.InventoryService;
import kuchtastefan.utility.InputUtil;

public class HeroCharacterService {
    private final InventoryService inventoryService;
    private final HeroAbilityManager heroAbilityManager;

    public HeroCharacterService(InventoryService inventoryService, HeroAbilityManager heroAbilityManager) {
        this.inventoryService = inventoryService;
        this.heroAbilityManager = heroAbilityManager;
    }

    public void heroCharacterMenu(Hero hero) {
        System.out.println("0. Go back");
        System.out.println("1. Inventory");
        System.out.println("2. Abilities");
        final int choice = InputUtil.intScanner();
        switch (choice) {
            case 0 -> {
            }
            case 1 -> this.inventoryService.inventoryMenu(hero);
            case 2 -> this.upgradeAbilityMenu(hero);
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

    public InventoryService getInventoryService() {
        return inventoryService;
    }

    public HeroAbilityManager getHeroAbilityManager() {
        return heroAbilityManager;
    }
}
