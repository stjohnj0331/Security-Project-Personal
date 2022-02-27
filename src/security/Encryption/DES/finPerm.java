package security.Encryption.DES;

public class finPerm {

    static int[] table = new int[64];

    public static void initTable(){
        table = new int[] {40,8,48,16,56,24,64,32,39,7,47,15,55,23,63,31,38,6,46,14,54,22,62
                ,30,37,5,45,13,53,21,61,29,36,4,44,12,52,20,60,28,35,3,43,11,51,19,59,27
                   ,34,2,42,10,50,18,58,26,33,1,41,9,49,17,57,25};
    }

    public static String permute(String binMessage){
        initTable();
        StringBuilder output = new StringBuilder();
        for(int i = 0 ; i < 64 ; i++){
            int index = table[i]-1;
            output.append(binMessage.charAt(index));
        }
        return output.toString();
    }
}
