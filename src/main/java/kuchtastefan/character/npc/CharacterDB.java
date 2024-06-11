package kuchtastefan.character.npc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kuchtastefan.character.npc.enemy.Enemy;
import kuchtastefan.utility.RuntimeTypeAdapterFactoryUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class CharacterDB {

    public static final Map<Integer, NonPlayerCharacter> CHARACTER_DB = new HashMap<>();

    private static final Gson GSON = new GsonBuilder().registerTypeAdapterFactory(RuntimeTypeAdapterFactoryUtil.actionsRuntimeTypeAdapterFactory).create();

    public static void addCharacterToDB(NonPlayerCharacter character) {
        if (character.getCharacterSpellList() == null) {
            character.setCharacterSpellList(new ArrayList<>());
        }

        character.convertSpellIdToSpellList();
        character.setCanPerformAction(true);
        character.setMaxAbilitiesAndCurrentAbilities();

        if (character.getBuffsAndDebuffs() == null) {
            character.setBuffsAndDebuffs(new HashSet<>());
        }

        CHARACTER_DB.put(character.getNpcId(), character);
    }

    public static Enemy returnNewEnemy(int characterId) {
        Enemy enemy = (Enemy) CHARACTER_DB.get(characterId);
        Enemy newEnemy = GSON.fromJson(GSON.toJson(enemy), enemy.getClass());

        newEnemy.setItemDrop();
        newEnemy.setGoldDrop();
        return newEnemy;
    }

    public static NonPlayerCharacter getCharacter(int characterId) {
        return CHARACTER_DB.get(characterId);
    }

    public static NonPlayerCharacter returnNewSummonedCreature(int characterId) {
        return GSON.fromJson(GSON.toJson(CHARACTER_DB.get(characterId)), NonPlayerCharacter.class);
    }
}
