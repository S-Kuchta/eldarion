package kuchtastefan.region.location;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.item.ItemsLists;
import kuchtastefan.item.consumeableItem.ConsumableItem;
import kuchtastefan.item.craftingItem.CraftingReagentItem;
import kuchtastefan.item.wearableItem.WearableItem;
import kuchtastefan.utility.RandomNumberGenerator;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
public class Location {

    private final int locationId;
    protected final String locationName;
    protected final int locationLevel;
    protected int stageTotal;
    protected int stageCompleted;
    protected boolean cleared;
    protected final LocationType locationType;
    protected boolean canLocationBeExplored;
    protected Map<Integer, LocationStage> locationStages;


    public Location(int locationId, String locationName, int locationLevel, int stageTotal, LocationType locationType, boolean canLocationBeExplored) {
        this.locationId = locationId;
        this.locationName = locationName;
        this.locationLevel = locationLevel;
        this.stageTotal = stageTotal;
        this.stageCompleted = 0;
        this.cleared = false;
        this.locationType = locationType;
        this.canLocationBeExplored = canLocationBeExplored;
        this.locationStages = new HashMap<>();
    }

    public void rewardAfterCompletedAllStages(Hero hero) {
        for (int i = 0; i < 2; i++) {
            List<WearableItem> suitableItem = ItemsLists.returnWearableItemListByItemLevel(
                    this.locationLevel, null, true);

            int randomItem = RandomNumberGenerator.getRandomNumber(0, suitableItem.size() - 1);
            hero.getHeroInventory().addItemWithNewCopyToItemList(suitableItem.get(randomItem));
        }

        for (int i = 0; i < 4; i++) {
            List<CraftingReagentItem> craftingReagentItems = ItemsLists.returnCraftingReagentItemListByItemLevel(
                    this.locationLevel, null);

            int randomItem = RandomNumberGenerator.getRandomNumber(0, craftingReagentItems.size() - 1);
            hero.getHeroInventory().addItemWithNewCopyToItemList(craftingReagentItems.get(randomItem));
        }

        for (int i = 0; i < 3; i++) {
            List<ConsumableItem> consumableItems = ItemsLists.returnConsumableItemListByItemLevel(
                    this.locationLevel, null);

            int randomItem = RandomNumberGenerator.getRandomNumber(0, consumableItems.size() - 1);
            hero.getHeroInventory().addItemWithNewCopyToItemList(consumableItems.get(randomItem));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return locationLevel == location.locationLevel
                && cleared == location.cleared
                && stageTotal == location.stageTotal
                && Objects.equals(locationName, location.locationName)
                && locationType == location.locationType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationName, locationLevel, stageTotal, locationType, cleared);
    }
}
