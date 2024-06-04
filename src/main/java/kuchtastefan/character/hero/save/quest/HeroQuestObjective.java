package kuchtastefan.character.hero.save.quest;

import kuchtastefan.character.hero.save.SaveGameEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class HeroQuestObjective implements SaveGameEntity {

    private final int id;
    private boolean completed;

}
