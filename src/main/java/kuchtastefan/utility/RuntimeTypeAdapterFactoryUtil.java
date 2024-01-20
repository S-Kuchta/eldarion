package kuchtastefan.utility;

import kuchtastefan.actions.Action;
import kuchtastefan.actions.ActionRestoreHealth;
import kuchtastefan.service.RuntimeTypeAdapterFactory;

public class RuntimeTypeAdapterFactoryUtil {
    public static final RuntimeTypeAdapterFactory<Action> actionsRuntimeTypeAdapterFactory = RuntimeTypeAdapterFactory
            .of(Action.class)
            .registerSubtype(ActionRestoreHealth.class);

}
