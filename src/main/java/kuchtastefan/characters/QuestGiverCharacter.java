package kuchtastefan.characters;

import kuchtastefan.characters.hero.Hero;
import kuchtastefan.quest.Quest;
import kuchtastefan.quest.QuestService;
import kuchtastefan.utility.PrintUtil;

import java.util.ArrayList;
import java.util.List;

public class QuestGiverCharacter extends GameCharacter {

    private final List<Quest> quests;
    private final QuestService questService;
    private final String baseName;


    public QuestGiverCharacter(String name, int level) {
        super(name, level);
        this.quests = new ArrayList<>();
        this.questService = new QuestService();
        this.baseName = name;
    }

    private void connectHeroQuestListWithCharacterQuestList(Hero hero) {
        for (Quest heroQuest : hero.getListOfAcceptedQuests()) {
            for (Quest quest : this.quests) {
                if (quest.equals(heroQuest)) {
                    this.quests.remove(quest);
                    this.quests.add(heroQuest);
                }
            }
        }
    }

    public void questGiverMenu(Hero hero) {
        connectHeroQuestListWithCharacterQuestList(hero);
        setNameBasedOnQuestsAvailable(hero);

        this.questService.questGiverMenu(hero, this.quests, this.name);
    }

    public void setNameBasedOnQuestsAvailable(Hero hero) {
        connectHeroQuestListWithCharacterQuestList(hero);
        boolean haveQuestAvailable = false;
        boolean haveQuestComplete = false;

        int numberOfCompletedQuests = 0;

        for (Quest quest : this.quests) {
            if (hero.getListOfAcceptedQuests().contains(quest)) {
                haveQuestAvailable = true;
            }

            if (hero.getListOfAcceptedQuests().contains(quest) && quest.isCompleted() && !quest.isTurnedIn()) {
                haveQuestComplete = true;
            }

            if (quest.isTurnedIn()) {
                numberOfCompletedQuests++;
            }
        }

        if (!haveQuestAvailable) {
            this.setName(this.baseName + " - ! -");
        } else {
            this.setName(this.baseName);
        }

        if (haveQuestComplete) {
            this.setName(this.baseName + " - ? -");
        }

        System.out.println(this.quests.size() + " / " + numberOfCompletedQuests);
        if (this.quests.size() == numberOfCompletedQuests) {
            this.setName(this.baseName);
        }
    }

    public void addQuest(Quest quest) {
        this.quests.add(quest);
    }
}
