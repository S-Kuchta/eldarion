package kuchtastefan.character.hero;

import kuchtastefan.ability.Ability;
import kuchtastefan.ability.AbilityPointsSpendOrRemove;
import kuchtastefan.utility.ConsoleColor;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class HeroAbilityManager {
    private Hero hero;

    public HeroAbilityManager(Hero hero) {
        this.hero = hero;
    }

    private List<Ability> abilityList() {
        List<Ability> abilityList = new ArrayList<>();
        for (Ability ability : Ability.values()) {
            if (!ability.equals(Ability.ABSORB_DAMAGE)) {
                abilityList.add(ability);
            }
        }

        return abilityList;
    }

    public void spendAbilityPoints() {
        if (this.hero.getUnspentAbilityPoints() <= 0) {
            System.out.println("You don't have any ability points to spend.");
            PrintUtil.printLongDivider();
        } else {
            while (this.hero.getUnspentAbilityPoints() > 0) {
                PrintUtil.printAbilityPoints(this.hero);
                System.out.println("Choose ability to upgrade:");
                System.out.println("You have " + this.hero.getUnspentAbilityPoints() + " to spend.");
                printPossibleAbilitiesToUpgrade(AbilityPointsSpendOrRemove.SPEND);
                setAbilityToUpgrade(InputUtil.intScanner(), 1, -1, AbilityPointsSpendOrRemove.SPEND);
            }

            System.out.println("You have spent all your available points. Your abilities are: ");
            PrintUtil.printAbilityPoints(this.hero);
        }
    }

    public void removeAbilityPoints() {
        while (true) {
            PrintUtil.printAbilityPoints(this.hero);
            System.out.println("Choose ability to remove:");
            printPossibleAbilitiesToUpgrade(AbilityPointsSpendOrRemove.REMOVE);
            int removeAbilityInput = InputUtil.intScanner();
            if (removeAbilityInput == 0) {
                PrintUtil.printAbilityPoints(this.hero);
                break;
            }

            setAbilityToUpgrade(removeAbilityInput, -1, 1, AbilityPointsSpendOrRemove.REMOVE);
        }
    }

    private void printPossibleAbilitiesToUpgrade(AbilityPointsSpendOrRemove spendOrRemovePoint) {
        String string = spendOrRemovePoint.equals(AbilityPointsSpendOrRemove.SPEND) ?
                ConsoleColor.CYAN + "\t0. " + ConsoleColor.RESET + "Explain abilities":
                ConsoleColor.CYAN + "\t0. " + ConsoleColor.RESET + "I am done";
        System.out.println(string);

        int index = 1;
        for (Ability ability : this.abilityList()) {
            System.out.println("\t" + ConsoleColor.CYAN + index + ". " + ConsoleColor.RESET + ability.toString());
            index++;
        }
    }

    private void setAbilityToUpgrade(int numberOfAbility, int numberOfPoints, int heroAvailablePointsChange, AbilityPointsSpendOrRemove spendOrRemovePoint) {
        if (numberOfAbility == 0) {
            if (spendOrRemovePoint.equals(AbilityPointsSpendOrRemove.SPEND)) {
                explainAbilities();
            }
        } else {
            if (numberOfAbility < 1 || numberOfAbility > abilityList().size()) {
                PrintUtil.printEnterValidInput();
            } else {
                this.hero.setNewAbilityPoint(abilityList().get(numberOfAbility - 1), numberOfPoints, heroAvailablePointsChange);
            }
        }
    }

    private void explainAbilities() {
        PrintUtil.printExtraLongDivider();
        for (Ability ability : Ability.values()) {
            System.out.println(ConsoleColor.YELLOW + "\t" + ability.toString() +ConsoleColor.RESET + ": " + ability.getDescription());
        }
    }
}