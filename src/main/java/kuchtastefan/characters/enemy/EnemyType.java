package kuchtastefan.characters.enemy;

public enum EnemyType {

    HUMANOID("Humanoid creatures, bearing semblance to humans, vary in appearance and temperament. Some are noble and wise, while others, shrouded in mystery, wield arcane powers or possess unmatched strength"),
    BEAST("Beasts, creatures of primal instinct, roam untamed lands. From majestic dragons ruling the skies to feral wolves prowling the forests, these creatures embody raw power and untamed ferocity"),
    UNDEAD("Undead beings, once mortal, now wander in eternal unrest. Embraced by death's grasp, these spectral figures shroud themselves in darkness, driven by an unending hunger for souls");

    private final String description;

    EnemyType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
