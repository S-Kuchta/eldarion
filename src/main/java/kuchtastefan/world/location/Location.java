package kuchtastefan.world.location;

import kuchtastefan.quest.Quest;
import kuchtastefan.quest.QuestStatus;
import kuchtastefan.quest.questGiver.QuestGiverCharacterDB;
import kuchtastefan.utility.ConsoleColor;
import kuchtastefan.utility.PrintUtil;
import kuchtastefan.world.location.locationStage.CanEnterStageAfterComplete;
import kuchtastefan.world.location.locationStage.LocationStage;
import kuchtastefan.world.location.locationStage.specificLocationStage.LocationStageBlacksmith;
import kuchtastefan.world.location.locationStage.specificLocationStage.LocationStageQuestGiver;
import kuchtastefan.world.location.locationStage.specificLocationStage.LocationStageVendor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
public class Location {

    private final int locationId;
    protected final String locationName;
    protected final int locationLevel;
    protected boolean cleared;
    protected LocationDifficulty locationDifficulty;
    protected Map<Integer, LocationStage> locationStages;
    private int currentStageToEnter;


    public Location(int locationId, String locationName, int locationLevel) {
        this.locationId = locationId;
        this.locationName = locationName;
        this.locationLevel = locationLevel;
        this.cleared = false;
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
                    if (quest.getQuestStatus().equals(QuestStatus.AVAILABLE) || quest.getQuestStatus().equals(QuestStatus.COMPLETED)) {
                        stringBuilder.append("[");
                        stringBuilder.append(PrintUtil.returnQuestSuffix(quest));
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
                + "\tStages completed: " + this.getStageCompleted() + " / " + this.getStageTotal() + " "
                + "\tLocation difficulty: " + this.locationDifficulty);
        PrintUtil.printExtraLongDivider();
    }

    public void printMenu(int index) {
        System.out.println("\tWhat do you want to do?");
        PrintUtil.printMenuOptions("Go back on the path", "Explore location", "Hero Menu");

        if (this.getStageDiscovered() > 0) {
            System.out.println(ConsoleColor.YELLOW_UNDERLINED + "\t\t\t\t\t\t\tLocation Stages\t\t\t\t\t\t\t" + ConsoleColor.RESET);
            for (Map.Entry<Integer, LocationStage> locationStage : this.getLocationStages().entrySet()) {
                if (locationStage.getValue().isStageDiscovered()) {
                    String completed;
                    if (locationStage.getValue() instanceof CanEnterStageAfterComplete && this.isCleared()) {
                        completed = "";
                    } else {
                        completed = locationStage.getValue().isStageCompleted() ? ConsoleColor.YELLOW + " ✔ " + ConsoleColor.RESET : "";
                    }

                    PrintUtil.printIndexAndText(String.valueOf(index + locationStage.getKey()), locationStage.getValue().getStageName() + " " + completed);
                    System.out.println();
                }
            }
        }
    }

    public int getStageDiscovered() {
        int count = 0;
        for (LocationStage locationStage : locationStages.values()) {
            if (locationStage.isStageDiscovered()) {
                count++;
            }
        }

        return count;
    }

    public int getStageCompleted() {
        int count = 0;
        for (LocationStage locationStage : locationStages.values()) {
            if (locationStage.isStageCompleted()) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return locationLevel == location.locationLevel
                && cleared == location.cleared
                && Objects.equals(locationName, location.locationName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationName, locationLevel, cleared);
    }
}
