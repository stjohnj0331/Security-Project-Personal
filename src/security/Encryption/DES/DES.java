package security.Encryption.DES;

import security.SecUtil;

/**
 * initial and final permutation are confirmed.
 * next we need to expand as we begin encryption
 */
public class DES {
    static SecUtil util = new SecUtil();
    public static void main(String[] args){
        String input = "Justin S";
        System.out.println("input: "+input);
        String binary = util.convToBin(input);
        System.out.println("binary:   "+binary);
        String permuted = initPerm.permute(binary);
        System.out.println("permuted: "+permuted);
        String undone = finPerm.permute(permuted);
        System.out.println("permuted: "+undone);
    }
}
