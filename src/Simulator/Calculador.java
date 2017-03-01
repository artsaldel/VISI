package Simulator;

/**
 * @author mario
 */

public class Calculador {

    private int z;
    private int v;
    private int c;
    private int n;
    
    public Calculador(){
        z = 0;
        v = 0;
        c = 0;
        n = 0;
    }
    
    public int condition(String type){
        int execute = 1;
        switch (type){
            case "1110":
            {
                break;
            }
            case "0000":
            {
                if (z == 1)
                    break;
                else{
                    execute = 0;
                    break;
                }                    
            }
            case "0001":
            {
                if (z == 0)
                    break;
                else{
                    execute = 0;
                    break;
                } 
            }
            case "0010":
            {
                if (c == 1)
                    break;
                else{
                    execute = 0;
                    break;
                } 
            }
            case "0011":
            {
                if (c == 0)
                    break;
                else{
                    execute = 0;
                    break;
                } 
            }
            case "0100":
            {
                if (n == 1)
                    break;
                else{
                    execute = 0;
                    break;
                } 
            }
            case "0101":
            {
                if (n == 0)
                    break;
                else{
                    execute = 0;
                    break;
                } 
            }
            case "0110":
            {
                if (v == 1)
                    break;
                else{
                    execute = 0;
                    break;
                } 
            }
            case "0111":
            {
                if (v == 0)
                    break;
                else{
                    execute = 0;
                    break;
                } 
            }
            case "1000":
            {
                if (z == 0 && c == 1)
                    break;
                else{
                    execute = 0;
                    break;
                } 
            }
            case "1001":
            {
                if (z == 1|| c == 0)
                    break;
                else{
                    execute = 0;
                    break;
                } 
            }
            case "1010":
            {
                if ((n == 1 && v == 1) || (n == 0 && v == 0))
                    break;
                else{
                    execute = 0;
                    break;
                } 
            }
            case "1011":
            {
                if ((n == 1 && v == 0) || (n == 0 && v == 1))
                    break;
                else{
                    execute = 0;
                    break;
                } 
            }
            case "1100":
            {
                if (z == 0 && ((n == 1 && v == 1) || (n == 0 && v == 0)))
                    break;
                else{
                    execute = 0;
                    break;
                } 
            }
            case "1101":
            {
                if (z == 1 || (n == 1 && v == 0) || (n == 0 && v == 1))
                    break;
                else{
                    execute = 0;
                    break;
                } 
            }
            default:
            {
                execute = -1;
                break;
            }
        }
        return execute;
    }

    public void checkN(String binary){
        if (binary.charAt(0) == '1')
            n = 1;
        else
            n = 0;
    }
    
    public void checkZ(String binary){
        if (binary.equals("00000000000000000000000000000000"))
            z = 1;
        else
            z = 0;
    }
    
    public void checkV(String binary1, String binary2, String binary3){
        if (binary1.charAt(0) == binary2.charAt(0) && binary1.charAt(0) != binary3.charAt(0))
            v = 1;
        else
            v = 0;
    }
    
    public void checkC(long number){
        if (number > 2147483647 || number < -2147483647)
            c = 1;
        else
            c = 0;
    }
    
    public String rrx(String int1, String flags){
        String result = int1;
        result = Integer.toString(c) + result.substring(0, 31);
        if (flags.equals("1")){
            checkN(result);
            checkZ(result);
            c = Integer.parseInt(int1.substring(31, 32));
        }
        return result;
    }
    
    public String rot(int rotations, String int1, int dir, String flags){
        String result = int1;
        while (rotations >= 32){
            rotations -= 32;
        }
        for (int i = 0; i < rotations; i++){
            if (dir == 1)
                result = result.substring(31, 32) + result.substring(0, 31);
            else
                result = result.substring(1, 32) + result.substring(0, 1) ;
        }
        if (flags.equals("1")){
            checkN(result);
            checkZ(result);
            if (rotations != 0)
                c = Integer.parseInt(int1.substring(32-rotations, 33-rotations));
        }
        return result;
    }
    
    public long multi(int int1, int int2, int int3, String flags){
        long result = (int1*int2) + int3;
        if (flags.equals("1")){
            String bin = String.format("%32s", Long.toBinaryString(result)).replace(' ', '0');
            checkN(bin.substring(bin.length()-32, bin.length()));
            checkZ(bin.substring(bin.length()-32, bin.length()));
        }
        return result;
    }

