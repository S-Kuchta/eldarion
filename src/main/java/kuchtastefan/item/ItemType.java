package kuchtastefan.item;

public enum ItemType {

    SWORD("Attack with sword have 5% chance to attack two times."),
    AXE("Critical hit with Axe is 3x stronger instead of 2x"),
    HEAD("Head armor"),
    BODY("Body armor"),
    HANDS("Hands armor"),
    BOOTS("Boots armor");

    private final String description;

    ItemType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
