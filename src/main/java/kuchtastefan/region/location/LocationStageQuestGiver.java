package kuchtastefan.region.location;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.quest.questGiver.QuestGiverCharacterDB;

public class LocationStageQuestGiver extends LocationStage {

    private final int questGiverId;


    public LocationStageQuestGiver(String stageName, int questGiverId) {
        super(stageName);
        this.questGiverId = questGiverId;
    }

    @Override
    public boolean exploreStage(Hero hero, Location location) {
        QuestGiverCharacterDB.returnQuestGiverMenu(this.questGiverId, hero);

        return QuestGiverCharacterDB.returnQuestGiverFromDB(this.questGiverId).checkIfAllAcceptedQuestsAreCompleted(hero);
    }
}
