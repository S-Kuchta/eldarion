package kuchtastefan;

import java.util.Map;
import java.util.Scanner;

public class Main {

    private final static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("Welcome to the Gladiatus game!");
        System.out.println("Enter your name: ");
        final String name = scanner.nextLine();
        final Hero hero = new Hero(name);
        System.out.println("Hello " + hero.getName() + ". Let's start the game!");

        while (hero.getUnspentPoints() > 0) {
            System.out.println("\nYour abilities: ");
            for(Map.Entry<Ability,Integer> entry: hero.getAbilities().entrySet()) {
                System.out.print(entry.getKey() + ": " + entry.getValue() + ", ");
            }
            printAbilitiesToUpgrade(hero.getUnspentPoints());
            setAbilityToUpgrade(scanner.nextInt(), hero);
        }
    }

    private static void printAbilitiesToUpgrade(int unspentPoints) {
        System.out.println("Choose ability to upgrade:");
        System.out.println("You have " + unspentPoints + " to spend.");
        System.out.println("0. Explain abilities\n1. Attack\n2. Defence\n3. Dexterity\n4. Skill\n5. Luck\n6. Health");
    }

    private static void setAbilityToUpgrade(int numberOfAbility, Hero hero) {
        switch (numberOfAbility) {
            case 0 -> {
                explainAbilities(hero);
            }
            case 1 -> setNewAbilitiesPoints(Ability.ATTACK, hero);
            case 2 -> setNewAbilitiesPoints(Ability.DEFENCE, hero);
            case 3 -> setNewAbilitiesPoints(Ability.DEXTERITY, hero);
            case 4 -> setNewAbilitiesPoints(Ability.SKILL, hero);
            case 5 -> setNewAbilitiesPoints(Ability.LUCK, hero);
            case 6 -> setNewAbilitiesPoints(Ability.HEALTH, hero);



        }
    }

    private static void setNewAbilitiesPoints(Ability ability, Hero hero) {
        hero.getAbilities().put(ability, hero.getAbilities().get(ability) + 1);
        hero.setUnspentPoints(hero.getUnspentPoints() - 1);
    }

    private static void explainAbilities(Hero hero) {
        for(Map.Entry<Ability,Integer> entry: hero.getAbilities().entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getKey().getDescription());
        }
        System.out.println();
    }
}