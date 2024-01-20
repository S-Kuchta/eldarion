package kuchtastefan.actions;

import kuchtastefan.ability.Ability;
import kuchtastefan.characters.GameCharacter;

public class ActionRestoreHealth extends Action {

    public ActionRestoreHealth(String actionName, int actionValue) {
        super(actionName, actionValue);
    }

    @Override
    public void performAction(GameCharacter gameCharacter) {
        int maxCharacterHealth = gameCharacter.getMaxAbilities().get(Ability.HEALTH);
        int currentCharacterHealth = gameCharacter.getCurrentAbilityValue(Ability.HEALTH);

        if (maxCharacterHealth - currentCharacterHealth <= this.getActionValue()) {
            gameCharacter.getCurrentAbilities().put(Ability.HEALTH, maxCharacterHealth);
        } else {
            gameCharacter.getCurrentAbilities().put(Ability.HEALTH, currentCharacterHealth + this.getActionValue());
        }

        System.out.println("\tYou have restored " + this.getActionValue() + " health. Your healths are: "
                + gameCharacter.getCurrentAbilityValue(Ability.HEALTH));
    }
}
