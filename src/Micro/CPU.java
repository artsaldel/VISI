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
public class CPU {
    //all
    ControlUnit CU = new ControlUnit();
    boolean Stall = false; 
    boolean salto=false;
    int newpc = 0;
    //fetch
    private Mux Muxpc=new Mux();
    public Reg PC = new Reg(1);
    private Reg IFDEC = new Reg(2);
    private Adder AddF=new Adder();
    public InstMem IM=new InstMem();
    private int pc=0;
    private int Finst=0;
    //deco
    private Mux MuxB=new Mux();
    public BankReg BR = new BankReg();
    private Reg DECEX = new Reg(4);
    private int Dinst=0;
    private int top;
    private int tdest;
    private int tflag;
    private int dtemp;
    private int inB;
    private int inA;
    private int inopB;
    private int inopA;
    private int inmed;
    //exe
    private Reg EXMEM = new Reg(2);
    ALU alu = new ALU();
    int aluA;
    int aluB;
    int aluOut;
    //mem
    private Reg MEMWB = new Reg(1);
    public DataMem DM= new DataMem(100); 
    private Mux Muxmem=new Mux();
    int memOut;
    //wb
    int wbdata;
    int wbdest;
    int wbopcode;
    
    
    public CPU(){
    //llenar memoria de instrucciones aqui
    
    }
    
    public void DoFetch(){
        
        pc=IFDEC.GetData(0);
        PC.SetData(0, pc);
        Finst = IM.GetData(pc);
        pc=AddF.Add(pc, 1);
    
    }
    
    public void DoDec(){
    
    Dinst=IFDEC.GetData(1);
    dtemp=Dinst;
    top=(dtemp>>27)&31;
    
    if(top==13 || top==14 || top==15 || top==16){ //memoria
        if(top==13 || top==14){ //carga
        dtemp=Dinst;
        tdest=0;//(dtemp>>22)&15;
        dtemp=Dinst;
        tflag=(dtemp>>26)&1;

        dtemp=Dinst;
        inA=(dtemp>>22)&15;
        inopA=inA;
        inA=BR.GetDataA(inA);

        dtemp=Dinst;
        inB=0;
        inopB=66;
        inB=BR.GetDataB(0);

        dtemp=Dinst;
        inmed=(dtemp>>0)&4194303;

        inB=inmed;//MuxB.Select(inB, inmed, tflag);  
            }
        
        else{ //obtener
        
            dtemp=Dinst;
            tdest=(dtemp>>22)&15;
            dtemp=Dinst;
            tflag=(dtemp>>26)&1;

            dtemp=Dinst;
            inA=0;//(dtemp>>22)&15;
            inopA=inA;
            inA=BR.GetDataA(inA);

            dtemp=Dinst;
            inB=0;
            inopB=88;
            inB=BR.GetDataB(0);

            dtemp=Dinst;
            inmed=(dtemp>>0)&4194303;

            inB=inmed;//MuxB.Select(inB, inmed, tflag);

            }

        
    }
    
    else if (top==17 || top==18 || top==19 || top==20){ //saltos-------------------------
    
    dtemp=Dinst;
    tdest=0;
    dtemp=Dinst;
    tflag=(dtemp>>26)&1;
    
    dtemp=Dinst;
    inA=0;
    inopA=inA;
    inA=BR.GetDataA(inA);
    
    dtemp=Dinst;
    inB=0;
    inopB=inB;
    inB=BR.GetDataB(inB);
    
    dtemp=Dinst;
    inmed=(dtemp)&67108863;
    
    inB=inmed;   
        
        
    }
        
        
    else{ //aritmeticos/logicos
    
    dtemp=Dinst;
    tdest=(dtemp>>22)&15;
    dtemp=Dinst;
    tflag=(dtemp>>26)&1;
    
    dtemp=Dinst;
    inA=(dtemp>>18)&15;
    inopA=inA;
    inA=BR.GetDataA(inA);
    
    //------------------------
    if(CU.wc[0].Dest==inopA){inA=MEMWB.GetData(0);}
    //------------------------
    
    dtemp=Dinst;
    inB=(dtemp>>14)&15;
    inopB=inB;
    inB=BR.GetDataB(inB);
    
    dtemp=Dinst;
    inmed=(dtemp>>0)&262143;
    
    inB=MuxB.Select(inB, inmed, tflag);   
    }
    
    
    
    if((CU.wc[2].OpCode==15 || CU.wc[2].OpCode==16) && (CU.wc[2].Dest==inopA || CU.wc[2].Dest==inopB)){CU.AddInst(0, 0);Stall=true;}
    else if(salto){CU.AddInst(0, 0);salto=false;}
    else{CU.AddInst(top, tdest);}
    
    }
    
