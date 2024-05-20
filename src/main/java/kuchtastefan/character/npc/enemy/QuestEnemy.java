package kuchtastefan.character.npc.enemy;

import kuchtastefan.ability.Ability;
import kuchtastefan.character.npc.CharacterType;
import lombok.Getter;

import java.util.Map;

@Getter
public class QuestEnemy extends Enemy {

    final int questObjectiveId;

    public QuestEnemy(String name, Map<Ability, Integer> abilities, CharacterType characterType, int[] enemySpells, int questObjectiveId) {
        super(name, abilities, characterType, enemySpells);
        this.questObjectiveId = questObjectiveId;
    }
}
