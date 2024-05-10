package kuchtastefan.character.npc.enemy;

import kuchtastefan.ability.Ability;
import kuchtastefan.character.npc.CharacterType;
import kuchtastefan.quest.questObjectives.QuestObjectiveTarget;
import kuchtastefan.world.Biome;
import lombok.Getter;

import java.util.Map;
import java.util.Objects;

@Getter
public class QuestEnemy extends Enemy implements QuestObjectiveTarget {

    final int questObjectiveId;

    public QuestEnemy(String name, Map<Ability, Integer> abilities, CharacterType characterType, Biome[] biome, int maxStack, int[] enemySpells, int questObjectiveId) {
        super(name, abilities, characterType, biome, maxStack, enemySpells);
        this.questObjectiveId = questObjectiveId;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        QuestEnemy that = (QuestEnemy) object;
        return questObjectiveId == that.questObjectiveId && super.name.equals(that.name) && super.npcId == that.npcId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(questObjectiveId);
    }
}
