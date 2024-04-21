package kuchtastefan.world.event.specificEvent;

import kuchtastefan.ability.Ability;
import kuchtastefan.character.hero.Hero;
import kuchtastefan.character.npc.enemy.Enemy;
import kuchtastefan.character.spell.CharactersInvolvedInBattle;
import kuchtastefan.constant.ConstantSymbol;
import kuchtastefan.item.Item;
import kuchtastefan.item.junkItem.JunkItem;
import kuchtastefan.service.BattleService;
import kuchtastefan.service.QuestService;
import kuchtastefan.utility.ConsoleColor;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;
import kuchtastefan.utility.RandomNumberGenerator;
import kuchtastefan.world.event.Event;
import lombok.Getter;

import java.util.List;

@Getter
public class CombatEvent extends Event {

    private final BattleService battleService;
    private final List<Enemy> enemies;

    public CombatEvent(int eventLevel, List<Enemy> enemies) {
        super(eventLevel);
        this.battleService = new BattleService();
        this.enemies = enemies;
    }

    @Override
    public boolean eventOccurs(Hero hero) {
        QuestService questService = new QuestService();

        System.out.println("\tIn the distance, you've caught sight of:");
        for (Enemy enemy : this.enemies) {
            System.out.println("\t"
                    + ConstantSymbol.SWORD_SYMBOL + ConsoleColor.YELLOW + " " + enemy.getName() + ConsoleColor.RESET
                    + " - " + enemy.getCharacterRarity().name() + " - (Level " + enemy.getLevel() + ") "
                    + ConstantSymbol.SWORD_SYMBOL);
        }

        System.out.println("\n\tWill you attempt a silent evasion or initiate an attack?");
        boolean battle = false;
        int enemyHaste = 0;
        for (Enemy enemy : this.enemies) {
            if (enemy.getEffectiveAbilityValue(Ability.HASTE) > enemyHaste) {
                enemyHaste = enemy.getEffectiveAbilityValue(Ability.HASTE);
            }
        }

        int heroHaste = hero.getEffectiveAbilityValue(Ability.HASTE);
        int chanceToEvasion = 50 + (heroHaste - enemyHaste);
        if (chanceToEvasion > 100) {
            chanceToEvasion = 100;
        }

        System.out.println("\tYou have " + chanceToEvasion + "% chance to evasion from battle");
        PrintUtil.printIndexAndText("0", "Try to evasion");
        System.out.println();
        PrintUtil.printIndexAndText("1", "Attack");
        System.out.println();

        while (true) {
            int choice = InputUtil.intScanner();
            switch (choice) {
                case 0 -> {
                    if (RandomNumberGenerator.getRandomNumber(0, 100) > chanceToEvasion) {
                        battle = true;
                        System.out.println("\tYou were too slow. Enemy is attacking you!");
                    } else {
                        System.out.println("\tYou successfully escaped from battle!");
                    }
                }
                case 1 -> battle = true;
                default -> PrintUtil.printEnterValidInput();
            }

            if (choice == 0 || choice == 1) {
                break;
            }
        }

        if (battle) {
            final boolean haveHeroWon = this.battleService.battle(hero, this.enemies);

            if (haveHeroWon) {
                for (Enemy enemy : this.enemies) {
                    questService.updateQuestProgressFromEnemyActions(hero, enemy);
                    double goldEarn = enemy.getGoldDrop();
                    double experiencePointGained = enemy.enemyExperiencePointsValue();

                    PrintUtil.printLongDivider();
                    for (Item item : enemy.getItemsDrop()) {
                        hero.getHeroInventory().addItemWithNewCopyToItemList(item);

                        ConsoleColor consoleColor = ConsoleColor.YELLOW;
                        if (item instanceof JunkItem) {
                            consoleColor = ConsoleColor.WHITE;
                        }

                        System.out.println("\tYou loot " + consoleColor + item.getName() + ConsoleColor.RESET);
                    }

                    if (goldEarn > 0) {
                        System.out.println("\tYou loot " + ConsoleColor.YELLOW + goldEarn + ConsoleColor.RESET + " golds");
                    }

                    hero.addGolds(goldEarn);
                    hero.gainExperiencePoints(experiencePointGained);
                    hero.checkIfQuestObjectivesAndQuestIsCompleted();
                    PrintUtil.printLongDivider();
                }
                return true;
            }
        }

        return false;
    }
}
