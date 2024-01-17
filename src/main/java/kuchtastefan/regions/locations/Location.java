package kuchtastefan.regions.locations;

import kuchtastefan.characters.QuestGiverCharacter;
import kuchtastefan.characters.enemy.Enemy;
import kuchtastefan.characters.enemy.EnemyList;
import kuchtastefan.characters.enemy.EnemyRarity;
import kuchtastefan.characters.hero.Hero;
import kuchtastefan.items.ItemsLists;
import kuchtastefan.items.consumeableItem.ConsumableItem;
import kuchtastefan.items.craftingItem.CraftingReagentItem;
import kuchtastefan.items.wearableItem.WearableItem;
import kuchtastefan.quest.Quest;
import kuchtastefan.utility.RandomNumberGenerator;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class Location {

    protected final String locationName;
    protected final int locationLevel;
    protected int stageTotal;
    protected int stageCompleted;
    protected boolean cleared;
    protected final LocationType locationType;
    protected boolean canLocationBeExplored;


    public Location(String locationName, int locationLevel, int stageTotal, LocationType locationType, boolean canLocationBeExplored) {
        this.locationName = locationName;
        this.locationLevel = locationLevel;
        this.stageTotal = stageTotal;
        this.stageCompleted = 0;
        this.cleared = false;
        this.locationType = locationType;
        this.canLocationBeExplored = canLocationBeExplored;
    }

    public void rewardAfterCompletedAllStages(Hero hero) {
        for (int i = 0; i < 2; i++) {
            List<WearableItem> suitableItem = ItemsLists.returnWearableItemListByItemLevel(this.locationLevel, null, true);
            int randomItem = RandomNumberGenerator.getRandomNumber(0, suitableItem.size() - 1);
            hero.getHeroInventory().addItemWithNewCopyToItemList(suitableItem.get(randomItem));
        }

        for (int i = 0; i < 4; i++) {
            List<CraftingReagentItem> craftingReagentItems = ItemsLists.returnCraftingReagentItemListByItemLevel(this.locationLevel, null);
            int randomItem = RandomNumberGenerator.getRandomNumber(0, craftingReagentItems.size() - 1);
            hero.getHeroInventory().addItemWithNewCopyToItemList(craftingReagentItems.get(randomItem));
        }

        for (int i = 0; i < 3; i++) {
            List<ConsumableItem> consumableItems = ItemsLists.returnConsumableItemListByItemLevel(this.locationLevel, null);
            int randomItem = RandomNumberGenerator.getRandomNumber(0, consumableItems.size() - 1);
            hero.getHeroInventory().addItemWithNewCopyToItemList(consumableItems.get(randomItem));
        }
    }

    public List<Enemy> enemyList() {
        double stageMultiplier = 1 + (0.1 * stageCompleted);
        List<Enemy> enemies = EnemyList.returnEnemyListByLocationTypeAndLevel(this.locationType, this.locationLevel, null, EnemyRarity.ELITE);
        for (Enemy enemy : enemies) {
            enemy.increaseAbilityPointsByMultiplier(stageMultiplier);
        }
        return enemies;
    }

    public void questGiveForLocation() {
        QuestGiverCharacter questGiverCharacter = new QuestGiverCharacter("Random Name", 8);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return locationLevel == location.locationLevel && cleared == location.cleared && stageTotal == location.stageTotal && Objects.equals(locationName, location.locationName) && locationType == location.locationType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationName, locationLevel, stageTotal, locationType, cleared);
    }
}
