package kuchtastefan.utility;

import kuchtastefan.actions.Action;
import kuchtastefan.actions.actionsWIthDuration.*;
import kuchtastefan.actions.instantActions.*;
import kuchtastefan.character.GameCharacter;
import kuchtastefan.character.npc.NonPlayerCharacter;
import kuchtastefan.character.enemy.Enemy;
import kuchtastefan.character.hero.Hero;
import kuchtastefan.item.Item;
import kuchtastefan.item.consumeableItem.ConsumableItem;
import kuchtastefan.item.craftingItem.CraftingReagentItem;
import kuchtastefan.item.junkItem.JunkItem;
import kuchtastefan.item.questItem.QuestItem;
import kuchtastefan.item.wearableItem.WearableItem;
import kuchtastefan.quest.Quest;
import kuchtastefan.quest.QuestChain;
import kuchtastefan.quest.questObjectives.*;
import kuchtastefan.region.location.*;
import kuchtastefan.service.RuntimeTypeAdapterFactory;


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
            .registerSubtype(ActionSummonCreature.class)
            .registerSubtype(ActionDrainMana.class)
            .registerSubtype(ActionSkipTurn.class)
            .registerSubtype(ActionDecreaseAbilityPoint.class);

    public static final RuntimeTypeAdapterFactory<GameCharacter> gameCharactersRuntimeTypeAdapterFactory = RuntimeTypeAdapterFactory
            .of(GameCharacter.class)
            .registerSubtype(GameCharacter.class)
            .registerSubtype(NonPlayerCharacter.class)
            .registerSubtype(Enemy.class)
            .registerSubtype(Hero.class);

    public static final RuntimeTypeAdapterFactory<QuestObjective> questObjectiveRuntimeTypeAdapterFactory = RuntimeTypeAdapterFactory
            .of(QuestObjective.class)
            .registerSubtype(QuestKillObjective.class)
            .registerSubtype(QuestBringItemFromEnemyObjective.class)
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
