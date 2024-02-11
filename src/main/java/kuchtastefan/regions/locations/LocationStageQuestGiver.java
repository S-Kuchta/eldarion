package kuchtastefan.regions.locations;

import kuchtastefan.characters.QuestGiverCharacter;
import kuchtastefan.characters.hero.Hero;
import kuchtastefan.quest.QuestList;

public class LocationStageQuestGiver extends LocationStage {

    private final Integer questId;
    private final String questGiverName;


    public LocationStageQuestGiver(String stageName, Integer questId, String questGiverName) {
        super(stageName);
        this.questId = questId;
        this.questGiverName = questGiverName;
    }

    @Override
    public boolean exploreStage(Hero hero, Location location) {
        QuestGiverCharacter questGiverCharacter = new QuestGiverCharacter(this.questGiverName, 8);
        questGiverCharacter.addQuest(QuestList.questList.get(this.questId));
        questGiverCharacter.setNameBasedOnQuestsAvailable(hero);
        questGiverCharacter.questGiverMenu(hero);

        return questGiverCharacter.checkIfAllAcceptedQuestsAreCompleted(hero);
    }

    @Override
    public void completeStage() {
    }
}