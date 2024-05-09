package kuchtastefan.character.npc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kuchtastefan.character.hero.Hero;
import kuchtastefan.character.npc.enemy.Enemy;
import kuchtastefan.quest.Quest;
import kuchtastefan.utility.RuntimeTypeAdapterFactoryUtil;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class CharacterDB {

    @Getter
    public static final Map<Integer, NonPlayerCharacter> CHARACTER_DB = new HashMap<>();
    private static final Gson GSON = new GsonBuilder().registerTypeAdapterFactory(RuntimeTypeAdapterFactoryUtil.actionsRuntimeTypeAdapterFactory).create();

    public static Enemy returnNewEnemy(int characterId) {
        Enemy newEnemy = GSON.fromJson(GSON.toJson(CHARACTER_DB.get(characterId)), Enemy.class);
        newEnemy.setItemDrop();
        newEnemy.setGoldDrop();

        return newEnemy;
    }

    public static NonPlayerCharacter returnNewCharacter(int characterId) {
        return GSON.fromJson(GSON.toJson(CHARACTER_DB.get(characterId)), NonPlayerCharacter.class);
    }

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
}
