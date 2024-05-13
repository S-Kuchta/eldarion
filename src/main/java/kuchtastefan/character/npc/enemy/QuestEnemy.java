package kuchtastefan.character.npc.enemy;

import kuchtastefan.ability.Ability;
import kuchtastefan.character.npc.CharacterType;
import lombok.Getter;

import java.util.Map;
import java.util.Objects;

@Getter
public class QuestEnemy extends Enemy {

    final int questObjectiveId;

    public QuestEnemy(String name, Map<Ability, Integer> abilities, CharacterType characterType, int[] enemySpells, int questObjectiveId) {
        super(name, abilities, characterType, enemySpells);
        this.questObjectiveId = questObjectiveId;
    }

//    @Override
//    public boolean equals(Object object) {
//        if (this == object) return true;
//        if (object == null || getClass() != object.getClass()) return false;
//        QuestEnemy that = (QuestEnemy) object;
//        return questObjectiveId == that.questObjectiveId && name.equals(((QuestEnemy) object).name);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hashCode(questObjectiveId);
//    }
}
