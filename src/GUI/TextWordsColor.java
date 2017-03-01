package GUI;

import javax.swing.*;
import java.awt.*;
import javax.swing.text.*;


public class TextWordsColor extends JFrame {
    
    private int findLastNonWordChar (String text, int index) {
        while (--index >= 0) {
            if (String.valueOf(text.charAt(index)).matches("\\W")) {
                break;
            }
        }
        return index;
    }   // FIN DEL MÉTODO findLastNonWordChar

    private int findFirstNonWordChar (String text, int index) {
        while (index < text.length()) {
            if (String.valueOf(text.charAt(index)).matches("\\W")) {
                break;
            }
            index++;
        }
        return index;
    }   // FIN DEL MÉTODO findFirstNonWordChar

    public TextWordsColor (JTextPane txt) {
        
        String mnemonic = "SUM|RES|MUL|DIV|MOD|COMP|AND|OR|XOR|NAND|NOR|CORI|"
                + "CAR|CARP|OBT|OBTP|SAL|SALNI|SALM|"
                + "sum|res|mul|div|mod|comp|and|or|xor|nand|nor|cori|car|carp|carp|"
                + "obt|obt|obtp|sal|salni|salm";
        
        String registros = "r0|r1|r2|r3|r4|r5|r6|r7|r8|r9|r10|r11|r12|r13|r14|r15|"
                + "R0|R1|R2|R3|R4|R5|R6|R7|R8|R9|R10|R11|R12|R13|R14|R15";
        String tokens = "#|;|0x";
        String number = "0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|"
                + "21|22|23|24|25|26|27|28|29|30|31";
        final StyleContext cont = StyleContext.getDefaultStyleContext();
        // COLOREA DE ROJO LOS REGISTROS
        final AttributeSet attr = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.BLUE);
        // COLOREA DE VERDE LOS MNEMÓNICOS
        final AttributeSet attr1 = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.MAGENTA);
        final AttributeSet attr2 = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.ORANGE);
        final AttributeSet attrBlack = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.BLACK);
        DefaultStyledDocument doc = new DefaultStyledDocument() {
            public void insertString (int offset, String str, AttributeSet a) throws BadLocationException {
                super.insertString(offset, str, a);

                String text = getText(0, getLength());
                int before = findLastNonWordChar(text, offset);
                if (before < 0) before = 0;
                int after = findFirstNonWordChar(text, offset + str.length());
                int wordL = before;
                int wordR = before;

                while (wordR <= after) {
                    if (wordR == after || String.valueOf(text.charAt(wordR)).matches("\\W")) {
                        if (text.substring(wordL, wordR).matches("(\\W)*("+ registros +")"))
                            setCharacterAttributes(wordL, wordR - wordL, attr, false);
                        else if (text.substring(wordL, wordR).matches("(\\W)*("+ mnemonic +")"))
                            setCharacterAttributes(wordL, wordR - wordL, attr1, false);
                        else if (text.substring(wordL, wordR).matches("(\\W)*("+ tokens +")"))
                            setCharacterAttributes(wordL, wordR - wordL, attr2, false);
                        else if (text.substring(wordL, wordR).matches("(\\W)*("+ number +")"))
                            setCharacterAttributes(wordL, wordR - wordL, attr2, false);
                        else
                            setCharacterAttributes(wordL, wordR - wordL, attrBlack, false);
                        wordL = wordR;
                    }
                    wordR++;
                }
            }

            public void remove (int offs, int len) throws BadLocationException {
                super.remove(offs, len);

                String text = getText(0, getLength());
                int before = findLastNonWordChar(text, offs);
                if (before < 0) before = 0;
                int after = findFirstNonWordChar(text, offs);

                if (text.substring(before, after).matches("(\\W)*("+ registros +")")) 
                    setCharacterAttributes(before, after - before, attr, false);
                else if (text.substring(before, after).matches("(\\W)*("+ mnemonic +")"))
                    setCharacterAttributes(before, after - before, attr1, false);
                else if (text.substring(before, after).matches("(\\W)*("+ tokens +")"))
                    setCharacterAttributes(before, after - before, attr2, false);
                else 
                    setCharacterAttributes(before, after - before, attrBlack, false);
                
            }
        };
        txt.setDocument(doc);
    }


}   // FIN DE LA CLASE

    

