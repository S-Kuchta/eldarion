package kuchtastefan.quest.questObjectives;

import kuchtastefan.characters.hero.Hero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestKillObjective extends QuestObjective {
    private final QuestEnemy questEnemyToKill;
    private final int countEnemyToKill;
    private int currentCountEnemyProgress;


    public QuestKillObjective(String questObjectiveName, QuestEnemy questEnemyToKill, int countEnemyToKill) {
        super(questObjectiveName);
        this.questEnemyToKill = questEnemyToKill;
        this.countEnemyToKill = countEnemyToKill;
    }

    @Override
    public void printQuestObjectiveAssignment(Hero hero) {
        if (currentCountEnemyProgress <= this.countEnemyToKill) {
            System.out.println("\tKill " + this.countEnemyToKill + "x " + this.questEnemyToKill.getQuestEnemyRarity() + " "
                    + this.questEnemyToKill.getQuestEnemyName() + " - " +
                    "You have " + this.currentCountEnemyProgress +
                    " / " + this.countEnemyToKill + " killed");
        }
    }

    @Override
    public void checkIfQuestObjectiveIsCompleted(Hero hero) {
        if (countEnemyToKill == currentCountEnemyProgress) {
            setCompleted(true);
        }
    }

    @Override
    public void removeCompletedItemsOrEnemies(Hero hero) {

    }

    public void increaseCurrentCountEnemyProgress() {
        this.currentCountEnemyProgress++;
    }
}
