package kuchtastefan.characters.hero;

import kuchtastefan.ability.Ability;
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
            PrintUtil.printDivider();
        } else {
            while (this.hero.getUnspentAbilityPoints() > 0) {
                PrintUtil.printAbilityPoints(this.hero);
                System.out.println("Choose ability to upgrade:");
                System.out.println("You have " + this.hero.getUnspentAbilityPoints() + " to spend.");
                printPossibleAbilitiesToUpgrade("spend");
                setAbilityToUpgrade(InputUtil.intScanner(), 1, -1, "spend");
            }

            System.out.println("You have spent all your available points. Your abilities are: ");
            PrintUtil.printAbilityPoints(this.hero);
        }
    }

    public void removeAbilityPoints() {
        while (true) {
            PrintUtil.printAbilityPoints(this.hero);
            System.out.println("Choose ability to remove:");
            printPossibleAbilitiesToUpgrade("remove");
            int removeAbilityInput = InputUtil.intScanner();
            if (removeAbilityInput == 0) {
                PrintUtil.printAbilityPoints(this.hero);
                break;
            }

            setAbilityToUpgrade(removeAbilityInput, -1, 1, "remove");
        }
    }

    private void printPossibleAbilitiesToUpgrade(String spendOrRemovePoint) {
        spendOrRemovePoint = spendOrRemovePoint.equals("spend") ? "\t0. Explain abilities" : "\t0. I am done";
        System.out.println(spendOrRemovePoint);
        int index = 1;
        for (Ability ability : this.abilityList()) {
            System.out.println("\t" + index + ". " + ability.toString());
            index++;
        }
    }

    private void setAbilityToUpgrade(int numberOfAbility, int numberOfPoints, int heroAvailablePointsChange, String spendOrRemovePoint) {
        if (numberOfAbility == 0) {
            if (spendOrRemovePoint.equals("spend")) {
                explainAbilities();
            }
        } else {
            if (numberOfAbility < 1 || numberOfAbility > abilityList().size()) {
                System.out.println("Enter Valid number");
            } else {
                this.hero.setNewAbilityPoint(abilityList().get(numberOfAbility - 1), numberOfPoints, heroAvailablePointsChange);
            }
        }
    }

    private void explainAbilities() {
        for (Ability ability : Ability.values()) {
            System.out.println(ability + ": " + ability.getDescription());
        }
    }
}
