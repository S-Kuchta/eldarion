package kuchtastefan;

import java.util.Scanner;

public class ScannerUtil {

    private final static Scanner SCANNER = new Scanner(System.in);

    public static String stringScanner() {
        return SCANNER.nextLine();
    }

    public static int intScanner() {
        while(true) {
            try {
                int scanner = SCANNER.nextInt();
                SCANNER.nextLine();
                return scanner;
            } catch (Exception e) {
                System.out.println("Enter valid input(number).");
                SCANNER.nextLine();
            }
        }
    }
}
