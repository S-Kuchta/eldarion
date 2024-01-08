package kuchtastefan.characters.enemy;

import java.util.ArrayList;
import java.util.List;

public class EnemyList {
    private final List<Enemy> enemyList;

    public EnemyList() {
        this.enemyList = new ArrayList<>();
    }

    public List<Enemy> getEnemyList() {
        return enemyList;
    }
}