    public void DoExe(){
    
    aluA=DECEX.GetData(0);
    aluB=DECEX.GetData(1);
    int tempop=CU.ec[0].ALUOp;
    if(tempop==17 || tempop==18 || tempop==19 || tempop==20){
    
        if(tempop==17){newpc=aluB;salto=true;CU.flushPipes();}
        else if(tempop==18){if(alu.Z){newpc=aluB;salto=true;CU.flushPipes();}}
        else if(tempop==19){if(!alu.Z){newpc=aluB;salto=true;CU.flushPipes();}}
        else if(tempop==20){if(alu.N){newpc=aluB;salto=true;CU.flushPipes();}}
    
    }
    
    
    //adelantamiento
    if(CU.wc[1].Dest==DECEX.GetData(2)){
        aluA=EXMEM.GetData(1);}
    else if(CU.wc[0].Dest==DECEX.GetData(2)){
        aluA=MEMWB.GetData(0);
    }
    
    if(CU.wc[1].Dest==DECEX.GetData(3)){
        aluB=EXMEM.GetData(1);
    }
    else if(CU.wc[0].Dest==DECEX.GetData(3)){
        aluB=MEMWB.GetData(0);
    }
    
    aluOut=alu.Do(aluA, aluB, CU.ec[0].ALUOp);
    }
   
    
    
    public void DoMem(){
    
    if(CU.mc[0].NoMem==1){
        memOut=EXMEM.GetData(1);
    }
    else{
        if(CU.mc[0].W==1){DM.SetData(EXMEM.GetData(1), EXMEM.GetData(0));memOut=EXMEM.GetData(1);}
        else{memOut=DM.GetData(EXMEM.GetData(1));}        
    }
    }
    
    public void DoWB(){
    wbdest=CU.wc[0].Dest;
    wbdata=MEMWB.GetData(0);
    wbopcode=CU.wc[0].OpCode;
    }
    
    public void NextClock(){
        
    if (Stall==false)
    {
        
    if(salto){IFDEC.SetData(0, newpc);System.out.println("*******************Salto en linea: "+(pc-2));}
    else{IFDEC.SetData(0, pc);}
        
    IFDEC.SetData(1, Finst);
    
    
    if(tflag == 1){
        DECEX.SetData(0, inA);
        DECEX.SetData(1, inB);
        DECEX.SetData(2, inopA);
        DECEX.SetData(3, 69);
    }
    else{
        DECEX.SetData(0, inA);
        DECEX.SetData(1, inB);
        DECEX.SetData(2, inopA);
        DECEX.SetData(3, inopB);
    }
    
    EXMEM.SetData(0, aluA);
    EXMEM.SetData(1, aluOut);
    
    MEMWB.SetData(0, memOut);
    
    if((wbopcode!=13) && (wbopcode!=14) ){BR.SetData(wbdest, wbdata);}
    
    CU.UpdatePipes();
    }
    else
    {
    
    EXMEM.SetData(0, aluA);
    EXMEM.SetData(1, aluOut);
    
    MEMWB.SetData(0, memOut);
    
    if((wbopcode!=13) && (wbopcode!=14)){BR.SetData(wbdest, wbdata);}
    
    CU.UpdatePipes();
    Stall=false;
    }
    
    
    
    }
    
    public void Print_IFDEC(){
    System.out.println("-----------------------------------------------------");
    System.out.println("-----FETCH");
    System.out.println("PC= "+ pc);
    System.out.println("Inst= "+Finst);
    }
    
    public void Print_DECEX(){
    System.out.println("-----DECO");
    System.out.println("inopA= "+inopA);
    System.out.println("inopB= "+inopB);
    System.out.println("inA= "+inA);
    System.out.println("inB= "+inB);
    }
    
    public void Print_EXMEM(){
    System.out.println("-----EXE");
    System.out.println("aluOut= "+aluOut); 
    }
    
    public void Print_MEMWB(){
    System.out.println("-----MEM");
    System.out.println("memOut= "+memOut); 
    }
    
    public void Print_WB(){
    System.out.println("-----WB");
    System.out.println("dest= "+wbdest);
    System.out.println("data= "+wbdata);
    }
    
    public int Get_inst(){
    return Finst;
    }
    
        
        
    
    
    
    
    
    
}
