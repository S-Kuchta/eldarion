package kuchtastefan.region.location;

import kuchtastefan.character.npc.QuestGiverCharacter;
import kuchtastefan.character.hero.Hero;
import kuchtastefan.quest.QuestGiverCharacterDB;
import kuchtastefan.quest.QuestMap;

public class LocationStageQuestGiver extends LocationStage {

    private final int questGiverId;


    public LocationStageQuestGiver(String stageName, int questGiverId) {
        super(stageName);
        this.questGiverId = questGiverId;
    }

    @Override
    public boolean exploreStage(Hero hero, Location location) {
        QuestGiverCharacterDB.getQuestGiverMenu(this.questGiverId, hero);

        return QuestGiverCharacterDB.QUEST_GIVER_CHARACTER_MAP.get(this.questGiverId).checkIfAllAcceptedQuestsAreCompleted(hero);
    }
}
