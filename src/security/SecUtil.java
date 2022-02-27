package security;

import java.io.InputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;

public class SecUtil {
    public String fileToHextStr(Path path) throws Exception {
        if (Files.notExists(path)) {
            throw new Exception("File not found! " + path);
        }

        StringBuilder hex = new StringBuilder();

        int value;

        try (InputStream inputStream = Files.newInputStream(path)) {
            while ((value = inputStream.read()) != -1) {
                hex.append(String.format("%02X ", value));
            }
        }
        return hex.toString();
    }


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

    public BigInteger stringConversion(String hex) {
        BigInteger[] output = new BigInteger[2];
        if (hex.charAt(0) == '0' && hex.charAt(1) == 'x')
            hex = hex.substring(2);
        while (hex.length() < 16)
            hex = "0" + hex;
        output[0] = new BigInteger(hex, 16);
        return output[0];
    }

    /**
     * pre encryption
     * @return convert hex string to message
     */
    public String asciiToHex(String message){
        char[] ch = message.toCharArray();
        StringBuilder hex = new StringBuilder();
        for(char c : ch){
            int i = c;
            hex.append(Integer.toHexString(i));
        }
        return hex.toString();
    }

    /**
     * post encryption
     * @return hex string of message
     */
    public String hexToAscii(String hex){
        StringBuilder message = new StringBuilder();
        for(int i = 0 ; i < hex.length()-1 ; i+=2){
            String temp = hex.substring(i, i+2);
            message.append((char)Integer.parseInt(temp, 16));
        }
        return message.toString();
    }

    public String convToBin(String ascii){
        //take a string of ascii text and convert it to hex, then binary
        byte[] hex = ascii.getBytes();
        BigInteger i = new BigInteger(hex);
        StringBuilder binary = new StringBuilder(i.toString(2));
        if(binary.length() % 2 != 0)
            binary.insert(0,0);
        return binary.toString();
    }

    public static void main(String[] args){
        SecUtil util = new SecUtil();
        System.out.println(util.convToBin("This is a test of test length"));

    }
}
