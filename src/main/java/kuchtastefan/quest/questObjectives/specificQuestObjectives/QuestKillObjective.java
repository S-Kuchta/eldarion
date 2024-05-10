package kuchtastefan.quest.questObjectives.specificQuestObjectives;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.character.npc.CharacterDB;
import kuchtastefan.character.npc.NonPlayerCharacter;
import kuchtastefan.character.npc.enemy.Enemy;
import kuchtastefan.character.npc.enemy.QuestEnemy;
import kuchtastefan.quest.QuestStatus;
import kuchtastefan.quest.questObjectives.QuestObjective;
import kuchtastefan.quest.questObjectives.QuestObjectiveTarget;
import lombok.Getter;
import lombok.Setter;

//@Getter
//@Setter
public class QuestKillObjective extends QuestObjective {
    private final int questEnemyId;
    private final int countEnemyToKill;


    public QuestKillObjective(String questObjectiveName, int questEnemyId, int countEnemyToKill, int questObjectiveId) {
        super(questObjectiveId, questObjectiveName);
        this.questEnemyId = questEnemyId;
        this.countEnemyToKill = countEnemyToKill;
    }

    @Override
    public void questObjectiveAssignment(Hero hero) {
        QuestEnemy questTarget = (QuestEnemy) CharacterDB.CHARACTER_DB.get(this.questEnemyId);
        System.out.println("\tKill " + hero.getEnemyKilled().getAmountOfKilledEnemy(questTarget) + "/" + this.countEnemyToKill + " " + questTarget.getName());
        checkIfQuestObjectiveIsCompleted(hero);
    }

    @Override
    public void checkIfQuestObjectiveIsCompleted(Hero hero) {
        QuestEnemy questTarget = (QuestEnemy) CharacterDB.CHARACTER_DB.get(this.questEnemyId);
        if (!this.completed && countEnemyToKill == hero.getEnemyKilled().getAmountOfKilledEnemy(questTarget)) {
            setCompleted(hero, true);
        }
    }
}
