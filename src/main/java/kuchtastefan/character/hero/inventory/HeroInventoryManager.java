package kuchtastefan.character.hero.inventory;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.character.hero.save.SaveGameEntityList;
import kuchtastefan.character.hero.save.item.HeroItem;
import kuchtastefan.character.hero.save.item.HeroWearableItem;
import kuchtastefan.item.Item;
import kuchtastefan.item.ItemDB;
import kuchtastefan.item.itemFilter.ItemFilter;
import kuchtastefan.item.itemType.ItemType;
import kuchtastefan.item.specificItems.questItem.QuestItem;
import kuchtastefan.item.specificItems.wearableItem.WearableItem;
import kuchtastefan.quest.QuestDB;
import kuchtastefan.quest.questObjectives.QuestObjective;
import kuchtastefan.quest.questObjectives.QuestObjectiveDB;
import kuchtastefan.utility.*;
import kuchtastefan.utility.printUtil.PrintUtil;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class HeroInventoryManager {

    private final SaveGameEntityList<HeroItem> heroItems;

    public HeroInventoryManager(Hero hero) {
        this.heroItems = hero.getSaveGameEntities().getHeroItems();
    }


    public void addItem(Item item, int count) {
        int id = item.getItemId();
        if (item instanceof WearableItem wearableItem) {
            id = wearableItem.getNewItemId();
        }

        if (heroItems.containsEntity(id)) {
            heroItems.getEntity(id).increaseAmount(count);
        } else {
            if (item instanceof WearableItem wearableItem) {
                heroItems.addEntity(new HeroWearableItem(id, count, wearableItem.getWearableItemQuality()));
            } else {
                heroItems.addEntity(new HeroItem(id, count));
            }
        }

        System.out.println("\t" + count + "x " + item.getName() + " has been added to your inventory");
    }

    public void addQuestItem(QuestItem questItem, int count, Hero hero) {
        addItem(questItem, count);

        QuestObjective questObjective = QuestObjectiveDB.getQuestObjectiveById(questItem.getQuestObjectiveId());
        questObjective.printProgress(hero);
        questObjective.verifyQuestObjectiveCompletion(hero);
        QuestDB.findQuestByObjectiveId(questItem.getQuestObjectiveId()).checkIfQuestIsCompleted(hero);
    }

    public int getItemCount(Item item) {
        if (item instanceof WearableItem wearableItem) {
            return heroItems.getEntity(wearableItem.getNewItemId()).getAmount();
        } else {
            return heroItems.getEntity(item.getItemId()).getAmount();
        }
    }

    public boolean hasRequiredItems(Item item, int count) {
        if (heroItems.containsEntity(item.getItemId())) {
            return heroItems.getEntity(item.getItemId()).getAmount() >= count;
        } else {
            return false;
        }
    }

    public Item getItem(int itemId) {
        return heroItems.getEntity(itemId).getItem();
    }

    public void removeItem(Item item, int count) {
        int id = item.getItemId();

        if (!hasRequiredItems(item, count)) {
            System.out.println("\tYou don't have anything to remove");
            return;
        }

        if (heroItems.containsEntity(id)) {
            System.out.print("\t" + ConsoleColor.YELLOW + item.getName() + ConsoleColor.RESET + " has been removed from your inventory");
            if (heroItems.getEntity(id).getAmount() == count) {
                System.out.println();
                heroItems.removeEntityById(id);
            } else {
                heroItems.getEntity(id).decreaseAmount(count);
                System.out.println(" -> " + this.getItemCount(item) + "x left");
            }
        }
    }

    public Map<Integer, HeroItem> getFilteredHeroInventory(ItemFilter itemFilter) {
        Map<Integer, HeroItem> items = new HashMap<>();
        int index = 1;
        for (HeroItem heroItem : heroItems.getSaveEntities().values()) {
            Item item = ItemDB.returnItemFromDB(heroItem.getId());
            if (itemFilter.filterItem(item) != null) {
                items.put(index++, heroItem);
            }
        }

        return items;
    }


    public boolean selectItem(Hero hero, UsingHeroInventory usingHeroInventory, ItemFilter itemFilter) {
        List<Class<? extends Item>> classes = ItemClassList.allClassList();
        boolean flag = false;

        System.out.println();
        while (true) {
            PrintUtil.printMenuHeader("Hero Inventory");
            if (itemFilter.isCanBeChanged()) {
                printClassChoice(itemFilter, classes);
                printTypeChoice(itemFilter, classes.size() + 1);
                printLevelChoice(itemFilter);
                PrintUtil.printExtraLongDivider();
            }

            printHeroInventory(hero, itemFilter);
            String choice = InputUtil.stringScanner().toUpperCase();
            if (choice.matches("\\d+")) {
                if (Integer.parseInt(choice) == 0) {
                    return flag;
                }

                flag = handleNumericChoice(Integer.parseInt(choice), usingHeroInventory, hero, itemFilter);
            } else {
                handleNonNumericChoice(choice, itemFilter, classes);
            }
        }
    }

    public void printHeroInventory(Hero hero, ItemFilter itemFilter) {
        Map<Integer, HeroItem> heroInventory = getFilteredHeroInventory(itemFilter);

        if (heroInventory.isEmpty()) {
            System.out.println("\tFor specific Item Type, Class or Level, you don't have any items in your inventory");
            PrintUtil.printIndexAndText(String.valueOf(0), "Go back\n");
            return;
        }

        PrintUtil.printIndexAndText(String.valueOf(0), "Go back\n");
        for (Map.Entry<Integer, HeroItem> entry : heroInventory.entrySet()) {
            PrintUtil.printIndexAndText(String.valueOf(entry.getKey()), entry.getValue().getAmount() + "x: ");
            entry.getValue().getItem().printItemDescription(hero);
        }
    }

    private boolean handleNumericChoice(int choice, UsingHeroInventory usingHeroInventory, Hero hero, ItemFilter itemFilter) {
        Map<Integer, HeroItem> items = getFilteredHeroInventory(itemFilter);
        if (choice - 1 < items.size()) {
            return usingHeroInventory.itemOptions(hero, items.get(choice).getItem());
        } else {
            PrintUtil.printEnterValidInput();
        }

        return false;
    }

    private void handleNonNumericChoice(String choice, ItemFilter itemFilter, List<Class<? extends Item>> classes) {
        if (itemFilter.isCanBeChanged()) {
            try {
                if (containsSpecialCharacter(choice)) {
                    itemFilter.getItemLevelFilter().handleItemLevel(choice, itemFilter);
                    return;
                }

                int choiceValue = LetterToNumber.valueOf(choice).getValue();
                if (choiceValue <= classes.size()) {
                    handleClassChoice(classes.get(choiceValue - 1), itemFilter);
                } else {
                    handleTypeChoice(ItemTypeList.itemTypesByClass(itemFilter.getItemClassFilter())
                            .get(choiceValue - classes.size() - 1), itemFilter);
                }
            } catch (Exception e) {
                PrintUtil.printEnterValidInput();
            }
        }
    }

    private void printClassChoice(ItemFilter itemFilter, List<Class<? extends Item>> classes) {
        int index = 1;
        for (Class<? extends Item> itemClass : classes) {
            String className;
            if (itemFilter.getItemClassFilter().containsClass(itemClass)) {
                className = itemClass.getSimpleName();
            } else {
                className = ConsoleColor.WHITE + itemClass.getSimpleName() + ConsoleColor.RESET;
            }

            PrintUtil.printIndexAndText(LetterToNumber.getStringFromValue(index++), className);
        }

        System.out.println();
    }

    private void handleClassChoice(Class<? extends Item> itemClass, ItemFilter itemFilter) {
        if (itemFilter.getItemClassFilter().containsClass(itemClass)) {
            itemFilter.getItemClassFilter().removeItemClass(itemClass);
        } else {
            itemFilter.getItemClassFilter().addItemClass(itemClass);
        }
    }

    private void printTypeChoice(ItemFilter itemFilter, int indexStart) {
        int index = indexStart;
        List<ItemType> itemTypes = ItemTypeList.itemTypesByClass(itemFilter.getItemClassFilter());
        for (ItemType itemType : itemTypes) {
            String typeName;
            if (itemFilter.getItemTypeFilter().containsType(itemType)) {
                typeName = itemType.toString();
            } else {
                typeName = ConsoleColor.WHITE + itemType.toString() + ConsoleColor.RESET;
            }

            PrintUtil.printIndexAndText(LetterToNumber.getStringFromValue(index++), typeName);
        }

        System.out.println();
    }

    private void handleTypeChoice(ItemType itemType, ItemFilter itemFilter) {
        if (itemFilter.getItemTypeFilter().containsType(itemType)) {
            itemFilter.getItemTypeFilter().removeItemType(itemType);
        } else {
            itemFilter.getItemTypeFilter().addItemType(itemType);
        }
    }

    private void printLevelChoice(ItemFilter itemFilter) {
        itemFilter.getItemLevelFilter().printLevelRange();
        System.out.println();
        PrintUtil.printIndexAndText("+", "Increase min item level");
        PrintUtil.printIndexAndText("-", "Decrease min item level");
        PrintUtil.printIndexAndText("++", "Increase max item level");
        PrintUtil.printIndexAndText("--", "Decrease max item level");

        System.out.println();
    }

    private boolean containsSpecialCharacter(String choice) {
        return choice.contains("-") || choice.contains("+");
    }

}























