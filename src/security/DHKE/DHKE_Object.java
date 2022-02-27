package security.DHKE;

import java.util.InputMismatchException;
import java.util.Scanner;

public class DHKE_Object {

    Scanner kb = new Scanner(System.in);
    static int input;
    // p is a large prime
    // g is a number 2-(p-2)
    // g^a mod p
    // g^b mod p
    public int p, g, pubKey;
    private int privKey;
    private int sharedSecret;

    private DHKE_Object Alice;
    private DHKE_Object Bob;

    public DHKE_Object() {
    }

    public DHKE_Object(int p, int g) {
        this.p = p;
        this.g = g;
    }

    private void setPrivateKey(DHKE_Object x, int privKey) {
        x.setPrivateKey(privKey);
    }

    private void setPrivateKey(int privKey) {
        this.privKey = privKey;
    }

    private void setSharedSecret(int sharedSecret) {
        this.sharedSecret = sharedSecret;
    }

    public int getPublicKey() {
        return pubKey;
    }

    public int getPublicKey(DHKE_Object x) {
        return x.getPublicKey();
    }

    private void setPublicKey(DHKE_Object x) {
        System.out.println("equation: " + x.g + "^" + x.privKey + " mod " + x.p);
        // x.pubKey = ((int) Math.pow(x.g, x.privKey)) % x.p;
        int r = x.g;
        String H = Integer.toBinaryString(x.privKey);
        for (int i = 1; i < H.length(); i++) {
            r = ((int) Math.pow(r, 2)) % x.p;
            // r = r % n;
            if (H.charAt(i) == '1')
                r = (r * x.g) % x.p;
            // r = r % n;
        }
        x.pubKey = r;
    }

    private void setSharedSecret(DHKE_Object x, int othersPubKey) {
        // x.setSharedSecret(((int) Math.pow(othersPubKey, x.privKey)) % x.p);
        int r = othersPubKey;
        String H = Integer.toBinaryString(x.privKey);
        for (int i = 1; i < H.length(); i++) {
            r = ((int) Math.pow(r, 2)) % x.p;
            // r = r % n;
            if (H.charAt(i) == '1')
                r = (r * othersPubKey) % x.p;
            // r = r % n;
        }
        x.setSharedSecret(r);
    }

    public String toString() {
        String output = "\n\n********************************" +
                "\nPublic domain parameters:" +
                "\ng: " + Alice.g +
                "\np: " + Bob.p +
                "\nAlice:" +
                "\na: " + Alice.privKey + " (private)" +
                "\nA: " + Alice.getPublicKey() + " (public)" +
                "\nBob:" +
                "\nb: " + Bob.privKey + " (private)" +
                "\nB: " + Bob.getPublicKey() + " (public)" +
                "\nAlice's shared secret: " + Alice.sharedSecret +
                "\nBob's shared secret: " + Bob.sharedSecret +
                "\n********************************\n\n";
        return output;
    }

    public void menu() {
        do {
            try {
                System.out.println("Please select from the following options"
                        + "\n1. Establish domain parameters"
                        + "\n2. Pick Alice's private key and compute their public key"
                        + "\n3. Pick Bob's's private key and compute their public key"
                        + "\n4. Establish and verify shared secret"
                        + "\n5. Print values"
                        + "\n6. Exit");
                input = kb.nextInt();
                // need to check input
                if (input == 1) {
                    // check for prime and for valid input
                    System.out.println("Please enter a prime number for p");
                    int p = kb.nextInt();

                    // check for in range and valid input
                    System.out.println("Please enter a number between 2 and " + (p - 2) + " for g");
                    int g = kb.nextInt();

                    Alice = new DHKE_Object(p, g);
                    Bob = new DHKE_Object(p, g);
                    // need to check input
                } else if (input == 2) {
                    System.out.println("Please enter Alice's private key value");

                    setPrivateKey(Alice, kb.nextInt());

                    setPublicKey(Alice);
                    // need to check input
                } else if (input == 3) {
                    System.out.println("Please enter Bob's private key value");

                    setPrivateKey(Bob, kb.nextInt());

                    setPublicKey(Bob);
                } else if (input == 4) {
                    setSharedSecret(Alice, Bob.getPublicKey());
                    setSharedSecret(Bob, Alice.getPublicKey());
                } else if (input == 5) {
                    System.out.println(toString());
                } else {
                    kb.close();
                    System.exit(0);
                }
            } catch (InputMismatchException e) {
                kb.next();
                System.out.print("Invalid input. ");
                menu();
                input = kb.nextInt();
            }
        } while (input < 1 || input > 6);
    }

    public static void front() {
        DHKE_Object dhke = new DHKE_Object();
        dhke.menu();
    }

}
