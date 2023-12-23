package kuchtastefan.item;

import java.util.List;

public class ItemList {
    private final List<Item> itemList;

    public ItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public List<Item> getItemList() {
        return itemList;
    }
}
