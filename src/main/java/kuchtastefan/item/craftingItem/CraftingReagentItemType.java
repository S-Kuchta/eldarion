package kuchtastefan.item.craftingItem;

public enum CraftingReagentItemType {
    BLACKSMITH_REAGENT("Reagent used for blacksmith. For smith or refinement items."),
    ALCHEMY_REAGENT("Reagent used for alchemy. For create new potions,");

    private final String description;

    CraftingReagentItemType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
