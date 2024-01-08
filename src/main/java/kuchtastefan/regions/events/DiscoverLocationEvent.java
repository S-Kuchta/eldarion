package kuchtastefan.regions.events;

import kuchtastefan.characters.hero.Hero;
import kuchtastefan.regions.locations.Location;
import kuchtastefan.utility.RandomNumberGenerator;

import java.util.List;

public class DiscoverLocationEvent extends Event {

    private final List<Location> allLocations;
    private final List<Location> discoveredLocations;

    public DiscoverLocationEvent(int eventLevel, List<Location> allLocations, List<Location> discoveredLocations) {
        super(eventLevel);
        this.allLocations = allLocations;
        this.discoveredLocations = discoveredLocations;
    }

    @Override
    public void eventOccurs(Hero hero) {
        if (RandomNumberGenerator.getRandomNumber(0,5) > 4) {
            while (true) {
                int randomNumber = RandomNumberGenerator.getRandomNumber(0, this.allLocations.size() - 1);
                Location location = allLocations.get(randomNumber);
                if (!discoveredLocations.contains(location)) {
                    discoveredLocations.add(location);
                    System.out.println("\tYou discovered " + location.getLocationName() + ", recommended level: " + location.getLocationLevel() + " level");
                    location.locationMenu();
                    break;
                }

                if (allLocations.size() == discoveredLocations.size()) {
                    System.out.println("\tYou discovered all locations in this region");
                    break;
                }
            }
        } else {
            System.out.println("\tYou believed you caught a glimpse of something, yet it proved to be unremarkable");
        }
    }

}
