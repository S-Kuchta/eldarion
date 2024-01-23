package kuchtastefan.spell;

import kuchtastefan.ability.Ability;
import kuchtastefan.actions.Action;
import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.actions.actionsWIthDuration.ActionWithDuration;
import kuchtastefan.characters.GameCharacter;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Spell {
    private final String spellName;
    private final String spellDescription;
    private final List<Action> spellActions;
    private final Map<Ability, Integer> bonusValueFromAbility;
    private final int spellLevel;
    private final int spellManaCost;
    private final int turnCoolDown;
    private int currentTurnCoolDown;
    private boolean canSpellBeCasted;


    public Spell(String spellName, String spellDescription, List<Action> spellActions, int turnCoolDown,
                 Map<Ability, Integer> bonusValueFromAbility, int spellLevel, int spellManaCost) {
        this.spellName = spellName;
        this.spellDescription = spellDescription;
        this.spellActions = spellActions;
        this.bonusValueFromAbility = bonusValueFromAbility;
        this.spellLevel = spellLevel;
        this.spellManaCost = spellManaCost;
        this.turnCoolDown = turnCoolDown;
        this.currentTurnCoolDown = 0;
        this.canSpellBeCasted = true;
    }

    public void useSpell(GameCharacter spellCaster, GameCharacter spellTarget) {
        if (this.canSpellBeCasted) {
            for (Action action : this.spellActions) {
                if (this.bonusValueFromAbility != null) {
                    for (Map.Entry<Ability, Integer> abilityBonus : this.bonusValueFromAbility.entrySet()) {
                        final int totalActionValue = action.getMaxActionValue()
                                + spellCaster.getCurrentAbilityValue(abilityBonus.getKey())
                                * abilityBonus.getValue();

                        action.setCurrentActionValue(totalActionValue);
                    }
                }

                if (action.getActionEffectOn().equals(ActionEffectOn.SPELL_TARGET)) {
                    actionOrActionWithDuration(action, spellTarget);
                }

                if (action.getActionEffectOn().equals(ActionEffectOn.SPELL_CASTER)) {
                    actionOrActionWithDuration(action, spellCaster);
                }

                this.currentTurnCoolDown = 0;
            }
        } else {
            System.out.println("\tYou can not cast spell. Spell is on coolDown! (You have to wait "
                    + (this.turnCoolDown - this.currentTurnCoolDown) + " turns)");
        }
    }

    private void actionOrActionWithDuration(Action action, GameCharacter effectOn) {
        if (action instanceof ActionWithDuration) {
            effectOn.addActionWithDuration((ActionWithDuration) action);
        } else {
            action.performAction(effectOn);
        }
    }

    public void increaseTurnCoolDown() {
        if (this.turnCoolDown == 0) {
            this.canSpellBeCasted = true;
        } else {
            this.currentTurnCoolDown++;
            checkTurnCoolDown();
        }

    }

    private void checkTurnCoolDown() {
        if (this.currentTurnCoolDown < this.turnCoolDown) {
            this.canSpellBeCasted = false;
        }

        if (this.currentTurnCoolDown == this.turnCoolDown) {
            this.canSpellBeCasted = true;
        }
    }
}
