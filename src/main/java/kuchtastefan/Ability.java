package kuchtastefan;

public enum Ability {
    ATTACK("Attack is the ability to deal damage. Final damage is also affected by dexterity and skill."),
    DEFENCE("Defence is the ability to reduce damage. Final damage is also affected by dexterity."),
    DEXTERITY("Dexterity is important for both attack and defence. It affects final damage and final damage reduction."),
    SKILL("Skill is important for attack and also for critical hit chance."),
    LUCK("Luck is important for critical hit chance."),
    HEALTH("Health is the amount of damage you can take before you die. After each battle, health can be restored to full.");

    private final String description;

    Ability(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
