package kuchtastefan.character.hero.inventory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kuchtastefan.character.hero.Hero;
import kuchtastefan.item.Item;
import kuchtastefan.item.itemFilter.ItemFilter;
import kuchtastefan.item.itemType.HaveType;
import kuchtastefan.item.specificItems.questItem.QuestItem;
import kuchtastefan.utility.*;
import kuchtastefan.utility.printUtil.PrintUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class HeroInventory {

    private final Map<Item, Integer> heroInventory;

    public HeroInventory() {
        this.heroInventory = new HashMap<>();
    }


    public void addItemToInventory(Item item, int count) {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(RuntimeTypeAdapterFactoryUtil.actionsRuntimeTypeAdapterFactory).create();
        Item itemCopy = gson.fromJson(gson.toJson(item), item.getClass());

        System.out.println("\t" + count + "x " + itemCopy.getName() + " has been added to your inventory");
        for (int i = 0; i < count; i++) {
            this.heroInventory.merge(itemCopy, 1, Integer::sum);
        }
    }

    public void addQuestItemToInventory(QuestItem questItem, int count, Hero hero) {
        if (hero.getHeroQuests().containsQuestObjective(questItem.getQuestObjectiveId())) {
            addItemToInventory(questItem, count);
            hero.getHeroQuests().updateQuestObjectiveProgress(hero, questItem.getQuestObjectiveId());
        }
    }

    public Item getItemFromInventory(int itemId) {
        for (Item item : this.heroInventory.keySet()) {
            if (item.getItemId() == itemId) {
                return item;
            }
        }

        return null;
    }

    public int getItemCount(Item item) {
        return this.heroInventory.getOrDefault(item, 0);
    }

    public boolean hasRequiredItems(Item item, int count) {
        if (this.heroInventory.containsKey(item)) {
            return this.heroInventory.get(item) >= count;
        }

        return false;
    }

    public void removeItemFromHeroInventory(Item item, int count) {
        if (this.heroInventory.isEmpty()) {
            System.out.println("\tYou don't have anything to remove");
            return;
        }

        if (hasRequiredItems(item, count)) {
            System.out.print("\t" + ConsoleColor.YELLOW + item.getName() + ConsoleColor.RESET + " has been removed from your inventory");
            if (this.heroInventory.get(item) == count) {
                this.heroInventory.remove(item);
            } else {
                this.heroInventory.put(item, this.heroInventory.get(item) - count);
                System.out.println(" -> " + this.heroInventory.get(item) + "x left");
            }
        }
    }

    public <T extends Item> Map<T, Integer> returnHeroInventory(Class<T> itemClass) {
        Map<T, Integer> itemMap = new HashMap<>();

        for (Map.Entry<? extends Item, Integer> entry : this.getHeroInventory().entrySet()) {
            if (itemClass.isInstance(entry.getKey())) {
                itemMap.put(itemClass.cast(entry.getKey()), entry.getValue());
            }
        }

        return itemMap;
    }

    public <T extends Item> Map<T, Integer> returnHeroInventory(Class<T> itemClass, ItemFilter itemFilter) {
        Map<T, Integer> itemMap = new HashMap<>();

        for (Map.Entry<? extends Item, Integer> entry : returnHeroInventory(itemClass).entrySet()) {
            if (itemFilter.isCheckLevel() && !ItemLevelCondition.checkItemLevelCondition(entry.getKey(), itemFilter.getMaxItemLevel(), itemFilter.getMinItemLevel())) {
                continue;
            }

            if (entry.getKey() instanceof HaveType itemWithType) {
                if (itemFilter.isCheckType() && !itemWithType.getItemType().equals(itemFilter.getItemType())) {
                    continue;
                }
            }

            itemMap.put(itemClass.cast(entry.getKey()), entry.getValue());
        }

        return itemMap;
    }

    private <T extends Item> void printHeroInventory(Class<T> itemClass, ItemFilter itemFilter, int indexStart, Hero hero) {
        Map<T, Integer> inventory = returnHeroInventory(itemClass, itemFilter);
        int index = indexStart;

        String header = itemFilter.isCheckType() ? itemFilter.getItemType().toString() : SplitByCamelCase.splitStringByCamelCase(itemClass.getSimpleName());
        PrintUtil.printMenuHeader(header + " Inventory");

        if (inventory.isEmpty()) {
            System.out.println("\tInventory is empty");
        }

        PrintUtil.printIndexAndText("0", "Go back");
        System.out.println();
        for (Map.Entry<T, Integer> entry : inventory.entrySet()) {
            PrintUtil.printIndexAndText(String.valueOf(index++), "(" + entry.getValue() + "x) ");
            entry.getKey().printItemDescription(hero);
        }
    }

    public <T extends Item> boolean selectItem(Hero hero, Class<T> itemClass, ItemFilter itemFilter, UsingHeroInventory usingHeroInventory, int indexStart) {
        hero.getHeroInventory().printHeroInventory(itemClass, itemFilter, indexStart, hero);

        final int choice = InputUtil.intScanner();
        if (choice == 0) {
            usingHeroInventory.mainMenu(hero);
        } else {
            List<T> items = new ArrayList<>(hero.getHeroInventory().returnHeroInventory(itemClass, itemFilter).keySet());
            if (choice - 1 < items.size()) {
                return usingHeroInventory.itemOptions(hero, items.get(choice - 1));
            } else {
                PrintUtil.printEnterValidInput();
            }
        }

        return false;
    }
}
