/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Micro;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 *
 * @author greivin
 */
public class InstMem {
    public static int Largo;
    private static int Mem[];
    private static int Output;
    public static int i = 0;
    

    
    public static void InstMem(){
    
    Output=0;
    Mem = new int[256];
    
    for(int i = 0;i < 100;i++){Mem[i] = 0;}
    File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
            archivo = new File ("generado.isi");
            fr = new FileReader (archivo);
            br = new BufferedReader(fr);

         // Lectura del fichero
            String linea;
            
            while((linea=br.readLine())!=null){
                Mem[i] = Integer.parseInt(linea);
                System.out.println(Mem[i]);
                i += 1;
                
            }  
                
         }
        catch(Exception e){
            e.printStackTrace();
        }finally{
            try{                    
                if( null != fr ){   
                    fr.close();     
                }                  
            }catch (Exception e2){ 
                e2.printStackTrace();
            }
    
    }
    }
    
    
    public static void InstMem(String compilado){
    
    Output=0;
    Mem = new int[100];
    
    for(int i = 0;i < 100;i++){Mem[i] = 0;}
    File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
            archivo = new File (compilado);
            fr = new FileReader (archivo);
            br = new BufferedReader(fr);

         // Lectura del fichero
            String linea;
            
            while((linea=br.readLine())!=null){
                Mem[i] = Integer.parseInt(linea);
                System.out.println(Mem[i]);
                i += 1;
                
            }  
                
         }
        catch(Exception e){
            e.printStackTrace();
        }finally{
            try{                    
                if( null != fr ){   
                    fr.close();     
                }                  
            }catch (Exception e2){ 
                e2.printStackTrace();
            }
    
    }
    }
    
    
    public void SetData(int pos,int data){
    Mem[pos]=data;
    }
    
    public int GetData(int pos){
    Output = Mem[pos];
    return Output;
    }

   
}
