package kuchtastefan.ability;

import kuchtastefan.domain.Hero;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;

public class HeroAbilityManager {
    private final Hero hero;

    public HeroAbilityManager(Hero hero) {
        this.hero = hero;
    }

    public void spendAbilityPoints() {
        while (hero.getUnspentAbilityPoints() > 0) {
            System.out.println("\nYour abilities: ");
            PrintUtil.printCurrentAbilityPoints(hero);
            printPossibleAbilitiesToUpgrade(hero.getUnspentAbilityPoints());
            setAbilityToUpgrade(InputUtil.intScanner(), hero);
        }

        System.out.println("You have spent all your available points. You abilities are: ");
        PrintUtil.printCurrentAbilityPoints(hero);
    }

    private void printPossibleAbilitiesToUpgrade(int unspentPoints) {
        System.out.println("Choose ability to upgrade:");
        System.out.println("You have " + unspentPoints + " to spend.");
        System.out.println("0. Explain abilities\n1. Attack\n2. Defence\n3. Dexterity\n4. Skill\n5. Luck\n6. Health");
    }

    private void setAbilityToUpgrade(int numberOfAbility, Hero hero) {
        int numberOfPoints = 1;
        switch (numberOfAbility) {
            case 0 -> explainAbilities();
            case 1 -> hero.setNewAbilitiesPoints(Ability.ATTACK, numberOfPoints);
            case 2 -> hero.setNewAbilitiesPoints(Ability.DEFENCE, numberOfPoints);
            case 3 -> hero.setNewAbilitiesPoints(Ability.DEXTERITY, numberOfPoints);
            case 4 -> hero.setNewAbilitiesPoints(Ability.SKILL, numberOfPoints);
            case 5 -> hero.setNewAbilitiesPoints(Ability.LUCK, numberOfPoints);
            case 6 -> hero.setNewAbilitiesPoints(Ability.HEALTH, numberOfPoints);
            default -> System.out.println("Enter valid value.");
        }
    }

    private void explainAbilities() {
        for (Ability ability : Ability.values()) {
            System.out.println(ability + ": " + ability.getDescription());
        }
    }
}
