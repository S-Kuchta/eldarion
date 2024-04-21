package kuchtastefan.actions.actionsWIthDuration.specificActionWithDuration;

import kuchtastefan.ability.Ability;
import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.actions.ActionName;
import kuchtastefan.actions.ActionStatusEffect;
import kuchtastefan.actions.actionsWIthDuration.ActionWithDuration;
import kuchtastefan.actions.actionsWIthDuration.actionMarkerInterface.ActionWithIncreasedValueByPrimaryAbility;
import kuchtastefan.character.GameCharacter;
import kuchtastefan.utility.ConsoleColor;

public class ActionAbsorbDamage extends ActionWithDuration implements ActionWithIncreasedValueByPrimaryAbility {

    public ActionAbsorbDamage(ActionName actionName, ActionEffectOn actionEffectOn, int baseActionValue,
                              int maxActionTurns, int actionMaxStacks, int chanceToPerformAction,
                              boolean canBeActionCriticalHit, ActionStatusEffect actionStatusEffect) {

        super(actionName, actionEffectOn, baseActionValue, maxActionTurns, actionMaxStacks,
                chanceToPerformAction, canBeActionCriticalHit, actionStatusEffect);
    }

    @Override
    public void performAction(GameCharacter gameCharacter) {
        int increaseAbilityWithStacks = this.getCurrentActionValue() * this.getActionCurrentStacks();
        gameCharacter.increaseEffectiveAbilityValue(Ability.ABSORB_DAMAGE, increaseAbilityWithStacks);
    }

    @Override
    public void printActionDescription(GameCharacter spellCaster, GameCharacter spellTarget) {
        System.out.print("Absorb "
                + ConsoleColor.CYAN + this.returnActionValueRange(spellCaster).minimumValue() + ConsoleColor.RESET
                + " - " + ConsoleColor.CYAN + this.returnActionValueRange(spellCaster).maximumValue() + ConsoleColor.RESET
                + " incoming damage");
    }

    @Override
    public int returnPriorityPoints(GameCharacter spellCaster, GameCharacter spellTarget) {
        if (spellCaster.getEffectiveAbilityValue(Ability.HEALTH) < spellCaster.getEnhancedAbilities().get(Ability.HEALTH) / 3) {
            return 4;
        } else {
            return 2;
        }
    }
}
