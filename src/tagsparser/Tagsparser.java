/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tagsparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 *
 * @author cesar
 */
public class Tagsparser {

    /**
     * @param args the command line arguments
     */
    
    static public Tags T = new Tags();
    public static void main(String[] args) {
        // TODO code application logic here
    
    }
    
    
    
    public static void buscaEtiqueta(String name){
        T = new Tags();
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
            archivo = new File (name);
            fr = new FileReader (archivo);
            br = new BufferedReader(fr);

            String linea;

            int i=1;
                    
            while((linea=br.readLine())!= null){
                //System.out.println(linea);
                if(linea.contains(":") && !T.Contiene(linea.substring(0, linea.indexOf(":")))){
                    T.addTag(linea.substring(0, linea.indexOf(":")), i);
                }
                    i++;
            }

                    
         }   
         catch(Exception e){
            e.printStackTrace();
         }
    
    }
   
}
