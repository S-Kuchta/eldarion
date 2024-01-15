package kuchtastefan.regions.events;

import kuchtastefan.ability.Ability;
import kuchtastefan.characters.enemy.Enemy;
import kuchtastefan.characters.enemy.EnemyList;
import kuchtastefan.characters.hero.Hero;
import kuchtastefan.items.Item;
import kuchtastefan.regions.locations.LocationType;
import kuchtastefan.service.BattleService;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.RandomNumberGenerator;

import java.util.List;

public class CombatEvent extends Event {

    private final BattleService battleService;
//    private final EnemyList enemyList;
    private final List<Enemy> enemies;
    private final LocationType locationType;

    public CombatEvent(int eventLevel, /*EnemyList enemyList*/ List<Enemy> enemies, LocationType locationType) {
        super(eventLevel);
        this.battleService = new BattleService();
//        this.enemyList = enemyList;
        this.enemies = enemies;
        this.locationType = locationType;
    }

    @Override
    public void eventOccurs(Hero hero) {

//        List<Enemy> suitableEnemies = this.enemyList.returnEnemyListByLocationTypeAndLevel(this.locationType, this.eventLevel, null);
//        final int randomNumber = RandomNumberGenerator.getRandomNumber(0, suitableEnemies.size() - 1);
//
//        Enemy randomEnemy = suitableEnemies.get(randomNumber);
        final int randomNumber = RandomNumberGenerator.getRandomNumber(0, enemies.size() - 1);
        Enemy randomEnemy = enemies.get(randomNumber);

        System.out.println("\tIn the distance, you've caught sight of " + randomEnemy.getName() + " (Level " + randomEnemy.getLevel() + "), "
                + "\n\tWill you attempt a silent evasion or initiate an attack?");
        System.out.println("\t0. Try to evasion");
        System.out.println("\t1. Attack");
        int choice = InputUtil.intScanner();

        switch (choice) {
            case 0 -> {
            }
            case 1 -> {
                final int heroHealthBeforeBattle = hero.getAbilities().get(Ability.HEALTH);
                final boolean haveHeroWon = this.battleService.battle(hero, randomEnemy);
                if (haveHeroWon) {
                    double goldEarn = randomEnemy.getGoldDrop();
                    double experiencePointGained = randomEnemy.getLevel() * 20;

                    for (Item item : randomEnemy.getItemsDrop()) {
                        hero.getHeroInventory().addItemWithNewCopyToItemList(item);
                        System.out.println("\tYou loot " + item.getName());
                    }
                    System.out.println("\tYou loot " + goldEarn + " golds");
                    hero.addGolds(goldEarn);

                    hero.gainExperiencePoints(experiencePointGained);

                    hero.getEnemyKilled().addEnemyKilled(randomEnemy.getName());

                    hero.checkQuestProgress(randomEnemy.getName());

                    hero.setAbility(Ability.HEALTH, heroHealthBeforeBattle);
                    hero.checkQuestObjectivesAndQuestCompleted();
                }


            }
        }


    }
}
