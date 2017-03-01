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
public class BankReg {

    
private int Bank[];
private int OutputA;
private int OutputB;
    
    

    
public BankReg(){
Bank = new int[16];
OutputA=0;
OutputB=0;
//for(int i=0;i<16;i++){Bank[i]=i;}
}

public void SetData(int pos,int data){
    if(pos!=0){Bank[pos]=data;}
    }
    
public int GetDataA(int pos)
    {
    OutputA=Bank[pos];
    return OutputA;
    }

    public int GetDataB(int pos)
    {
    OutputB=Bank[pos];
    return OutputB;
    }

    public void print()
    {
        for (int i =0;i <16; i++){
            System.out.println(Bank[i]);
        }
    }
 
}
