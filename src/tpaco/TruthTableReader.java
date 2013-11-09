/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tpaco;

import java.util.*;

/**
 *
 * @author Federico Prado Macat
 */
public class TruthTableReader {
    private String variableNames;
    private ArrayList<Term> terms;
    
    public TruthTableReader(ArrayList<Term> terms, String variableNames) {
        this.terms = terms;
        this.variableNames = variableNames;
    }
    
    public static TruthTableReader read(String formulas) {
        String parts[] = formulas.split("\\n");
        String variableNames;
        ArrayList<Term> terms = new ArrayList<Term>();
        
        for (int i = 1; i < parts.length; i++) {
            terms.add(Term.read(parts[i]));
        }
        
        variableNames = parts[0];
        return new TruthTableReader(terms, variableNames);
    }
    
    public void printReadable(){
        System.out.println("variables: " + variableNames + "\n" + "terminos: " + terms.toString());
    }
    public void letrasVariables(ArrayList<Term> tablaUsuario){
        
    }
}
