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
        System.out.println("\t" + gameCharacter.getName() + " summoned " + CharacterList.getAllCharactersMapWithId().get(this.summonedNpcId).getName());
    }

    public GameCharacter returnSummonedCharacter() {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(RuntimeTypeAdapterFactoryUtil.actionsRuntimeTypeAdapterFactory).create();

        NonPlayerCharacter nonPlayerCharacter = gson.fromJson(gson.toJson(CharacterList.getAllCharactersMapWithId().get(this.summonedNpcId)), NonPlayerCharacter.class);
        nonPlayerCharacter.setMaxAbilitiesAndCurrentAbilities();
        nonPlayerCharacter.setCanPerformAction(true);
        nonPlayerCharacter.setBattleActionsWithDuration(new HashSet<>());
        nonPlayerCharacter.setRegionActionsWithDuration(new HashSet<>());
        nonPlayerCharacter.setCharacterSpellList(new ArrayList<>());
        nonPlayerCharacter.convertSpellIdToSpellList();
        nonPlayerCharacter.increaseAbilityPointsByAddition(this.gameCharacterLevel);

        return nonPlayerCharacter;
    }
}
