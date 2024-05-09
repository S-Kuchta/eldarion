package kuchtastefan.quest;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.quest.questObjectives.QuestObjective;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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