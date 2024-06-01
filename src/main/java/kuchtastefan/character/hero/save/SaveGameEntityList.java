package kuchtastefan.character.hero.save;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public class SaveGameEntityList<T extends SaveGameEntity> {

    private final Map<Integer, T> saveEntities;

    public boolean containsEntity(int id) {
        return this.saveEntities.containsKey(id);
    }

    public void addEntity(T saveEntity) {
        this.saveEntities.put(saveEntity.getId(), saveEntity);
    }

    public T getEntity(int id) {
        return this.saveEntities.get(id);
    }

    public int[] getEntitiesIds() {
        return this.saveEntities.keySet().stream().mapToInt(Integer::intValue).toArray();
    }
}
