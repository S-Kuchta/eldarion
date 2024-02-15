package kuchtastefan.characters.hero;

import kuchtastefan.characters.hero.inventory.InventoryService;
import kuchtastefan.gameSettings.GameSettings;
import kuchtastefan.quest.QuestService;
import kuchtastefan.characters.spell.HeroSpellManager;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;
import lombok.Getter;

@Getter
public class HeroCharacterInfoService {
    private final InventoryService inventoryService;
    private final HeroAbilityManager heroAbilityManager;
    private final QuestService questService;
    private final HeroSpellManager heroSpellManager;

    public HeroCharacterInfoService(HeroAbilityManager heroAbilityManager) {
        this.inventoryService = new InventoryService();
        this.heroAbilityManager = heroAbilityManager;
        this.questService = new QuestService();
        this.heroSpellManager = new HeroSpellManager();
    }

    public void heroCharacterMenu(Hero hero) {
        PrintUtil.printIndexAndText("0", "Go back");
        System.out.println();
        PrintUtil.printIndexAndText("1", "Hero Info");
        System.out.println();
        PrintUtil.printIndexAndText("2", "Inventory");
        System.out.println();
        PrintUtil.printIndexAndText("3", "Abilities");
        System.out.println();
        PrintUtil.printIndexAndText("4", "Quests");
        System.out.println();
        PrintUtil.printIndexAndText("5", "Spells");
        System.out.println();
        PrintUtil.printIndexAndText("6", "Game Settings");
        System.out.println();
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
            case 4 -> this.questService.heroAcceptedQuestMenu(hero, hero.getHeroAcceptedQuestIdQuest());
            case 5 -> this.heroSpellManager.spellMenu(hero);
            case 6 -> this.gameSettingsMenu();
            default -> PrintUtil.printEnterValidInput();
        }
    }

    private void upgradeAbilityMenu(Hero hero) {
        PrintUtil.printIndexAndText("0", "Go back");
        System.out.println();
        PrintUtil.printIndexAndText("1", "Spend points (" + hero.getUnspentAbilityPoints() + " points left)");
        System.out.println();
        PrintUtil.printIndexAndText("2", "Remove points");
        System.out.println();
        final int choice = InputUtil.intScanner();
        switch (choice) {
            case 0 -> heroCharacterMenu(hero);
            case 1 -> this.heroAbilityManager.spendAbilityPoints();
            case 2 -> this.heroAbilityManager.removeAbilityPoints();
            default -> PrintUtil.printEnterValidInput();
        }
    }

    private void showHeroInfo(Hero hero) {
        PrintUtil.printCurrentAbilityPointsWithItems(hero);
        PrintUtil.printCurrentWearingArmor(hero);
    }

    private void gameSettingsMenu() {
        while (true) {
            PrintUtil.printIndexAndText("0", "Go back");
            System.out.println();
            PrintUtil.printIndexAndText("1", "Print String slowly - ");
            PrintUtil.printGameSettings(GameSettings.isPrintStringSlowly());
            System.out.println();
            PrintUtil.printIndexAndText("2", "Show information about Action - ");
            PrintUtil.printGameSettings(GameSettings.isShowInformationAboutActionName());
            System.out.println();
            PrintUtil.printIndexAndText("3", "Hide spells on CoolDown - ");
            PrintUtil.printGameSettings(GameSettings.isHideSpellsOnCoolDown());
            System.out.println();

            int choice = InputUtil.intScanner();
            switch (choice) {
                case 0 -> {
                    return;
                }
                case 1 -> GameSettings.setPrintStringSlowly();
                case 2 -> GameSettings.setShowInformationAboutActionName();
                case 3 -> GameSettings.setHideSpellsOnCoolDown();
            }
        }
    }
}
