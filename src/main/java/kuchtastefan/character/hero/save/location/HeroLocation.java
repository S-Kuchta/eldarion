package kuchtastefan.character.hero.save.location;

import kuchtastefan.character.hero.save.SaveGameEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public class HeroLocation implements SaveGameEntity {

    private final int id;
    private boolean cleared;
    private int currentStageToEnter;
    private final Map<Integer, HeroLocationStage> stages;

}
