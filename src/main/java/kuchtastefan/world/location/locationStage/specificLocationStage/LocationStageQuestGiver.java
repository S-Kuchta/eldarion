package kuchtastefan.world.location.locationStage.specificLocationStage;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.quest.Quest;
import kuchtastefan.quest.QuestDB;
import kuchtastefan.quest.questGiver.QuestGiverCharacterDB;
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
        setStage(hero);
        QuestGiverCharacterDB.questGiverMenu(this.questGiverId, hero);
        return QuestGiverCharacterDB.returnQuestGiverFromDB(this.questGiverId).checkIfAllAcceptedQuestsAreCompleted(hero);
    }

    public void setStage(Hero hero) {
        QuestGiverCharacterDB.returnQuestGiverFromDB(this.questGiverId).setQuestsStatus(hero);
//        for (Quest quest : QuestGiverCharacterDB.returnQuestGiverFromDB(this.questGiverId).getQuests()) {
//            QuestDB.setQuestStatus(hero, quest);
//        }

        QuestGiverCharacterDB.returnQuestGiverFromDB(this.questGiverId).setNameBasedOnQuestsAvailable();
        this.setStageName(QuestGiverCharacterDB.returnQuestGiverName(this.questGiverId));
    }
}
