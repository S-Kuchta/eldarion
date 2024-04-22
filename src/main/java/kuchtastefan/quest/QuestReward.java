package kuchtastefan.quest;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.item.Item;
import kuchtastefan.item.ItemDB;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestReward {
    private Integer[] itemsReward;
    private final double goldsReward;
    private double experiencePointsReward;

    public QuestReward(Integer[] itemsReward, double goldsReward, double experiencePointsReward) {
        this.itemsReward = itemsReward;
        this.goldsReward = goldsReward;
        this.experiencePointsReward = experiencePointsReward;
    }

    public void giveQuestReward(Hero hero) {
        hero.gainExperiencePoints(this.experiencePointsReward);
        System.out.println("\tYou gained " + this.goldsReward + " golds");
        hero.addGolds(this.goldsReward);

        for (Integer itemId : this.itemsReward) {
            Item itemReward = ItemDB.returnItemFromDB(itemId);
            hero.getHeroInventory().addItemToInventory(itemReward, 1);
            System.out.println("\tReward for completing the quest: " + itemReward.getName());
        }
    }

    public void calculateExperiencePointsReward(int questLevel) {
        this.experiencePointsReward = 80 * questLevel + 100;
    }
}
