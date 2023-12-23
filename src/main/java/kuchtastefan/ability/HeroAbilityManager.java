package kuchtastefan.ability;

import kuchtastefan.domain.Hero;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;

public class HeroAbilityManager {
    private Hero hero;

    public HeroAbilityManager(Hero hero) {
        this.hero = hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public void spendAbilityPoints() {
//        this.hero = hero;
        if (this.hero.getUnspentAbilityPoints() <= 0) {
            System.out.println("You don't have any ability points to spend.");
            PrintUtil.printDivider();
        } else {
            while (this.hero.getUnspentAbilityPoints() > 0) {
                PrintUtil.printCurrentAbilityPointsWithoutItems(hero);
                System.out.println("Choose ability to upgrade:");
                System.out.println("You have " + this.hero.getUnspentAbilityPoints() + " to spend.");
                printPossibleAbilitiesToUpgrade("spend");
                setAbilityToUpgrade(InputUtil.intScanner(), 1, -1, "spend");
            }

            System.out.println("You have spent all your available points. Your abilities are: ");
            PrintUtil.printCurrentAbilityPointsWithoutItems(hero);
        }
    }

    public void removeAbilityPoints() {
        while (true) {
            PrintUtil.printCurrentAbilityPointsWithoutItems(hero);
            System.out.println("Choose ability to remove:");
            printPossibleAbilitiesToUpgrade("remove");
            int removeAbilityInput = InputUtil.intScanner();
            if (removeAbilityInput == 0) {
                PrintUtil.printCurrentAbilityPointsWithoutItems(hero);
                break;
            }

            setAbilityToUpgrade(removeAbilityInput, -1, 1, "remove");
        }
    }

    private void printPossibleAbilitiesToUpgrade(String spendOrRemovePoint) {
        spendOrRemovePoint = spendOrRemovePoint.equals("spend") ? "Explain abilities" : "I am done";
        System.out.println("0. " + spendOrRemovePoint + "\n1. Attack\n2. Defence\n3. Dexterity\n4. Skill\n5. Luck\n6. Health");
    }

    private void setAbilityToUpgrade(int numberOfAbility, int numberOfPoints, int heroAvailablePointsChange, String spendOrRemovePoint) {
        switch (numberOfAbility) {
            case 0 -> {
                if (spendOrRemovePoint.equals("spend")) {
                    explainAbilities();
                }
            }
            case 1 -> this.hero.setNewAbilitiesPoints(Ability.ATTACK, numberOfPoints, heroAvailablePointsChange);
            case 2 -> this.hero.setNewAbilitiesPoints(Ability.DEFENCE, numberOfPoints, heroAvailablePointsChange);
            case 3 -> this.hero.setNewAbilitiesPoints(Ability.DEXTERITY, numberOfPoints, heroAvailablePointsChange);
            case 4 -> this.hero.setNewAbilitiesPoints(Ability.SKILL, numberOfPoints, heroAvailablePointsChange);
            case 5 -> this.hero.setNewAbilitiesPoints(Ability.LUCK, numberOfPoints, heroAvailablePointsChange);
            case 6 -> this.hero.setNewAbilitiesPoints(Ability.HEALTH, numberOfPoints, heroAvailablePointsChange);
            default -> System.out.println("Enter valid value.");
        }
    }

    private void explainAbilities() {
        for (Ability ability : Ability.values()) {
            System.out.println(ability + ": " + ability.getDescription());
        }
    }
}
