package kuchtastefan.character.hero.save.quest;

import kuchtastefan.character.hero.save.SaveGameEntity;
import kuchtastefan.quest.QuestStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
public class HeroQuest implements SaveGameEntity {

    private final int id;
    private QuestStatus questStatus;
    private final Map<Integer, HeroQuestObjective> objectives;

    public boolean containsObjectiveById(int id) {
        return this.objectives.containsKey(id);
    }

    public HeroQuestObjective getObjectiveById(int id) {
        return this.objectives.get(id);
    }

    public void addObjective(HeroQuestObjective objective) {
        this.objectives.put(objective.getId(), objective);
    }
}
