package kuchtastefan.quest.questObjectives;

import kuchtastefan.character.enemy.Enemy;
import kuchtastefan.character.enemy.CharacterList;
import kuchtastefan.character.hero.Hero;
import kuchtastefan.constant.ConstantSymbol;
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
        Enemy enemy = CharacterList.getEnemyMap().get(this.questEnemyId);
        if (currentCountEnemyProgress <= this.countEnemyToKill) {
            System.out.println("\t" + ConstantSymbol.QUEST_OBJECTIVE_SYMBOL + " Kill " + this.countEnemyToKill + "x " + enemy.getCharacterRarity() + " "
                    + ConsoleColor.YELLOW + enemy.getName() + ConsoleColor.RESET + " - " +
                    "You have " + this.currentCountEnemyProgress +
                    " / " + this.countEnemyToKill + " killed " + ConstantSymbol.QUEST_OBJECTIVE_SYMBOL);
        }
    }

    @Override
    public void checkIfQuestObjectiveIsCompleted(Hero hero) {
        if (countEnemyToKill == currentCountEnemyProgress) {
            setCompleted(true);
        }
    }

    public void increaseCurrentCountEnemyProgress() {
        this.currentCountEnemyProgress++;
    }
}
