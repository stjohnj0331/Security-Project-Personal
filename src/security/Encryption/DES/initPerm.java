package security.Encryption.DES;

public class initPerm {

    static int[] table = new int[64];

    public static void initTable(){
        table = new int[] {58,50,42,34,26,18,10,2,60,52,44,36,28,20,12,4,62,54,46,38,30,22
                ,14,6,64,56,48,40,32,24,16,8,57,49,41,33,25,17,9,1,59,51,43,35,27,19,11,3
                ,61,53,45,37,29,21,13,5,63,55,47,39,31,23,15,7};
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
