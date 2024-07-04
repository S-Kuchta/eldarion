package kuchtastefan.world.location.locationStage;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.item.Item;
import kuchtastefan.item.ItemDB;
import kuchtastefan.world.location.Location;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class LocationStage {

    private String stageName;
    private LocationStageStatus stageStatus;
    private Integer[] itemsIdNeededToEnterStage;

    public LocationStage(String stageName) {
        this.stageStatus = LocationStageStatus.NOT_DISCOVERED;
        this.stageName = stageName;
    }


    public abstract boolean exploreStage(Hero hero, Location location);

    private boolean haveHeroKey(Hero hero) {
        for (Integer itemId : this.itemsIdNeededToEnterStage) {
            Item item = ItemDB.returnItemFromDB(itemId);
            if (!hero.getHeroInventoryManager().hasRequiredItems(item, 1)) {
                return false;
            }
        }

        return true;
    }

    public boolean canHeroEnterStage(Hero hero) {
        if (!this.haveHeroKey(hero)) {
            System.out.println("\tYou don't have needed keys to enter location! You need: ");
            for (int itemId : this.itemsIdNeededToEnterStage) {
                System.out.println("\t" + ItemDB.returnItemFromDB(itemId).getName() + ", ");
            }

            return false;
        }

        if (!(this instanceof CanEnterStageAfterComplete) && this.isCleared()) {
            System.out.println("\tNothing new to do in " + this.stageName);
            return false;
        }

        return true;
    }

    public void discoverStage() {
        if (isDiscovered() || isCleared()) {
            return;
        }

        this.stageStatus = LocationStageStatus.DISCOVERED;
    }

    public void completeStage() {
        this.stageStatus = LocationStageStatus.CLEARED;
        if (this instanceof RemoveLocationStageProgress removeLocationStageProgress) {
            removeLocationStageProgress.removeProgressAfterCompletedStage();
        }
    }

    public boolean isDiscovered() {
        return this.stageStatus == LocationStageStatus.DISCOVERED;
    }

    public boolean isCleared() {
        return this.stageStatus == LocationStageStatus.CLEARED;
    }
}
