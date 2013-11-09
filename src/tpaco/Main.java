/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tpaco;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import tpaco.exception.TruthTableReaderWrongTerm;
import tpaco.exception.TruthTableReaderWrongVariables;

/**
 *
 * @author mauricio
 */
public class Main {

    public static void main(String[] args) throws IOException {
        //testFormula();
        testTruthTableReader();
    }
    
    public static void testFormula() throws IOException {
        //Formula f = Formula.read(new BufferedReader(new FileReader(args[0])));
        Formula f = Formula.read(
                "0000\n"
                + "0001\n"
                + "0010\n"
                + "0011\n"
                + "0101\n"
                + "0111\n"
                + "1000\n"
                + "1010\n"
                + "1100\n"
                + "1101\n"
                + "1111");
        f.setVariables("abcdek");
        f.reduceToPrimeImplicants();
        System.out.println(f);
        f.reducePrimeImplicantsToSubset();
        System.out.println(f);
        System.out.println(f.getHumanReadable());
        f.terminosDerivados();
    }
    
    public static void testTruthTableReader() {
        
        boolean tiraError = false;
        
        
        TruthTableReader ttr = null;
        
        //ejemplo 1
        try {
            ttr = TruthTableReader.read(
                "abcf" + "\n" +
                "0001" + "\n" +
                "0010" + "\n" +
                "0100" + "\n" +
                "0111" + "\n" +
                "1001" + "\n" +
                "1010" + "\n" +
                "1101" + "\n" +
                "1111"
            );
        } catch (Exception ex) {
            tiraError = true;
        }
        if (tiraError) {
            System.out.println("se rompio la clase, no lee bien el ejemplo 1");
        } else {
            ttr.printReadable();
        }
        
        //ejemplo 1.a
        try {
            ttr = TruthTableReader.read(
                "abcz" + "\n" +
                "0001" + "\n" +
                "0010" + "\n" +
                "0100" + "\n" +
                "0111" + "\n" +
                "1001" + "\n" +
                "1010" + "\n" +
                "1101" + "\n" +
                "1111"
            );
        } catch (Exception ex) {
            tiraError = true;
        }
        
        if (!tiraError) {
            System.out.println("La clase TruthTableReader no funciona bien, la ultima variable debe ser f (resultado de la funcion), ejemplo 1.a");
        } else {
            ttr.printReadable();
        }
        
        //ejemplo 2
        try {
            ttr = TruthTableReader.read(
                "abc+f" + "\n" +
                "0001" + "\n" +
                "0010" + "\n" +
                "0100" + "\n" +
                "0111" + "\n" +
                "1001" + "\n" +
                "1010" + "\n" +
                "1101" + "\n" +
                "1111"
            );      
        } catch (TruthTableReaderWrongTerm ex) {
            
        } catch (TruthTableReaderWrongVariables ex) {
            tiraError = true;
        }
        
        if (!tiraError) {
            System.out.println("La clase TruthTableReader no funciona bien, las variables abc+f son incorrectas, ejemplo 2");
        }
        
        //ejemplo 3
        try {
            ttr = TruthTableReader.read(
                "abcf" + "\n" +
                "00a1" + "\n" +
                "0010" + "\n" +
                "0100" + "\n" +
                "0111" + "\n" +
                "1001" + "\n" +
                "1010" + "\n" +
                "1101" + "\n" +
                "1111"
            );
        } catch (TruthTableReaderWrongTerm ex) {
            tiraError = true;
        } catch (TruthTableReaderWrongVariables ex) {
        }
        
        if (!tiraError) {
            System.out.println("La clase TruthTableReader no funciona bien, el termino 00a1 es incorrecto, ejemplo 3");
        }

        //ejemplo 4
        try {
            ttr = TruthTableReader.read(
                "abcf" + "\n" +
                "00011" + "\n" +
                "0010" + "\n" +
                "0100" + "\n" +
                "0111" + "\n" +
                "1001" + "\n" +
                "1010" + "\n" +
                "1101" + "\n" +
                "1111"
            );      
        } catch (TruthTableReaderWrongTerm ex) {
            tiraError = true;
        } catch (TruthTableReaderWrongVariables ex) {
        }
        
        if (!tiraError) {
            System.out.println("La clase TruthTableReader no funciona bien, el termino 00011 es incorrecto, mas valores que variables, ejemplo 4");
        }

        //ejemplo 5
        try {
            ttr = TruthTableReader.read(
                "abcf" + "\n" +
                "001" + "\n" +
                "0010" + "\n" +
                "0100" + "\n" +
                "0111" + "\n" +
                "1001" + "\n" +
                "1010" + "\n" +
                "1101" + "\n" +
                "1111"
            );      
        } catch (TruthTableReaderWrongTerm ex) {
            tiraError = true;
        } catch (TruthTableReaderWrongVariables ex) {
        }
        
        if (!tiraError) {
            System.out.println("La clase TruthTableReader no funciona bien, el termino 001 es incorrecto, mas valores que variables, ejemplo 5");
        }
        
        //ejemplo 6
        // tiene menos terminos (7 en vez de 8)
        try {
            ttr = TruthTableReader.read(
                "abcf" + "\n" +
                "0001" + "\n" +
                "0010" + "\n" +
                "0100" + "\n" +
                "0111" + "\n" +
                "1001" + "\n" +
                "1010" + "\n" +
                "1101"
            );      
        } catch (TruthTableReaderWrongTerm ex) {
            tiraError = true;
        } catch (TruthTableReaderWrongVariables ex) {
        }
        
        if (!tiraError) {
            System.out.println("La clase TruthTableReader no funciona bien, la cantidad de terminos no es correcta, mas valores que variables, ejemplo 6");
        }

        //ejemplo 7
        try {
            ttr = TruthTableReader.read(
                "abcf" + "\n" +
                "0001" + "\n" +
                "0010" + "\n" +
                "0100" + "\n" +
                "0111" + "\n" +
                "1001" + "\n" +
                "1010" + "\n" +
                "1101" + "\n" +
                "1111"
            );      
        } catch (TruthTableReaderWrongTerm ex) {
            tiraError = true;
        } catch (TruthTableReaderWrongVariables ex) {
        }
        
        if (tiraError) {
            System.out.println("La clase TruthTableReader no funciona bien, ejemplo 6");
        } else if (ttr.getTermsSize() != 5) {
            System.out.println("La clase TruthTableReader no funciona bien, deberia obtener solo 5 terminos, ejemplo 7");
        }
        
        //ejemplo 8
        try {
            ttr = TruthTableReader.read(
                "xyzf" + "\n" +
                "0001" + "\n" +
                "0010" + "\n" +
                "0100" + "\n" +
                "0111" + "\n" +
                "1001" + "\n" +
                "1010" + "\n" +
                "1101" + "\n" +
                "1111"
            );      
        } catch (TruthTableReaderWrongTerm ex) {
            tiraError = true;
        } catch (TruthTableReaderWrongVariables ex) {
        }
        
        if (tiraError) {
            System.out.println("La clase TruthTableReader no funciona bien, ejemplo 6");
        } else if (ttr.getVariables() != "xyz") {
            System.out.println("La clase TruthTableReader no funciona bien, deberia leer las variables xyz, ejemplo 8");
        }
        
        
        //ejemplo 9
        try {
            ttr = TruthTableReader.read(
                "abcf" + "\n" +
                "0001" + "\n" +
                "0010" + "\n" +
                "0100" + "\n" +
                "0111" + "\n" +
                "1001" + "\n" +
                "1010" + "\n" +
                "1101" + "\n" +
                "1111"
            );      
        } catch (TruthTableReaderWrongTerm ex) {
            tiraError = true;
        } catch (TruthTableReaderWrongVariables ex) {
        }
        
        if (tiraError) {
            System.out.println("La clase TruthTableReader no funciona bien, ejemplo 6");
        } else {
            ArrayList<Term> terms = ttr.getTerms();
            if (terms.get(0).getHumanReadable("abcd") != "a' b' c'") {
                System.out.println("La clase TruthTableReader no funciona bien, el termino 0 no esta bien, devolvio " + terms.get(0).getHumanReadable("abcd") + ", ejemplo 9");
            } else if (terms.get(1).getHumanReadable("abcd") != "a' b c") {
                System.out.println("La clase TruthTableReader no funciona bien, el termino 0 no esta bien, devolvio " + terms.get(1).getHumanReadable("abcd") + ", ejemplo 9");
            } else if (terms.get(2).getHumanReadable("abcd") != "a b' c'") {
                System.out.println("La clase TruthTableReader no funciona bien, el termino 0 no esta bien, devolvio " + terms.get(2).getHumanReadable("abcd") + ", ejemplo 9");
            } else if (terms.get(3).getHumanReadable("abcd") != "a b c'") {
                System.out.println("La clase TruthTableReader no funciona bien, el termino 0 no esta bien, devolvio " + terms.get(3).getHumanReadable("abcd") + ", ejemplo 9");
            } else if (terms.get(4).getHumanReadable("abcd") != "a b c") {
                System.out.println("La clase TruthTableReader no funciona bien, el termino 0 no esta bien, devolvio " + terms.get(4).getHumanReadable("abcd") + ", ejemplo 9");
            }
            
        }

    }
}
 