    public int and(int int1, int int2, String flags) {
        int result = int1 & int2;
        if (flags.equals("1")){
            String bin = String.format("%32s", Integer.toBinaryString(result)).replace(' ', '0');
            checkN(bin);
            checkZ(bin);
        }
        return result;
    }
    
    public long add(int int1, int int2, boolean carry, String flags) {
        long int3 = int2;
        if (carry){
            int3 = int2 + c;
        }
        long result = int1 + int3;
        if (flags.equals("1")){
            String binr = String.format("%64s", Long.toBinaryString(result)).replace(' ', '0');
            String bin1 = String.format("%32s", Integer.toBinaryString(int1)).replace(' ', '0');
            String bin2 = String.format("%32s", Integer.toBinaryString(int2)).replace(' ', '0');
            checkN(binr.substring(binr.length()-32, binr.length()));
            checkZ(binr.substring(binr.length()-32, binr.length()));
            checkC(result);
            checkV(bin1, bin2, binr.substring(binr.length()-32, binr.length()));
        }
        return result;
    }
    
    public long sub(int int1, int int2, boolean carry, String flags) {
        int int3 = int1;
        if (carry && c == 0){
            int3 = int1 - 1;
        }
        long result = int3 - int2;
        if (flags.equals("1")){
            String binr = String.format("%64s", Long.toBinaryString(result)).replace(' ', '0');
            String bin1 = String.format("%32s", Integer.toBinaryString(int1)).replace(' ', '0');
            String bin2 = String.format("%32s", Integer.toBinaryString(int2)).replace(' ', '0');
            checkN(binr.substring(binr.length()-32, binr.length()));
            checkZ(binr.substring(binr.length()-32, binr.length()));
            checkC(result);
            checkV(bin1, bin2, binr.substring(binr.length()-32, binr.length()));
        }
        return result;
    }
    
     public int or(int int1, int int2, String flags) {
        int result = int1 | int2;
        if (flags.equals("1")){
            String bin = String.format("%32s", Integer.toBinaryString(result)).replace(' ', '0');
            checkN(bin);
            checkZ(bin);
        }
        return result;
    }
    
    public int xor(int int1, int int2, String flags) {
        int result = int1 ^ int2;
        if (flags.equals("1")){
            String bin = String.format("%32s", Integer.toBinaryString(result)).replace(' ', '0');
            checkN(bin);
            checkZ(bin);
        }
        return result;
    }

    public int lsr(int int1, int int2, String flags) {
        int result = (int2 >> int1);
        if (flags.equals("1")){
            checkN(String.format("%32s", Integer.toBinaryString(result)).replace(' ', '0'));
            checkZ(String.format("%32s", Integer.toBinaryString(result)).replace(' ', '0'));
            if (int1 > 32)
                c = 0;
            else if (int1 != 0)
                c = Integer.parseInt(Integer.toBinaryString(int2).substring(32-int1, 33-int1));
        }
        return result;
    }
    
    public int asr(int int1, int int2, String flags) {
        int result = (int2 >>> int1);
        if (flags.equals("1")){
            checkN(String.format("%32s", Integer.toBinaryString(result)).replace(' ', '0'));
            checkZ(String.format("%32s", Integer.toBinaryString(result)).replace(' ', '0'));
            if (int1 > 32)
                c = 0;
            else if (int1 != 0)
                c = Integer.parseInt(Integer.toBinaryString(int2).substring(32-int1, 33-int1));
        }
        return result;
    }
    
    public int lsl(int int1, int int2, String flags) {
        int result = (int2 << int1);
        if (flags.equals("1")){
            checkN(String.format("%32s", Integer.toBinaryString(result)).replace(' ', '0'));
            checkZ(String.format("%32s", Integer.toBinaryString(result)).replace(' ', '0'));
            if (int1 > 32)
                c = 0;
            else if (int1 != 0)
                c = Integer.parseInt(Integer.toBinaryString(int2).substring(int1, int1));
        }
        return result;
    }
    
    public void mov(String int1, String flags){
        if (flags.equals("1")){
            if (int1.substring(0, 1).equals("1"))
                n = 1;
            else
                n = 0;
            if (int1.equals("00000000000000000000000000000000"))
                z = 1;
            else
                z = 0;
        }
    }
}
