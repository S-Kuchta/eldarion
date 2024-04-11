package kuchtastefan.character.spell;

import kuchtastefan.actions.Action;
import kuchtastefan.character.hero.CharacterClass;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Spell {
    private final int spellId;
    private final String spellName;
    private final String spellDescription;
    private final List<Action> spellActions;
    private final int spellLevel;
    private int spellManaCost;
    private final int turnCoolDown;
    private int currentTurnCoolDown;
    private boolean canSpellBeCasted;
    private final CharacterClass spellClass;
    private final boolean hitAllEnemy;


    public Spell(int spellId, String spellName, String spellDescription, List<Action> spellActions, int turnCoolDown,
                 int spellLevel, int spellManaCost, CharacterClass spellClass, boolean hitAllEnemy) {

        this.spellId = spellId;
        this.spellName = spellName;
        this.spellDescription = spellDescription;
        this.spellActions = spellActions;
        this.spellLevel = spellLevel;
        this.spellManaCost = spellManaCost;
        this.turnCoolDown = turnCoolDown;
        this.currentTurnCoolDown = turnCoolDown;
        this.hitAllEnemy = hitAllEnemy;
        this.canSpellBeCasted = true;
        this.spellClass = spellClass;
    }

    public void increaseSpellCoolDown() {
        checkSpellCoolDown();
        this.currentTurnCoolDown++;
    }

    public void checkSpellCoolDown() {
        if (this.currentTurnCoolDown < this.turnCoolDown) {
            this.canSpellBeCasted = false;
        }

        if (this.currentTurnCoolDown >= this.turnCoolDown) {
            this.canSpellBeCasted = true;
        }
    }
}
