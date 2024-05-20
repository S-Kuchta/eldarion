package kuchtastefan.character.npc.enemy;

import kuchtastefan.ability.Ability;
import kuchtastefan.character.npc.CharacterType;
import kuchtastefan.character.npc.NonPlayerCharacter;
import kuchtastefan.constant.Constant;
import kuchtastefan.item.Item;
import kuchtastefan.item.ItemDB;
import kuchtastefan.item.itemFilter.ItemFilter;
import kuchtastefan.utility.RandomNumberGenerator;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class Enemy extends NonPlayerCharacter {

    private List<Item> itemsDrop;
    private Integer[] constantItemDrop;
    private double goldDrop;


    public Enemy(String name, Map<Ability, Integer> abilities,
                 CharacterType characterType, int[] enemySpells) {

        super(name, abilities, enemySpells, characterType);
        this.goldDrop = 0;
        this.itemsDrop = new ArrayList<>();
    }

    public void setGoldDrop() {
        if (this.characterType.equals(CharacterType.HUMANOID)) {
            double goldDrop = 15 + this.level + ((double) this.characterRarity.getExperienceGainedValue() / 2);

            setGoldDrop(RandomNumberGenerator.getRandomNumber(
                    (int) (goldDrop * 0.75), (int) goldDrop));
        }
    }

    public void setItemDrop() {
        if (this.itemsDrop == null) {
            this.itemsDrop = new ArrayList<>();
        }

        List<Item> itemList = ItemDB.returnItemListForEnemyDrop(new ItemFilter(this.level));
        int itemsForDrop = RandomNumberGenerator.getRandomNumber(1, 3);

        for (int i = 0; i < itemsForDrop; i++) {
            addItemToItemDrop(itemList.get(RandomNumberGenerator.getRandomNumber(0, itemList.size() - 1)));
        }

        if (this.constantItemDrop != null) {
            for (int itemId : this.constantItemDrop) {
                addItemToItemDrop(ItemDB.returnItemFromDB(itemId));
            }
        }
    }

    public void addItemToItemDrop(Item item) {
        if (!this.itemsDrop.contains(item)) {
            this.itemsDrop.add(item);
        }
    }

    public double enemyExperiencePointsValue() {
        return this.getLevel() * Constant.GAIN_EXPERIENCE_LEVEL_MULTIPLIER + this.getCharacterRarity().getExperienceGainedValue();
    }
}
