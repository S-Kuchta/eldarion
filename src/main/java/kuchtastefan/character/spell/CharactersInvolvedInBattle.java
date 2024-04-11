package kuchtastefan.character.spell;

import kuchtastefan.character.GameCharacter;
import kuchtastefan.character.hero.Hero;

import java.util.List;

public record CharactersInvolvedInBattle(Hero hero, GameCharacter spellCaster, GameCharacter spellTarget,
                                         List<GameCharacter> enemyCharacters, List<GameCharacter> alliesCharacters,
                                         List<GameCharacter> tempCharacterList) {

}
