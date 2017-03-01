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
public class Reg {
 
    //Campos de la clase
    private int Largo;
    private int Reg[];
    private int Output;

    
    public Reg (int L) { 
        Reg = new int[L]; 
        Output=0;
        Largo = L;
        for(int i=0;i<L;i++){Reg[i]=0;}
   
    }
    
    public void SetData(int pos,int data){
    Reg[pos]=data;
    }
    
    public int GetData(int pos){
    Output=Reg[pos];
    return Output;
    }
    


   

    
    
    
    
}

