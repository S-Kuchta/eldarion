package kuchtastefan.actions;

import kuchtastefan.ability.Ability;
import kuchtastefan.character.GameCharacter;
import kuchtastefan.utility.RandomNumberGenerator;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public abstract class Action {

    protected ActionName actionName;
    protected final int maxActionValue;
    protected int currentActionValue;
    protected final ActionEffectOn actionEffectOn;
    protected final int chanceToPerformAction;
    protected final boolean canBeActionCriticalHit;


    public Action(ActionName actionName, ActionEffectOn actionEffectOn, int maxActionValue, int chanceToPerformAction, boolean canBeActionCriticalHit) {
        this.actionName = actionName;
        this.maxActionValue = maxActionValue;
        this.currentActionValue = maxActionValue;
        this.actionEffectOn = actionEffectOn;
        this.chanceToPerformAction = chanceToPerformAction;
        this.canBeActionCriticalHit = canBeActionCriticalHit;
    }

    public abstract void performAction(GameCharacter gameCharacter);

    public abstract void printActionDescription(GameCharacter actionCaster, GameCharacter actionTarget);

    public boolean willPerformAction() {
        return RandomNumberGenerator.getRandomNumber(0, 100) <= this.chanceToPerformAction;
    }

    public void setNewCurrentActionValue(int value) {
        this.currentActionValue = value;
    }

    public int returnTotalActionValue(Map<Ability, Integer> bonusValueFromAbility, GameCharacter spellCaster) {
        int totalActionValue = this.maxActionValue + (spellCaster.getLevel() * 2);
        if (bonusValueFromAbility != null) {
            for (Map.Entry<Ability, Integer> abilityBonus : bonusValueFromAbility.entrySet()) {
                totalActionValue += spellCaster.getCurrentAbilityValue(abilityBonus.getKey()) * abilityBonus.getValue();
            }
        }

        return totalActionValue;
    }
}
