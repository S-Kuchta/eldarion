package kuchtastefan.character.npc.enemy;

import kuchtastefan.ability.Ability;
import kuchtastefan.character.npc.CharacterType;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class QuestEnemy extends Enemy {

    private int questObjectiveId;

    public QuestEnemy(String name, Map<Ability, Integer> abilities, CharacterType characterType, int[] enemySpells, int questObjectiveId) {
        super(name, abilities, characterType, enemySpells);
        this.questObjectiveId = questObjectiveId;
    }
}
