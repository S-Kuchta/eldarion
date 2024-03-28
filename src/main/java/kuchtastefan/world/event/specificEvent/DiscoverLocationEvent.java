package kuchtastefan.world.event.specificEvent;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.world.event.Event;
import kuchtastefan.world.location.Location;
import kuchtastefan.utility.RandomNumberGenerator;
import lombok.Getter;

import java.util.List;

@Getter
public class DiscoverLocationEvent extends Event {

    private final List<Location> allLocations;


    public DiscoverLocationEvent(int eventLevel, List<Location> allLocations) {
        super(eventLevel);
        this.allLocations = allLocations;
    }

    @Override
    public boolean eventOccurs(Hero hero) {
        while (true) {
            int randomNumber = RandomNumberGenerator.getRandomNumber(0, this.allLocations.size() - 1);
            Location location = this.allLocations.get(randomNumber);
            if (!hero.getDiscoveredLocationList().containsKey(location.getLocationId()) && location.getLocationLevel() <= hero.getLevel()) {
                hero.getDiscoveredLocationList().put(location.getLocationId(), location);
                System.out.println("\t--> You discovered " + location.getLocationName() + ", recommended level: " + location.getLocationLevel() + " level <--");
                hero.gainExperiencePoints(50);
                break;
            }

            if (location.getLocationLevel() > hero.getLevel()) {
                System.out.println("\tYou are too afraid to go closer to the location.");
                break;
            }

            if (hero.getDiscoveredLocationList().containsKey(location.getLocationId())) {
                System.out.println("\tThis location seems familiar to you, you've been here before.");
                break;
            }

            if (this.allLocations.size() == hero.getDiscoveredLocationList().size()) {
                System.out.println("\tYou discovered all locations in this region.");
                break;
            }
        }

        return true;
    }

}
