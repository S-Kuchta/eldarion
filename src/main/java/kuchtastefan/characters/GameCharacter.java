package kuchtastefan.characters;

import kuchtastefan.ability.Ability;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public abstract class GameCharacter {
    protected String name;
    protected Map<Ability, Integer> abilities;
    protected Map<Ability, Integer> maxAbilities;
    protected Map<Ability, Integer> currentAbilities;
    protected int level;


    public GameCharacter(String name, Map<Ability, Integer> abilities) {
        this.name = name;
        this.abilities = abilities;
        this.level = 1;
        this.maxAbilities = abilities;
        this.currentAbilities = abilities;
    }

    public GameCharacter(String name, int level) {
        this.name = name;
        this.level = level;
        this.abilities = initializeAbilityForNonEnemyCharacters();
        this.maxAbilities = this.abilities;
        this.currentAbilities = maxAbilities;
    }

    public void receiveDamage(int damage) {
        this.currentAbilities.put(Ability.HEALTH, this.getCurrentAbilityValue(Ability.HEALTH) - damage);
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

    public int getCurrentAbilityValue(Ability ability) {
        return this.currentAbilities.get(ability);
    }

}
