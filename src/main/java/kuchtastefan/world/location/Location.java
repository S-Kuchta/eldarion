package kuchtastefan.world.location;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.quest.Quest;
import kuchtastefan.quest.QuestStatus;
import kuchtastefan.quest.questGiver.QuestGiverCharacterDB;
import kuchtastefan.utility.ConsoleColor;
import kuchtastefan.utility.printUtil.PrintUtil;
import kuchtastefan.utility.printUtil.QuestPrint;
import kuchtastefan.world.location.locationStage.CanEnterStageAfterComplete;
import kuchtastefan.world.location.locationStage.LocationStage;
import kuchtastefan.world.location.locationStage.LocationStageStatus;
import kuchtastefan.world.location.locationStage.specificLocationStage.LocationStageBlacksmith;
import kuchtastefan.world.location.locationStage.specificLocationStage.LocationStageQuestGiver;
import kuchtastefan.world.location.locationStage.specificLocationStage.LocationStageVendor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Location {

    private final int locationId;
    protected final String locationName;
    protected final int locationLevel;
    protected LocationStatus locationStatus;
    protected LocationDifficulty locationDifficulty;
    protected Map<Integer, LocationStage> locationStages;


    public Location(int locationId, String locationName, int locationLevel) {
        this.locationId = locationId;
        this.locationName = locationName;
        this.locationLevel = locationLevel;
        this.locationStatus = LocationStatus.NOT_DISCOVERED;
        this.locationStages = new HashMap<>();
    }


    public StringBuilder printLocationServices() {
        boolean vendor = false;
        int numOfVendors = 0;

        StringBuilder stringBuilder = new StringBuilder();
        for (LocationStage stage : locationStages.values()) {
            if (stage instanceof LocationStageVendor) {
                vendor = true;
                numOfVendors++;
            }

            if (stage instanceof LocationStageBlacksmith) {
                stringBuilder.append("[");
                stringBuilder.append(ConsoleColor.YELLOW).append("Blacksmithing").append(ConsoleColor.RESET);
                stringBuilder.append("]");
            }

            if (stage instanceof LocationStageQuestGiver locationStageQuestGiver) {
                for (Quest quest : QuestGiverCharacterDB.returnQuestGiverFromDB(locationStageQuestGiver.getQuestGiverId()).getQuests()) {
                    if (quest.getStatus().equals(QuestStatus.AVAILABLE) || quest.getStatus().equals(QuestStatus.COMPLETED)) {
                        stringBuilder.append("[");
                        stringBuilder.append(QuestPrint.returnQuestSuffix(quest));
                        stringBuilder.append("]");
                    }
                }
            }
        }

        if (vendor) {
            stringBuilder.append("[").append(ConsoleColor.YELLOW);
            stringBuilder.append("Vendor ").append(ConsoleColor.RESET).append("(").append(numOfVendors).append("x)");
            stringBuilder.append("]");
        }

        return stringBuilder;
    }

    public void printHeader() {
        PrintUtil.printExtraLongDivider();
        System.out.println(ConsoleColor.YELLOW + "\t" + this.getLocationName() + ConsoleColor.RESET
                + "\tLocation level: " + this.locationLevel + " "
                + "\tStages completed: " + this.getCountOfStageCompleted() + " / " + this.getStageTotal() + " "
                + "\tLocation difficulty: " + this.locationDifficulty);
        PrintUtil.printExtraLongDivider();
    }

    public void printMenu(int index) {
        System.out.println("\tWhat do you want to do?");
        PrintUtil.printMenuOptions("Go back on the path", "Explore location", "Hero Menu");

        if (this.getCountOfStageDiscovered() > 0 || this.getCountOfStageCompleted() > 0) {
            System.out.println(ConsoleColor.YELLOW_UNDERLINED + "\t\t\t\t\t\t\tLocation Stages\t\t\t\t\t\t\t" + ConsoleColor.RESET);
            for (Map.Entry<Integer, LocationStage> locationStage : this.getLocationStages().entrySet()) {
                if (locationStage.getValue().isDiscovered() || locationStage.getValue().isCleared()) {

                    String completed;
                    if (locationStage.getValue() instanceof CanEnterStageAfterComplete && this.isCompleted()) {
                        completed = "";
                    } else {
                        completed = locationStage.getValue().isCleared() ? ConsoleColor.YELLOW + " ✔ " + ConsoleColor.RESET : "";
                    }

                    PrintUtil.printIndexAndText(String.valueOf(index + locationStage.getKey()), locationStage.getValue().getStageName() + " " + completed);
                    System.out.println();
                }
            }
        }
    }

    public void questLocationStageSet(Hero hero) {
        for (LocationStage locationStage : this.locationStages.values()) {
            if (locationStage instanceof LocationStageQuestGiver locationStageQuestGiver) {
                locationStageQuestGiver.setStage(hero);
            }
        }
    }

    public int getCountOfStageDiscovered() {
        int count = 0;
        for (LocationStage locationStage : locationStages.values()) {
            if (locationStage.isDiscovered()) {
                count++;
            }
        }

        return count;
    }

    public int getCountOfStageCompleted() {
        int count = 0;
        for (LocationStage locationStage : locationStages.values()) {
            if (locationStage.isCleared()) {
                count++;
            }
        }

        return count;
    }

    public LocationStage getLocationStage(int order) {
        return locationStages.get(order);
    }

    public int getStageTotal() {
        return locationStages.size();
    }

    public void setDiscovered() {
        this.locationStatus = LocationStatus.DISCOVERED;
    }

    public void setCompleted() {
        this.locationStatus = LocationStatus.COMPLETED;
    }

    public boolean isDiscovered() {
        return this.locationStatus == LocationStatus.DISCOVERED;
    }

    public boolean isCompleted() {
        return this.locationStatus == LocationStatus.COMPLETED;
    }
}
