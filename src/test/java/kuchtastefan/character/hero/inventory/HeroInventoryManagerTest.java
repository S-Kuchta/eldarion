package kuchtastefan.character.hero.inventory;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.item.Item;
import kuchtastefan.item.specificItems.craftingItem.CraftingReagentItem;
import kuchtastefan.item.specificItems.craftingItem.CraftingReagentItemType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeroInventoryManagerTest {

    private HeroInventoryManager heroInventoryManager;

    @BeforeEach
    void setUp() {
        Hero hero = new Hero("Test");
        this.heroInventoryManager = new HeroInventoryManager(hero);
    }

    @Test
    void addItem() {
        // Given
        Item item = new CraftingReagentItem(1, "TestItem", 10, CraftingReagentItemType.BLACKSMITH_REAGENT, 1);
        int count = 1;
        // When
        heroInventoryManager.addItem(item, count);
        // Then
        assertEquals(count, heroInventoryManager.getItemCount(item));
    }
}