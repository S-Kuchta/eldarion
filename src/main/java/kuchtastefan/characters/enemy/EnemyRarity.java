package kuchtastefan.characters.enemy;

import lombok.Getter;

@Getter
public enum EnemyRarity {
    COMMON(0),
    RARE(20),
    ELITE(40);

    private final int experienceGainedValue;

    EnemyRarity(int experienceGainedValue) {
        this.experienceGainedValue = experienceGainedValue;
    }

}
