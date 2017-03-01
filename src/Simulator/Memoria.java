/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulator;

/**
 *
 * @author mario
 */
public class Memoria {
    
    private final String[][] memoria;
    private final int size;
    
    public Memoria(int largo, String[][] datos){
        memoria = datos;
        size = largo;
    }
    
    public void set(String pos, String val){
        for (int i = 0; i < size; i++){
            if (memoria[i][0].equals(pos)){
                memoria[i][1] = val;
                break;
            }
        }
    }
    
    public void set(String pos, String val, int b){
        for (int i = 0; i < size; i++){
            if (memoria[i][0].equals(pos)){
                if (b == 3)
                   memoria[i][1] = val.substring(6, 8) + memoria[i][1].substring(2, 8);
                else if (b == 2)
                   memoria[i][1] = memoria[i][1].substring(0, 2) + val.substring(6, 8) + memoria[i][1].substring(4, 8);
                else if (b == 1)
                    memoria[i][1] = memoria[i][1].substring(0, 4) + val.substring(6, 8) + memoria[i][1].substring(6, 8);
                else
                    memoria[i][1] = memoria[i][1].substring(0, 6) + val.substring(6, 8);
                break;
            }
        }
    }
    
    public String getByte(String pos, int b){        
        String data = null;
        for (int i = 0; i < size; i++){
            if (memoria[i][0].equals(pos)){
                if (b == 3)
                   data = memoria[i][1].substring(0, 2);
                else if (b == 2)
                   data = memoria[i][1].substring(2, 4);
                else if (b == 1)
                    data = memoria[i][1].substring(4, 6);
                else
                    data = memoria[i][1].substring(6, 8);
                break;
            }
        }
        return data;
    }
    
    public String get(String pos){
        String data = null;
        for (int i = 0; i < size; i++){
            if (memoria[i][0]==null)
                break;
            if (memoria[i][0].equals(pos)){
                data = memoria[i][1];
                break;
            }
        }
        return data;
    }
    
    public String get(String valuePos, int pos){
        if (pos > size)
            return null;
        else if (memoria[pos][0]==null)
            return null;
        else if(memoria[pos][0].equals(valuePos))
            return memoria[pos][1]; 
        else
            return null;
    }
    
}
