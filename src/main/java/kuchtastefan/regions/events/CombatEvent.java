package kuchtastefan.regions.events;

import kuchtastefan.characters.enemy.Enemy;
import kuchtastefan.characters.enemy.EnemyList;
import kuchtastefan.characters.hero.Hero;
import kuchtastefan.items.Item;
import kuchtastefan.quest.questObjectives.QuestEnemy;
import kuchtastefan.regions.locations.LocationType;
import kuchtastefan.service.BattleService;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;
import kuchtastefan.utility.RandomNumberGenerator;
import lombok.Getter;

import java.util.ArrayList;
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

        int randomNumber;
        System.out.println(enemies.size());

        List<Enemy> enemyList = new ArrayList<>();
        for (int i = 0; i < RandomNumberGenerator.getRandomNumber(this.minimumEnemiesCount, this.maximumEnemiesCount); i++) {
            if (!this.enemies.isEmpty()) {
                randomNumber = RandomNumberGenerator.getRandomNumber(0, enemies.size() - 1);
            } else {
                return false;
            }
            enemyList.add(EnemyList.returnEnemyWithNewCopy(enemies.get(randomNumber), enemies.get(randomNumber).getEnemyRarity()));
        }

        System.out.println("\tIn the distance, you've caught sight of:");
        for (Enemy enemy : enemyList) {
            System.out.println("\t" + enemy.getName() + " - " + enemy.getEnemyRarity().name() + " - (Level " + enemy.getLevel() + "), ");
        }
        System.out.println("\n\tWill you attempt a silent evasion or initiate an attack?");
        System.out.println("\t0. Try to evasion");
        System.out.println("\t1. Attack");
        final int choice = InputUtil.intScanner();

        switch (choice) {
            case 0 -> {
            }
            case 1 -> {
                final boolean haveHeroWon = this.battleService.battle(hero, enemyList);
                if (haveHeroWon) {

                    for (Enemy randomEnemy : enemyList) {
                        double goldEarn = randomEnemy.getGoldDrop();
                        double experiencePointGained = randomEnemy.getLevel() * 20 + randomEnemy.getEnemyRarity().getExperienceGainedValue();

                        PrintUtil.printLongDivider();
                        for (Item item : randomEnemy.getItemsDrop()) {
                            hero.getHeroInventory().addItemWithNewCopyToItemList(item);
                            System.out.println("\tYou loot " + item.getName());
                        }
                        System.out.println("\tYou loot " + goldEarn + " golds");

                        hero.addGolds(goldEarn);
                        hero.gainExperiencePoints(experiencePointGained);
                        hero.getEnemyKilled().addEnemyKilled(randomEnemy.getName());
                        hero.checkQuestProgress(new QuestEnemy(randomEnemy.getName(), randomEnemy.getEnemyRarity()));
                        hero.checkIfQuestObjectivesAndQuestIsCompleted();
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
