package kuchtastefan.region.location;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.item.Item;
import kuchtastefan.item.ItemDB;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class LocationStage {

    private final String stageName;
    private boolean stageCompleted;
    private boolean stageDiscovered;
    private Integer[] itemsIdNeededToEnterStage;

    public LocationStage(String stageName) {
        this.stageName = stageName;
    }

    public abstract boolean exploreStage(Hero hero, Location location);

    public boolean canHeroEnterStage(Hero hero) {
        for (Integer i : this.itemsIdNeededToEnterStage) {
            Item item = ItemDB.returnItemFromDB(i);
            if (!hero.getHeroInventory().getHeroInventory().containsKey(item)) {
                return false;
            }
        }

        return true;
    }
}
