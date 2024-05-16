package kuchtastefan.actions.instantAction;

import kuchtastefan.actions.Action;
import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.actions.ActionName;
import kuchtastefan.actions.actionValue.ActionWithoutValue;
import kuchtastefan.character.GameCharacter;
import kuchtastefan.character.npc.CharacterDB;
import kuchtastefan.character.npc.NonPlayerCharacter;
import kuchtastefan.utility.ConsoleColor;
import lombok.Getter;

@Getter
public class ActionSummonCreature extends Action implements ActionWithoutValue {

    private final int summonedNpcId;

    public ActionSummonCreature(ActionName actionName, ActionEffectOn actionEffectOn, int maxActionValue,
                                int chanceToPerformAction, int summonedNpcId) {

        super(actionName, actionEffectOn, maxActionValue, chanceToPerformAction);
        this.summonedNpcId = summonedNpcId;
    }

    @Override
    public void performAction() {
        System.out.println("\t" + charactersInvolvedInBattle.getSpellCaster().getName() + " summoned " + CharacterDB.CHARACTER_DB.get(this.summonedNpcId).getName());
        this.charactersInvolvedInBattle.getTempCharacterList().add(returnSummonedCharacter(charactersInvolvedInBattle.getSpellCaster().getLevel()));
    }

    private NonPlayerCharacter returnSummonedCharacter(int gameCharacterLevel) {
        NonPlayerCharacter nonPlayerCharacter = CharacterDB.returnNewCharacter(this.summonedNpcId);
        nonPlayerCharacter.increaseAbilityPointsByMultiplier(gameCharacterLevel);

        return nonPlayerCharacter;
    }

    @Override
    public void printActionDescription(GameCharacter spellCaster, GameCharacter spellTarget) {
        System.out.print("Summon " + ConsoleColor.YELLOW + returnSummonedCharacter(spellCaster.getLevel()).getName() + ConsoleColor.RESET + " to fight on your side");
    }

    @Override
    public int returnPriorityPoints(GameCharacter spellCaster, GameCharacter spellTarget) {
        return 4;
    }
}
