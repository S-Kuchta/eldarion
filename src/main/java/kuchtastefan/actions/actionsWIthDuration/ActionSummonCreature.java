package kuchtastefan.actions.actionsWIthDuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kuchtastefan.actions.Action;
import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.actions.ActionName;
import kuchtastefan.character.GameCharacter;
import kuchtastefan.character.npc.NonPlayerCharacter;
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
        System.out.println("\t" + gameCharacter.getName() + " summoned " + CharacterList.getAllCharactersMapWithId().get(this.npcCharacterId).getName());
    }

    public GameCharacter returnSummonedCharacter() {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(RuntimeTypeAdapterFactoryUtil.actionsRuntimeTypeAdapterFactory).create();

        NonPlayerCharacter nonPlayerCharacter = gson.fromJson(gson.toJson(CharacterList.getAllCharactersMapWithId().get(this.npcCharacterId)), NonPlayerCharacter.class);
        nonPlayerCharacter.setMaxAbilitiesAndCurrentAbilities();
        nonPlayerCharacter.setCanPerformAction(true);
        nonPlayerCharacter.setBattleActionsWithDuration(new HashSet<>());
        nonPlayerCharacter.setRegionActionsWithDuration(new HashSet<>());
        nonPlayerCharacter.setCharacterSpellList(new ArrayList<>());
        nonPlayerCharacter.convertSpellIdToSpellList();

        return nonPlayerCharacter;
    }
}
