package kuchtastefan;

import java.util.HashMap;
import java.util.Map;

public class Hero {

    private String name;
    private Map<Ability, Integer> abilities;
    private int unspentPoints;

    public Hero(String name) {
        this.name = name;
        this.abilities = this.getInitialAbilities();
        this.unspentPoints = 7;
    }

    private Map<Ability, Integer> getInitialAbilities() {
        return new HashMap<>(Map.of(
                Ability.ATTACK, 1,
                Ability.DEFENCE, 1,
                Ability.DEXTERITY,1,
                Ability.SKILL,1,
                Ability.LUCK,1,
                Ability.HEALTH, 50
        ));
    }

    public String getName() {
        return name;
    }

    public Map<Ability, Integer> getAbilities() {
        return abilities;
    }

    public int getUnspentPoints() {
        return unspentPoints;
    }

    public void setUnspentPoints(int unspentPoints) {
        this.unspentPoints = unspentPoints;
    }
}
