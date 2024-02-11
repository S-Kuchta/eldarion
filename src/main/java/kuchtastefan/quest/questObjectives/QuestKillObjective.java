package kuchtastefan.quest.questObjectives;

import kuchtastefan.characters.enemy.Enemy;
import kuchtastefan.characters.enemy.EnemyList;
import kuchtastefan.characters.hero.Hero;
import kuchtastefan.utility.ConsoleColor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestKillObjective extends QuestObjective {
    private final Integer questEnemyId;
    private final int countEnemyToKill;
    private int currentCountEnemyProgress;


    public QuestKillObjective(String questObjectiveName, Integer questEnemyId, int countEnemyToKill) {
        super(questObjectiveName);
        this.questEnemyId = questEnemyId;
        this.countEnemyToKill = countEnemyToKill;
    }

    @Override
    public void printQuestObjectiveAssignment(Hero hero) {
        Enemy enemy = EnemyList.getEnemyMap().get(this.questEnemyId);
        if (currentCountEnemyProgress <= this.countEnemyToKill) {
            System.out.println("\tKill " + this.countEnemyToKill + "x " + enemy.getEnemyRarity() + " "
                    + ConsoleColor.YELLOW + enemy.getName() + ConsoleColor.RESET + " - " +
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
