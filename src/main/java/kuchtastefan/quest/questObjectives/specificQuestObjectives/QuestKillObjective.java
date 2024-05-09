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

@Getter
@Setter
public class QuestKillObjective extends QuestObjective {
    private final int questEnemyId;
    private final int countEnemyToKill;
    private int currentCountEnemyProgress;


    public QuestKillObjective(String questObjectiveName, int questEnemyId, int countEnemyToKill, int questObjectiveId) {
        super(questObjectiveId, questObjectiveName);
        this.questEnemyId = questEnemyId;
        this.countEnemyToKill = countEnemyToKill;
    }

//    @Override
//    public void questObjectiveAssignment(Hero hero) {
//        NonPlayerCharacter enemy = CharacterDB.CHARACTER_DB.get(this.questEnemyId);
//        System.out.println("\tKill " + this.countEnemyToKill + "x " + enemy.getCharacterRarity() + " "
//                + enemy.getName() + " - " + "You have " + this.currentCountEnemyProgress + " / " + this.countEnemyToKill + " killed ");
//    }

    @Override
    public void questObjectiveAssignment(Hero hero) {
        NonPlayerCharacter questTarget = CharacterDB.CHARACTER_DB.get(this.questEnemyId);

        System.out.println("\tKill " + hero.getEnemyKilled().getAmountOfKilledEnemy(questTarget) + this.countEnemyToKill + "/" + " " + questTarget.getName());
    }

    @Override
    public void checkIfQuestObjectiveIsCompleted(Hero hero) {
        if (countEnemyToKill == currentCountEnemyProgress) {
            System.out.println("\t" + " You completed " + this.getQuestObjectiveName() + " quest objective");
            setCompleted(true);
        }
    }

    @Override
    public boolean makeProgressInQuestObjective(QuestObjectiveTarget questObjectiveTarget, Hero hero) {
        if (questObjectiveTarget instanceof Enemy enemy) {
            return enemy.getNpcId() == questEnemyId;
        }

        return false;
    }

    public void increaseCurrentCountEnemyProgress() {
        this.currentCountEnemyProgress++;
    }
}
