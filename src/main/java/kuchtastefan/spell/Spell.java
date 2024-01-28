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
        this.currentTurnCoolDown = turnCoolDown;
        this.canSpellBeCasted = true;
    }

    public boolean useSpell(GameCharacter spellCaster, GameCharacter spellTarget) {
        System.out.println("current turn: " + currentTurnCoolDown);
        if (this.canSpellBeCasted && spellCaster.getCurrentAbilityValue(Ability.MANA) >= this.spellManaCost) {
            for (Action action : this.spellActions) {
                if (action.willPerformAction()) {

                    int totalActionValue = action.getMaxActionValue();
                    if (this.bonusValueFromAbility != null) {
                        for (Map.Entry<Ability, Integer> abilityBonus : this.bonusValueFromAbility.entrySet()) {
                            totalActionValue += spellCaster.getCurrentAbilityValue(abilityBonus.getKey())
                                    * abilityBonus.getValue();
                            System.out.println("ability: " + abilityBonus.getKey() + ", value: " + abilityBonus.getValue());
                        }
                    }

                    System.out.println("total value: " + totalActionValue);
                    action.setNewActionValue(totalActionValue);

                    if (action.getActionEffectOn().equals(ActionEffectOn.SPELL_TARGET)) {
                        actionOrActionWithDuration(action, spellTarget);
                    }

                    if (action.getActionEffectOn().equals(ActionEffectOn.SPELL_CASTER)) {
                        actionOrActionWithDuration(action, spellCaster);
                    }
                }
            }

            spellCaster.lowerAbility(this.spellManaCost, Ability.MANA);
            this.currentTurnCoolDown = 0;
            checkTurnCoolDown();
            return true;
        } else {
            if (spellCaster.getCurrentAbilityValue(Ability.MANA) < this.spellManaCost) {
                System.out.println("\tYou do not have enough Mana to perform this ability!");
            } else {
                System.out.println("\tYou can not cast " + this.spellName + ". Spell is on coolDown! (You have to wait "
                        + ((this.turnCoolDown - this.currentTurnCoolDown) + 1) + " turns)");
            }

            return false;
        }
    }

    private void actionOrActionWithDuration(Action action, GameCharacter effectOnCharacter) {
        if (action instanceof ActionWithDuration) {
            effectOnCharacter.addActionWithDuration((ActionWithDuration) action);
        } else {
            action.performAction(effectOnCharacter);
        }
    }

    public void increaseTurnCoolDown() {
        checkTurnCoolDown();
        this.currentTurnCoolDown++;
    }

    private void checkTurnCoolDown() {
        if (this.currentTurnCoolDown < this.turnCoolDown) {
            this.canSpellBeCasted = false;
        }

        if (this.currentTurnCoolDown >= this.turnCoolDown) {
            this.canSpellBeCasted = true;
        }
    }
}
