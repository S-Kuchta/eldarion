package kuchtastefan.character.enemy;

import kuchtastefan.ability.Ability;
import kuchtastefan.character.npc.CharacterType;
import kuchtastefan.character.npc.NonPlayerCharacter;
import kuchtastefan.item.Item;
import kuchtastefan.item.ItemDB;
import kuchtastefan.region.location.LocationType;
import kuchtastefan.utility.RandomNumberGenerator;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class Enemy extends NonPlayerCharacter {

    private List<Item> itemsDrop;
    private Integer[] specialItemsDrop;
    private double goldDrop;
    private CharacterType characterType;
    private final LocationType[] locationType;
    private final int maxStack;


    public Enemy(String name, Map<Ability, Integer> abilities,
                 CharacterType characterType, LocationType[] locationType, int maxStack, int[] enemySpells) {

        super(name, abilities, enemySpells);
        this.goldDrop = 0;
        this.characterType = characterType;
        this.locationType = locationType;
        this.maxStack = maxStack;
        this.itemsDrop = new ArrayList<>();
    }

    public void goldDrop() {
        if (this.characterType.equals(CharacterType.HUMANOID)) {
            double goldDrop = 15 + this.level + ((double) this.characterRarity.getExperienceGainedValue() / 2);

            setGoldDrop(RandomNumberGenerator.getRandomNumber(
                    (int) (goldDrop * 0.75), (int) goldDrop));
        }
    }

    public void addItemsDropToEnemy() {
        if (this.itemsDrop == null) {
            this.itemsDrop = new ArrayList<>();
        }

        List<Item> itemList = ItemDB.returnItemListByLevel(getLevel(), null);
        int itemsForDrop = RandomNumberGenerator.getRandomNumber(1, 3);

        for (int i = 0; i < itemsForDrop; i++) {
            addItemToItemDrop(itemList.get(RandomNumberGenerator.getRandomNumber(0, itemList.size() - 1)));
        }

        if (this.specialItemsDrop != null) {
            for (int itemId : this.specialItemsDrop) {
                addItemToItemDrop(ItemDB.returnItemFromDB(itemId));
            }
        }
    }

    public void addItemToItemDrop(Item item) {
        if (!this.itemsDrop.contains(item)) {
            this.itemsDrop.add(item);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enemy enemy = (Enemy) o;
        return this.name.equals(enemy.name) && characterType == enemy.characterType && Arrays.equals(locationType, enemy.locationType) && enemy.characterRarity.equals(((Enemy) o).characterRarity);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, goldDrop, characterType, characterRarity);
        result = 31 * result + Arrays.hashCode(locationType);
        return result;
    }
}
