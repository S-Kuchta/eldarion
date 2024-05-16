package kuchtastefan.character.spell;

import kuchtastefan.ability.Ability;
import kuchtastefan.actions.Action;
import kuchtastefan.character.GameCharacter;
import kuchtastefan.character.hero.CharacterClass;
import kuchtastefan.utility.ConsoleColor;
import kuchtastefan.utility.RandomNumberGenerator;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Spell {

    private final int spellId;
    private final String spellName;
    private final String spellDescription;
    private final List<Action> spellActions;
    private final int spellLevel;
    private int spellManaCost;
    private final int turnCoolDown;
    private int currentTurnCoolDown;
    private boolean canSpellBeCasted;
    private final CharacterClass spellClass;
    private final boolean hitAllEnemy;


    public Spell(int spellId, String spellName, String spellDescription, List<Action> spellActions, int turnCoolDown,
                 int spellLevel, int spellManaCost, CharacterClass spellClass, boolean hitAllEnemy) {

        this.spellId = spellId;
        this.spellName = spellName;
        this.spellDescription = spellDescription;
        this.spellActions = spellActions;
        this.spellLevel = spellLevel;
        this.spellManaCost = spellManaCost;
        this.turnCoolDown = turnCoolDown;
        this.currentTurnCoolDown = turnCoolDown;
        this.hitAllEnemy = hitAllEnemy;
        this.canSpellBeCasted = true;
        this.spellClass = spellClass;
    }

    /**
     * Attempts to use the spell on the specified target by the caster.
     * Checks if the spell can be cast and if the caster has enough mana.
     * If successful, performs the spell actions, applies bonuses from caster's abilities,
     * and handles critical hit chance. Then updates caster's mana, coolDown, and performs actions on the target.
     *
     * @param charactersInvolvedInBattle The characters targeted by the spell.
     * @return True if the spell was successfully cast, false otherwise.
     */
    public boolean useSpell(CharactersInvolvedInBattle charactersInvolvedInBattle) {

        GameCharacter spellCaster = charactersInvolvedInBattle.getSpellCaster();
        GameCharacter spellTarget = charactersInvolvedInBattle.getSpellTarget();

        if (this.isCanSpellBeCasted() && spellCaster.getEffectiveAbilityValue(Ability.MANA) >= this.getSpellManaCost()) {

            System.out.println("\t" + spellCaster.getName() + " use " + this.getSpellName());
            if (isAttackSuccessful(spellCaster, spellTarget)) {
                performSuccessfulAttack(charactersInvolvedInBattle);
            } else {
                System.out.println("\t" + ConsoleColor.RED + spellCaster.getName() + " Missed Enemy!");
            }

            spellCaster.decreaseEffectiveAbilityValue(this.getSpellManaCost(), Ability.MANA);
            this.setCurrentTurnCoolDown(0);
            this.checkSpellCoolDown();

            return true;
        } else {
            if (spellCaster.getEffectiveAbilityValue(Ability.MANA) < this.getSpellManaCost()) {
                System.out.println(ConsoleColor.RED + "\tYou do not have enough Mana to perform this ability!" + ConsoleColor.RESET);
            } else {
                System.out.println(ConsoleColor.RED + "\tYou can not cast " + this.getSpellName() + ". Spell is on coolDown! (You have to wait "
                        + ((this.getTurnCoolDown() - this.getCurrentTurnCoolDown()) + 1) + " turns)" + ConsoleColor.RESET);
            }

            return false;
        }
    }

    private void performSuccessfulAttack(CharactersInvolvedInBattle charactersInvolvedInBattle) {
        for (Action action : this.getSpellActions()) {
            System.out.println("perform: " + action.getActionName());
            if (action.willPerformAction()) {
                charactersInvolvedInBattle.setSpellTarget(action.determineActionTarget(charactersInvolvedInBattle));
                if (this.hitAllEnemy) {
                    List<GameCharacter> characterList = charactersInvolvedInBattle.getCharacterList(action.determineActionTarget(charactersInvolvedInBattle), true);
                    for (GameCharacter character : characterList) {
                        charactersInvolvedInBattle.setSpellTarget(character);
                        action.handleAction(charactersInvolvedInBattle);
                    }
                } else {
                    action.handleAction(charactersInvolvedInBattle);
                }
            }
        }
    }

    private boolean isAttackSuccessful(GameCharacter spellCaster, GameCharacter spellTarget) {
        int chanceToMiss = spellTarget.getEffectiveAbilityValue(Ability.HASTE) - spellCaster.getEffectiveAbilityValue(Ability.HASTE);
        return chanceToMiss <= RandomNumberGenerator.getRandomNumber(0, 100);
    }

    public void increaseSpellCoolDown() {
        checkSpellCoolDown();
        this.currentTurnCoolDown++;
    }

    public void checkSpellCoolDown() {
        this.canSpellBeCasted = this.currentTurnCoolDown >= this.turnCoolDown;
    }

    public String getSpellName() {
        return ConsoleColor.MAGENTA + spellName + ConsoleColor.RESET;
    }

}
