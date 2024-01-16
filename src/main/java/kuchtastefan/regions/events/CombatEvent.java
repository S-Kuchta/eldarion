package kuchtastefan.regions.events;

import kuchtastefan.ability.Ability;
import kuchtastefan.characters.enemy.Enemy;
import kuchtastefan.characters.hero.Hero;
import kuchtastefan.items.Item;
import kuchtastefan.items.questItem.QuestItem;
import kuchtastefan.quest.Quest;
import kuchtastefan.quest.questObjectives.QuestBringItemObjective;
import kuchtastefan.quest.questObjectives.QuestObjective;
import kuchtastefan.regions.locations.LocationType;
import kuchtastefan.service.BattleService;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.RandomNumberGenerator;

import java.util.List;

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
    public void eventOccurs(Hero hero) {

        final int randomNumber = RandomNumberGenerator.getRandomNumber(0, enemies.size() - 1);
        Enemy randomEnemy = enemies.get(randomNumber);

        System.out.println("\tIn the distance, you've caught sight of " + randomEnemy.getName() + " (Level " + randomEnemy.getLevel() + "), "
                + "\n\tWill you attempt a silent evasion or initiate an attack?");
        System.out.println("\t0. Try to evasion");
        System.out.println("\t1. Attack");
        final int choice = InputUtil.intScanner();

        switch (choice) {
            case 0 -> {
            }
            case 1 -> {
                final int heroHealthBeforeBattle = hero.getAbilities().get(Ability.HEALTH);
                final boolean haveHeroWon = this.battleService.battle(hero, randomEnemy);
                if (haveHeroWon) {

                    QuestItem questItem = null;
//                    for (Quest quest : hero.getListOfAcceptedQuests()) {
//                        for (QuestObjective questBringItemObjective : quest.getQuestObjectives()) {
//                            if (questBringItemObjective instanceof QuestBringItemObjective
//                                    && ((QuestBringItemObjective) questBringItemObjective).getLocationType().equals(this.locationType)
//                                    && ((QuestBringItemObjective) questBringItemObjective).getEnemyNeededToItemDrop().equals(randomEnemy.getName())
//                                    && RandomNumberGenerator.getRandomNumber(0, 2) == 0) {
//
//                                questItem = ((QuestBringItemObjective) questBringItemObjective).getItemDropNeeded();
//                                randomEnemy.addItemToItemDrop(((QuestBringItemObjective) questBringItemObjective).getItemDropNeeded());
//                            }
//                        }
//                    }

                    for (Quest quest : hero.getListOfAcceptedQuests()) {
                        for (QuestObjective questObjective : quest.getQuestObjectives()) {
                            if (questObjective instanceof QuestBringItemObjective
                                    && ((QuestBringItemObjective) questObjective).checkEnemy(randomEnemy.getName())
                                    && ((QuestBringItemObjective) questObjective).checkLocation(this.locationType)) {

                                questItem = ((QuestBringItemObjective) questObjective).getItemDropNeeded();
                                randomEnemy.addItemToItemDrop(questItem);
                            }
                        }
                    }

                    double goldEarn = randomEnemy.getGoldDrop();
                    double experiencePointGained = randomEnemy.getLevel() * 20 + randomEnemy.getEnemyRarity().getExperienceGainedValue();

                    for (Item item : randomEnemy.getItemsDrop()) {
                        hero.getHeroInventory().addItemWithNewCopyToItemList(item);
                        System.out.println("\tYou loot " + item.getName());
                    }
                    System.out.println("\tYou loot " + goldEarn + " golds");

                    hero.addGolds(goldEarn);
                    hero.gainExperiencePoints(experiencePointGained);
                    hero.getEnemyKilled().addEnemyKilled(randomEnemy.getName());
                    hero.setAbility(Ability.HEALTH, heroHealthBeforeBattle);
                    hero.checkQuestProgress(randomEnemy.getName(), questItem);
                    hero.checkQuestObjectivesAndQuestCompleted();
                }
                hero.setAbility(Ability.HEALTH, heroHealthBeforeBattle);
            }
        }
    }
}
