package security.Encryption.RSA;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Scanner;
import security.SecUtil;

public class RSA_Object {

    private BigInteger p, q, e, n, phi, inverse;
    private static BigInteger u, r;
    private static final RSA_Object rsa = new RSA_Object();
    private static final Scanner kb = new Scanner(System.in);
    private static final SecureRandom rand = new SecureRandom();
    SecUtil util = new SecUtil();

    private RSA_Object(){}

    private void setP(BigInteger p) {this.p = p;}

    private void setQ(BigInteger q) {this.q = q;}

    public BigInteger getE() {return e;}

    private void setE(BigInteger e) {this.e = e;}

    public BigInteger getN() {return n;}

    public void setN() {this.n = p.multiply(q);}

    private BigInteger getPhi() {return phi;}

    private void setPhi() {
        BigInteger p = this.p.subtract(valueOf(1));
        BigInteger q = this.q.subtract(valueOf(1));
        this.phi = p.multiply(q);
    }

    private BigInteger getInverse() {return inverse;}

    private void setInverse() {
        this.inverse = getInverse(e, phi);
    }

    public static BigInteger randBigInt(int numBits){
        return new BigInteger(numBits, rand);
    }

    public int getKeyLength(){return n.bitLength();}

    /**
     * a.compareTo(b) returns:
     * 0 if a is equal to b
     * 1 if a > b
     * -1 if a < b
     * @param a value
     * @param b value
     * @return inverse of a mod b
     * Extended Euclidean Algorithm
     */
    private static BigInteger getInverse(BigInteger a, BigInteger b){
        BigInteger G, mult, temp, n;
        BigInteger t1 = valueOf(0);
        BigInteger t2 = valueOf(1);
        //b > a or a < b
        if(a.compareTo(b) < 0){
            n = b;
            temp = a;
            a = b;
            b = temp;
        }else
            n = a;
        do{
            mult = (a.divide(b)).multiply(valueOf(-1));
            G = (a.add(b.multiply(mult)));
            a = b;
            b = G;
            temp = t2;
            t2 = t1.add(t2.multiply(mult));
            t1 = temp;
        }while(G.compareTo(valueOf(1)) > 0);
        if(t2.compareTo(valueOf(0)) < 0)
            return n.add(t2);
        return t2;
    }

    /**
     *
     * @param a = value to be evaluated
     * @param b = value to be evaluated
     * @return = true if co-prime false otherwise
     */
    public static boolean isRelPrime(BigInteger a, BigInteger b){
        if(b.equals(valueOf(1)))
            return true;
        if(b.equals(valueOf(0)))
            return false;
        return isRelPrime(b, a.mod(b));
    }

    /**
     * Fast exponentiation
     * @param x
     * @param h = exponent
     * @param p = modulus
     * @return x^h mod p
     */
    public static BigInteger squareAndMultiply(BigInteger x, BigInteger h, BigInteger p){
        BigInteger r = x;
        String H = h.toString(2);
        for(int i = 1 ; i < H.length() ; i++){
            r = (r.pow(2)).mod(p);
            if(H.charAt(i) == '1')
                r = (r.multiply(x)).mod(p);
        }
        return r;
    }

    /**for the first steps of Miller-Rabin Primality test**/
    public static void computeUandR(BigInteger p){
        p = p.subtract(valueOf(1));
        u = valueOf(1);
        BigInteger temp = p.divide(valueOf(2).pow(u.intValueExact()));
        while(temp.mod(valueOf(2)).equals(valueOf(0))){
            u = u.add(valueOf(1));
            temp = p.divide(valueOf(2).pow(u.intValueExact()));
        }
        r = temp;
    }

    public static boolean MRPrime(BigInteger p){
        BigInteger s = valueOf(11), z, a;
        //base cases
        if(p.equals(valueOf(2)) || p.equals(valueOf(3)))
            return true;
        if(p.equals(valueOf(1)) || (p.mod(valueOf(2)).equals(valueOf(0))))
            return false;
        computeUandR(p);
        for(int i = 1 ; i <= s.intValue() ; i++){
            a = randBigInt(p.bitCount());
            z = squareAndMultiply(a, r, p);
            if(!z.equals(valueOf(1)) && !z.equals(p.subtract(valueOf(1)))){
                long j = 1;

                while((valueOf(j)).compareTo(u) < 0){
                    z = z.pow(2).mod(p);
                    if(z.equals(valueOf(1)))
                        return false;
                    j++;
                }
                if(!z.equals(p.subtract(valueOf(1))))
                    return false;
            }
        }
        return true;
    }

    /**
     * a.compareTo(b) returns:
     * 0 if a is equal to b
     * 1 if a > b
     * -1 if a < b
     */
    private static boolean fermatPrime(BigInteger p){
        BigInteger s = valueOf(11);
        //base cases
        if(p.intValueExact() == 2 || p.intValueExact() == 3)
            return true;
        //check for 1 and even numbers
        if(p.equals(valueOf(1)) || (p.mod(valueOf(2))).equals(valueOf(0)))
            return false;
        for(int i = 1 ; i <= s.intValue() ; i++){
            BigInteger a = valueOf(rand.nextLong(2, p.longValueExact()-2));
            if(squareAndMultiply(a, p.subtract(valueOf(1)), p).compareTo(valueOf(1)) != 0)
                return false;
        }
        return true;
    }

    /**
     * Shortens the method call for BigInteger.valueOf() to just valueOf();
     * QoL method
     * @param p = long value to convert
     * @return BigInteger of p
     */
    private static BigInteger valueOf(long p){
        return BigInteger.valueOf(p);
    }

