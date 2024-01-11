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
        hero.getEnemyKilled().getQuestEnemyKilled().putIfAbsent(enemyToKill, 0);

        System.out.println("\tKill " + this.countEnemyToKill + "x - " + this.enemyToKill );
        System.out.println("\tYou have " + hero.getEnemyKilled().getQuestEnemyKilled().get(enemyToKill)
                + " / " + this.countEnemyToKill + " killed");
    }

    @Override
    public void checkCompleted(Hero hero) {
        if (hero.getEnemyKilled().checkIfHeroContainsEnoughQuestEnemyKilled(this.enemyToKill, this.countEnemyToKill)) {
            System.out.println("\t--> You completed " + getQuestObjectiveName() + " quest objective <--");
            setCompleted(true);
        }
    }

    @Override
    public void removeCompletedItemsOrEnemies(Hero hero) {
        hero.getEnemyKilled().removeQuestEnemyKilled(this.enemyToKill);
    }
}
