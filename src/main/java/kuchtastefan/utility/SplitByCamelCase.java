package kuchtastefan.utility;

import org.apache.commons.lang3.StringUtils;

public class SplitByCamelCase {
    public static String splitByCamelCase(String s) {
        return String.join(" ", StringUtils.splitByCharacterTypeCamelCase(s));
    }
}
