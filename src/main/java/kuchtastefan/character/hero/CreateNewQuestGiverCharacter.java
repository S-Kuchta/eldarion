package kuchtastefan.character.hero;

import kuchtastefan.character.npc.QuestGiverCharacter;
import kuchtastefan.quest.QuestDB;

public class CreateNewQuestGiverCharacter {

    public static QuestGiverCharacter questGiver(String name, int questId, Hero hero) {
        QuestGiverCharacter questGiverCharacter = new QuestGiverCharacter(name);
        questGiverCharacter.addQuest(QuestDB.returnQuest(questId));
        questGiverCharacter.setNameBasedOnQuestsAvailable(hero);

        return questGiverCharacter;
    }
}
