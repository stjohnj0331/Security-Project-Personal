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
public class TEA {
    Utilities utl = new Utilities();
    private BigInteger[] K = new BigInteger[4];
    private BigInteger DELTA = new BigInteger("9e3779b9", 16);

    public TEA(BigInteger[] keys) {
        K = keys;
    }

    // encrypt
    public String encrypt(BigInteger[] plaintext) {
        BigInteger L = plaintext[0], R = plaintext[1];
        String output;
        BigInteger sum = new BigInteger("0", 16);
        for (int i = 0; i < 32; i++) {
            sum = sum.add(utl.bM(DELTA));
            L = utl.bM(L.add((utl.SL(R).add(K[0])).xor(R.add(sum)).xor(utl.SR(R).add(K[1]))));
            R = utl.bM(R.add((utl.SL(L).add(K[2])).xor(L.add(sum)).xor(utl.SR(L).add(K[3]))));
        }
        output = L.toString(16) + R.toString(16);
        return output;
    }

    // decrypt
    public String decrypt(BigInteger[] ciphertext) {
        BigInteger L = ciphertext[0], R = ciphertext[1];
        String output;
        DELTA = new BigInteger("9e3779b9", 16);
        BigInteger sum = utl.bM(DELTA.shiftLeft(5));
        for (int i = 0; i < 32; i++) {
            R = utl.bM(R.subtract((utl.SL(L).add(K[2])).xor(L.add(sum)).xor(utl.SR(L).add(K[3]))));
            L = utl.bM(L.subtract((utl.SL(R).add(K[0])).xor(R.add(sum)).xor(utl.SR(R).add(K[1]))));
            sum = utl.bM(sum.subtract(DELTA));
        }
        output = L.toString(16) + R.toString(16);
        return output;
    }
}
