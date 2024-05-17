package kuchtastefan.utility;

import kuchtastefan.actions.Action;
import kuchtastefan.actions.actionsWIthDuration.*;
import kuchtastefan.actions.actionsWIthDuration.specificActionWithDuration.*;
import kuchtastefan.actions.instantAction.*;
import kuchtastefan.character.GameCharacter;
import kuchtastefan.character.npc.NonPlayerCharacter;
import kuchtastefan.character.npc.enemy.Enemy;
import kuchtastefan.character.hero.Hero;
import kuchtastefan.character.npc.enemy.QuestEnemy;
import kuchtastefan.character.npc.vendor.VendorCharacter;
import kuchtastefan.character.npc.vendor.specificVendorCharacter.ConsumableVendorCharacter;
import kuchtastefan.character.npc.vendor.specificVendorCharacter.CraftingReagentItemVendorCharacter;
import kuchtastefan.character.npc.vendor.specificVendorCharacter.JunkVendorCharacter;
import kuchtastefan.character.npc.vendor.specificVendorCharacter.WearableItemVendorCharacter;
import kuchtastefan.item.Item;
import kuchtastefan.item.specificItems.consumeableItem.ConsumableItem;
import kuchtastefan.item.specificItems.craftingItem.CraftingReagentItem;
import kuchtastefan.item.specificItems.junkItem.JunkItem;
import kuchtastefan.item.specificItems.keyItem.KeyItem;
import kuchtastefan.item.specificItems.questItem.QuestItem;
import kuchtastefan.item.specificItems.questItem.UsableQuestItem;
import kuchtastefan.item.specificItems.wearableItem.WearableItem;
import kuchtastefan.item.usableItem.UsableItem;
import kuchtastefan.quest.Quest;
import kuchtastefan.quest.QuestChain;
import kuchtastefan.quest.questObjectives.*;
import kuchtastefan.quest.questObjectives.specificQuestObjectives.*;
import kuchtastefan.service.RuntimeTypeAdapterFactory;
import kuchtastefan.world.location.locationStage.*;
import kuchtastefan.world.location.locationStage.specificLocationStage.*;


public class RuntimeTypeAdapterFactoryUtil {

//    public static final Gson gson = new GsonBuilder()
//            .registerTypeAdapterFactory(RuntimeTypeAdapterFactoryUtil.actionsRuntimeTypeAdapterFactory)
//            .registerTypeAdapterFactory(RuntimeTypeAdapterFactoryUtil.itemsRuntimeTypeAdapterFactory)
//            .registerTypeAdapterFactory(RuntimeTypeAdapterFactoryUtil.locationStageRuntimeTypeAdapterFactory)
//            .registerTypeAdapterFactory(RuntimeTypeAdapterFactoryUtil.questRuntimeTypeAdapterFactory)
//            .registerTypeAdapterFactory(RuntimeTypeAdapterFactoryUtil.questObjectiveRuntimeTypeAdapterFactory)
//            .enableComplexMapKeySerialization().setPrettyPrinting().create();

    public static final RuntimeTypeAdapterFactory<Action> actionsRuntimeTypeAdapterFactory = RuntimeTypeAdapterFactory
            .of(Action.class)
            .registerSubtype(Action.class)
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
            .registerSubtype(ActionInvulnerability.class)
            .registerSubtype(ActionReflectSpell.class)
            .registerSubtype(ActionRemoveBuffOrDebuff.class)
            .registerSubtype(ActionSummonCreature.class)
            .registerSubtype(ActionDrainMana.class)
            .registerSubtype(ActionSkipTurn.class)
            .registerSubtype(ActionLoseMana.class)
            .registerSubtype(ActionDecreaseAbilityPoint.class);

    public static final RuntimeTypeAdapterFactory<NonPlayerCharacter> gameCharactersRuntimeTypeAdapterFactory = RuntimeTypeAdapterFactory
            .of(NonPlayerCharacter.class)
            .registerSubtype(NonPlayerCharacter.class)
            .registerSubtype(Enemy.class)
            .registerSubtype(QuestEnemy.class);

    public static final RuntimeTypeAdapterFactory<QuestObjective> questObjectiveRuntimeTypeAdapterFactory = RuntimeTypeAdapterFactory
            .of(QuestObjective.class)
            .registerSubtype(QuestKillObjective.class)
            .registerSubtype(QuestClearLocationObjective.class)
            .registerSubtype(QuestUseItemObjective.class)
            .registerSubtype(QuestBringItemObjective.class);

    public static final RuntimeTypeAdapterFactory<? extends Item> itemsRuntimeTypeAdapterFactory = RuntimeTypeAdapterFactory
            .of(Item.class)
            .registerSubtype(Item.class)
            .registerSubtype(ConsumableItem.class)
            .registerSubtype(CraftingReagentItem.class)
            .registerSubtype(JunkItem.class)
            .registerSubtype(QuestItem.class)
            .registerSubtype(UsableQuestItem.class)
            .registerSubtype(KeyItem.class)
            .registerSubtype(WearableItem.class);

    public static final RuntimeTypeAdapterFactory<? extends LocationStage> locationStageRuntimeTypeAdapterFactory = RuntimeTypeAdapterFactory
            .of(LocationStage.class)
            .registerSubtype(LocationStageQuestGiver.class)
            .registerSubtype(LocationStageCombat.class)
            .registerSubtype(LocationStageFindTreasure.class)
            .registerSubtype(LocationStageFindItem.class)
            .registerSubtype(LocationStageUseItem.class)
            .registerSubtype(LocationStageBlacksmith.class)
            .registerSubtype(LocationStagePlaceToRest.class)
            .registerSubtype(LocationStageNothingOutcome.class)
            .registerSubtype(LocationStageVendor.class);

    public static final RuntimeTypeAdapterFactory<? extends Quest> questRuntimeTypeAdapterFactory = RuntimeTypeAdapterFactory
            .of(Quest.class)
            .registerSubtype(Quest.class)
            .registerSubtype(QuestChain.class);

    public static final RuntimeTypeAdapterFactory<? extends VendorCharacter> vendorRuntimeTypeAdapterFactory = RuntimeTypeAdapterFactory
            .of(VendorCharacter.class)
            .registerSubtype(ConsumableVendorCharacter.class)
            .registerSubtype(WearableItemVendorCharacter.class)
            .registerSubtype(CraftingReagentItemVendorCharacter.class)
            .registerSubtype(JunkVendorCharacter.class);
}
