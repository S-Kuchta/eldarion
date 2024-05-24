package kuchtastefan.utility.printUtil;

import kuchtastefan.character.hero.Hero;

import static kuchtastefan.utility.printUtil.PrintUtil.printLongDivider;

public class ShopPrint {

    public static void printShopHeader(Hero hero, String shop) {
        printLongDivider();
        System.out.println("\t\t" + "Welcome to the "
                + shop + " Shop\t\t\tYou have "
                + hero.getHeroGold() + " golds");
        printLongDivider();
    }


}
