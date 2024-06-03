package kuchtastefan.quest.questObjectives.specificQuestObjectives;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.character.npc.CharacterDB;
import kuchtastefan.character.npc.enemy.QuestEnemy;
import kuchtastefan.quest.questObjectives.QuestObjective;
import kuchtastefan.quest.questObjectives.ResetObjectiveProgress;
import lombok.Getter;

@Getter
public class QuestKillObjective extends QuestObjective implements ResetObjectiveProgress {
    private final int questEnemyId;
    private final int countEnemyToKill;


    public QuestKillObjective(String questObjectiveName, int questEnemyId, int countEnemyToKill, int id) {
        super(id, questObjectiveName);
        this.questEnemyId = questEnemyId;
        this.countEnemyToKill = countEnemyToKill;
    }

    @Override
    public void printQuestObjectiveProgress(Hero hero) {
        QuestEnemy questTarget = (QuestEnemy) CharacterDB.CHARACTER_DB.get(this.questEnemyId);
        int numberOfKilledEnemies = Math.min(countEnemyToKill, hero.getEnemyKilled().getAmountOfKilledEnemy(questTarget.getNpcId()));
        System.out.println("\tKill " + numberOfKilledEnemies + "/" + this.countEnemyToKill + " " + questTarget.getName());
    }

    @Override
    public void verifyQuestObjectiveCompletion(Hero hero) {
        QuestEnemy questTarget = (QuestEnemy) CharacterDB.CHARACTER_DB.get(this.questEnemyId);
        if (countEnemyToKill == hero.getEnemyKilled().getAmountOfKilledEnemy(questTarget.getNpcId())) {
            setCompleted(hero, true);
        }
    }

    @Override
    public void resetCompletedQuestObjectiveAssignment(Hero hero) {
        hero.getEnemyKilled().removeQuestEnemyKilled(this.questEnemyId, this.countEnemyToKill);
    }
}
