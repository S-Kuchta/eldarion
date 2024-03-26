package kuchtastefan.actions.instantActions;

import kuchtastefan.actions.Action;
import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.actions.ActionName;
import kuchtastefan.character.GameCharacter;
import kuchtastefan.character.npc.CharacterDB;
import kuchtastefan.character.npc.NonPlayerCharacter;
import lombok.Getter;

@Getter
public class ActionSummonCreature extends Action {

    private final int summonedNpcId;
    private int gameCharacterLevel;

    public ActionSummonCreature(ActionName actionName, ActionEffectOn actionEffectOn, int maxActionValue,
                                int chanceToPerformAction, boolean canBeActionCriticalHit, int summonedNpcId) {

        super(actionName, actionEffectOn, maxActionValue, chanceToPerformAction, canBeActionCriticalHit);
        this.summonedNpcId = summonedNpcId;
    }

    @Override
    public void performAction(GameCharacter gameCharacter) {
        this.gameCharacterLevel = gameCharacter.getLevel();
        System.out.println("\t" + gameCharacter.getName() + " summoned " + CharacterDB.CHARACTER_DB.get(this.summonedNpcId).getName());
    }

    public NonPlayerCharacter returnSummonedCharacter() {
        NonPlayerCharacter nonPlayerCharacter = CharacterDB.returnNewCharacter(this.summonedNpcId);
        nonPlayerCharacter.increaseAbilityPointsByMultiplier(this.gameCharacterLevel);

        return nonPlayerCharacter;
    }
}
