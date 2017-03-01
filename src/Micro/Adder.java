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
public class Adder {
    int Input;
    int Output;
    
    public Adder(){
    Input=0;
    Output=0;
    }
    
    public int Add(int Value, int Add){
        Input=Value;
        Output=Value + Add;
        return Output;
    }
}
