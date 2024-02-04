package kuchtastefan.characters.spell;

import kuchtastefan.ability.Ability;
import kuchtastefan.actions.Action;
import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.actions.actionsWIthDuration.ActionWithDuration;
import kuchtastefan.characters.GameCharacter;
import kuchtastefan.characters.hero.HeroClass;
import kuchtastefan.constant.Constant;
import kuchtastefan.utility.ConsoleColor;
import kuchtastefan.utility.RandomNumberGenerator;
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
    private final HeroClass spellClass;


    public Spell(String spellName, String spellDescription, List<Action> spellActions, int turnCoolDown,
                 Map<Ability, Integer> bonusValueFromAbility, int spellLevel, int spellManaCost, HeroClass spellClass) {

        this.spellName = spellName;
        this.spellDescription = spellDescription;
        this.spellActions = spellActions;
        this.bonusValueFromAbility = bonusValueFromAbility;
        this.spellLevel = spellLevel;
        this.spellManaCost = spellManaCost;
        this.turnCoolDown = turnCoolDown;
        this.currentTurnCoolDown = turnCoolDown;
        this.canSpellBeCasted = true;
        this.spellClass = spellClass;
    }

    /**
     * Attempts to use the spell on the specified target by the caster.
     * Checks if the spell can be cast and if the caster has enough mana.
     * If successful, performs the spell actions, applies bonuses from caster's abilities,
     * and handles critical hit chance. Then updates caster's mana, coolDown, and performs actions on the target.
     *
     * @param spellCaster The character casting the spell.
     * @param spellTarget The character targeted by the spell.
     * @return True if the spell was successfully cast, false otherwise.
     */
    public boolean useSpell(GameCharacter spellCaster, GameCharacter spellTarget) {
        if (this.canSpellBeCasted && spellCaster.getCurrentAbilityValue(Ability.MANA) >= this.spellManaCost) {
            System.out.println("\t" + spellCaster.getName() + " use " + ConsoleColor.MAGENTA + this.spellName + ConsoleColor.RESET);

            boolean criticalHit = RandomNumberGenerator.getRandomNumber(1, 100)
                    <= spellCaster.getCurrentAbilityValue(Ability.CRITICAL_HIT_CHANCE);

            for (Action action : this.spellActions) {
                action.setNewCurrentActionValue(action.getMaxActionValue());

                if (action.willPerformAction()) {
                    int totalActionValue = action.getMaxActionValue();

                    if (this.bonusValueFromAbility != null) {
                        for (Map.Entry<Ability, Integer> abilityBonus : this.bonusValueFromAbility.entrySet()) {
                            totalActionValue += spellCaster.getCurrentAbilityValue(abilityBonus.getKey())
                                    * abilityBonus.getValue();
                        }
                    }

                    if (criticalHit && action.isCanBeActionCriticalHit()) {
                        System.out.println("\t" + action.getActionName() + " Critical hit!");
                        totalActionValue *= Constant.CRITICAL_HIT_MULTIPLIER;
                    }
                    action.setNewCurrentActionValue(RandomNumberGenerator.getRandomNumber(
                            (int) (totalActionValue * Constant.LOWER_DAMAGE_MULTIPLIER), totalActionValue));

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

    public void checkTurnCoolDown() {
        if (this.currentTurnCoolDown < this.turnCoolDown) {
            this.canSpellBeCasted = false;
        }

        if (this.currentTurnCoolDown >= this.turnCoolDown) {
            this.canSpellBeCasted = true;
        }
    }
}
