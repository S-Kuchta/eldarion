package kuchtastefan.regions.locations;

import kuchtastefan.character.npc.QuestGiverCharacter;
import kuchtastefan.character.hero.Hero;
import kuchtastefan.quest.QuestMap;

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
        questGiverCharacter.addQuest(QuestMap.mapIdQuest.get(this.questId));
        questGiverCharacter.setNameBasedOnQuestsAvailable(hero);
        questGiverCharacter.questGiverMenu(hero);

        return questGiverCharacter.checkIfAllAcceptedQuestsAreCompleted(hero);
    }
}
