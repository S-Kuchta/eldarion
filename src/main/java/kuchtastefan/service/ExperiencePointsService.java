package kuchtastefan.service;

import kuchtastefan.constant.Constant;
import lombok.Getter;

@Getter
public class ExperiencePointsService {

    private double neededExperiencePointsForNewLevel;

    public boolean gainedNewLevel(double heroExperiencePoints) {
        return heroExperiencePoints >= this.neededExperiencePointsForNewLevel;
    }

    public void setNeededExperiencePointsForNewLevel(int heroLevel) {
        double multiplier = 1 + 0.1 * heroLevel - 0.1;
        this.neededExperiencePointsForNewLevel = Math.ceil(Constant.BASE_EXPERIENCE_POINTS_NEEDED_FOR_NEW_LEVEL * multiplier * heroLevel);
    }
}
