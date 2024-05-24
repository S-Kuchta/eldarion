package kuchtastefan.quest;

import kuchtastefan.character.hero.Hero;
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

    public boolean canBeQuestAccepted(Hero hero) {
        try {
            return hero.getHeroQuests().getHeroAcceptedQuest().get(this.previousQuestId).getQuestStatus().equals(QuestStatus.TURNED_IN);
        } catch (NullPointerException e) {
            return false;
        }
    }
}