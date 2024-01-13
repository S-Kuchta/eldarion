package kuchtastefan.quest.questObjectives;

import kuchtastefan.characters.hero.Hero;


public class QuestKillObjective extends QuestObjective {
    private final String enemyToKill;
    private final int countEnemyToKill;

    public QuestKillObjective(String questObjectiveName, String enemyToKill, int countEnemyToKill) {
        super(questObjectiveName);
        this.enemyToKill = enemyToKill;
        this.countEnemyToKill = countEnemyToKill;
    }

    @Override
    public void printQuestObjectiveAssignment(Hero hero) {
        hero.getEnemyKilled().getQuestEnemyKilled().putIfAbsent(this.enemyToKill, 0);
        System.out.println("\tKill " + this.countEnemyToKill + "x " + this.enemyToKill + " - " +
                "You have " + hero.getEnemyKilled().getQuestEnemyKilled().get(this.enemyToKill) +
                " / " + this.countEnemyToKill + " killed");
    }

    @Override
    public void checkQuestObjectiveCompleted(Hero hero) {
        if (hero.getEnemyKilled().checkIfHeroContainsEnoughQuestEnemyKilled(this.enemyToKill, this.countEnemyToKill)) {
            setCompleted(true);
        }
    }

    @Override
    public void removeCompletedItemsOrEnemies(Hero hero) {
        hero.getEnemyKilled().removeQuestEnemyKilled(this.enemyToKill);
    }

    public String getEnemyToKill() {
        return enemyToKill;
    }

    public int getCountEnemyToKill() {
        return countEnemyToKill;
    }
}
