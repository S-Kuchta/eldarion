package kuchtastefan.world.event.specificEvent;

import kuchtastefan.ability.Ability;
import kuchtastefan.character.hero.Hero;
import kuchtastefan.character.npc.enemy.Enemy;
import kuchtastefan.character.npc.enemy.QuestEnemy;
import kuchtastefan.constant.Constant;
import kuchtastefan.item.Item;
import kuchtastefan.item.specificItems.questItem.QuestItem;
import kuchtastefan.service.BattleService;
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
        printEnemies();
        boolean battle = willHeroAttack(hero);
        return battle && battle(hero);
    }

    private void printEnemies() {
        System.out.println("\tIn the distance, you've caught sight of:");
        this.enemies.forEach(enemy -> System.out.println("\t" + enemy.getName() + " - "
                + enemy.getCharacterRarity().name() + " - (Level " + enemy.getLevel() + ") "));
        System.out.println("\n\tWill you attempt a silent evasion or initiate an attack?");
    }

    private boolean battle(Hero hero) {
        hero.setInCombat(true);
        boolean haveHeroWon = this.battleService.battle(hero, this.enemies);

        if (haveHeroWon) {
            battleWon(hero);
        } /*else {
            battleLost(hero);
        }*/

//        this.battleService.resetSpellsCoolDowns(hero);
//        hero.getBuffsAndDebuffs().clear();
        hero.setInCombat(false);

        return haveHeroWon;
    }

    private boolean willHeroAttack(Hero hero) {
        int chanceToEvasion = calculateEvasionChance(hero);
        System.out.println("\tYou have " + chanceToEvasion + "% chance to evasion from battle");
        PrintUtil.printMenuOptions("Try to evasion", "Attack");
        return makeChoice(chanceToEvasion);
    }

    private int calculateEvasionChance(Hero hero) {
        int enemyHaste = 0;
        for (Enemy enemy : this.enemies) {
            if (enemy.getEffectiveAbilityValue(Ability.HASTE) > enemyHaste) {
                enemyHaste = enemy.getEffectiveAbilityValue(Ability.HASTE);
            }
        }

        int heroHaste = hero.getEffectiveAbilityValue(Ability.HASTE);
        return Math.min(100, 50 + (heroHaste - enemyHaste));
    }

    private boolean makeChoice(int chanceToEvasion) {
        while (true) {
            int choice = InputUtil.intScanner();
            if (choice == 0) {
                return RandomNumberGenerator.getRandomNumber(0, 100) > chanceToEvasion;
            } else if (choice == 1) {
                return true;
            } else {
                PrintUtil.printEnterValidInput();
            }
        }
    }

    private void battleWon(Hero hero) {
        for (Enemy enemy : this.enemies) {
            System.out.println();
            double goldEarn = enemy.getGoldDrop();

            PrintUtil.printMenuHeader(ConsoleColor.RESET + "Loot from " + enemy.getName());
            for (Item item : enemy.getItemsDrop()) {
                if (item instanceof QuestItem questItem) {
                    hero.getHeroInventory().addQuestItemToInventory(questItem, 1, hero);
                    continue;
                }

                hero.getHeroInventory().addItemToInventory(item, 1);
            }

            if (goldEarn > 0) {
                System.out.println("\tYou loot " + ConsoleColor.YELLOW + goldEarn + ConsoleColor.RESET + " golds");
            }

            hero.addGolds(goldEarn);
            hero.gainExperiencePoints(enemy.enemyExperiencePointsValue());

            if (enemy instanceof QuestEnemy questEnemy) {
                hero.getEnemyKilled().addQuestEnemyKilled(hero, questEnemy);
            }
        }
    }

    private void battleLost(Hero hero) {
//        int goldToRemove = Constant.GOLD_TO_REMOVE_PER_LEVEL_AFTER_DEAD * hero.getLevel();
//
//        hero.checkHeroGoldsAndSubtractIfHaveEnough(goldToRemove);
//        hero.getEffectiveAbilities().put(Ability.HEALTH, hero.getEnhancedAbilities().get(Ability.HEALTH));
//
//        System.out.println("\n\t" + ConsoleColor.RED + "You have died!" + ConsoleColor.RESET);
//        System.out.println("\tYou lost " + goldToRemove + " golds!");

    }
}
