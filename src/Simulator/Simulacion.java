package Simulator;

import java.io.IOException;
import java.math.BigInteger;

/*
 * Clase encargada de la simulación general del programa. Por medio de una llamada desde la interfaz,
 * guarda la memoria de datos desde la misma y procede a analizar y guardar las instrucciones del archivo
 * out.txt, para luego ejecutar dichas instrucciones. Se encarga de clasificación, ejecución y análisis
 * de las diferentes instrucciones, así como de actualizar el PC y de medir el tiempo de ejecución
 * @author mario
 */

public class Simulacion {
    
    private Memoria memoria;        //Memoria de datos
    private Memoria registros;      //Banco de Registros
    private Memoria programa;       //Memoria del programa (ROM)
    private Calculador calculador;  //Clase calculadora, se comporta como una ALU con operaciones específicas a la instrucción
    private Analizador analizador;  //Clase analizadora, lee la memoria del programa desde out.txt
    private String error;           //Mensaje de salida del simulador. Puede ser éxito o un error específico
    private String pc;              //PC actual del programa
    private int total;              //Cantidad de instrucciones ejecutadas
    private boolean stop;           //Variable encargada de detener el programa
    
    public Simulacion(){        
    }
    
    /*
    Método encargado de la ejecución contínua del programa. Se encarga de clasificar cada instrucción
    y de devolver un mensaje con respecto al estado de la simulación. Recibe como entrada la memoria de datos
    proveniente de la interfaz gráfica
    */
    
    public String start(String [][] datos) throws IOException{
        
        pc = "00000000";    //Valor inicial del PC
        total = 0;          //Valor inicial de instrucciones ejecutadas
        analizador = new Analizador();  //Instanciación del analizador
        calculador = new Calculador();  //Instanciación del calculador
        error = "El programa ha sido simulado con éxito";   //Mensaje de éxito de la simulación
        stop = false;       //Variable encargada de detener la simulación, al inicio es falsa
        String textFile = "";   //Archivo de texto correspondiente al path de out.txt
        if (System.getProperty("os.name").equals("Linux")){
            textFile = System.getProperty("user.dir") + "/out.txt"; //Linux
        }
        else{
            textFile = System.getProperty("user.dir") + "\\out.txt"; //Windows
        }
        //Se analiza la validez del archivo out.txt
        if (analizador.analizeText(textFile) != null){
            programa = new Memoria(256, analizador.analizeText(textFile)); //Inicializa la memoria del programa
            memoria = new Memoria(256, datos); //Inicializa la memoria de datos
            String[][] reg = {{"0000", "00000000"},{"0001", "00000000"},{"0010", "00000000"},{"0011", "00000000"},
                             {"0100", "00000000"},{"0101", "00000000"},{"0110", "00000000"},{"0111", "00000000"},
                             {"1000", "00000000"},{"1001", "00000000"},{"1010", "00000000"},{"1011", "00000000"},
                             {"1100", "00000000"},{"1101", "00000000"},{"1110", "00000000"},{"1111", "00000000"}};
            registros = new Memoria(16, reg); //Inicializa el banco de registros
            while (!stop && !pc.equals("00000400") && error.equals("El programa ha sido simulado con éxito") && (programa.get(pc, (int)(Long.parseLong(pc, 16)/4)) != null)){

        
                total += 1;
                String numBinario= String.format("%32s", (new BigInteger(programa.get(pc, (int)(Long.parseLong(pc, 16)/4)), 16).toString(2))).replace(' ', '0');
                switch (calculador.condition(numBinario.substring(0, 4))){
                        case 1:
                        {
                            if (numBinario.substring(4, 6).equals("00")){
                                    error = tipoData(numBinario);                                    
                                    long decimal = Long.parseLong(pc, 16);
                                    decimal += 4;
                                    String hexDecimal = Long.toHexString(decimal);
                                    pc = String.format("%8s", hexDecimal).replace(' ', '0');
                                    break;
                                }
                            else if (numBinario.substring(4, 6).equals("01"))
                                {
                                    error = tipoMem(numBinario);                                    
                                    long decimal = Long.parseLong(pc, 16);
                                    decimal += 4;
                                    String hexDecimal = Long.toHexString(decimal);
                                    pc = String.format("%8s", hexDecimal).replace(' ', '0');
                                    break;
                                }
                            else if (numBinario.substring(4, 6).equals("10"))
                                {
                                    String newPc = tipoBranch(numBinario);
                                    if (newPc == null)
                                    {
                                        error = "Dirección prohibida";
                                        break;
                                    }
                                    else
                                    {
                                        pc = newPc;
                                        break;
                                    }
                                }
                            else
                                {
                                    error = "Intrucción no soportada";
                                    break;
                                }
                        }
                        case 0:
                        {
                            long decimal = Long.parseLong(pc, 16);
                            decimal += 4;
                            String hexDecimal = Long.toHexString(decimal);
                            pc = String.format("%8s", hexDecimal).replace(' ', '0');
                            break;
                        }
                        case -1:
                        {
                            error = "Intrucción no soportada";
                            break;
                        }
                    }
            }
        }
        else{
            //Si el texto es inválido, se muestra mensaje de error
            error = "El archivo que se desea simular es muy grande para la memoria disponible"
                    + " o existe una instrucción que viola el formato establecido";
        }
        return error;
    }
    
