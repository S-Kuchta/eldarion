package kuchtastefan.character.hero.save.item;

import kuchtastefan.character.hero.save.SaveGameEntity;
import lombok.Getter;

@Getter
public class HeroItem implements SaveGameEntity {

    private final int id;

    public HeroItem(int id) {
        this.id = id;
    }
}
