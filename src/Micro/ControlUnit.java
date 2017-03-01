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
public class ControlUnit {
    
    ExeC[] ec=new ExeC[2];
    MemC[] mc=new MemC[3];
    WBC[] wc=new WBC[4];
    FetchC[] fc=new FetchC[1];
    
    public ControlUnit(){
    
        for(int i=0;i<2;i++){ec[i]=new ExeC(0);}
        for(int i=0;i<3;i++){mc[i]=new MemC(0,1);}
        for(int i=0;i<4;i++){wc[i]=new WBC(0,0);}
    
    }
    
    
    public void UpdatePipes(){
    
        ec[0]=ec[1];
        mc[0]=mc[1];
        mc[1]=mc[2];
        wc[0]=wc[1];
        wc[1]=wc[2];
        wc[2]=wc[3];
    
    }
    
    public void flushPipes(){
    
       ec[1]=new ExeC(0);
       mc[2]=new MemC(0,1);
       wc[3]=new WBC(0,0); 
    }
    
    
    public void AddInst(int op,int dest){
    
    if(op==0){ //suma
       ec[1]=new ExeC(0);
       mc[2]=new MemC(0,1);
       wc[3]=new WBC(dest,0); 
    }
    else if(op==1){ //resta
       ec[1]=new ExeC(1);
       mc[2]=new MemC(0,1);
       wc[3]=new WBC(dest,1); 
    }
    else if(op==2){ //mul
       ec[1]=new ExeC(2);
       mc[2]=new MemC(0,1);
       wc[3]=new WBC(dest,2); 
    }
    else if(op==3){ //div
       ec[1]=new ExeC(3);
       mc[2]=new MemC(0,1);
       wc[3]=new WBC(dest,3); 
    }
    else if(op==4){ //mod
       ec[1]=new ExeC(4);
       mc[2]=new MemC(0,1);
       wc[3]=new WBC(dest,4); 
    }
    else if(op==5){ //cmp
       ec[1]=new ExeC(5);
       mc[2]=new MemC(0,1);
       wc[3]=new WBC(dest,5); 
    }
    else if(op==6){ //and
       ec[1]=new ExeC(6);
       mc[2]=new MemC(0,1);
       wc[3]=new WBC(dest,6); 
    }
    else if(op==7){ //or
       ec[1]=new ExeC(7);
       mc[2]=new MemC(0,1);
       wc[3]=new WBC(dest,7); 
    }
    else if(op==8){ //xor
       ec[1]=new ExeC(8);
       mc[2]=new MemC(0,1);
       wc[3]=new WBC(dest,8); 
    }
    else if(op==9){ //nand
       ec[1]=new ExeC(9);
       mc[2]=new MemC(0,1);
       wc[3]=new WBC(dest,9); 
    }
    else if(op==10){ //nor
       ec[1]=new ExeC(10);
       mc[2]=new MemC(0,1);
       wc[3]=new WBC(dest,10); 
    }
    else if(op==11){ //cori
       ec[1]=new ExeC(11);
       mc[2]=new MemC(0,1);
       wc[3]=new WBC(dest,11); 
    }
    else if(op==12){ //cord
       ec[1]=new ExeC(12);
       mc[2]=new MemC(0,1);
       wc[3]=new WBC(dest,12); 
    }
    else if(op==13){ //car
       ec[1]=new ExeC(13);
       mc[2]=new MemC(1,0);
       wc[3]=new WBC(dest,13); 
    }
    else if(op==14){ //carp
       ec[1]=new ExeC(14);
       mc[2]=new MemC(1,0);
       wc[3]=new WBC(dest,14); 
    }
    else if(op==15){ //obt
       ec[1]=new ExeC(15);
       mc[2]=new MemC(0,0);
       wc[3]=new WBC(dest,15); 
    }
    else if(op==16){ //obtp
       ec[1]=new ExeC(16);
       mc[2]=new MemC(0,0);
       wc[3]=new WBC(dest,16); 
    }
    else if(op==17){ //sal
       ec[1]=new ExeC(17);
       mc[2]=new MemC(0,1);
       wc[3]=new WBC(0,17); 
    }
    else if(op==18){ //sali
       ec[1]=new ExeC(18);
       mc[2]=new MemC(0,1);
       wc[3]=new WBC(0,18); 
    }
    else if(op==19){ //salni
       ec[1]=new ExeC(19);
       mc[2]=new MemC(0,1);
       wc[3]=new WBC(0,19); 
    }
    else if(op==20){ //salm
       ec[1]=new ExeC(20);
       mc[2]=new MemC(0,1);
       wc[3]=new WBC(0,20); 
    }
    
    }
    
    
    
    
    
}
