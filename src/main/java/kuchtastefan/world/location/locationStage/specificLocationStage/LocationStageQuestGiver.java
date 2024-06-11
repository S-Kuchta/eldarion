package kuchtastefan.world.location.locationStage.specificLocationStage;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.quest.QuestDB;
import kuchtastefan.quest.questGiver.QuestGiverCharacter;
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
        QuestGiverCharacter questGiverCharacter = QuestGiverCharacterDB.returnQuestGiverFromDB(this.questGiverId);
        QuestDB.setInitialQuestsStatusFromGivenList(hero, questGiverCharacter.getQuests());

        questGiverCharacter.setQuests();
        questGiverCharacter.setNameBasedOnQuestsAvailable();
        setStageName(questGiverCharacter.getName());
    }
}
