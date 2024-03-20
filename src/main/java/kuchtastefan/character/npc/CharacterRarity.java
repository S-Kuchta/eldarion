package kuchtastefan.character.npc;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum CharacterRarity {
    SUMMONED(0),
    COMMON(10),
    RARE(25),
    ELITE(50),
    BOSS(100);

    private final int experienceGainedValue;

    CharacterRarity(int experienceGainedValue) {
        this.experienceGainedValue = experienceGainedValue;
    }

    @Override
    public String toString() {
        return StringUtils.capitalize(name().toLowerCase().replace("_", " "));
    }

}
