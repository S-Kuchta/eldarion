package kuchtastefan.utility;

import org.apache.commons.lang3.StringUtils;

public class SplitByCamelCase {
    public static String splitStringByCamelCase(String s) {
        return String.join(" ", StringUtils.splitByCharacterTypeCamelCase(s));
    }
}
