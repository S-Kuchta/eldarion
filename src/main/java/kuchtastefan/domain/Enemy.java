package kuchtastefan.domain;

import kuchtastefan.ability.Ability;

import java.util.Map;

public class Enemy extends GameCharacter {

    public Enemy(String name, Map<Ability, Integer> abilities) {
        super(name, abilities);
    }
}
