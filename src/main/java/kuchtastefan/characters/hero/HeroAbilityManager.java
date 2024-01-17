package kuchtastefan.characters.hero;

import kuchtastefan.ability.Ability;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HeroAbilityManager {
    private Hero hero;

    public HeroAbilityManager(Hero hero) {
        this.hero = hero;
    }

    public void spendAbilityPoints() {
        if (this.hero.getUnspentAbilityPoints() <= 0) {
            System.out.println("You don't have any ability points to spend.");
            PrintUtil.printDivider();
        } else {
            while (this.hero.getUnspentAbilityPoints() > 0) {
                PrintUtil.printCurrentAbilityPoints(this.hero);
                System.out.println("Choose ability to upgrade:");
                System.out.println("You have " + this.hero.getUnspentAbilityPoints() + " to spend.");
                printPossibleAbilitiesToUpgrade("spend");
                setAbilityToUpgrade(InputUtil.intScanner(), 1, -1, "spend");
            }

            System.out.println("You have spent all your available points. Your abilities are: ");
            PrintUtil.printCurrentAbilityPoints(hero);
        }
    }

    public void removeAbilityPoints() {
        while (true) {
            PrintUtil.printCurrentAbilityPoints(this.hero);
            System.out.println("Choose ability to remove:");
            printPossibleAbilitiesToUpgrade("remove");
            int removeAbilityInput = InputUtil.intScanner();
            if (removeAbilityInput == 0) {
                PrintUtil.printCurrentAbilityPoints(this.hero);
                break;
            }

            setAbilityToUpgrade(removeAbilityInput, -1, 1, "remove");
        }
    }

    private void printPossibleAbilitiesToUpgrade(String spendOrRemovePoint) {
        spendOrRemovePoint = spendOrRemovePoint.equals("spend") ? "\t0. Explain abilities" : "\t0. I am done";
        System.out.println(spendOrRemovePoint);
        int index = 1;
        for (Ability ability : Ability.values()) {
            System.out.println("\t" + index + ". " + ability.toString());
            index++;
        }
    }

    private void setAbilityToUpgrade(int numberOfAbility, int numberOfPoints, int heroAvailablePointsChange, String spendOrRemovePoint) {
        Ability tempAbility = null;
        if (numberOfAbility == 0) {
            if (spendOrRemovePoint.equals("spend")) {
                explainAbilities();
            }
        } else {
            int index = 1;
            if (numberOfAbility < 1 || numberOfAbility > Ability.values().length) {
                System.out.println("Enter Valid number");
            } else {
                for (Ability ability : Ability.values()) {
                    if (index == numberOfAbility) {
                        tempAbility = ability;
                    }
                    index++;
                }
                assert tempAbility != null;
                this.hero.setNewAbilityPoint(tempAbility, numberOfPoints, heroAvailablePointsChange);
            }
        }
    }

    private void explainAbilities() {
        for (Ability ability : Ability.values()) {
            System.out.println(ability + ": " + ability.getDescription());
        }
    }
}
