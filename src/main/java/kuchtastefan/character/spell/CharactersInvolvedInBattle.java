package kuchtastefan.character.spell;

import kuchtastefan.character.GameCharacter;
import kuchtastefan.character.hero.Hero;
import lombok.Getter;

import java.util.List;

@Getter
public class CharactersInvolvedInBattle {

        private Hero hero;
        private final GameCharacter spellCaster;
        private final GameCharacter spellTarget;
        private final List<GameCharacter> enemyCharacters;
        private final List<GameCharacter> alliesCharacters;
        private final List<GameCharacter> tempCharacterList;

        public CharactersInvolvedInBattle(Hero hero, GameCharacter spellCaster, GameCharacter spellTarget, List<GameCharacter> enemyCharacters, List<GameCharacter> alliesCharacters, List<GameCharacter> tempCharacterList) {
                this.hero = hero;
                this.spellCaster = spellCaster;
                this.spellTarget = spellTarget;
                this.enemyCharacters = enemyCharacters;
                this.alliesCharacters = alliesCharacters;
                this.tempCharacterList = tempCharacterList;
        }

        public CharactersInvolvedInBattle(GameCharacter spellCaster, GameCharacter spellTarget, List<GameCharacter> enemyCharacters, List<GameCharacter> alliesCharacters, List<GameCharacter> tempCharacterList) {
                this.spellCaster = spellCaster;
                this.spellTarget = spellTarget;
                this.enemyCharacters = enemyCharacters;
                this.alliesCharacters = alliesCharacters;
                this.tempCharacterList = tempCharacterList;
        }
}


