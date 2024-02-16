package kuchtastefan.regions.locations;

import kuchtastefan.characters.hero.Hero;
import kuchtastefan.items.Item;
import kuchtastefan.items.ItemsLists;
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
            Item item = ItemsLists.getItemMapIdItem().get(i);
            if (!hero.getHeroInventory().getHeroInventory().containsKey(item)) {
                return false;
            }
        }

        return true;
    }
}
