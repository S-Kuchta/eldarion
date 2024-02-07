package kuchtastefan.quest;

import kuchtastefan.characters.hero.Hero;
import kuchtastefan.items.Item;
import kuchtastefan.items.ItemsLists;
import kuchtastefan.items.wearableItem.WearableItem;
import kuchtastefan.utility.RandomNumberGenerator;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuestReward {
    private Integer[] itemsReward;
    private final double goldsReward;
    private final double experiencePointsReward;

    public QuestReward(Integer[] itemsReward, double goldsReward, double experiencePointsReward) {
        this.itemsReward = itemsReward;
        this.goldsReward = goldsReward;
        this.experiencePointsReward = experiencePointsReward;
    }

    protected void giveQuestReward(Hero hero) {
        hero.gainExperiencePoints(this.experiencePointsReward);
        System.out.println("\tYou gained " + this.goldsReward + " golds");
        hero.addGolds(this.goldsReward);

        for (Integer itemId : this.itemsReward) {
            Item itemReward = ItemsLists.getItemMapIdItem().get(itemId);
            hero.getHeroInventory().addItemWithNewCopyToItemList(itemReward);
            System.out.println("\tReward for completing the quest: " + itemReward.getName());
        }
    }

    public void generateRandomWearableItemsReward(int numberOfItems, List<WearableItem> itemsToReward) {
        for (int i = 0; i < numberOfItems; i++) {
            int randomItemGet = RandomNumberGenerator.getRandomNumber(0, itemsToReward.size() - 1);
//            this.itemsReward.add(itemsToReward.get(randomItemGet));
        }
    }
}
