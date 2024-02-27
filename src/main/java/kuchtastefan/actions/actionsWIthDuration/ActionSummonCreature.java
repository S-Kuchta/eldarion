package kuchtastefan.actions.actionsWIthDuration;

import kuchtastefan.actions.Action;
import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.actions.ActionName;
import kuchtastefan.character.GameCharacter;
import kuchtastefan.character.NpcCharacter;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;

@Getter
public class ActionSummonCreature extends Action {

    private final NpcCharacter npcCharacter;

    public ActionSummonCreature(ActionName actionName, ActionEffectOn actionEffectOn, int maxActionValue,
                                int chanceToPerformAction, boolean canBeActionCriticalHit, NpcCharacter npcCharacter) {
        super(actionName, actionEffectOn, maxActionValue, chanceToPerformAction, canBeActionCriticalHit);
        this.npcCharacter = npcCharacter;
    }

    @Override
    public void performAction(GameCharacter gameCharacter) {
        this.npcCharacter.setMaxAbilitiesAndCurrentAbilities();
        this.npcCharacter.setCanPerformAction(true);
        this.npcCharacter.setBattleActionsWithDuration(new HashSet<>());
        this.npcCharacter.setRegionActionsWithDuration(new HashSet<>());
        this.npcCharacter.setCharacterSpellList(new ArrayList<>());
        this.npcCharacter.convertSpellIdToSpellList();
    }
}
