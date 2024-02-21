package kuchtastefan.character.enemy;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum EnemyRarity {
    COMMON(5),
    RARE(20),
    ELITE(40),
    BOSS(60);

    private final int experienceGainedValue;

    EnemyRarity(int experienceGainedValue) {
        this.experienceGainedValue = experienceGainedValue;
    }

    @Override
    public String toString() {
        return StringUtils.capitalize(name().toLowerCase().replace("_", " "));
    }

}
