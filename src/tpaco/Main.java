/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tpaco;

import java.io.IOException;

/**
 *
 * @author mauricio
 */
public class Main {

    public static void main(String[] args) throws IOException {
        testFormula();
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
}
