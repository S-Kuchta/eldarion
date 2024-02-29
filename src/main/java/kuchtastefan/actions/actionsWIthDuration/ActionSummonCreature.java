package kuchtastefan.actions.actionsWIthDuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kuchtastefan.actions.Action;
import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.actions.ActionName;
import kuchtastefan.character.GameCharacter;
import kuchtastefan.character.NpcCharacter;
import kuchtastefan.character.enemy.CharacterList;
import kuchtastefan.utility.RuntimeTypeAdapterFactoryUtil;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;

@Getter
public class ActionSummonCreature extends Action {

    private final int npcCharacterId;

    public ActionSummonCreature(ActionName actionName, ActionEffectOn actionEffectOn, int maxActionValue,
                                int chanceToPerformAction, boolean canBeActionCriticalHit, int npcCharacterId) {
        super(actionName, actionEffectOn, maxActionValue, chanceToPerformAction, canBeActionCriticalHit);
        this.npcCharacterId = npcCharacterId;
    }

    @Override
    public void performAction(GameCharacter gameCharacter) {
        System.out.println("\t" + gameCharacter.getName() + " summoned " + CharacterList.getEnemyMap().get(this.npcCharacterId).getName());
    }

    public GameCharacter returnSummonedCharacter() {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(RuntimeTypeAdapterFactoryUtil.actionsRuntimeTypeAdapterFactory).create();

        NpcCharacter npcCharacter = gson.fromJson(gson.toJson(CharacterList.getEnemyMap().get(this.npcCharacterId)), NpcCharacter.class);
        npcCharacter.setMaxAbilitiesAndCurrentAbilities();
        npcCharacter.setCanPerformAction(true);
        npcCharacter.setBattleActionsWithDuration(new HashSet<>());
        npcCharacter.setRegionActionsWithDuration(new HashSet<>());
        npcCharacter.setCharacterSpellList(new ArrayList<>());
        npcCharacter.convertSpellIdToSpellList();

        return npcCharacter;
    }
}
