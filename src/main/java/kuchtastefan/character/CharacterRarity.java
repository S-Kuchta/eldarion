package kuchtastefan.character;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum CharacterRarity {
    SUMMONED(0),
    COMMON(5),
    RARE(20),
    ELITE(40),
    BOSS(60);

    private final int experienceGainedValue;

    CharacterRarity(int experienceGainedValue) {
        this.experienceGainedValue = experienceGainedValue;
    }

    @Override
    public String toString() {
        return StringUtils.capitalize(name().toLowerCase().replace("_", " "));
    }

}
