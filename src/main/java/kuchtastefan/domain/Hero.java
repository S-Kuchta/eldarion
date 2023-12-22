package kuchtastefan.domain;

import kuchtastefan.ability.Ability;
import kuchtastefan.constant.Constant;

import java.util.HashMap;
import java.util.Map;

public class Hero extends GameCharacter {

    private int heroAvailablePoints;

    public Hero(String name) {
        super(name, new HashMap<>());
        this.abilities = this.getInitialAbilities();
        this.heroAvailablePoints = Constant.INITIAL_ABILITY_POINTS;
    }

    public Hero(String name, Map<Ability, Integer> abilities, int heroAvailablePoints) {
        super(name, abilities);
        this.heroAvailablePoints = heroAvailablePoints;
    }

    private Map<Ability, Integer> getInitialAbilities() {
        return new HashMap<>(Map.of(
                Ability.ATTACK, 1,
                Ability.DEFENCE, 1,
                Ability.DEXTERITY, 1,
                Ability.SKILL, 1,
                Ability.LUCK, 1,
                Ability.HEALTH, 50
        ));
    }

    public void updateAbility(Ability ability, int delta) {
        if (ability.equals(Ability.HEALTH)) {
            this.abilities.put(ability, this.abilities.get(ability) + delta * Constant.HEALTH_OF_ONE_POINT);
        } else {
            this.abilities.put(ability, this.abilities.get(ability) + delta);
        }
    }

    public void updateAvailablePoints(int delta) {
        this.heroAvailablePoints += delta;
    }

    public int getHeroAvailablePoints() {
        return heroAvailablePoints;
    }

    public void setAbility(Ability ability, int value) {
        abilities.put(ability, value);
    }

    public void setHeroAvailablePoints(int heroAvailablePoints) {
        this.heroAvailablePoints = heroAvailablePoints;
    }

    public void setName(String name) {
        this.name = name;
    }
}