    public Memoria getMem(){
        return memoria;
    }
    
    public void stop(){
        stop = true;
    }
    
    public Memoria getReg(){
        return registros;
    }
    
    public Memoria getProg(){
        return programa;
    }
    
    public int getTime(){
        return total*10;
    }
    
    private String tipoData(String instruccion){
        String msj = "El programa ha sido simulado con éxito";
        String s = instruccion.substring(11, 12);   //Activación de banderas
        String rn = instruccion.substring(12, 16);
        String rd = instruccion.substring(16, 20);
        if (instruccion.substring(4, 8).equals("0000") && instruccion.substring(24, 28).equals("1001")){
            //rn se comporta como si fuera rd de acuerdo al set de instrucciones
            //rd se comporta como si fuera ra de acuerdo al set de instrucciones
            rd = registros.get(rd);
            String rm = registros.get(instruccion.substring(20, 24));
            String ra = registros.get(instruccion.substring(28, 32)); //ra = rn en el set de instrucciones
            long result = 0;
            if (instruccion.substring(8, 11).equals("000"))
                result = calculador.multi((int)Long.parseLong(rm, 16), (int)Long.parseLong(ra, 16), 0, s);
            else if (instruccion.substring(8, 11).equals("001"))
                result = calculador.multi((int)Long.parseLong(rm, 16), (int)Long.parseLong(ra, 16), (int)Long.parseLong(rd, 16), s);
            else
                msj = "Instrucción no soportada";
            if (msj.equals("El programa ha sido simulado con éxito")){
                String hexDecimal = Long.toHexString(result);
                String hex = String.format("%8s", hexDecimal).replace(' ', '0');
                registros.set(rn, hex.substring(hex.length()-8, hex.length()));
            }
        }
        else{
            String rm = String.format("%32s", Integer.toBinaryString((int)Long.parseLong(registros.get(instruccion.substring(28, 32)), 16))).replace(' ', '0');
            String src2 = "";
            if(instruccion.substring(6, 7).equals("1")){
                String bit32 = String.format("%24s", "").replace(' ', '0');
                src2 = calculador.rot(2*Integer.parseInt(instruccion.substring(20, 24), 2), bit32 + instruccion.substring(24, 32), 1, "0");
            }
            else if((instruccion.substring(27, 28).equals("0") && !instruccion.substring(7, 11).equals("1101")) || (instruccion.substring(20, 28).equals("00000000") && instruccion.substring(7, 11).equals("1101"))){
                String hex = registros.get(instruccion.substring(28, 32));
                src2 = String.format("%32s", Integer.toBinaryString((int)Long.parseLong(hex, 16))).replace(' ', '0');
                /*
                switch(instruccion.substring(25, 27)){
                    case "00":
                    {
                        String hex = String.format("%32s", Long.toBinaryString(Long.parseLong(rm, 16) << Long.parseLong(instruccion.substring(20, 25), 2))).replace(' ', '0');
                        src2 = hex.substring(hex.length()-32, hex.length());
                        break;
                    }
                    case "01":
                    {
                        String hex = String.format("%32s", Long.toBinaryString(Long.parseLong(rm, 16) >> Long.parseLong(instruccion.substring(20, 25), 2))).replace(' ', '0');
                        src2 = hex.substring(hex.length()-32, hex.length());
                        break;
                    }
                    case "10":
                    {
                        String hex = String.format("%32s", Long.toBinaryString(Long.parseLong(rm, 16) >>> Long.parseLong(instruccion.substring(20, 25), 2))).replace(' ', '0');
                        src2 = hex.substring(hex.length()-32, hex.length());
                        break;
                    }
                    case "11":
                    {
                        src2 = calculador.rot(Integer.parseInt(instruccion.substring(20, 25), 2), String.format("%32s", Long.toBinaryString(Long.parseLong(rm, 16))).replace(' ', '0'), 0, "0");
                        break;
                    }                    
                }*/
            }
            else if(instruccion.substring(27, 28).equals("0") && instruccion.substring(7, 11).equals("1101")){
                String bin = instruccion.substring(20, 25);
                src2 = String.format("%32s", Integer.toBinaryString((int)Long.parseLong(bin, 2))).replace(' ', '0');
            }
            else{
                src2 = String.format("%32s", Integer.toBinaryString((int)Long.parseLong(registros.get(instruccion.substring(20, 24)), 16))).replace(' ', '0');
                /*String rs = registros.get(instruccion.substring(20, 24));
                switch(instruccion.substring(25, 27)){
                    case "00":
                    {
                        String hex = String.format("%32s", Long.toBinaryString(Long.parseLong(rm, 16) << Long.parseLong(rs, 16))).replace(' ', '0');
                        src2 = hex.substring(hex.length()-32, hex.length());
                        break;
                    }
                    case "01":
                    {
                        String hex = String.format("%32s", Long.toBinaryString(Long.parseLong(rm, 16) >> Long.parseLong(rs, 16))).replace(' ', '0');
                        src2 = hex.substring(hex.length()-32, hex.length());
                        break;
                    }
                    case "10":
                    {
                        String hex = String.format("%32s", Long.toBinaryString(Long.parseLong(rm, 16) >>> Long.parseLong(rs, 16))).replace(' ', '0');
                        src2 = hex.substring(hex.length()-32, hex.length());
                        break;
                    }
                    case "11":
                    {
                        src2 = calculador.rot((int)Long.parseLong(rs.substring(6, 8), 16), String.format("%32s", Long.toBinaryString(Long.parseLong(rm, 16))).replace(' ', '0'), 0, "0");
                        break;
                    }
                }*/
            }
            switch (instruccion.substring(7, 11)){
                case "0000":
                {
                    long result = calculador.and((int)Long.parseLong(src2, 2), (int)Long.parseLong(registros.get(rn), 16), s);
                    String hexDecimal = Long.toHexString(result);
                    String hex = String.format("%8s", hexDecimal).replace(' ', '0');
                    registros.set(rd, hex.substring(hex.length()-8, hex.length()));
                    break;
                }
                case "0001":
                {
                    int result = calculador.xor((int)Long.parseLong(src2, 2), (int)Long.parseLong(registros.get(rn), 16), s);
                    String hexDecimal = Long.toHexString(result);
                    String hex = String.format("%8s", hexDecimal).replace(' ', '0');
                    registros.set(rd, hex.substring(hex.length()-8, hex.length()));
                    break;
                }
                case "0010":
                {
                    long result = calculador.sub((int)Long.parseLong(registros.get(rn), 16), (int)Long.parseLong(src2, 2), false, s);
                    String hexDecimal = Long.toHexString(result);
                    String hex = String.format("%8s", hexDecimal).replace(' ', '0');
                    registros.set(rd, hex.substring(hex.length()-8, hex.length()));
                    break;
                }
                case "0011":
                {
                    long result = calculador.sub((int)Long.parseLong(src2, 2), (int)Long.parseLong(registros.get(rn), 16), false, s);
                    String hexDecimal = Long.toHexString(result);
                    String hex = String.format("%8s", hexDecimal).replace(' ', '0');
                    registros.set(rd, hex.substring(hex.length()-8, hex.length()));
                    break;
                }
                case "0100":
                { 
                    long result = calculador.add((int)Long.parseLong(src2, 2), (int)Long.parseLong(registros.get(rn), 16), false, s);
                    String hexDecimal = Long.toHexString(result);
                    String hex = String.format("%8s", hexDecimal).replace(' ', '0');
                    registros.set(rd, hex.substring(hex.length()-8, hex.length()));
                    break;
                }
                case "0101":
                {
                    long result = calculador.add((int)Long.parseLong(src2, 2), (int)Long.parseLong(registros.get(rn), 16), true, s);
                    String hexDecimal = Long.toHexString(result);
                    String hex = String.format("%8s", hexDecimal).replace(' ', '0');
                    registros.set(rd, hex.substring(hex.length()-8, hex.length()));
                    break;
                }
                case "0110":
                {
                    long result = calculador.sub((int)Long.parseLong(registros.get(rn), 16), (int)Long.parseLong(src2, 2), true, s);
                    String hexDecimal = Long.toHexString(result);
                    String hex = String.format("%8s", hexDecimal).replace(' ', '0');
                    registros.set(rd, hex.substring(hex.length()-8, hex.length()));
                    break;
                }
                case "0111":
                {
                    long result = calculador.sub((int)Long.parseLong(src2, 2), (int)Long.parseLong(registros.get(rn), 16), true, s);
                    String hexDecimal = Long.toHexString(result);
                    String hex = String.format("%8s", hexDecimal).replace(' ', '0');
                    registros.set(rd, hex.substring(hex.length()-8, hex.length()));
                    break;
                }
                case "1010":
                {
                    calculador.sub((int)Long.parseLong(registros.get(rn), 16), (int)Long.parseLong(src2, 2), false, "1");
                    break;
                }
                case "1011":
                {
                    calculador.add((int)Long.parseLong(src2, 2), (int)Long.parseLong(registros.get(rn), 16), false, "1");
                    break;
                }
                case "1100":
                {
                    int result = calculador.or((int)Long.parseLong(src2, 2), (int)Long.parseLong(registros.get(rn), 16), s);
                    String hexDecimal = Long.toHexString(result);
                    String hex = String.format("%8s", hexDecimal).replace(' ', '0');
                    registros.set(rd, hex.substring(hex.length()-8, hex.length()));
                    break;
                }
                case "1101":
                {
                    if (instruccion.substring(6, 7).equals("1") || instruccion.substring(20, 28).equals("00000000")){
                        calculador.mov(src2, s);
                        registros.set(rd, String.format("%8s", Long.toHexString(Long.parseLong(src2, 2))).replace(' ', '0'));
                    }
                    else{
                        if (instruccion.substring(6, 7).equals("0") && instruccion.substring(25, 27).equals("01")){
                            long result = calculador.lsr((int)Long.parseLong(src2, 2), (int)Long.parseLong(rm, 2), s);
                            String hexDecimal = Long.toHexString(result);
                            String hex = String.format("%8s", hexDecimal).replace(' ', '0');
                            registros.set(rd, hex.substring(hex.length()-8, hex.length()));
                        }
                        else if (instruccion.substring(6, 7).equals("0") && instruccion.substring(25, 27).equals("00")){
                            long result = calculador.lsl((int)Long.parseLong(src2, 2), (int)Long.parseLong(rm, 2), s);
                            String hexDecimal = Long.toHexString(result);
                            String hex = String.format("%8s", hexDecimal).replace(' ', '0');
                            registros.set(rd, hex.substring(hex.length()-8, hex.length()));
                        }
                        else if (instruccion.substring(6, 7).equals("0") && instruccion.substring(25, 27).equals("11") && !instruccion.substring(20, 24).equals("0000")){
                            String result = calculador.rot((int)Long.parseLong(src2, 2), rm, 1, s);
                            String hexDecimal = Long.toHexString(Long.parseLong(result, 2));
                            String hex = String.format("%8s", hexDecimal).replace(' ', '0');
                            registros.set(rd, hex.substring(hex.length()-8, hex.length()));
                        }
                        else if (instruccion.substring(6, 7).equals("0") && instruccion.substring(25, 27).equals("10")){
                            long result = calculador.asr((int)Long.parseLong(src2, 2), (int)Long.parseLong(rm, 2), s);
                            String hexDecimal = Long.toHexString(result);
                            String hex = String.format("%8s", hexDecimal).replace(' ', '0');
                            registros.set(rd, hex.substring(hex.length()-8, hex.length()));
                        }
                        else if (instruccion.substring(27, 28).equals("0") && instruccion.substring(6, 7).equals("0") && instruccion.substring(25, 27).equals("11") && instruccion.substring(20, 24).equals("0000")){
                            String result = calculador.rrx(rm, s);
                            String hexDecimal = Long.toHexString(Long.parseLong(result, 2));
                            String hex = String.format("%8s", hexDecimal).replace(' ', '0');
                            registros.set(rd, hex.substring(hex.length()-8, hex.length()));
                        }
                    }
                    break;
                }
                case "1110":
                {
                    long result = calculador.and((int)~Long.parseLong(src2, 2), (int)Long.parseLong(registros.get(rn), 16), s);
                    String hexDecimal = Long.toHexString(result);
                    String hex = String.format("%8s", hexDecimal).replace(' ', '0');
                    registros.set(rd, hex.substring(hex.length()-8, hex.length()));
                    break;
                }
                case "1111":
                {
                    int result = calculador.or(0, (int)~Long.parseLong(registros.get(rn), 16), s);
                    String hexDecimal = Long.toHexString(result);
                    String hex = String.format("%8s", hexDecimal).replace(' ', '0');
                    registros.set(rd, hex.substring(hex.length()-8, hex.length()));
                    break;
                }
            }
        }
        return msj;
    }
    
