package kuchtastefan.character.spell;

import kuchtastefan.character.GameCharacter;
import kuchtastefan.character.hero.Hero;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CharactersInvolvedInBattle {

        private Hero hero;
        private GameCharacter spellCaster;
        private GameCharacter spellTarget;
        private List<GameCharacter> enemyCharacters;
        private List<GameCharacter> alliesCharacters;
        private List<GameCharacter> tempCharacterList;


        public CharactersInvolvedInBattle(GameCharacter spellCaster, GameCharacter spellTarget, List<GameCharacter> enemyCharacters, List<GameCharacter> alliesCharacters, List<GameCharacter> tempCharacterList) {
                this.spellCaster = spellCaster;
                this.spellTarget = spellTarget;
                this.enemyCharacters = enemyCharacters;
                this.alliesCharacters = alliesCharacters;
                this.tempCharacterList = tempCharacterList;
        }

        public CharactersInvolvedInBattle(Hero hero) {
                this.hero = hero;
                this.spellCaster = hero;
                this.spellTarget = hero;
                this.enemyCharacters = new ArrayList<>();
                this.alliesCharacters = new ArrayList<>();
                this.tempCharacterList = new ArrayList<>();
        }

        public List<GameCharacter> getCharacterList(GameCharacter gameCharacter, boolean isSameList) {
                return alliesCharacters.contains(gameCharacter) == isSameList ? alliesCharacters : enemyCharacters;
        }
}


