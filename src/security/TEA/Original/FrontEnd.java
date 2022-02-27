/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.math.BigInteger;

/**
 *
 * @author Office
 */
public class FrontEnd {
    private static Utilities utl = new Utilities();

    /**
     * we need to maintain 32bit dat the entire time we are working to emulate the
     * program working on a 32bit machine also need to normalize all input so that
     * it is 32bit, 64bit, or 128bit respective
     * 
     * @param args
     */
    public static void main(String[] args) {

        // take in message and key
        // in the future have the user input these data

        // need to modify utility methods so that I can put in any length of string for
        // encryption and decryption.

        String plaintextIn = "0x123456789abcdef";

        String key = "0xa56babcdf000ffffffffffffabcdef01";
        String ciphertextGoal = "7556391b2315d9f8";

        // convert the message and key to some format(long)
        BigInteger[] plaintext = utl.stringConversion(plaintextIn, 8);
        System.out.println("Original message:  " + plaintextIn);
        // break 128bit key into 4x32bit keys
        BigInteger[] keys = utl.keys(key);

        // pass data to encryption
        TEA tea = new TEA(keys);
        String ciphertextOut = tea.encrypt(plaintext);
        System.out.println("intendedCT: " + ciphertextGoal);
        System.out.println("ciphertext: " + ciphertextOut);

        // pass data to decryption
        BigInteger[] ciphertext = utl.stringConversion(ciphertextOut, 8);
        String message = tea.decrypt(ciphertext);

        // display outcomes
        System.out.println("Decrypted Message: 0x" + message);
    }
}
