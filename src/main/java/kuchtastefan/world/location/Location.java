package kuchtastefan.world.location;

import kuchtastefan.quest.Quest;
import kuchtastefan.quest.QuestStatus;
import kuchtastefan.quest.questGiver.QuestGiverCharacterDB;
import kuchtastefan.utility.ConsoleColor;
import kuchtastefan.utility.PrintUtil;
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
    protected int stageTotal;
    protected int stageCompleted;
    protected boolean cleared;
    protected boolean canLocationBeExplored;
    protected LocationDifficulty locationDifficulty;
    protected Map<Integer, LocationStage> locationStages;


    public Location(int locationId, String locationName, int locationLevel, int stageTotal, boolean canLocationBeExplored) {
        this.locationId = locationId;
        this.locationName = locationName;
        this.locationLevel = locationLevel;
        this.stageTotal = stageTotal;
        this.stageCompleted = 0;
        this.cleared = false;
        this.canLocationBeExplored = canLocationBeExplored;
        this.locationStages = new HashMap<>();
    }

    public void incrementStageCompleted() {
        this.stageCompleted++;
    }

    public StringBuilder returnLocationServices() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return locationLevel == location.locationLevel
                && cleared == location.cleared
                && stageTotal == location.stageTotal
                && Objects.equals(locationName, location.locationName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationName, locationLevel, stageTotal, cleared);
    }
}
