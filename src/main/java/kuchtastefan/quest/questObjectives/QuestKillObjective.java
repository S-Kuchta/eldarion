package kuchtastefan.quest.questObjectives;

import kuchtastefan.characters.hero.Hero;

public class QuestKillObjective extends QuestObjective {
    private final String enemyToKill;
    private final int countEnemyToKill;
    private final StringBuilder questObjectiveAssignment;

    public QuestKillObjective(String questObjectiveName, String enemyToKill, int countEnemyToKill) {
        super(questObjectiveName);
        this.enemyToKill = enemyToKill;
        this.countEnemyToKill = countEnemyToKill;
        this.questObjectiveAssignment = new StringBuilder();
    }

    @Override
    public void printQuestObjectiveAssignment(Hero hero) {
        this.questObjectiveAssignment.setLength(0);
        hero.getEnemyKilled().getQuestEnemyKilled().putIfAbsent(this.enemyToKill, 0);
        this.questObjectiveAssignment.append("\tKill ").append(this.countEnemyToKill).append("x ").append(this.enemyToKill).append(" - ");
        this.questObjectiveAssignment.append("You have ")
                .append(hero.getEnemyKilled().getQuestEnemyKilled().get(this.enemyToKill))
                .append(" / ").append(this.countEnemyToKill)
                .append(" killed");

        System.out.println(this.questObjectiveAssignment);

//        System.out.println("\tKill " + this.countEnemyToKill + "x - " + this.enemyToKill);
//        System.out.println("\tYou have " + hero.getEnemyKilled().getQuestEnemyKilled().get(this.enemyToKill)
//                + " / " + this.countEnemyToKill + " killed");
    }

    @Override
    public void checkQuestObjectiveCompleted(Hero hero) {
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
