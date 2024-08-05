package kuchtastefan.world.event.specificEvent;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.character.hero.save.location.HeroLocation;
import kuchtastefan.utility.RandomNumberGenerator;
import kuchtastefan.world.event.Event;
import kuchtastefan.world.location.Location;
import kuchtastefan.world.location.LocationDB;
import kuchtastefan.world.location.LocationStatus;
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

            if (location.isDiscovered()) {
                System.out.println("\t--> This location seems familiar to you, you've been here before <--");
                break;
            }

            if (!location.isDiscovered() && location.getLocationLevel() <= hero.getLevel()) {

                location.setLocationStatus(LocationStatus.DISCOVERED);
                location.saveLocation(hero);

                System.out.println("\t--> You discovered " + location.getLocationName() + ", recommended level: " + location.getLocationLevel() + " level <--");
                hero.gainExperiencePoints(50);
                break;
            }
        }

        return true;
    }

}
