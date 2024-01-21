package kuchtastefan.utility;

import kuchtastefan.actions.Action;
import kuchtastefan.actions.actionsWIthDuration.ActionIncreaseAbilityPoint;
import kuchtastefan.actions.actionsWIthDuration.ActionRestoreHealthOverTime;
import kuchtastefan.actions.instantActions.ActionRestoreHealth;
import kuchtastefan.actions.actionsWIthDuration.ActionWithDuration;
import kuchtastefan.service.RuntimeTypeAdapterFactory;

public class RuntimeTypeAdapterFactoryUtil {
    public static final RuntimeTypeAdapterFactory<Action> actionsRuntimeTypeAdapterFactory = RuntimeTypeAdapterFactory
            .of(Action.class)
            .registerSubtype(ActionWithDuration.class)
            .registerSubtype(ActionRestoreHealth.class)
            .registerSubtype(ActionRestoreHealthOverTime.class)
            .registerSubtype(ActionIncreaseAbilityPoint.class);

}
