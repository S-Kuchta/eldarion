package kuchtastefan.character.hero.save.location;

import kuchtastefan.world.location.locationStage.LocationStageStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class HeroLocationStage {

    private final int order;
    private final LocationStageStatus stageStatus;
}
