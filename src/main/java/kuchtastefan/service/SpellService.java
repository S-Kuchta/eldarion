package kuchtastefan.service;

import kuchtastefan.ability.Ability;
import kuchtastefan.actions.Action;
import kuchtastefan.character.GameCharacter;
import kuchtastefan.character.spell.Spell;
import kuchtastefan.character.spell.CharactersInvolvedInBattle;
import kuchtastefan.utility.ConsoleColor;
import kuchtastefan.utility.RandomNumberGenerator;

public class SpellService {

//    private final ActionService actionService;
//
//    public SpellService() {
//        this.actionService = new ActionService();
//    }

    /**
     * Attempts to use the spell on the specified target by the caster.
     * Checks if the spell can be cast and if the caster has enough mana.
     * If successful, performs the spell actions, applies bonuses from caster's abilities,
     * and handles critical hit chance. Then updates caster's mana, coolDown, and performs actions on the target.
     *
     *
     * @param charactersInvolvedInBattle The characters targeted by the spell.
     * @return True if the spell was successfully cast, false otherwise.
     */
    public boolean useSpell(Spell spell, CharactersInvolvedInBattle charactersInvolvedInBattle) {

        ActionService actionService = new ActionService();
        GameCharacter spellCaster = charactersInvolvedInBattle.spellCaster();
        GameCharacter spellTarget = charactersInvolvedInBattle.spellTarget();

        if (spell.isCanSpellBeCasted() && spellCaster.getCurrentAbilityValue(Ability.MANA) >= spell.getSpellManaCost()) {

            System.out.println("\t" + spellCaster.getName() + " use " + ConsoleColor.MAGENTA + spell.getSpellName() + ConsoleColor.RESET);

            if (isAttackSuccessful(spellCaster, spellTarget)) {
                boolean criticalHit = RandomNumberGenerator.getRandomNumber(1, 100) <= spellCaster.getCurrentAbilityValue(Ability.CRITICAL_HIT_CHANCE);

                for (Action action : spell.getSpellActions()) {
                    actionService.performAction(action, charactersInvolvedInBattle, criticalHit, spell.isHitAllEnemy());
                }
            } else {
                System.out.println("\t" + ConsoleColor.RED + spellCaster.getName() + " Missed Enemy!");
            }

            spellCaster.decreaseCurrentAbilityValue(spell.getSpellManaCost(), Ability.MANA);
            spell.setCurrentTurnCoolDown(0);
            spell.checkSpellCoolDown();
            return true;
        } else {
            if (spellCaster.getCurrentAbilityValue(Ability.MANA) < spell.getSpellManaCost()) {
                System.out.println(ConsoleColor.RED + "\tYou do not have enough Mana to perform this ability!" + ConsoleColor.RESET);
            } else {
                System.out.println(ConsoleColor.RED + "\tYou can not cast " + spell.getSpellName() + ". Spell is on coolDown! (You have to wait "
                        + ((spell.getTurnCoolDown() - spell.getCurrentTurnCoolDown()) + 1) + " turns)" + ConsoleColor.RESET);
            }

            return false;
        }
    }

    private boolean isAttackSuccessful(GameCharacter spellCaster, GameCharacter spellTarget) {
        int spellCasterHaste = spellCaster.getCurrentAbilityValue(Ability.HASTE);
        int spellTargetHaste = spellTarget.getCurrentAbilityValue(Ability.HASTE);

        if (spellCasterHaste >= spellTargetHaste) {
            return true;
        } else {
            int chanceToMiss = spellTargetHaste - spellCasterHaste;
            return chanceToMiss <= RandomNumberGenerator.getRandomNumber(0, 100);
        }
    }
}