    /**
     * a.compareTo(b) returns:
     * 0 if a is equal to b
     * 1 if a > b
     * -1 if a < b
     * @param p = prime candidate
     * @return p is prime or p is composite
     */
    private static boolean isPrime(BigInteger p){
        if(p.compareTo(valueOf(10000000)) < 0)
            return !fermatPrime(p);
        else
            return !MRPrime(p);
    }

    private static void rsaPrimer() {
        BigInteger p, q, e;
        boolean rPrime;
        // int p, int q, int e
        //must check that p and q are prime
        System.out.println("p and q must be large prime numbers");
        do {
            System.out.println("Please enter p (preferably larger than 500):");
            System.out.println("Example of small prime(256 bits): \n" +
                    "102639592829741105772054196573991675900716567808038066803341933521790711307779");
            p = kb.nextBigInteger();
            while (isPrime(p)) {
                System.out.println(p + " isn't prime\nPlease enter p (preferably larger than 500):");
                p = kb.nextBigInteger();
            }
        }while(p.compareTo(valueOf(500)) < 0);
        rsa.setP(p);
        System.out.println("Please enter q (preferably larger than 500):");
        System.out.println("Example of another small prime(256 bits): \n" +
                "106603488380168454820927220360012878679207958575989291522270608237193062808643");
        q = kb.nextBigInteger();
        while(isPrime(q)){
            System.out.println(q +" isn't prime.\nPlease enter q (preferably larger than 500):");
            q = kb.nextBigInteger();
        }
        rsa.setQ(q);
        rsa.setPhi();
        rsa.setN();
        System.out.println("e must be between 1 and " + (rsa.getPhi().subtract(valueOf(1))) +
                " and be relatively prime to " + (rsa.getPhi()) + "\nPlease enter e:");
        e = kb.nextBigInteger();
        rPrime = isRelPrime(e, rsa.getPhi());
        while (!rPrime) {
            System.out.println("e must be between 1 and " + (rsa.getPhi().subtract(valueOf(1))) +
                    " and be relatively prime to " + (rsa.getPhi()) + "\nPlease enter e:");
            e = kb.nextBigInteger();
            rPrime = isRelPrime(e, rsa.getPhi());
        }
        rsa.setE(e);
        rsa.setInverse();

        System.out.println("\nn = " + rsa.getN()
                + "\nphi(n) = " + rsa.getPhi()
                + "\ne = " + rsa.getE()
                + "\nd = " + rsa.inverse);
    }

    //ascii string --> hex --> bigInt
    /**
     * message size must be shorter than message length <= key length / 8
     * @param message = plaintext message to be encrypted
     */
    public void encrypt(String message){
        //convert message to hex
        String hexPlaintext = util.asciiToHex(message);
        System.out.println("hex: "+hexPlaintext);
        //convert it to a BigInt
        BigInteger pt = new BigInteger(hexPlaintext,16);
        System.out.println("BigInt: "+pt);
        //encrypt with squareAndMultiply
        BigInteger ct = squareAndMultiply(pt, getE(), getN());
        //send as encrypted BigInt
        System.out.println("square and multiply: "+ct);
        decrypt(ct);
    }
    //bigInt --> hex --> to ascii string
    public void decrypt(BigInteger ciphertext){
        //decrypt with squareAndMultiply
        BigInteger pt = squareAndMultiply(ciphertext, getInverse(), getN());
        System.out.println("undo square and multiply: "+pt);
        //convert to hex string
        String output = pt.toString(16);
        System.out.println("hex: "+output);
        //convert hex string to ascii
        String message = util.hexToAscii(output);
        //send as decrypted plaintext
        System.out.println("Message: "+message);
    }

    public static int menu() {
        System.out.println("""
                Welcome to the RSA basics, please select from the following
                1. Enter RSA primers/public key (p, q, and e)
                2. Encrypt a message
                3. Decrypt a message
                4. Exit""");
        int input = kb.nextInt();
        kb.nextLine();
        return input;
    }

    public static void front() {
        int input = menu();
        String message;
        while (input <= 4) {
            if (input == 1)
                rsaPrimer();
            else if (input == 2) {
                do {
                    System.out.println("Please enter your message. Can only be "+(rsa.getKeyLength()/8)+" characters long.");
                    message = kb.nextLine();
                }while(message.length() <1 || message.length() > rsa.getKeyLength()/8);
                rsa.encrypt(message);
            } else if (input == 3) {
                System.out.println("Please enter your ciphertext.");
                rsa.decrypt(kb.nextBigInteger());
            } else if (input == 4) {
                kb.close();
                System.exit(0);
            }
            input = menu();
        }
    }

    public static void main(String[] args) {
        //SecUtil test = new SecUtil();
        //getInverse verified
        //int a = 40, b = 3;
        //System.out.println("Inverse of "+a+" mod "+b+" = " + getInverse(valueOf(a),valueOf(b)));
        //isRelPrime verified
        //int x = 2, y = 8;
        //System.out.print(x+" and "+y+" ");
        //if(isRelPrime(valueOf(x), valueOf(y)))
        //    System.out.println("are relatively prime");
        //else
        //    System.out.println("are not relatively prime");
        //squareAndMultiply verified (double)
        //int g = 2356; b = 125; int p = 45;
        //System.out.println(g+"^"+b+" mod "+p+" = "+squareAndMultiply(valueOf(g), valueOf(b), valueOf(p)));
        //fermatPrime verified up to 10,000,000
        //MRPrime verified
        //ascii to hex converter verified
        //String message = test.asciiToHex("hi   123");
        //System.out.println(message);
        //BigInteger number = new BigInteger(message,16);
        //System.out.println(number);
        //System.out.println(number.toString(16));
        //hex to ascii converted verified
        //System.out.println(test.hexToAscii(message));
        //need to use sufficiently large values to encrypt ascii
        //front();
    }
}
