package kuchtastefan.quest.questObjectives;

import kuchtastefan.characters.hero.Hero;
import lombok.Getter;

@Getter
public class QuestKillObjective extends QuestObjective {
//    private final String enemyToKill;
    private final QuestEnemy questEnemyToKill;
    private final int countEnemyToKill;

//    public QuestKillObjective(String questObjectiveName, String enemyToKill, int countEnemyToKill) {
//        super(questObjectiveName);
//        this.enemyToKill = enemyToKill;
//        this.countEnemyToKill = countEnemyToKill;
//    }

    public QuestKillObjective(String questObjectiveName, QuestEnemy questEnemyToKill, int countEnemyToKill) {
        super(questObjectiveName);
        this.questEnemyToKill = questEnemyToKill;
        this.countEnemyToKill = countEnemyToKill;
    }

    @Override
    public void printQuestObjectiveAssignment(Hero hero) {
        hero.getEnemyKilled().getQuestEnemyKilled().putIfAbsent(this.questEnemyToKill.getQuestEnemyName(), 0);
        if (hero.getEnemyKilled().getQuestEnemyKilled().get(this.enemyToKill) < this.countEnemyToKill) {
            System.out.println("\tKill " + this.countEnemyToKill + "x " + this.enemyToKill + " - " +
                    "You have " + hero.getEnemyKilled().getQuestEnemyKilled().get(this.enemyToKill) +
                    " / " + this.countEnemyToKill + " killed");
        } else {
            System.out.println("\tKill " + this.countEnemyToKill + "x " + this.enemyToKill + " - " +
                    "You have " + this.countEnemyToKill +
                    " / " + this.countEnemyToKill + " killed");
        }
    }

//    @Override
//    public void printQuestObjectiveAssignment(Hero hero) {
//        hero.getEnemyKilled().getQuestEnemyKilled().putIfAbsent(this.enemyToKill, 0);
//        if (hero.getEnemyKilled().getQuestEnemyKilled().get(this.enemyToKill) < this.countEnemyToKill) {
//            System.out.println("\tKill " + this.countEnemyToKill + "x " + this.enemyToKill + " - " +
//                    "You have " + hero.getEnemyKilled().getQuestEnemyKilled().get(this.enemyToKill) +
//                    " / " + this.countEnemyToKill + " killed");
//        } else {
//            System.out.println("\tKill " + this.countEnemyToKill + "x " + this.enemyToKill + " - " +
//                    "You have " + this.countEnemyToKill +
//                    " / " + this.countEnemyToKill + " killed");
//        }
//    }

    @Override
    public void checkIfQuestObjectiveIsCompleted(Hero hero) {
        if (hero.getEnemyKilled().checkIfHeroContainsEnoughQuestEnemyKilled(this.enemyToKill, this.countEnemyToKill)) {
            setCompleted(true);
        }
    }

    @Override
    public void removeCompletedItemsOrEnemies(Hero hero) {
        hero.getEnemyKilled().removeQuestEnemyKilled(this.enemyToKill);
    }
}
