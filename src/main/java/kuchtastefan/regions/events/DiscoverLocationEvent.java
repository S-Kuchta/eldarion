package kuchtastefan.regions.events;

import kuchtastefan.characters.hero.Hero;
import kuchtastefan.regions.locations.Location;
import kuchtastefan.regions.locations.LocationService;
import kuchtastefan.utility.RandomNumberGenerator;
import lombok.Getter;

import java.util.List;

@Getter
public class DiscoverLocationEvent extends Event {

    private final List<Location> allLocations;
    private final List<Location> discoveredLocations;

    public DiscoverLocationEvent(int eventLevel, List<Location> allLocations, List<Location> discoveredLocations) {
        super(eventLevel);
        this.allLocations = allLocations;
        this.discoveredLocations = discoveredLocations;
    }

    @Override
    public boolean eventOccurs(Hero hero) {
        if (RandomNumberGenerator.getRandomNumber(0, 5) > 4) {
            while (true) {
                int randomNumber = RandomNumberGenerator.getRandomNumber(0, this.allLocations.size() - 1);
                Location location = this.allLocations.get(randomNumber);
                if (!this.discoveredLocations.contains(location)) {
                    this.discoveredLocations.add(location);
                    System.out.println("\t--> You discovered " + location.getLocationName() + ", recommended level: " + location.getLocationLevel() + " level <--");
                    hero.gainExperiencePoints(50);
                    new LocationService().locationMenu(hero, location);
                    break;
                }

                if (this.allLocations.size() == this.discoveredLocations.size()) {
                    System.out.println("\tYou discovered all locations in this region");
                    break;
                }
            }
        } else {
            System.out.println("\t--> You believed you caught a glimpse of something, yet it proved to be unremarkable <--");
        }
        return true;
    }

}
