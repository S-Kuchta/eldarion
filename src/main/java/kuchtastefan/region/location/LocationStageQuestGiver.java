package kuchtastefan.region.location;

import kuchtastefan.character.npc.QuestGiverCharacter;
import kuchtastefan.character.hero.Hero;
import kuchtastefan.quest.QuestGiverCharacterDB;
import kuchtastefan.quest.QuestMap;

public class LocationStageQuestGiver extends LocationStage {

    private final int questGiverId;
    private final Integer questId;
    private final String questGiverName;


    public LocationStageQuestGiver(String stageName, int questGiverId, Integer questId, String questGiverName) {
        super(stageName);
        this.questGiverId = questGiverId;
        this.questId = questId;
        this.questGiverName = questGiverName;
    }

    @Override
    public boolean exploreStage(Hero hero, Location location) {
//        QuestGiverCharacter questGiverCharacter = new QuestGiverCharacter(this.questGiverName);
//        questGiverCharacter.addQuest(QuestMap.mapIdQuest.get(this.questId));
//        questGiverCharacter.setNameBasedOnQuestsAvailable(hero);
//        questGiverCharacter.questGiverMenu(hero);

//        return questGiverCharacter.checkIfAllAcceptedQuestsAreCompleted(hero);

        return QuestGiverCharacterDB.QUEST_GIVER_CHARACTER_MAP.get(questGiverId).checkIfAllAcceptedQuestsAreCompleted(hero);
    }
}
