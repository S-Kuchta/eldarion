package kuchtastefan.utility;

import kuchtastefan.actions.Action;
import kuchtastefan.actions.actionsWIthDuration.*;
import kuchtastefan.actions.instantActions.*;
import kuchtastefan.items.Item;
import kuchtastefan.items.consumeableItem.ConsumableItem;
import kuchtastefan.items.craftingItem.CraftingReagentItem;
import kuchtastefan.items.junkItem.JunkItem;
import kuchtastefan.items.questItem.QuestItem;
import kuchtastefan.items.wearableItem.WearableItem;
import kuchtastefan.quest.Quest;
import kuchtastefan.quest.QuestChain;
import kuchtastefan.quest.questObjectives.*;
import kuchtastefan.regions.locations.*;
import kuchtastefan.service.RuntimeTypeAdapterFactory;


public class RuntimeTypeAdapterFactoryUtil {

    public static final RuntimeTypeAdapterFactory<Action> actionsRuntimeTypeAdapterFactory = RuntimeTypeAdapterFactory
            .of(Action.class)
            .registerSubtype(ActionWithDuration.class)
            .registerSubtype(ActionRestoreHealth.class)
            .registerSubtype(ActionDealDamage.class)
            .registerSubtype(ActionDealDamageOverTime.class)
            .registerSubtype(ActionRestoreHealthOverTime.class)
            .registerSubtype(ActionIncreaseAbilityPoint.class)
            .registerSubtype(ActionAbsorbDamage.class)
            .registerSubtype(ActionRestoreMana.class)
            .registerSubtype(ActionRestoreManaOverTime.class)
            .registerSubtype(ActionStun.class)
            .registerSubtype(ActionInstantStun.class)
            .registerSubtype(ActionInvulnerability.class)
            .registerSubtype(ActionReflectSpell.class)
            .registerSubtype(ActionRemoveBuffOrDebuff.class)
            .registerSubtype(ActionDecreaseAbilityPoint.class);

    public static final RuntimeTypeAdapterFactory<QuestObjective> questObjectiveRuntimeTypeAdapterFactory = RuntimeTypeAdapterFactory
            .of(QuestObjective.class)
            .registerSubtype(QuestKillObjective.class)
            .registerSubtype(QuestBringItemObjective.class)
            .registerSubtype(QuestClearLocationObjective.class)
            .registerSubtype(QuestFindObjective.class);

    public static final RuntimeTypeAdapterFactory<? extends Item> itemsRuntimeTypeAdapterFactory = RuntimeTypeAdapterFactory
            .of(Item.class)
            .registerSubtype(ConsumableItem.class)
            .registerSubtype(CraftingReagentItem.class)
            .registerSubtype(JunkItem.class)
            .registerSubtype(QuestItem.class)
            .registerSubtype(WearableItem.class);

    public static final RuntimeTypeAdapterFactory<? extends LocationStage> locationStageRuntimeTypeAdapterFactory = RuntimeTypeAdapterFactory
            .of(LocationStage.class)
            .registerSubtype(LocationStageQuestGiver.class)
            .registerSubtype(LocationStageCombat.class)
            .registerSubtype(LocationStageFindTreasure.class)
            .registerSubtype(LocationStageFindItem.class);

    public static final RuntimeTypeAdapterFactory<? extends Quest> questRuntimeTypeAdapterFactory = RuntimeTypeAdapterFactory
            .of(Quest.class)
            .registerSubtype(Quest.class)
            .registerSubtype(QuestChain.class);
}