    private String tipoMem(String instruccion){
        String msj = error;
        String rn = registros.get(instruccion.substring(12, 16));
        String rd = instruccion.substring(16, 20);
        String src2 = "";
        if (instruccion.substring(6, 7).equals("1")){
            String rm = registros.get(instruccion.substring(28, 32));
            src2 = Long.toBinaryString(Long.parseLong(rm, 16));
            /*switch(instruccion.substring(25, 27)){
                case "00":
                {
                    String hex = String.format("%32s", Long.toBinaryString(Long.parseLong(rm, 16) << Long.parseLong(instruccion.substring(20, 25), 2))).replace(' ', '0');
                    src2 = hex.substring(hex.length()-32, hex.length());
                    break;
                }
                case "01":
                {
                    String hex = String.format("%32s", Long.toBinaryString(Long.parseLong(rm, 16) >> Long.parseLong(instruccion.substring(20, 25), 2))).replace(' ', '0');
                    src2 = hex.substring(hex.length()-32, hex.length());
                    break;
                }
                case "10":
                {
                    String hex = String.format("%32s", Long.toBinaryString(Long.parseLong(rm, 16) >>> Long.parseLong(instruccion.substring(20, 25), 2))).replace(' ', '0');
                    src2 = hex.substring(hex.length()-32, hex.length());
                    break;
                }
                case "11":
                {
                    src2 = calculador.rot(Integer.parseInt(instruccion.substring(20, 25), 2), String.format("%32s", Long.toBinaryString(Long.parseLong(rm, 16))).replace(' ', '0'), 0, "0");
                    break;
                }                    
            }*/
        }
        else{
            src2 = instruccion.substring(20, 32);
        }
        String dirAbs;
        if (instruccion.substring(8, 9).equals("1")){
            String hex = String.format("%8s", Long.toHexString(Long.parseLong(rn, 16) + Long.parseLong(src2, 2))).replace(' ', '0');
            dirAbs = hex.substring(hex.length()-8, hex.length());
        }
        else{
            String hex = String.format("%8s", Long.toHexString(Long.parseLong(rn, 16) - Long.parseLong(src2, 2))).replace(' ', '0');
            dirAbs = hex.substring(hex.length()-8, hex.length());
        }
        if (instruccion.substring(7, 8).equals("0") && instruccion.substring(10, 11).equals("0")){
            registros.set(instruccion.substring(12, 16), dirAbs);
            dirAbs = rn;
        }
        else if (instruccion.substring(7, 8).equals("1") && instruccion.substring(10, 11).equals("1")){
            registros.set(instruccion.substring(12, 16), dirAbs);
        }
        if ((int)Long.parseLong(dirAbs, 16) >= 2048 ||  (int)Long.parseLong(dirAbs, 16) < 1024){
            msj = "Dirección fuera de rango";
        }
        else
        {
            if (instruccion.substring(9, 10).equals("0")){
                if ((int)Long.parseLong(dirAbs, 16)%4 != 0){
                    msj = "Direccionamiento resulta en desalineamiento";
                }
                else{
                    if(instruccion.substring(11, 12).equals("0")){
                        memoria.set(dirAbs, registros.get(rd));
                    }
                    else{
                        registros.set(rd, memoria.get(dirAbs));
                    }
                }
            }
            else if (instruccion.substring(9, 10).equals("1")){
                if(instruccion.substring(11, 12).equals("0")){
                    String dirAbs2 = String.format("%8s", Long.toHexString(Long.parseLong(dirAbs, 16) - (Long.parseLong(dirAbs, 16)%4))).replace(' ', '0');
                    memoria.set(dirAbs2, registros.get(rd), (int)(Long.parseLong(dirAbs, 16)%4));
                }
                else{
                    String dirAbs2 = String.format("%8s", Long.toHexString(Long.parseLong(dirAbs, 16) - (Long.parseLong(dirAbs, 16)%4))).replace(' ', '0');
                    registros.set(rd, "000000"+ memoria.getByte(dirAbs2, (int)(Long.parseLong(dirAbs, 16)%4)));
                }
            }
        }
        return msj;
    }
    
    private String tipoBranch(String instruccion){
        String bit32 = String.format("%8s", "").replace(' ', instruccion.charAt(8));
        long newPc = 8 + Long.parseLong(pc, 16) + (int)(Long.parseLong(bit32 + instruccion.substring(8, 32), 2) << 2);
        String hexPc = String.format("%8s", Long.toHexString(newPc)).replace(' ', '0');
        String lastDir =  String.format("%8s", Long.toHexString(analizador.getNumInst()*4)).replace(' ', '0');
        if (programa.get(hexPc) != null || hexPc.equals("00000400") || hexPc.equals(lastDir))
                return hexPc;
        else
            return null;
    }
    
}
