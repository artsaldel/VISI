/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analizador;

import static Analizador.Sintaxis.parser;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;
import tagsparser.Tags;
import tagsparser.Tagsparser;

/**
 *
 * @author cesar
 */
public class Semantico {
    /**
     * se definen las listas necesarias para el analisis o generacio de codigo
     */
    
    public static final String[] nemonicos = {"SUM","RES","MUL","DIV","MOD","COMP","AND","OR","XOR",
                    "NAND","NOR","CORI","CORD","CAR","CARP","OBT","OBTP","SAL",
                     "SALI","SALNI","SALM"};  
    
    public static final String[] nemonicosAlu = {"SUM","RES","MUL","DIV","MOD","AND","OR","XOR",
                    "NAND","NOR","CORI","CORD"};
                    
    public static final String[] nemonicosMemo =  { "CAR","CARP","OBT","OBTP","COMP"};

    public static final String[] nemonicosSal = {"SAL","SALI","SALNI","SALM"};

    public static final String[] registros = {"R0", "R1","R2","R3","R4","R5","R6","R7",
                    "R8","R9","R10","R11","R12", "R13","R14","R15"};
    public static final String[] nemonicoComp = {"COMP"};  
    static int instruccion = 0;
    
    static Tagsparser parser = new Tagsparser();
    
    public static void main(String[] args ) throws IOException{
        //leeArchivo();
        //crearArchivo();
    }
        
    /**
     * Se lee el archivo guardado para generar linea por linea las instrucciones 
     * 
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
            String linea;
            while((linea=br.readLine())!=null){
                StringTokenizer st = new StringTokenizer(linea, " ,");
                String temp = st.nextToken();
                if (Arrays.asList(nemonicosAlu).contains(temp.toUpperCase()))
                    ParserAlu(linea);
                if (Arrays.asList(nemonicosMemo).contains(temp.toUpperCase()))
                    ParserMemo(linea);
                if (Arrays.asList(nemonicosSal).contains(temp.toUpperCase()))    
                    ParserSal(linea);
                if (linea.contains(":"))
                    nop();
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
    
 
    /**
     * Se analizan las instrucciones que necesiten de la ALU
     * @param linea
     * @throws IOException 
     */
    public static void ParserAlu(String linea) throws IOException{
        StringTokenizer st = new StringTokenizer(linea, " ,");
        int temp = 0;
        String inmed = "";
         while(st.hasMoreTokens()) {
              temp = Arrays.asList(nemonicos).indexOf(st.nextToken().toUpperCase());
              temp = temp << 27;
              instruccion += temp;
              
              temp = Arrays.asList(registros).indexOf(st.nextToken().toUpperCase());
              temp = temp << 22;
              instruccion += temp;
              
              temp = Arrays.asList(registros).indexOf(st.nextToken().toUpperCase());
              temp = temp << 18;
              instruccion += temp;
              
              
              inmed = st.nextToken();
              if (inmed.contains("#")){
                  instruccion += 1 << 26;
                  temp = Integer.parseInt(inmed.substring(1));
                  instruccion += temp;
              }
              else{
                  temp = Arrays.asList(registros).indexOf(inmed.toUpperCase());
                  temp = temp << 14;
                  instruccion += temp;
              }
              
          }
        guardar("generado.isi", Integer.toString(instruccion)); 
        instruccion = 0;
    }
    
    
    
    /**
     * Se analizan las instrucciones que utilizan memoria
     * @param linea
     * @throws IOException 
     */
    public static void ParserMemo(String linea) throws IOException{
        StringTokenizer st = new StringTokenizer(linea, " ,");
        int temp = 0;
        String inmed = "";
        String temp1 = st.nextToken();
         while(st.hasMoreTokens()) {
             if (Arrays.asList(nemonicoComp).contains(temp1.toUpperCase())){
                temp = Arrays.asList(nemonicos).indexOf("RES");
                temp = temp << 27;
                instruccion += temp;
              
                temp = Arrays.asList(registros).indexOf("R0");
                temp = temp << 22;
                instruccion += temp;
              
                temp = Arrays.asList(registros).indexOf(st.nextToken().toUpperCase());
                temp = temp << 18;
                instruccion += temp;
              
              
                inmed = st.nextToken();
                if (inmed.contains("#")){
                   instruccion += 1 << 26;
                   temp = Integer.parseInt(inmed.substring(1));
                   instruccion += temp;
                }
                else{
                   temp = Arrays.asList(registros).indexOf(inmed.toUpperCase());
                   temp = temp << 14;
                   instruccion += temp;
                }
         
                
             }else{
              temp = Arrays.asList(nemonicos).indexOf(temp1.toUpperCase());
              temp = temp << 27;
              instruccion += temp;
              
              temp = Arrays.asList(registros).indexOf(st.nextToken().toUpperCase());
              temp = temp << 22;
              instruccion += temp;
              
              
              inmed = st.nextToken();
              if (inmed.contains("0x")){
                  instruccion += 1 << 26;
                  temp = Integer.parseInt(inmed.substring(2));
                  instruccion += temp;
              }
             }
          }
        guardar("generado.isi", Integer.toString(instruccion)); 
        instruccion = 0;
    }
    
    
    /**
     * Se analizan las instrucciones que contienen saltos
     * @param linea
     * @throws IOException 
     */
    public static void ParserSal(String linea) throws IOException{
        StringTokenizer st = new StringTokenizer(linea, " ,");
        int temp = 0;
        String temp1 = "";
        Tags J;
        while(st.hasMoreTokens()) {
              temp = Arrays.asList(nemonicos).indexOf(st.nextToken().toUpperCase());
              temp = temp << 27;
              instruccion += temp;
              
              temp1 = st.nextToken();
              temp = parser.T.tagpos(temp1);
              temp = temp << 0;
              instruccion += temp;
              
          }
        guardar("generado.isi", Integer.toString(instruccion)); 
        instruccion = 0;
    }
    
    /**
     * Metodo encargado de agregar un NOP cuando se requiera
     * @throws IOException 
     */
     public static void nop() throws IOException{
         instruccion = 0;
         guardar("generado.isi", Integer.toString(instruccion)); 
     }
       
     
     /**
      * en caso de que todo sea correcto se crea un nuevo archivo en lenguaje 
      * maquina, generado.isi
      * @param instruccion
      * @throws IOException 
      */
    public static void crearArchivo(String instruccion) throws IOException{
     try {

	    File file = new File("generado.isi");
            FileWriter fooWriter = new FileWriter(file, false); // true to append
                                                     // false to overwrite.
            fooWriter.write(instruccion);
            fooWriter.close();

    	} catch (IOException e) {
	      e.printStackTrace();
	}
    
  }
    
    /**
     * se guarda y sobreescribe lo que haya en el archivo para no tener instrucciones
     * de codigos anteriores
     * @param writeFileName
     * @param text
     * @throws IOException 
     */
    public static void guardar(String writeFileName, String text) throws IOException
    {
        String archivo=writeFileName;
        FileWriter fw=  new   FileWriter(archivo,true);
        BufferedWriter bw=  new BufferedWriter(fw);      
        PrintWriter  pw= new  PrintWriter (bw);

      

        pw.println(text); 

        pw.close();
    }
    
}

