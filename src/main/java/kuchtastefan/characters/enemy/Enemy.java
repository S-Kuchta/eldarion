package kuchtastefan.characters.enemy;

import kuchtastefan.ability.Ability;
import kuchtastefan.characters.GameCharacter;
import kuchtastefan.items.Item;
import kuchtastefan.items.ItemsLists;
import kuchtastefan.regions.locations.LocationType;
import kuchtastefan.utility.RandomNumberGenerator;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class Enemy extends GameCharacter {

    private List<Item> itemsDrop;
    private double goldDrop;
    private EnemyType enemyType;
    private final LocationType[] locationType;
    private final int maxStack;
    private EnemyRarity enemyRarity;

    public Enemy(String name, Map<Ability, Integer> abilities,
                 EnemyType enemyType, LocationType[] locationType, int maxStack) {
        super(name, abilities);
        this.goldDrop = 0;
        this.enemyType = enemyType;
        this.locationType = locationType;
        this.maxStack = maxStack;
        this.itemsDrop = new ArrayList<>();
    }

    public void goldDrop() {
        if (this.enemyType.equals(EnemyType.HUMANOID)) {
            setGoldDrop(RandomNumberGenerator.getRandomNumber(5 + getLevel(), 15 + getLevel()));
        }
    }

    public void increaseAbilityPointsByMultiplier(double multiplier) {
        for (Ability ability : Ability.values()) {
            if (this.getAbilities().containsKey(ability)) {
                this.getAbilities().put(ability, (int) (this.getAbilities().get(ability) * multiplier));
            }
        }
    }

    public void itemsDrop() {
        List<Item> tempList = new ArrayList<>();
        List<Item> itemList = ItemsLists.returnItemListByLevel(getLevel(), null);
        int itemsForDrop = RandomNumberGenerator.getRandomNumber(1, 3);

        for (int i = 0; i < itemsForDrop; i++) {
            int randomItemGenerate = RandomNumberGenerator.getRandomNumber(0, itemList.size() - 1);
            tempList.add(itemList.get(randomItemGenerate));
        }

        this.setItemsDrop(tempList);
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
        return this.name.equals(enemy.name) && enemyType == enemy.enemyType && Arrays.equals(locationType, enemy.locationType) && enemy.enemyRarity.equals(((Enemy) o).enemyRarity);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, goldDrop, enemyType, enemyRarity);
        result = 31 * result + Arrays.hashCode(locationType);
        return result;
    }
}
