package kuchtastefan.item.craftingItem;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum CraftingReagentItemType {
    BLACKSMITH_REAGENT("Reagent used for blacksmith. For smith or refinement items."),
    ALCHEMY_REAGENT("Reagent used for alchemy. For create new potions,");

    private final String description;

    CraftingReagentItemType(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return StringUtils.capitalize(name().toLowerCase().replace("_", " "));
    }

}
