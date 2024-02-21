package kuchtastefan.actions.actionsWIthDuration;

import org.apache.commons.lang3.StringUtils;

public enum ActionStatusEffect {

    BUFF("Any effect that increases the performance or statistics of your Heroes."),
    DEBUFF("Any effect that decreases the performance or statistics of your Heroes.");

    private final String description;

    ActionStatusEffect(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return StringUtils.capitalize(name().toLowerCase().replace("_", " "));
    }
}
