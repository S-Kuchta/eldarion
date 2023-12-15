package kuchtastefan.ability;

import kuchtastefan.domain.Hero;
import kuchtastefan.utility.InputUtils;
import kuchtastefan.utility.PrintUtils;

public class HeroAbilityManager {
    private final Hero hero;

    public HeroAbilityManager(Hero hero) {
        this.hero = hero;
    }

    public void spendHeroAvailablePoints() {
        int availablePoints = hero.getHeroAvailablePoints();

        if (availablePoints == 0) {
            System.out.println("You have no points to spend");
            return;
        }

        while (availablePoints > 0) {
            System.out.println("You have " + availablePoints + " point to spend. Choose wisely.");
            System.out.println("0. Explain abilities\n1. Attack\n2. Defence\n3. Dexterity\n4. Skill\n5. Luck\n6. Health");

            final int abilityIndex = InputUtils.readInt();
            Ability ability;
            switch (abilityIndex) {
                case 0 -> {
                    for (Ability a : Ability.values()) {
                        System.out.println(a + ": " + a.getDescription());
                    }
                    System.out.println();
                    continue;
                }
                case 1 -> ability = Ability.ATTACK;
                case 2 -> ability = Ability.DEFENCE;
                case 3 -> ability = Ability.DEXTERITY;
                case 4 -> ability = Ability.SKILL;
                case 5 -> ability = Ability.LUCK;
                case 6 -> ability = Ability.HEALTH;
                default -> {
                    System.out.println("Invalid index");
                    continue;
                }
            }
            System.out.println();
            hero.updateAbility(ability, 1);
            System.out.println("You have upgrade " + ability);
            hero.updateAvailablePoints(-1);

            if (availablePoints > 1) {
                PrintUtils.printAbilities(hero);
            }

            availablePoints--;
        }

        System.out.println("You have spent all your available points. Your abilities are: ");
        PrintUtils.printAbilities(hero);
        System.out.println();
    }

    public void removeHeroAvailablePoints() {
        while(true) {
            System.out.println("Which ability do you want to remove?");
            System.out.println("0. I am done\n1. Attack\n2. Defence\n3. Dexterity\n4. Skill\n5. Luck\n6. Health");

            final int abilityIndex = InputUtils.readInt();
            Ability ability;
            switch (abilityIndex) {
                case 0 -> {
                    return;
                }
                case 1 -> ability = Ability.ATTACK;
                case 2 -> ability = Ability.DEFENCE;
                case 3 -> ability = Ability.DEXTERITY;
                case 4 -> ability = Ability.SKILL;
                case 5 -> ability = Ability.LUCK;
                case 6 -> ability = Ability.HEALTH;
                default -> {
                    System.out.println("Invalid index");
                    continue;
                }
            }

            if(this.hero.getAbilities().get(ability) == 1) {
                System.out.println("You cannot remove points from this ability");
            } else {
                this.hero.updateAbility(ability, -1);
                this.hero.updateAvailablePoints(1);
                System.out.println("You have removed 1 point from " + ability);
                PrintUtils.printAbilities(this.hero);
                PrintUtils.printDivider();

            }
        }
    }

}
