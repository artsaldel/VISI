/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Micro;

/**
 *
 * @author greivin
 */
public class DataMem {
 private int Largo;
    private int Mem[];
    private int Output;
    

    
    public DataMem(int L){
        Largo=L;
        Output=0;
        Mem = new int[L];
    
    
    }
    
    public void SetData(int pos,int data){
        Mem[pos]=data;
    }
    
    public int GetData(int pos){
        Output = Mem[pos];
        return Output;
    }
}
