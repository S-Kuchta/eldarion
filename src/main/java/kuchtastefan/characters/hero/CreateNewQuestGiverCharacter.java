package kuchtastefan.characters.hero;

import kuchtastefan.characters.QuestGiverCharacter;
import kuchtastefan.quest.QuestMap;

public class CreateNewQuestGiverCharacter {

    public static QuestGiverCharacter questGiver(String name, int questId, Hero hero) {
        QuestGiverCharacter questGiverCharacter = new QuestGiverCharacter(name, 8);
        questGiverCharacter.addQuest(QuestMap.mapIdQuest.get(questId));
        questGiverCharacter.setNameBasedOnQuestsAvailable(hero);

        return questGiverCharacter;
    }
}
