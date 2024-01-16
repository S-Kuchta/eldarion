package kuchtastefan.characters.enemy;

public enum EnemyRarity {
    COMMON(0),
    RARE(20),
    ELITE(40);

    private final int experienceGainedValue;

    EnemyRarity(int experienceGainedValue) {
        this.experienceGainedValue = experienceGainedValue;
    }

    public int getExperienceGainedValue() {
        return experienceGainedValue;
    }
}
