/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tagsparser;

/**
 *
 * @author cesar
 */
import java.util.Arrays;

/**
 *
 * @author cesar
 */
public class Tags {
    
    int puntero=0;
    String [] tags ;
    int [] posicion;
    
    
    
    public Tags(){
    
      tags = new String[20];
     posicion=new int[20];
    
    }
    
    public void addTag(String name,int pos){
    
    tags[puntero]=name;
    posicion[puntero]=pos;
    puntero++;
    }
    
    public boolean Contiene(String name){
    return Arrays.asList(tags).contains(name);
    }
    
    public int tagpos(String name){
    int i= Arrays.asList(tags).indexOf(name);
    return posicion[i];
    }
    
    public void imprimir(){
    
    int j=0;
    while (j<tags.length){
        System.out.println("J: "+ j +" Tags: "+tags[j]+ " Posicion: "+posicion[j]);
        j++;
    }
    
    }
    
}