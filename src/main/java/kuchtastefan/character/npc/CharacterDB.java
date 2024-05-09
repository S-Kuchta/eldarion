package kuchtastefan.character.npc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kuchtastefan.character.hero.Hero;
import kuchtastefan.character.npc.enemy.Enemy;
import kuchtastefan.character.npc.enemy.QuestEnemy;
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
    public static final Map<Integer, QuestEnemy> QUEST_ENEMY_DB = new HashMap<>();

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

        if (character instanceof QuestEnemy questEnemy) {
            QUEST_ENEMY_DB.put(character.getNpcId(), questEnemy);
            System.out.println("Quest enemy added to db" + questEnemy.getName());
        } else if (character instanceof Enemy enemy) {
            CHARACTER_DB.put(character.getNpcId(), enemy);
            System.out.println("Enemy added to db" + enemy.getName());
        } else {
            CHARACTER_DB.put(character.getNpcId(), character);
            System.out.println("Character added to db" + character.getName());
        }
    }
}
