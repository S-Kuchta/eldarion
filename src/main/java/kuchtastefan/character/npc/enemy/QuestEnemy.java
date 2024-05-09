package kuchtastefan.character.npc.enemy;

import kuchtastefan.ability.Ability;
import kuchtastefan.character.hero.Hero;
import kuchtastefan.character.npc.CharacterType;
import kuchtastefan.quest.questObjectives.QuestObjectiveTarget;
import kuchtastefan.world.Biome;
import lombok.Getter;

import java.util.Map;

@Getter
public class QuestEnemy extends Enemy implements QuestObjectiveTarget {

    final int questObjectiveId;

    public QuestEnemy(String name, Map<Ability, Integer> abilities, CharacterType characterType, Biome[] biome, int maxStack, int[] enemySpells, int questObjectiveId) {
        super(name, abilities, characterType, biome, maxStack, enemySpells);
        this.questObjectiveId = questObjectiveId;
    }

//    @Override
//    public void makeProgressInQuestObjective(Hero hero) {
//        if (hero.getHeroQuests().containsQuestObjective(questObjectiveId)) {
//            hero.getHeroQuests().getQuestObjective(this.questObjectiveId).questObjectiveAssignment(hero);
//        }
//    }
}
