/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isi;

/**
 *
 * @author cesar
 */
public class sym {
   /* terminals */
  public static final int ADDNE = 35;
  public static final int STR = 22;
  public static final int ROREQ = 52;
  public static final int SBC = 8;
  public static final int LDR = 23;
  public static final int SUBEQ = 44;
  public static final int ANDNE = 43;
  public static final int ORREQ = 36;
  public static final int BNE = 29;
  public static final int ADDGT = 34;
  public static final int MVN = 19;
  public static final int REGISTER = 56;
  public static final int ORR = 12;
  public static final int LESS = 65;
  public static final int CMP = 10;
  public static final int CMN = 11;
  public static final int SEMICOLON = 58;
  public static final int ANDGT = 42;
  public static final int BGT = 30;
  public static final int B = 26;
  public static final int AND = 2;
  public static final int RORNE = 55;
  public static final int LSL = 14;
  public static final int ADDRESS = 64;
  public static final int SUBNE = 47;
  public static final int EORLT = 49;
  public static final int ORRNE = 39;
  public static final int EOR = 3;
  public static final int MOV = 13;
  public static final int RORGT = 54;
  public static final int SUBGT = 46;
  public static final int ORRGT = 38;
  public static final int EOF = 0;
  public static final int ASR = 15;
  public static final int error = 1;
  public static final int COMA = 57;
  public static final int MUL = 20;
  public static final int CLOSEBRACKET = 62;
  public static final int NEWLINE = 60;
  public static final int ADDLT = 33;
  public static final int ADD = 6;
  public static final int ADC = 7;
  public static final int NUMBER = 63;
  public static final int MLA = 21;
  public static final int BL = 27;
  public static final int ANDLT = 41;
  public static final int BLT = 31;
  public static final int EOREQ = 48;
  public static final int RSC = 9;
  public static final int RSB = 5;
  public static final int OPENBRACKET = 61;
  public static final int RORLT = 53;
  public static final int RRX = 16;
  public static final int BIC = 18;
  public static final int SUBLT = 45;
  public static final int ORRLT = 37;
  public static final int ROR = 17;
  public static final int EORNE = 51;
  public static final int LDRB = 25;
  public static final int ADDEQ = 32;
  public static final int TEXT = 59;
  public static final int STRB = 24;
  public static final int SUB = 4;
  public static final int ANDEQ = 40;
  public static final int BEQ = 28;
  public static final int EORGT = 50;
  public static final String[] terminalNames = new String[] {
  "EOF",
  "error",
  "AND",
  "EOR",
  "SUB",
  "RSB",
  "ADD",
  "ADC",
  "SBC",
  "RSC",
  "CMP",
  "CMN",
  "ORR",
  "MOV",
  "LSL",
  "ASR",
  "RRX",
  "ROR",
  "BIC",
  "MVN",
  "MUL",
  "MLA",
  "STR",
  "LDR",
  "STRB",
  "LDRB",
  "B",
  "BL",
  "BEQ",
  "BNE",
  "BGT",
  "BLT",
  "ADDEQ",
  "ADDLT",
  "ADDGT",
  "ADDNE",
  "ORREQ",
  "ORRLT",
  "ORRGT",
  "ORRNE",
  "ANDEQ",
  "ANDLT",
  "ANDGT",
  "ANDNE",
  "SUBEQ",
  "SUBLT",
  "SUBGT",
  "SUBNE",
  "EOREQ",
  "EORLT",
  "EORGT",
  "EORNE",
  "ROREQ",
  "RORLT",
  "RORGT",
  "RORNE",
  "REGISTER",
  "COMA",
  "SEMICOLON",
  "TEXT",
  "NEWLINE",
  "OPENBRACKET",
  "CLOSEBRACKET",
  "NUMBER",
  "ADDRESS",
  "LESS" 
};
}
