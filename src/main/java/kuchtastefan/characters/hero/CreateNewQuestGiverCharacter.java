package kuchtastefan.characters.hero;

import kuchtastefan.characters.QuestGiverCharacter;
import kuchtastefan.quest.QuestList;

public class CreateNewQuestGiverCharacter {

    public static QuestGiverCharacter questGiver(String name, int questId, Hero hero) {
        QuestGiverCharacter questGiverCharacter = new QuestGiverCharacter(name, 8);
        questGiverCharacter.addQuest(QuestList.mapIdQuest.get(questId));
        questGiverCharacter.setNameBasedOnQuestsAvailable(hero);
//        questGiverCharacter.questGiverMenu(hero);

        return questGiverCharacter;
    }
}
