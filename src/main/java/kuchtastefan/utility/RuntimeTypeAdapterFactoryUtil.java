package kuchtastefan.utility;

import kuchtastefan.actions.Action;
import kuchtastefan.actions.actionsWIthDuration.ActionIncreaseAbilityPoint;
import kuchtastefan.actions.actionsWIthDuration.ActionRestoreHealthOverTime;
import kuchtastefan.actions.actionsWIthDuration.ActionWithDuration;
import kuchtastefan.actions.instantActions.ActionRestoreHealth;
import kuchtastefan.items.Item;
import kuchtastefan.items.consumeableItem.ConsumableItem;
import kuchtastefan.items.craftingItem.CraftingReagentItem;
import kuchtastefan.items.junkItem.JunkItem;
import kuchtastefan.items.questItem.QuestItem;
import kuchtastefan.items.wearableItem.WearableItem;
import kuchtastefan.quest.questObjectives.QuestBringItemObjective;
import kuchtastefan.quest.questObjectives.QuestClearLocation;
import kuchtastefan.quest.questObjectives.QuestKillObjective;
import kuchtastefan.quest.questObjectives.QuestObjective;
import kuchtastefan.service.RuntimeTypeAdapterFactory;

public class RuntimeTypeAdapterFactoryUtil {
    public static final RuntimeTypeAdapterFactory<Action> actionsRuntimeTypeAdapterFactory = RuntimeTypeAdapterFactory
            .of(Action.class)
            .registerSubtype(ActionWithDuration.class, "ActionWithDuration")
            .registerSubtype(ActionRestoreHealth.class, "ActionRestoreHealth")
            .registerSubtype(ActionRestoreHealthOverTime.class, "ActionRestoreHealthOverTime")
            .registerSubtype(ActionIncreaseAbilityPoint.class, "ActionIncreaseAbilityPoint");

    public static final RuntimeTypeAdapterFactory<ActionWithDuration> actionsWithDurationTypeAdapterFactory = RuntimeTypeAdapterFactory
            .of(ActionWithDuration.class)
            .registerSubtype(ActionRestoreHealthOverTime.class, "ActionRestoreHealthWithDuration")
            .registerSubtype(ActionIncreaseAbilityPoint.class, "ActionIncreaseAbilityPointWithDuration");

    public static final RuntimeTypeAdapterFactory<QuestObjective> questObjectiveRuntimeTypeAdapterFactory = RuntimeTypeAdapterFactory
            .of(QuestObjective.class)
            .registerSubtype(QuestKillObjective.class)
            .registerSubtype(QuestBringItemObjective.class)
            .registerSubtype(QuestClearLocation.class);

    public static final RuntimeTypeAdapterFactory<? extends Item> itemsRuntimeTypeAdapterFactory = RuntimeTypeAdapterFactory
            .of(Item.class)
            .registerSubtype(ConsumableItem.class)
            .registerSubtype(CraftingReagentItem.class)
            .registerSubtype(JunkItem.class)
            .registerSubtype(QuestItem.class)
            .registerSubtype(WearableItem.class);
}
