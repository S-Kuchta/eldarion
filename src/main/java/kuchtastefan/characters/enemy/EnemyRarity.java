package kuchtastefan.characters.enemy;

import kuchtastefan.utility.LetterToNumberSpellLevel;
import lombok.Getter;

@Getter
public enum EnemyRarity {
    COMMON(5),
    RARE(20),
    ELITE(40),
    BOSS(80);

    private final int experienceGainedValue;

    EnemyRarity(int experienceGainedValue) {
        this.experienceGainedValue = experienceGainedValue;
    }

}
