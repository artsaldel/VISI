package FileManager;

import java.util.Arrays;
import javax.swing.JTable;
import javax.swing.table.*;


public class DataTables {
    private Object[][] tablaInstrucciones;
    private Object[][] tablaDatos;
    private Object[][] tablaRegistros;
            
    public DataTables()
    {
        this.tablaDatos = new Object[256][2];
        this.tablaInstrucciones = new Object[256][2];
        this.tablaRegistros = new Object[16][2];
    }

    public Object[][] getTablaInstrucciones() {
        return tablaInstrucciones;
    }

    public Object[][] getTablaDatos() {
        return tablaDatos;
    }
    
    public Object[][] getTablaRegistros() {
        return tablaRegistros;
    }
    
    public String[][] getTablaDatosSTR(Object[][] obj, int row)
    {
        String [][] tmp = new String[row][2];
        for (int i = 0; i < row; i++)
            for (int j = 0; j < 2; j++){
                tmp[i][j] = obj[i][j].toString();
            }
        return tmp;
    }
    
    public void updateDataFromGUITablaDatos(javax.swing.JTable tablaGUI)
    {
        for (int i = 0; i < 256; i++)
            for (int j = 1; j < 2; j++){
                tablaDatos[i][j] = tablaGUI.getValueAt(i,j).toString();
                //System.out.println(tablaDatos[i][j].toString());
            }
    }
    
    public void printTablaDatos()
    {
        for (int i = 0; i < 256; i++)
            for (int j = 1; j < 2; j++){
                System.out.println(tablaDatos[i][j].toString());
            }
    }

    public void setTablaInstrucciones(Object[][] tablaInstrucciones) {
        this.tablaInstrucciones = tablaInstrucciones;
    }

    public void setTablaDatos(Object[][] tablaDatos) {
        this.tablaDatos = tablaDatos;
    }

    public void setTablaRegistros(Object[][] tablaRegistros) {
        this.tablaRegistros = tablaRegistros;
    }

    public void initDataTable()
    {
        int contador = 0;
        String intStr, strHex, hex = null;
        for (int i = 0; i < 256; i++){
            for (int j = 0; j < 2; j++){
                if (j == 0) {
                   intStr = Integer.toString(contador);
                   strHex = Long.toHexString((i*4)+1024);
                   hex = String.format("%8s", strHex).replace(' ', '0');
                   tablaDatos[i][j] = contador; 
                }else
                   tablaDatos[i][j] = "0";
            }
            contador += 1;
        }
        //return tablaDatos;
    }
    
    public Object[][] initInstructionTable()
    {
        int contador = 0;
        String intStr, strHex, hex = null;
        for (int i = 0; i < 256; i++){
            for (int j = 0; j < 2; j++){
                if (j == 0) {
                   intStr = Integer.toString(contador);
                   strHex = Long.toHexString(i*4);
                   hex = String.format("%8s", strHex).replace(' ', '0');
                   tablaInstrucciones[i][j] = "0x" + contador; 
                }else
                   tablaInstrucciones[i][j] = "0";
            }
            contador += 1;
        }
        return tablaInstrucciones;
    }
    
    public Object[][] initRegisterTable()
    {
        for (int i = 0; i < 16; i++){
            for (int j = 0; j < 2; j++){
                if (j == 0) {
                    if (i == 0) {
                        tablaRegistros[i][j] = "R" + i + "  (Cero)"; 
                    }else if (i == 14) {
                        tablaRegistros[i][j] = "R" + i + "  (Saltos)"; 
                    }else if (i == 15) {
                        tablaRegistros[i][j] = "R" + i + "  (Retorno)"; 
                    }else
                        tablaRegistros[i][j] = "R" + i; 
                }else
                   tablaRegistros[i][j] = "00000000";
            }
        }
        return tablaRegistros;
    }
    
    public Object[][] UpdateRegisterTablePos(int i, int value)
    {
      tablaRegistros[i][1] = ""+value;       
        return tablaRegistros;
    }
    public Object[][] UpdateInstructionTablePos(int i, int value)
    {
      tablaInstrucciones[i][1] = ""+value;       
        return tablaInstrucciones;
    }
    public Object[][] UpdateDataTablePos(int i, int value)
    {
      tablaDatos[i][1] = ""+value;       
        return tablaDatos;
    }
    
    public void updateTableValues(JTable table, int row, int column, Object[][] obj)
    {
    // instrucciones y datos row = 256; column = 2
    // registros row = 16; column = 2
        for (int i = 0; i < row; i++)
            for (int j = 0; j < column; j++)
                //obj[i][j] = table.getValueAt(i, j).toString();
                table.setValueAt(obj[i][j], i, j);
    }
    
    public boolean verifySintaxis(Object[][] obj)
    {
        String strHex, hex, strTmp = null;
        String valor;
        //Object[][] tmp = new Object[256][2];
        for (int i = 0; i < 256; i++){
            for (int j = 1; j < 2; j++){
                valor = obj[i][j].toString();
                //System.out.println("VALOR: " + valor);
                if (!startsWith0x(valor.toString())
                        && valor.toString().length() < 8
                        && valor.toString().length() > 0
                        && (verifyHex(valor) || verifyDec(valor))){
                    //System.out.println("OK: hay menos de 8 caracteres");
                    //completeData(valor);
                    obj[i][j] = completeData(valor);
                
                }  else if (!startsWith0x(valor.toString())
                        && valor.toString().length() == 8
                        && (verifyHex(valor) || verifyDec(valor))){
                    //System.out.println("OK: hay SÓLO 8 caracteres");
                
                } else {
                    System.out.println("ERROR: " + obj[i][j].toString());
                    return false;
                }
            }//fin for j
        }//fin del fors ij
        return true;
    }
    
    public void verifySintaxis(String valor)
    {
        String strTmp;
        String strHex, hex = null;
                if (!startsWith0x(valor.toString())
                        && valor.toString().length() < 8
                        && valor.toString().length() > 0
                        && (verifyHex(valor) || verifyDec(valor))){
                    System.out.println("OK: hay menos de 8 caracteres");
                    completeData(valor);
                }
                
                else if (!startsWith0x(valor.toString())
                        && valor.toString().length() == 8
                        && (verifyHex(valor) || verifyDec(valor))){
                    System.out.println("OK: hay SÓLO 8 caracteres");
                }
                
                else
                {
                    System.out.println("ERROR");
                }

        
    }
    
    public String split0x(String value)
    {
        String[] parts = value.split("0x");
        //String part2 = parts[1]; // Elimina 0x, 0xvalue(obtiene value)   
        //System.out.println("A: " + parts[1]);
        return parts[1];
    }

    public boolean startsWith0x(String value)
    {
        if (value.startsWith("0x")) 
            return true;    
        else 
            return false;  
    }

    public boolean verifyHex(String value)
    {
        if (value.matches("-?[0-9a-f]+")) 
            return true;
        else
            return false;
    }
    
    public boolean verifyDec(String value)
    {
        if (value.matches("-?[0-9]+")) 
            return true;
        else
            return false;
    }
    
    public String completeData(String value)
    {
        String newValue = null;
        String ceros = "";
        for (int i = 0; i < (8 - value.length()); i++) {
            ceros += "0";
        }
        newValue = ceros + value;
        //System.out.println("Tamaño value: " + value.length());
        System.out.println("Completado: " + newValue);
        return newValue;
    }


}//fin del clase
