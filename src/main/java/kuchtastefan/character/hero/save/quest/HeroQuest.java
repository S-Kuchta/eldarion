package kuchtastefan.character.hero.save.quest;

import kuchtastefan.character.hero.save.SaveGameEntity;
import kuchtastefan.quest.QuestStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class HeroQuest implements SaveGameEntity {

    private final int id;
    private QuestStatus questStatus;
}
