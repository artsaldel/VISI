/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analizador;

import FileManager.MessageInterpreter;
import java.io.*;
import java.util.*;
import java.util.StringTokenizer;
import GUI.*;
import javax.swing.JOptionPane;
import tagsparser.*;
/**
 *
 * @author cesar
 */

public class Sintaxis {
  /**
   * Listas necesarias para el analisis
   */
public static final String[] nemonicosAlu = {"SUM","RES","MUL","DIV","MOD","AND","OR","XOR",
                    "NAND","NOR","CORI","CORD"};
                    
public static final String[] nemonicosMemo =   { "CAR","CARP","OBT","OBTP"};

public static final String[] nemonicosSal = {"SAL","SALI","SALNI","SALM"};

public static final String[] nemonicoComp = {"COMP"};  

public static final String[] registros = {"R1","R2","R3","R4","R5","R6","R7",
                    "R7","R8","R9","R10","R11","R12", "R13","R14","R15","R0"};


public static int line = 0;

public static boolean flagError = false;



static Tagsparser parser = new Tagsparser();


    public static void main(String[] args ){
        //leeArchivo();
    }
        
    /**
     * se lee el archivo guardado por el usuario
     * @param name 
     */
    public static void leeArchivo(String name){
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        
        parser.buscaEtiqueta(name);     
        
        try {
            archivo = new File (name);
            fr = new FileReader (archivo);
            br = new BufferedReader(fr);

         // Lectura del fichero
            String linea;
            while((linea=br.readLine())!=null)
                analiza(linea);
         }
        catch(Exception e){
            e.printStackTrace();
        }finally{
         // En el finally cerramos el fichero, para asegurarnos
         // que se cierra tanto si todo va bien como si salta 
         // una excepcion.
            try{                    
                if( null != fr ){   
                    fr.close();     
                }                  
            }catch (Exception e2){ 
                e2.printStackTrace();
            }
        }
   }
    
    /**
     * se analiza linea a linea la sintaxis
     * @param linea 
     */
    public static void analiza(String linea){
        StringTokenizer st = new StringTokenizer(linea, " :,");
          while(st.hasMoreTokens() && !flagError) {
              line += 1;
            if (st.countTokens() > 4){
                flagError = true;
                break;
            }
            else if (st.countTokens() == 4){
                opAlu(st);
            }
            else if (st.countTokens() == 3){
                opMemo(st);
            }
            else if (st.countTokens() == 2){
                opSal(st);
            }
            else if (st.countTokens() == 1){
                opEtiq(st);
            }
            else{
                flagError = true;
                break;
            }
        }
    }
    
    
    /*Metodo encargado de analizar las operaciones aritmeticas y logicas*/
    public static void opAlu(StringTokenizer linea){
        if (Arrays.asList(nemonicosAlu).contains(linea.nextToken().toUpperCase())){}
              else{
                  flagError = true;
              }
        if (Arrays.asList(registros).contains(linea.nextToken().toUpperCase())){}
              else{
                  flagError = true;
              }
        
        if (Arrays.asList(registros).contains(linea.nextToken().toUpperCase())){}
              else{
                  flagError = true;
              }
        
        String temp = linea.nextToken();
        if (!temp.contains("#")){
           if (Arrays.asList(registros).contains(temp.toUpperCase())){
           }
              else{
                  flagError = true;
              } 
        }
        else{
            try{
                Integer.parseInt(temp.substring(1));
            }catch (Exception e){
                flagError = true;
                
            }
        }
    }
    
    
    /*Metodo encargado de analizar las operaciones de memoria*/
    public static void opMemo(StringTokenizer linea){
        
        String temp1 = linea.nextToken();
        if (Arrays.asList(nemonicosMemo).contains(temp1.toUpperCase()) ){}
                  
              else{
                   if(Arrays.asList(nemonicoComp).contains(temp1.toUpperCase())){
                 
                        analizaComp(linea);
                    }else{ 
                        flagError = true;
                    }
              }
        try{
            if (Arrays.asList(registros).contains(linea.nextToken().toUpperCase())){}
              else{
                  flagError = true;
              }
        
            String temp = linea.nextToken();
            if (!temp.contains("0x")){
                if (Arrays.asList(registros).contains(temp.toUpperCase())){
                }
                else{
                  flagError = true;
                } 
            }
            else{
                try{
                    Integer.parseInt(temp.substring(2));
                }catch (Exception e){
                    flagError = true;
                
                }
            }
        }catch (Exception e){
                
            }
        
    }
    
    public static void analizaComp(StringTokenizer linea){
        /*if (Arrays.asList(nemonicoComp).contains(linea.nextToken().toUpperCase()))
                  System.out.println("Existe");
              else{
                  flagError = true;
              }*/
        if (Arrays.asList(registros).contains(linea.nextToken().toUpperCase())){}
              else{
                  flagError = true;
              }
        
        String temp = linea.nextToken();
        if (!temp.contains("#")){
           if (Arrays.asList(registros).contains(temp.toUpperCase())){
           }
              else{
                  flagError = true;
              } 
        }
        else{
            try{
                Integer.parseInt(temp.substring(1));
            }catch (Exception e){
                flagError = true;
                
            }
        }
    }
    /*Metodo encargado de analizar las operaciones de saltos*/
    public static void opSal(StringTokenizer linea){
        
        if (Arrays.asList(nemonicosSal).contains(linea.nextToken().toUpperCase())){}
              else{
                  flagError = true;
              }
        
        String temp = linea.nextToken();
        if (parser.T.Contiene(temp)){
        }else{
                  flagError = true;
              } 
       
    }
    
    
    public static void opEtiq(StringTokenizer linea){
        try{
            Integer.parseInt(linea.nextToken());
            flagError = true;
        }catch (Exception e){
        }
    }

}


