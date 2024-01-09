package kuchtastefan.service;

public class ExperiencePointsService {

    private double neededExperiencePointsForNewLevel;

    public boolean gainedNewLevel(double heroExperiencePoints) {
        return heroExperiencePoints >= this.neededExperiencePointsForNewLevel;
    }

    public void setNeededExperiencePointsForNewLevel(int heroLevel) {
        this.neededExperiencePointsForNewLevel = 400 * 1.2 * heroLevel;
    }

    public double getNeededExperiencePointsForNewLevel() {
        return neededExperiencePointsForNewLevel;
    }
}
