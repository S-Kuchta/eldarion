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
    private boolean stageCompleted;
    private boolean stageDiscovered;
    private Integer[] itemsIdNeededToEnterStage;

    public LocationStage(String stageName) {
        this.stageName = stageName;
    }

    public abstract boolean exploreStage(Hero hero, Location location);

    private boolean haveHeroKey(Hero hero) {
        for (Integer itemId : this.itemsIdNeededToEnterStage) {
            Item item = ItemDB.returnItemFromDB(itemId);
            if (!hero.getHeroInventory().getHeroInventory().containsKey(item)) {
                return false;
            }
        }

        return true;
    }

    public void completeStage() {
        this.stageCompleted = true;
        if (this instanceof RemoveLocationStageProgress removeLocationStageProgress) {
            removeLocationStageProgress.removeProgressAfterCompletedStage();
        }
    }

    public boolean canHeroEnterStage(Hero hero) {
        if (!this.haveHeroKey(hero)) {
            System.out.println("\tYou don't have needed keys to enter location! You need: ");
            for (int itemId : this.getItemsIdNeededToEnterStage()) {
                System.out.println("\t" + ItemDB.returnItemFromDB(itemId).getName() + ", ");
            }

            return false;
        }

        if (!(this instanceof CanEnterStageAfterComplete) && this.isStageCompleted()) {
            System.out.println("\tNothing new to do in " + this.getStageName());
            return false;
        }

        return true;
    }
}
