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
                      List<? extends QuestObjective> questObjectives,
                      QuestReward questReward, int previousQuestId) {

        super(questId, questName, questDescription, questLevel, questObjectives, questReward);
        this.previousQuestId = previousQuestId;
    }

    public boolean canBeQuestAccepted(Hero hero) {
        try {
            return hero.getHeroAcceptedQuestIdQuest().get(this.previousQuestId).isTurnedIn();
        } catch (NullPointerException e) {
            return false;
        }
    }
}