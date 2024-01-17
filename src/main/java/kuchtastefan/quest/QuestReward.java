package kuchtastefan.quest;

import kuchtastefan.characters.hero.Hero;
import kuchtastefan.items.Item;
import kuchtastefan.items.wearableItem.WearableItem;
import kuchtastefan.utility.RandomNumberGenerator;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class QuestReward {
    private final List<Item> itemsReward;
    private final double goldsReward;
    private final double experiencePointsReward;
    private int questLevel;

    public QuestReward(List<Item> itemsReward, double goldsReward, double experiencePointsReward, int questLevel) {
        this.itemsReward = itemsReward;
        this.goldsReward = goldsReward;
        this.experiencePointsReward = experiencePointsReward;
        this.questLevel = questLevel;
    }

    public QuestReward(double goldsReward, double experiencePointsReward, int questLevel) {
        this.goldsReward = goldsReward;
        this.experiencePointsReward = experiencePointsReward;
        this.questLevel = questLevel;
        this.itemsReward = new ArrayList<>();
    }

    protected void giveQuestReward(Hero hero) {
        hero.gainExperiencePoints(this.experiencePointsReward);
        System.out.println("You gained " + this.goldsReward + " golds");
        hero.addGolds(this.goldsReward);
        for (Item item : this.itemsReward) {
            System.out.println("Your quest reward is " + item.getName());
            hero.getHeroInventory().addItemWithNewCopyToItemList(item);
        }
    }

    public void generateRandomWearableItemsReward(int numberOfItems, List<WearableItem> itemsToReward) {
        for (int i = 0; i < numberOfItems; i++) {
            int randomItemGet = RandomNumberGenerator.getRandomNumber(0, itemsToReward.size() - 1);
            this.itemsReward.add(itemsToReward.get(randomItemGet));
        }
    }
}
