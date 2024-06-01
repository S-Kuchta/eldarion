package kuchtastefan.character.hero.heroQuest;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public class HeroQuestList <T extends HeroQuestType> {

    private final Map<Integer, T> questEntities;

    public boolean containsById(int id) {
        return this.questEntities.containsKey(id);
    }

    public void add(T questEntity) {
        this.questEntities.put(questEntity.getId(), questEntity);
    }

    public T get(int id) {
        return this.questEntities.get(id);
    }
}
