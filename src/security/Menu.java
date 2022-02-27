package security;

import security.Encryption.RSA.RSA_Object;
import security.DHKE.DHKE_Object;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {

    private static Scanner kb = new Scanner(System.in);
    private static int input;

    public static void mainMenu() throws Exception {
        do {
            try {
                System.out.println("Please select from one of the following.\n"
                        + "1: Encryption\n"
                        + "2: Hashing\n"
                        + "3: Exit\n");
                input = kb.nextInt();

                if (input == 1)
                    encryptionMenu();
                if (input == 2)
                    hashingMenu();
                if (input == 3) {
                    System.out.println("Exiting");
                    System.exit(0);
                } else
                    System.out.print("Invalid input. ");
            } catch (InputMismatchException e) {
                kb.next();
                System.out.print("Invalid input. ");
                mainMenu();
                input = kb.nextInt();
            }
        } while (input < 1 || input > 3);

    }

    public static void hashingMenu() throws Exception {
        System.out.println("Please select from one of the following.\n"
                + "1: MD5\n"
                + "2: SHA1\n"
                + "3: Return to main menu\n");
        do {
            try {
                input = kb.nextInt();
                if (input == 1)
                    System.out.println("MD5");
                if (input == 2)
                    System.out.println("SHA1");
                if (input == 3)
                    mainMenu();
            } catch (InputMismatchException e) {
                kb.next();
                System.out.println("Invalid input.");
                hashingMenu();
                input = kb.nextInt();
            }
        } while (input < 1 || input > 3);
    }

    /**
     * 
     * @throws Exception
     */
    public static void encryptionMenu() throws Exception {
        System.out.println("Please select from one of the following.\n"
                + "1: security.AES\n"
                + "2: security.DES\n"
                + "3: TEA\n"
                + "4: DH key exchange\n"
                + "5: security.RSA\n"
                + "6: EBC\n"
                + "7: CBC\n"
                + "8: Return to main menu\n");
        do {
            try {
                input = kb.nextInt();
                if (input == 1)
                    System.out.println("AES");
                if (input == 2)
                    System.out.println("DES");
                if (input == 3)
                    // Security_Study.TEA.TEA.tea();
                    System.out.println("TEA");
                if (input == 4)
                    DHKE_Object.front();
                if (input == 5)
                    RSA_Object.front();
                if (input == 6)
                    System.out.println("EBC");
                if (input == 7)
                    System.out.println("CBC");
                if (input == 8)
                    mainMenu();
            } catch (InputMismatchException e) {
                kb.next();
                System.out.println("Invalid input.");
                encryptionMenu();
                input = kb.nextInt();
            }
        } while (input < 1 || input > 8);
    }

}