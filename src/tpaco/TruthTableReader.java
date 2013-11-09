/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tpaco;

import java.util.*;
import tpaco.exception.*;

/**
 *
 * @author Federico Prado Macat
 */
public class TruthTableReader {
    private String variableNames;
    //guardar solo los que son 1
    private ArrayList<Term> terms;
    
    public TruthTableReader(ArrayList<Term> terms, String variableNames) {
        this.terms = terms;
        this.variableNames = variableNames;
    }
    
    public String getVariables() {
        return this.variableNames;
    }
    
    public ArrayList<Term> getTerms() {
        return this.terms;
    }
    
    public int getTermsSize() {
        return this.terms.size();
    }
    
    public static TruthTableReader read(String formulas) 
            throws TruthTableReaderWrongTerm, TruthTableReaderWrongVariables {

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
