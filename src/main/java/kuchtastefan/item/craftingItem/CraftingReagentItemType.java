package kuchtastefan.item.craftingItem;

public enum CraftingReagentItemType {
    BLACKSMITH("Reagent used for blacksmith. For smith or refinement items."),
    ALCHEMY("Reagent used for alchemy. For create new potions,");

    private final String description;

    CraftingReagentItemType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
