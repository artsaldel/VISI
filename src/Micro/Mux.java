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
public class Mux {
    
    
public Mux(){

}    
    
public int Select(int input1,int input2, int sel){

    
    if(sel==0){return input1;}
    else{return input2;}

}


}
