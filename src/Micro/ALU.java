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
public class ALU {
    int Output;
    boolean N=false; //negado
    boolean Z=false; //cero
    
    public ALU(){Output=0;}
    
    public int Do(int A, int B,int op){
    int temp=0;
    if(op == 0 || op==17 || op==18 || op==19 || op==20){temp = A+B;}
    else if(op == 1){temp = A-B;}
    else if(op==2){temp = A*B;}
    else if(op==3){temp = A/B;}
    else if(op==4){temp = A%B;}
    else if(op==5){temp = A-B;}
    else if(op==6){temp = A&B;}
    else if(op==7){temp = A|B;}
    else if(op==8){temp = A^B;}
    else if(op==9){temp = ~(A&B);}
    else if(op==10){temp = ~(A|B);}
    else if(op==11){temp = A<<B;}
    else if(op==12){temp=A>>B;}
    else if(op==13){temp=B;}
    else if(op==14){temp=B;}
    else if(op==15){temp=B;}
    else if(op==16){temp=B;}
    
    Output=temp;
    
    if(Output<0){N=true;}
    else if(Output==0){Z=true;}
    else{N=false;Z=false;}
    
    return temp;
    }
}
