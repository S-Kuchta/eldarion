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
        this.neededExperiencePointsForNewLevel = Constant.BASE_EXPERIENCE_POINTS_NEEDED_FOR_NEW_LEVEL * 1.2 * heroLevel;
    }
}
