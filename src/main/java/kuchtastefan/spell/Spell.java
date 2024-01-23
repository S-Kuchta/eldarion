package kuchtastefan.spell;

import kuchtastefan.ability.Ability;
import kuchtastefan.actions.Action;
import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.actions.actionsWIthDuration.ActionWithDuration;
import kuchtastefan.characters.GameCharacter;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class Spell {
    private final String spellName;
    private final String spellDescription;
    private final List<Action> spellActions;
    private final Map<Ability, Integer> bonusValueFromAbility;
    private final int spellLevel;


    public Spell(String spellName, String spellDescription, List<Action> spellActions, Map<Ability, Integer> bonusValueFromAbility, int spellLevel) {
        this.spellName = spellName;
        this.spellDescription = spellDescription;
        this.spellActions = spellActions;
        this.bonusValueFromAbility = bonusValueFromAbility;
        this.spellLevel = spellLevel;
    }

    public void useSpell(GameCharacter spellCaster, GameCharacter spellTarget) {
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
        }
    }

    private void actionOrActionWithDuration(Action action, GameCharacter effectOn) {
        if (action instanceof ActionWithDuration) {
            effectOn.addActionWithDuration((ActionWithDuration) action);
        } else {
            action.performAction(effectOn);
        }
    }
}
