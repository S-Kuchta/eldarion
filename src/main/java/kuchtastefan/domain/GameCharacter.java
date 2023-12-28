package kuchtastefan.domain;

import kuchtastefan.ability.Ability;

import java.util.Map;

public abstract class GameCharacter {
    protected String name;
    protected Map<Ability, Integer> abilities;
    protected int level;

    public GameCharacter(String name, Map<Ability, Integer> abilities) {
        this.name = name;
        this.abilities = abilities;
        this.level = 1;
    }

    public void receiveDamage(int damage) {
        this.abilities.put(Ability.HEALTH, this.getAbilityValue(Ability.HEALTH) - damage);
    }

    public String getName() {
        return name;
    }

    public int getAbilityValue(Ability ability) {
        return this.abilities.get(ability);
    }

    public Map<Ability, Integer> getAbilities() {
        return abilities;
    }
}
