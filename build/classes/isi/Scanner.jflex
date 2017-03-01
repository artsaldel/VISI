package isi;

import java_cup.runtime.*;
import java.io.Reader;
import ARManalyzers.ModuloError;
      
%% 

%class LexicalAnalyzer

%line
%column
%cup
%{
    private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn);
    }
    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
    }
%}

JUMP = \r|\n|\r\n
SPACE = [ \t\f]

TEXT = ([a-zA-Z] | [0-9] | "_")*
ADDRESS = "#0x"[[0-9]|[a-fA-F]]+
NUMBER = "#""-"?[0-9]+

%% 
   
<YYINITIAL> {
    
    "r0"               {  return symbol(sym.REGISTER, new String(yytext()));      }
    "r1"               {  return symbol(sym.REGISTER, new String(yytext()));      }
    "r2"               {  return symbol(sym.REGISTER, new String(yytext()));      }
    "r3"               {  return symbol(sym.REGISTER, new String(yytext()));      }
    "r4"               {  return symbol(sym.REGISTER, new String(yytext()));      }
    "r5"               {  return symbol(sym.REGISTER, new String(yytext()));      }
    "r6"               {  return symbol(sym.REGISTER, new String(yytext()));      }
    "r7"               {  return symbol(sym.REGISTER, new String(yytext()));      }
    "r8"               {  return symbol(sym.REGISTER, new String(yytext()));      }
    "r9"               {  return symbol(sym.REGISTER, new String(yytext()));      }
    "r10"              {  return symbol(sym.REGISTER, new String(yytext()));      }
    "r11"              {  return symbol(sym.REGISTER, new String(yytext()));      }
    "r12"              {  return symbol(sym.REGISTER, new String(yytext()));      }
    "r13"              {  return symbol(sym.REGISTER, new String(yytext()));      }
    "r14"              {  return symbol(sym.REGISTER, new String(yytext()));      }
    "r15"              {  return symbol(sym.REGISTER, new String(yytext()));      }

    ";"                {  return symbol(sym.SEMICOLON);     }
    ","                {  return symbol(sym.COMA);          }
    "["                {  return symbol(sym.OPENBRACKET);   }
    "]"                {  return symbol(sym.CLOSEBRACKET);  }
    "-"                {  return symbol(sym.LESS);          }

    



    "SUM"              {  return symbol(sym.SUM);   } 
    "RES"              {  return symbol(sym.RES);   } 
    "MUL"              {  return symbol(sym.MUL);   } 
    "DIV"              {  return symbol(sym.DIV);   } 
    "MOD"              {  return symbol(sym.MOD);   }
    "COMP"              {  return symbol(sym.COMP);   }
    

    "AND"              {  return symbol(sym.AND);   } 
    "OR"              {  return symbol(sym.OR);   }  
    "XOR"              {  return symbol(sym.XOR);   }  
    "NAND"              {  return symbol(sym.NAND);   }  
    "NOR"              {  return symbol(sym.NOR);   }  
    
    
    "CORI"              {  return symbol(sym.CORI);   }  
    "CORD"              {  return symbol(sym.CORD);   } 
    

    "CAR"              {  return symbol(sym.CAR);   }  
    "CARP"              {  return symbol(sym.CARP);   }  
    "OBT"              {  return symbol(sym.OBT);   } 
    "OBTP"              {  return symbol(sym.OBTP);   }  
    

    "SAL"              {  return symbol(sym.SAL);   }  
    "SALI"              {  return symbol(sym.SALI);   }  
    "SALNI"              {  return symbol(sym.SALNI);   }  
    "SALM"              {  return symbol(sym.SALM);   }
    

    "sum"              {  return symbol(sym.SUM);   } 
    "res"              {  return symbol(sym.RES);   } 
    "mul"              {  return symbol(sym.MUL);   } 
    "div"              {  return symbol(sym.DIV);   } 
    "mod"              {  return symbol(sym.MOD);   }
    "comp"              {  return symbol(sym.COMP);   }
    

    "and"              {  return symbol(sym.AND);   } 
    "or"              {  return symbol(sym.OR);   }  
    "xor"              {  return symbol(sym.XOR);   }  
    "nand"              {  return symbol(sym.NAND);   }  
    "nor"              {  return symbol(sym.NOR);   }  
    
    
    "cori"              {  return symbol(sym.CORI);   }  
    "cord"              {  return symbol(sym.CORD);   } 
    

    "car"              {  return symbol(sym.CAR);   }  
    "carp"              {  return symbol(sym.CARP);   }  
    "obt"              {  return symbol(sym.OBT);   } 
    "obtp"              {  return symbol(sym.OBTP);   }  
    

    "sal"              {  return symbol(sym.SAL);   }  
    "sali"              {  return symbol(sym.SALI);   }  
    "salni"              {  return symbol(sym.SALNI);   }  
    "salm"              {  return symbol(sym.SALM);   }

    {JUMP}             {  return symbol(sym.NEWLINE);        }
    {SPACE}            {  /* do nothing */                   }

    {ADDRESS}          {  return symbol(sym.ADDRESS, new String(yytext()));   }
    {NUMBER}           {  return symbol(sym.NUMBER,  new String(yytext()));   }
    {TEXT}             {  return symbol(sym.TEXT,    new String(yytext()));   }
}

[^]                    { 
                        ModuloError.insertError("Caracter ilegal <"+yytext()+">");
                        throw new Error("Caracter ilegal <"+yytext()+">");
                       }


