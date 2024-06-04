package kuchtastefan.quest;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.character.hero.save.quest.HeroQuest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestChain extends Quest {

    private final int previousQuestId;

    public QuestChain(int questId, String questName, String questDescription, int questLevel,
                      int[] questObjectivesIds,
                      QuestReward questReward, int previousQuestId, boolean instantTurnIn) {

        super(questId, questName, questDescription, questLevel, questObjectivesIds, questReward, instantTurnIn);
        this.previousQuestId = previousQuestId;
    }

    @Override
    public boolean canBeQuestAccepted(Hero hero) {
        HeroQuest previousQuest = hero.getSaveGameEntities().getHeroQuests().getEntity(this.previousQuestId);
        if (previousQuest == null) {
            return false;
        }

        return QuestStatus.TURNED_IN.equals(previousQuest.getQuestStatus());
    }
}