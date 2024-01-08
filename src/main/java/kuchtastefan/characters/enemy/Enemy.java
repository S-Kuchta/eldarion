package kuchtastefan.characters.enemy;

import kuchtastefan.ability.Ability;
import kuchtastefan.characters.GameCharacter;

import java.util.Map;

public class Enemy extends GameCharacter {
    public Enemy(String name, Map<Ability, Integer> abilities) {
        super(name, abilities);
    }
}
