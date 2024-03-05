package kuchtastefan.item.wearableItem;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum WearableItemType {

    WEAPON("Weapon"),
    HEAD("Head armor"),
    BODY("Body armor"),
    HANDS("Hands armor"),
    BOOTS("Boots armor");

    private final String description;

    WearableItemType(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return StringUtils.capitalize(name().toLowerCase().replace("_", " "));
    }

}
