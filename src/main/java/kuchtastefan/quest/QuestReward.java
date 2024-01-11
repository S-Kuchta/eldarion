package kuchtastefan.quest;

import kuchtastefan.characters.hero.Hero;
import kuchtastefan.items.Item;

import java.util.List;

public class QuestReward {
    private final Hero hero;
    private List<Item> itemsReward;
    private double goldsReward;
    private double experiencePointsReward;

    public QuestReward(Hero hero, List<Item> itemsReward, double goldsReward, double experiencePointsReward) {
        this.hero = hero;
        this.itemsReward = itemsReward;
        this.goldsReward = goldsReward;
        this.experiencePointsReward = experiencePointsReward;
    }

    protected void giveQuestReward() {
        this.hero.gainExperiencePoints(this.experiencePointsReward);
        System.out.println("You gained " + this.goldsReward + " golds");
        this.hero.addGolds(this.goldsReward);
        for (Item item : this.itemsReward) {
            this.hero.getHeroInventory().addItemWithNewCopyToItemList(item);
        }
    }

}
