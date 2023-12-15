package kuchtastefan;

import java.util.HashMap;
import java.util.Map;

public class Hero {

    private String name;
    private Map<Ability, Integer> abilities;
    private int unspentAbilityPoints;

    public Hero(String name) {
        this.name = name;
        this.abilities = this.getInitialAbilities();
        this.unspentAbilityPoints = 7;
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

    public void setNewAbilitiesPoints(Ability ability, int numberOfPoints) {
        if (ability.equals(Ability.HEALTH)) {
            this.abilities.put(ability, this.abilities.get(ability) + numberOfPoints * 5);
        } else {
            this.abilities.put(ability, this.abilities.get(ability) + numberOfPoints);
        }
        this.unspentAbilityPoints -= numberOfPoints;
    }

    public void printCurrentAbilityPoints() {
        for (Map.Entry<Ability, Integer> entry : this.abilities.entrySet()) {
            System.out.print(entry.getKey() + ": " + entry.getValue() + ", ");
        }
    }

    public String getName() {
        return name;
    }

    public Map<Ability, Integer> getAbilities() {
        return abilities;
    }

    public int getUnspentAbilityPoints() {
        return unspentAbilityPoints;
    }
}
