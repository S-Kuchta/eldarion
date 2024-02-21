package kuchtastefan.quest;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.quest.questObjectives.QuestObjective;
import kuchtastefan.utility.PrintUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class Quest {
    private final int questId;
    private final String questName;
    private final String questDescription;
    private final int questLevel;
    private boolean questCompleted;
    private List<? extends QuestObjective> questObjectives;
    private final QuestReward questReward;
    private boolean isTurnedIn;


    public Quest(int questId, String questName, String questDescription, int questLevel,
                 List<? extends QuestObjective> questObjectives, QuestReward questReward) {
        this.questId = questId;
        this.questName = questName;
        this.questDescription = questDescription;
        this.questLevel = questLevel;
        this.questObjectives = questObjectives;
        this.questReward = questReward;
        this.questCompleted = false;
        this.isTurnedIn = false;
    }

    /**
     * Check if is Quest completed, Quest is completed if all
     * questObjectives belonging to quest are completed.
     */
    public void checkIfQuestIsCompleted() {
        boolean completed = true;
        for (QuestObjective questObjective : this.questObjectives) {
            if (!questObjective.isCompleted()) {
                completed = false;
                break;
            }
        }
        if (completed && !this.questCompleted) {
            PrintUtil.printCompleteQuestText(this.questName);
            this.questCompleted = true;
        }
    }

    public void turnInTheQuestAndGiveReward(Hero hero) {
        if (this.questCompleted) {
            PrintUtil.printLongDivider();
            PrintUtil.printCompleteQuestText(this.questName);
            PrintUtil.printLongDivider();
            this.questReward.giveQuestReward(hero);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quest quest = (Quest) o;

        return questId == quest.questId && questLevel == quest.questLevel && Objects.equals(questName, quest.questName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questName, questDescription, questLevel);
    }
}
