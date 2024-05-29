package kuchtastefan.world.location.locationStage.specificLocationStage;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.quest.questGiver.QuestGiverCharacterDB;
import kuchtastefan.service.QuestService;
import kuchtastefan.world.location.Location;
import kuchtastefan.world.location.locationStage.CanEnterStageAfterComplete;
import kuchtastefan.world.location.locationStage.LocationStage;
import lombok.Getter;

@Getter
public class LocationStageQuestGiver extends LocationStage implements CanEnterStageAfterComplete {

    private final int questGiverId;


    public LocationStageQuestGiver(String stageName, int questGiverId) {
        super(stageName);
        this.questGiverId = questGiverId;
    }

    @Override
    public boolean exploreStage(Hero hero, Location location) {
        QuestGiverCharacterDB.questGiverMenu(this.questGiverId, hero);
        setStageName();
        return QuestGiverCharacterDB.returnQuestGiverFromDB(this.questGiverId).checkIfAllAcceptedQuestsAreCompleted(hero);
    }

    public void setStageName() {
        this.setStageName(QuestGiverCharacterDB.returnQuestGiverName(this.questGiverId));
    }
}
