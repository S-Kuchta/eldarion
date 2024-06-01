package kuchtastefan.character.hero.save.location;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class HeroLocationStage {

    private final int id;
    private boolean stageCompleted;
    private boolean stageDiscovered;
}
