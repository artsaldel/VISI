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
public class Micro {

    /**
     * @param args the command line arguments
     */
    
    public static CPU cpu;
    public static int largo=0;
    public static int pc=0;
    public static void main(String[] args) {
        // TODO code application logic here
    
        //init();
        
        
        }
        
        
    
    
    public static void NextStep(){
        
        
        try {
            if(pc < largo+7){
            cpu.DoFetch();
            cpu.DoDec();
            cpu.DoExe();
            cpu.DoMem();
            cpu.DoWB();
            cpu.NextClock();
            /*cpu.Print_IFDEC();
            cpu.Print_DECEX();
            cpu.Print_EXMEM();
            cpu.Print_MEMWB();
            cpu.Print_WB();*/
            pc = cpu.PC.GetData(0);
            }
            
        } catch (Exception e) {
            System.out.println("Error!!!!!");
                
        }
        
            
            
        
        
         
        }
    
        public static void init(){
            cpu = new CPU();
            int ins = 0;
            while(cpu.PC.GetData(0) < InstMem.Largo + 7){
                
                cpu.DoFetch();
                cpu.DoDec();
                cpu.DoExe();
                cpu.DoMem();
                cpu.DoWB();
                cpu.NextClock();
                /*cpu.Print_IFDEC();
                cpu.Print_DECEX();
                cpu.Print_EXMEM();
                cpu.Print_MEMWB();
                cpu.Print_WB();*/
                
         
}
    }

   
    
}
