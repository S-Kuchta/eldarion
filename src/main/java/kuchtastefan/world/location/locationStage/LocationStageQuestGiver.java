package kuchtastefan.world.location.locationStage;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.quest.questGiver.QuestGiverCharacterDB;
import kuchtastefan.world.location.Location;

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
