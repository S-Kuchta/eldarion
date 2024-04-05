package kuchtastefan.service;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.character.hero.HeroAbilityManager;
import kuchtastefan.character.spell.HeroSpellManager;
import kuchtastefan.gameSettings.GameSetting;
import kuchtastefan.gameSettings.GameSettingsDB;
import kuchtastefan.hint.HintName;
import kuchtastefan.hint.HintDB;
import kuchtastefan.utility.ConsoleColor;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        HintDB.printHint(HintName.HERO_MENU);

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
            List<GameSetting> gameSettingList = new ArrayList<>();
            PrintUtil.printIndexAndText("0", "Go back");
            System.out.println();
            int index = 1;
            for (Map.Entry<GameSetting, Boolean> gameSettings : GameSettingsDB.getGAME_SETTINGS_DB().entrySet()) {
                gameSettingList.add(gameSettings.getKey());
                PrintUtil.printIndexAndText(String.valueOf(index), gameSettings.getKey().toString() + " - ");
                PrintUtil.printGameSettingsYesOrNo(gameSettings.getValue());
                System.out.println();
                index++;
            }
            PrintUtil.printIndexAndText(String.valueOf(index), "Reset all Hints");
            System.out.println();

            int choice = InputUtil.intScanner();
            if (choice == 0) {
                break;
            } else if (choice == index) {
                HintDB.resetAllHints();
            } else {
                GameSettingsDB.setTrueOrFalse(gameSettingList.get(choice - 1));
            }
        }
    }
}
