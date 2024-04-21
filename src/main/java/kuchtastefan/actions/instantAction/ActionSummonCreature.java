package kuchtastefan.actions.instantAction;

import kuchtastefan.actions.Action;
import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.actions.ActionName;
import kuchtastefan.actions.actionsWIthDuration.actionMarkerInterface.ActionWithoutValue;
import kuchtastefan.character.GameCharacter;
import kuchtastefan.character.npc.CharacterDB;
import kuchtastefan.character.npc.NonPlayerCharacter;
import kuchtastefan.utility.ConsoleColor;
import lombok.Getter;

@Getter
public class ActionSummonCreature extends Action implements ActionWithoutValue {

    private final int summonedNpcId;

    public ActionSummonCreature(ActionName actionName, ActionEffectOn actionEffectOn, int maxActionValue,
                                int chanceToPerformAction, boolean canBeActionCriticalHit, int summonedNpcId) {

        super(actionName, actionEffectOn, maxActionValue, chanceToPerformAction, canBeActionCriticalHit);
        this.summonedNpcId = summonedNpcId;
    }

    @Override
    public void performAction(GameCharacter gameCharacter) {
        System.out.println("\t" + gameCharacter.getName() + " summoned " + CharacterDB.CHARACTER_DB.get(this.summonedNpcId).getName());
        this.charactersInvolvedInBattle.getTempCharacterList().add(returnSummonedCharacter(gameCharacter.getLevel()));
    }

    public NonPlayerCharacter returnSummonedCharacter(int gameCharacterLevel) {
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
