package Simulator;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author mario
 */
public class Analizador {
 
    private int numInst;
    
    public String[][] analizeText(String textFile) throws FileNotFoundException, IOException{
    
        String [][] programa = new String[256][2];
        FileInputStream fstream = new FileInputStream(textFile);
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String strLine;
        int index = 0;
        while ((strLine = br.readLine()) != null){
            String indexHex = String.format("%8s", Long.toHexString(index*4)).replace(' ', '0');
            if (strLine.length()==17 && index < 256 && strLine.substring(0, 8).equals(indexHex)){
                programa[index][0] = strLine.substring(0, 8);
                programa[index][1] = strLine.substring(9);
                index += 1;
            }
            else{
                programa = null;
                break;
            }
        }
        
        numInst = index;
        //Close the input stream
        br.close();
        if (index == 0){
            return null;
        }
        else{
            return programa;
        }
    }
    
    public int getNumInst(){
        return numInst;
    }
}
