package kuchtastefan.character.hero.save.location;

import kuchtastefan.character.hero.save.SaveGameEntity;
import kuchtastefan.world.location.LocationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public class HeroLocation implements SaveGameEntity {

    private final int id;
    private LocationStatus locationStatus;
    private final Map<Integer, HeroLocationStage> stages;
}
