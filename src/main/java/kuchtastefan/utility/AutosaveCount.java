package kuchtastefan.utility;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.constant.Constant;
import kuchtastefan.service.FileService;

public class AutosaveCount {

    private static int autosaveCount = 0;

    public static void checkAutosaveCount(Hero hero) {
        incrementAutosaveCount();

        if (autosaveCount == Constant.AUTOSAVE_COUNT_NEEDED) {
            FileService fileService = new FileService();
            fileService.autoSave(hero);
            resetAutosaveCount();
        }
    }

    private static void incrementAutosaveCount() {
        autosaveCount++;
    }

    private static void resetAutosaveCount() {
        autosaveCount = 0;
    }
}
