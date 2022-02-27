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
public class Utilities {
    private final BigInteger MASK32 = new BigInteger("00000000ffffffff", 16);

    /**
     *
     * @param hex
     * @param split
     * @return 0x123456789abcdef
     */
    public BigInteger[] stringConversion(String hex, int split) {
        BigInteger[] output = new BigInteger[2];
        if (hex.charAt(0) == '0' && hex.charAt(1) == 'x')
            hex = hex.substring(2);
        while (hex.length() < 16)
            hex = "0" + hex;
        String temp = hex.substring(0, 8);
        String temp1 = hex.substring(8);
        // System.out.println(temp+" "+temp1);
        output[0] = new BigInteger(temp, 16);
        output[1] = new BigInteger(temp1, 16);
        return output;
    }

    /**
     * need a method to take in a message, split it if it needs to be, into 32bit
     * blocks of big integers and return that array of big integers. need to pad if
     * neccessary. may just need to modify the above method.
     * 
     * @param hex
     * @return
     */
    public BigInteger[] stringConversion(String plaintext) {
        BigInteger[] output = new BigInteger[(int) (plaintext.length() / 8)];
        plaintext += "***";
        while (!plaintext.endsWith("***")) {// this will not work
            // process string into bigInts of 32 bits, or 8 letters
        }
        return output;
    }

    // need tool to break string key into 4x32bit (long) keys
    /**
     * 
     * @param hex
     * @return
     */
    public BigInteger[] keys(String hex) {
        String temp, temp1, temp2, temp3;
        BigInteger[] output = new BigInteger[4];
        // padd hex to ensure 128bit before splitting
        if (hex.charAt(0) == '0' && hex.charAt(1) == 'x')
            hex = hex.substring(2);
        hex = new BigInteger(hex, 16).toString(2);
        while (hex.length() < 128)
            hex = "0" + hex;
        temp = hex.substring(0, 32);
        temp1 = hex.substring(32, 64);
        temp2 = hex.substring(64, 96);
        temp3 = hex.substring(96);
        // System.out.println(temp+"\n"+temp1+"\n"+temp2+"\n"+temp3);
        // now in 32bit binary strings
        output[0] = new BigInteger(temp, 2);
        output[1] = new BigInteger(temp1, 2);
        output[2] = new BigInteger(temp2, 2);
        output[3] = new BigInteger(temp3, 2);
        return output;
    }

    // need to implement shifts that maintain 32bit size constraints
    /**
     * 
     * @param input
     * @return
     */
    public BigInteger SL(BigInteger input) {
        BigInteger output;
        input = bM(input);
        String temp = input.toString(2);
        // System.out.println("SL input: "+temp);
        while (temp.length() < 32)
            temp = "0" + temp;
        temp = temp.substring(4);
        temp = temp + "0000";
        // System.out.println("SL output: "+temp);
        output = new BigInteger(temp, 2);
        return output;
    }

    /**
     * 
     * @param input
     * @return
     */
    public BigInteger SR(BigInteger input) {
        BigInteger output;
        input = bM(input);
        String temp = input.toString(2);
        // System.out.println("SR input: "+temp);
        while (temp.length() < 32)
            temp = "0" + temp;
        // System.out.println("input: "+temp);
        temp = temp.substring(0, 27);
        temp = "00000" + temp;
        // System.out.println("SR output: "+temp);
        output = new BigInteger(temp, 2);
        return output;
    }

    // need a tool to check length or multiply all transactions with a 32bit mask
    /**
     * uses a 32bit mask to mimic the cpu dropping bits after 32
     * 
     * @param input
     * @return
     */
    public BigInteger bM(BigInteger input) {
        return input.and(MASK32);
    }
}
