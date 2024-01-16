package kuchtastefan.characters.enemy;

import kuchtastefan.ability.Ability;
import kuchtastefan.characters.GameCharacter;
import kuchtastefan.items.Item;
import kuchtastefan.items.ItemsLists;
import kuchtastefan.regions.locations.LocationType;
import kuchtastefan.utility.RandomNumberGenerator;

import java.util.*;

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

    public List<Item> getItemsDrop() {
        return itemsDrop;
    }

    public double getGoldDrop() {
        return goldDrop;
    }

    public void setGoldDrop(double goldDrop) {
        this.goldDrop = goldDrop;
    }

    public EnemyType getEnemyType() {
        return enemyType;
    }

    public LocationType[] getLocationType() {
        return locationType;
    }

    public int getMaxStack() {
        return maxStack;
    }

    public void setEnemyType(EnemyType enemyType) {
        this.enemyType = enemyType;
    }

    public void setItemsDrop(List<Item> itemsDrop) {
        this.itemsDrop = itemsDrop;
    }

    public EnemyRarity getEnemyRarity() {
        return enemyRarity;
    }

    public void setEnemyRarity(EnemyRarity enemyRarity) {
        this.enemyRarity = enemyRarity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enemy enemy = (Enemy) o;
        return this.name.equals(enemy.name) && enemyType == enemy.enemyType && Arrays.equals(locationType, enemy.locationType);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, goldDrop, enemyType);
        result = 31 * result + Arrays.hashCode(locationType);
        return result;
    }
}
