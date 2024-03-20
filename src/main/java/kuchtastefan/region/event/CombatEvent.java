package kuchtastefan.region.event;

import kuchtastefan.ability.Ability;
import kuchtastefan.character.enemy.Enemy;
import kuchtastefan.character.hero.Hero;
import kuchtastefan.constant.Constant;
import kuchtastefan.constant.ConstantSymbol;
import kuchtastefan.item.Item;
import kuchtastefan.region.location.LocationType;
import kuchtastefan.service.BattleService;
import kuchtastefan.utility.ConsoleColor;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;
import kuchtastefan.utility.RandomNumberGenerator;
import lombok.Getter;

import java.util.List;

@Getter
public class CombatEvent extends Event {

    private final BattleService battleService;
    private final List<Enemy> enemies;
    private final LocationType locationType;

    public CombatEvent(int eventLevel, List<Enemy> enemies, LocationType locationType) {
        super(eventLevel);
        this.battleService = new BattleService();
        this.enemies = enemies;
        this.locationType = locationType;
    }

    @Override
    public boolean eventOccurs(Hero hero) {

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
            if (enemy.getCurrentAbilityValue(Ability.HASTE) > enemyHaste) {
                enemyHaste = enemy.getCurrentAbilityValue(Ability.HASTE);
            }
        }

        int heroHaste = hero.getCurrentAbilityValue(Ability.HASTE);
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
                for (Enemy randomEnemy : this.enemies) {
                    double goldEarn = randomEnemy.getGoldDrop();
//                    double experiencePointGained = randomEnemy.getLevel() * Constant.GAIN_EXPERIENCE_LEVEL_MULTIPLIER + randomEnemy.getCharacterRarity().getExperienceGainedValue();
                    double experiencePointGained = randomEnemy.experiencePointsForDefeat();

                    PrintUtil.printLongDivider();
                    for (Item item : randomEnemy.getItemsDrop()) {
                        hero.getHeroInventory().addItemWithNewCopyToItemList(item);
                        System.out.println("\tYou loot " + ConsoleColor.YELLOW + item.getName() + ConsoleColor.RESET);
                    }

                    if (goldEarn > 0) {
                        System.out.println("\tYou loot " + ConsoleColor.YELLOW + goldEarn + ConsoleColor.RESET + " golds");
                    }

                    hero.addGolds(goldEarn);
                    hero.gainExperiencePoints(experiencePointGained);
                    hero.checkQuestProgress(randomEnemy.getNpcId());
                    hero.checkIfQuestObjectivesAndQuestIsCompleted();
                    PrintUtil.printLongDivider();
                }
                return true;
            }
        }

        return false;
    }
}
