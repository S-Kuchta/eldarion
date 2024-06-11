package kuchtastefan.item;

import kuchtastefan.service.FileService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ItemDBTest {

    @BeforeAll
    static void setUp() {
        FileService fileService = new FileService();
        fileService.importWearableItemsFromFile();
    }

    @Test
    void checkIfListContainsMoreThanOneItemWithTheSameId() {
        boolean hasDuplicates = hasDuplicateIds();
        assertFalse(hasDuplicates);
    }

    public static boolean hasDuplicateIds() {
        Set<Integer> ids = new HashSet<>();
        for (Item item : ItemDB.returnItemList()) {
            if (!ids.add(item.getItemId())) {
                return true;
            }
        }

        return false;
    }


}