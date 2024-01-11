package kuchtastefan.characters;

import kuchtastefan.ability.Ability;

import java.util.HashMap;
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

    public GameCharacter(String name, int level) {
        this.name = name;
        this.level = level;
        this.abilities = initializeAbilityForNonEnemyCharacters();
    }

    public void receiveDamage(int damage) {
        this.abilities.put(Ability.HEALTH, this.getAbilityValue(Ability.HEALTH) - damage);
    }

    public Map<Ability, Integer> initializeAbilityForNonEnemyCharacters() {
        return new HashMap<>(Map.of(
                Ability.ATTACK, 15,
                Ability.DEFENCE, 15,
                Ability.DEXTERITY, 15,
                Ability.SKILL, 15,
                Ability.LUCK, 15,
                Ability.HEALTH, 250
        ));
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
