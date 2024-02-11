package kuchtastefan.regions.events;

import kuchtastefan.ability.Ability;
import kuchtastefan.characters.enemy.Enemy;
import kuchtastefan.characters.hero.Hero;
import kuchtastefan.items.Item;
import kuchtastefan.regions.locations.LocationType;
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
    private final int minimumEnemiesCount;
    private final int maximumEnemiesCount;

    public CombatEvent(int eventLevel, List<Enemy> enemies, LocationType locationType, int minimumEnemiesCount, int maximumEnemiesCount) {
        super(eventLevel);
        this.battleService = new BattleService();
        this.enemies = enemies;
        this.locationType = locationType;
        this.minimumEnemiesCount = minimumEnemiesCount;
        this.maximumEnemiesCount = maximumEnemiesCount;
    }

    @Override
    public boolean eventOccurs(Hero hero) {

        System.out.println("\tIn the distance, you've caught sight of:");
        for (Enemy enemy : this.enemies) {
            System.out.println("\t" + enemy.getName() + " - " + enemy.getEnemyRarity().name() + " - (Level " + enemy.getLevel() + "), ");
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
                    double experiencePointGained = randomEnemy.getLevel() * 20 + randomEnemy.getEnemyRarity().getExperienceGainedValue();

                    PrintUtil.printLongDivider();
                    for (Item item : randomEnemy.getItemsDrop()) {
                        hero.getHeroInventory().addItemWithNewCopyToItemList(item);
                        System.out.println("\tYou loot " + ConsoleColor.GREEN + item.getName() + ConsoleColor.RESET);
                    }

                    if (goldEarn > 0) {
                        System.out.println("\tYou loot " + ConsoleColor.YELLOW + goldEarn + ConsoleColor.RESET + " golds");
                    }

                    hero.addGolds(goldEarn);
                    hero.gainExperiencePoints(experiencePointGained);
                    hero.checkQuestProgress(randomEnemy.getEnemyId());
                    hero.checkIfQuestObjectivesAndQuestIsCompleted();
                }
                return true;
            }
        }

        return false;
    }
}
