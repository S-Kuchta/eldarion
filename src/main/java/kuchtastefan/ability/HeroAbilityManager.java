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
        if (hero.getUnspentAbilityPoints() <= 0) {
            System.out.println("You don't have any ability points to spend.");
            PrintUtil.printDivider();
        } else {
            while (hero.getUnspentAbilityPoints() > 0) {
                System.out.println("\nYour abilities: ");
                PrintUtil.printCurrentAbilityPoints(hero);
                System.out.println("Choose ability to upgrade:");
                System.out.println("You have " + hero.getUnspentAbilityPoints() + " to spend.");
                printPossibleAbilitiesToUpgrade("spend");
                setAbilityToUpgrade(InputUtil.intScanner(), hero, 1, -1, "spend");
            }

            System.out.println("You have spent all your available points. Your abilities are: ");
            PrintUtil.printCurrentAbilityPoints(hero);
        }
    }

    private void printPossibleAbilitiesToUpgrade(String spendOrRemovePoint) {
        spendOrRemovePoint = spendOrRemovePoint.equals("spend") ? "Explain abilities" : "I am done";
        System.out.println("0. " + spendOrRemovePoint + "\n1. Attack\n2. Defence\n3. Dexterity\n4. Skill\n5. Luck\n6. Health");
    }

    private void setAbilityToUpgrade(int numberOfAbility, Hero hero, int numberOfPoints, int heroAvailablePointsChange, String spendOrRemovePoint) {
        switch (numberOfAbility) {
            case 0 -> {
                if (spendOrRemovePoint.equals("spend")) {
                    explainAbilities();
                }
            }
            case 1 -> hero.setNewAbilitiesPoints(Ability.ATTACK, numberOfPoints, heroAvailablePointsChange);
            case 2 -> hero.setNewAbilitiesPoints(Ability.DEFENCE, numberOfPoints, heroAvailablePointsChange);
            case 3 -> hero.setNewAbilitiesPoints(Ability.DEXTERITY, numberOfPoints, heroAvailablePointsChange);
            case 4 -> hero.setNewAbilitiesPoints(Ability.SKILL, numberOfPoints, heroAvailablePointsChange);
            case 5 -> hero.setNewAbilitiesPoints(Ability.LUCK, numberOfPoints, heroAvailablePointsChange);
            case 6 -> hero.setNewAbilitiesPoints(Ability.HEALTH, numberOfPoints, heroAvailablePointsChange);
            default -> System.out.println("Enter valid value.");
        }
    }

    private void explainAbilities() {
        for (Ability ability : Ability.values()) {
            System.out.println(ability + ": " + ability.getDescription());
        }
    }

    public void removeAbilityPoints() {
        while (true) {
            PrintUtil.printCurrentAbilityPoints(hero);
            System.out.println("Choose ability to remove:");
            printPossibleAbilitiesToUpgrade("remove");
            int removeAbilityInput = InputUtil.intScanner();
            if (removeAbilityInput == 0) {
                PrintUtil.printCurrentAbilityPoints(hero);
                break;
            }

            setAbilityToUpgrade(removeAbilityInput, hero, -1, 1, "remove");
        }

    }
}
