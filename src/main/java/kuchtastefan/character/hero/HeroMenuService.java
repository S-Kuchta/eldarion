package kuchtastefan.character.hero;

import kuchtastefan.character.hero.inventory.InventoryMenuService;
import kuchtastefan.gameSettings.GameSetting;
import kuchtastefan.gameSettings.GameSettingsDB;
import kuchtastefan.hint.HintName;
import kuchtastefan.hint.HintUtil;
import kuchtastefan.quest.QuestService;
import kuchtastefan.character.spell.HeroSpellManager;
import kuchtastefan.utility.ConsoleColor;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;
import lombok.Getter;

@Getter
public class HeroMenuService {
    private final InventoryMenuService inventoryMenuService;
    private final HeroAbilityManager heroAbilityManager;
    private final QuestService questService;
    private final HeroSpellManager heroSpellManager;

    public HeroMenuService(HeroAbilityManager heroAbilityManager) {
        this.inventoryMenuService = new InventoryMenuService();
        this.heroAbilityManager = heroAbilityManager;
        this.questService = new QuestService();
        this.heroSpellManager = new HeroSpellManager();
    }

    public void heroCharacterMenu(Hero hero) {
        HintUtil.printHint(HintName.HERO_MENU);

        PrintUtil.printLongDivider();
        System.out.printf("%30s %n", ConsoleColor.YELLOW + "Hero menu" + ConsoleColor.RESET);
        PrintUtil.printLongDivider();

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
            case 2 -> this.inventoryMenuService.inventoryMenu(hero);
            case 3 -> this.upgradeAbilityMenu(hero);
            case 4 -> this.questService.heroAcceptedQuestMenu(hero, hero.getHeroAcceptedQuest());
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
            PrintUtil.printGameSettings(GameSettingsDB.GAME_SETTINGS_DB.get(GameSetting.PRINT_STRING_SLOWLY));
            System.out.println();
            PrintUtil.printIndexAndText("2", "Show information about Action - ");
            PrintUtil.printGameSettings(GameSettingsDB.GAME_SETTINGS_DB.get(GameSetting.SHOW_INFORMATION_ABOUT_ACTION_NAME));
            System.out.println();
            PrintUtil.printIndexAndText("3", "Hide spells on CoolDown - ");
            PrintUtil.printGameSettings(GameSettingsDB.GAME_SETTINGS_DB.get(GameSetting.HIDE_SPELLS_ON_COOL_DOWN));
            System.out.println();
            PrintUtil.printIndexAndText("4", "Reset all Hints");
            System.out.println();

            int choice = InputUtil.intScanner();
            switch (choice) {
                case 0 -> {
                    return;
                }
                case 1 -> GameSettingsDB.setTrueOrFalse(GameSetting.PRINT_STRING_SLOWLY);
                case 2 -> GameSettingsDB.setTrueOrFalse(GameSetting.SHOW_INFORMATION_ABOUT_ACTION_NAME);
                case 3 -> GameSettingsDB.setTrueOrFalse(GameSetting.HIDE_SPELLS_ON_COOL_DOWN);
                case 4 -> HintUtil.resetAllHints();
            }
        }
    }
}
