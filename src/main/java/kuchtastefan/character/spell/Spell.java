package kuchtastefan.character.spell;

import kuchtastefan.ability.Ability;
import kuchtastefan.actions.Action;
import kuchtastefan.character.hero.CharacterClass;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Spell {
    private final int spellId;
    private final String spellName;
    private final String spellDescription;
    private final List<Action> spellActions;
    private final Map<Ability, Integer> bonusValueFromAbility;
    private final int spellLevel;
    private int spellManaCost;
    private final int turnCoolDown;
    private int currentTurnCoolDown;
    private boolean canSpellBeCasted;
    private final CharacterClass spellClass;
    private final boolean hitAllEnemy;


    public Spell(int spellId, String spellName, String spellDescription, List<Action> spellActions, int turnCoolDown,
                 Map<Ability, Integer> bonusValueFromAbility, int spellLevel, int spellManaCost,
                 CharacterClass spellClass, boolean hitAllEnemy) {

        this.spellId = spellId;
        this.spellName = spellName;
        this.spellDescription = spellDescription;
        this.spellActions = spellActions;
        this.bonusValueFromAbility = bonusValueFromAbility;
        this.spellLevel = spellLevel;
        this.spellManaCost = spellManaCost;
        this.turnCoolDown = turnCoolDown;
        this.currentTurnCoolDown = turnCoolDown;
        this.hitAllEnemy = hitAllEnemy;
        this.canSpellBeCasted = true;
        this.spellClass = spellClass;
    }

//    /**
//     * Attempts to use the spell on the specified target by the caster.
//     * Checks if the spell can be cast and if the caster has enough mana.
//     * If successful, performs the spell actions, applies bonuses from caster's abilities,
//     * and handles critical hit chance. Then updates caster's mana, coolDown, and performs actions on the target.
//     *
//     * @param spellCaster The character casting the spell.
//     * @param spellTarget The character targeted by the spell.
//     * @return True if the spell was successfully cast, false otherwise.
//     */
//    public boolean useSpell(GameCharacter spellCaster, GameCharacter spellTarget, List<GameCharacter> enemyCharacters, Hero hero, List<GameCharacter> tempCharacterList) {
//
//        if (this.canSpellBeCasted && spellCaster.getCurrentAbilityValue(Ability.MANA) >= this.spellManaCost) {
//
//            System.out.println("\t" + spellCaster.getName() + " use " + ConsoleColor.MAGENTA + this.spellName + ConsoleColor.RESET);
//
//            if (hitEnemy(spellCaster, spellTarget)) {
//                boolean criticalHit = RandomNumberGenerator.getRandomNumber(1, 100) <= spellCaster.getCurrentAbilityValue(Ability.CRITICAL_HIT_CHANCE);
//
//                if (spellTarget.isReflectSpell()) {
//                    for (Action action : spellTarget.getBattleActionsWithDuration()) {
//                        if (action instanceof ActionReflectSpell) {
//                            spellTarget.getBattleActionsWithDuration().remove(action);
//                            spellTarget = spellCaster;
//                            break;
//                        }
//                    }
//                }
//
//                for (Action action : this.spellActions) {
//                    if (this.hitAllEnemy) {
//                        for (GameCharacter actionTarget : enemyCharacters) {
//                            performAction(action, spellCaster, actionTarget, criticalHit, tempCharacterList);
//                        }
//                    } else if (action.getActionEffectOn().equals(ActionEffectOn.PLAYER)) {
//                        performAction(action, spellCaster, hero, criticalHit, tempCharacterList);
//                    } else {
//                        performAction(action, spellCaster, spellTarget, criticalHit, tempCharacterList);
//                    }
//                }
//            } else {
//                System.out.println("\t" + ConsoleColor.RED + spellCaster.getName() + " Missed Enemy!");
//            }
//
//            spellCaster.decreaseCurrentAbilityValue(this.spellManaCost, Ability.MANA);
//            this.currentTurnCoolDown = 0;
//            checkSpellCoolDown();
//            return true;
//        } else {
//            if (spellCaster.getCurrentAbilityValue(Ability.MANA) < this.spellManaCost) {
//                System.out.println(ConsoleColor.RED + "\tYou do not have enough Mana to perform this ability!" + ConsoleColor.RESET);
//            } else {
//                System.out.println(ConsoleColor.RED + "\tYou can not cast " + this.spellName + ". Spell is on coolDown! (You have to wait "
//                        + ((this.turnCoolDown - this.currentTurnCoolDown) + 1) + " turns)" + ConsoleColor.RESET);
//            }
//
//            return false;
//        }
//    }
//
//    private boolean hitEnemy(GameCharacter attacker, GameCharacter defender) {
//        int attackerHaste = attacker.getCurrentAbilityValue(Ability.HASTE);
//        int defenderHaste = defender.getCurrentAbilityValue(Ability.HASTE);
//
//        if (attackerHaste >= defenderHaste) {
//            return true;
//        } else {
//            int chanceToMiss = defenderHaste - attackerHaste;
//            return chanceToMiss <= RandomNumberGenerator.getRandomNumber(0, 100);
//        }
//    }
//
//    private void performAction(Action action, GameCharacter spellCaster, GameCharacter spellTarget, boolean criticalHit, List<GameCharacter> tempCharacterList) {
//
//        action.setCurrentActionValue(action.getBaseActionValue());
//
//        if (action.willPerformAction()) {
//            int totalActionValue = action.returnTotalActionValue(this.bonusValueFromAbility, spellCaster);
//
//            if (criticalHit && action.isCanBeActionCriticalHit()) {
//                System.out.println("\t" + action.getActionName() + " Critical hit!");
//                totalActionValue *= Constant.CRITICAL_HIT_MULTIPLIER;
//            }
//
//            if (action instanceof ActionIncreaseAbilityPoint || action instanceof ActionDecreaseAbilityPoint) {
//                action.setCurrentActionValue(totalActionValue);
//            } else {
//                action.setCurrentActionValue(RandomNumberGenerator.getRandomNumber(
//                        (int) (totalActionValue * Constant.LOWER_DAMAGE_MULTIPLIER), totalActionValue));
//            }
//
//            if (action instanceof ActionSummonCreature) {
//                tempCharacterList.add(((ActionSummonCreature) action).returnSummonedCharacter());
//            }
//
//            if (action instanceof ActionStun) {
//                action.performAction(spellTarget);
//            }
//
//            if (action.getActionEffectOn().equals(ActionEffectOn.SPELL_CASTER)) {
//                actionOrActionWithDuration(action, spellCaster);
//            } else {
//                actionOrActionWithDuration(action, spellTarget);
//            }
//        }
//    }
//
//    private void actionOrActionWithDuration(Action action, GameCharacter effectOnCharacter) {
//        if (action instanceof ActionWithDuration) {
//            effectOnCharacter.addActionWithDuration((ActionWithDuration) action);
//        } else {
//            action.performAction(effectOnCharacter);
//        }
//    }

    public void increaseSpellCoolDown() {
        checkSpellCoolDown();
        this.currentTurnCoolDown++;
    }

    public void checkSpellCoolDown() {
        if (this.currentTurnCoolDown < this.turnCoolDown) {
            this.canSpellBeCasted = false;
        }

        if (this.currentTurnCoolDown >= this.turnCoolDown) {
            this.canSpellBeCasted = true;
        }
    }
}
