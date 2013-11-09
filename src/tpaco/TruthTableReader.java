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
        int cantVars;
        ArrayList<Term> terms = new ArrayList<Term>();

        //guardamos variables
        variableNames = parts[0];

        //validamos variables
        cantVars = (variableNames.length() - 1);
        
        if (!variableNames.matches("^[a-zA-Z]+$")) {
            throw new TruthTableReaderWrongVariables("1 o mas variables no es una letra");
        }

        if (variableNames.charAt((variableNames.length() - 1)) != 'f') {
            throw new TruthTableReaderWrongVariables("El valor de la funcion no esta representado con la letra 'f',se utiliza la letra " + variableNames.charAt((variableNames.length() - 1)));
        }

        //sacamos la f del final
        variableNames = variableNames.substring(0, variableNames.length() - 1);

        //guardamos cada termino que es 1
        Term term;
        String line;
        for (int i = 1; i < parts.length; i++) {
            line = parts[i].trim();

            //fijarte si no es uno el valor de f
            if (line.charAt(line.length() - 1) != '1') {
                //validacion tamaño de cada termino
                if (line.length() != parts[0].length()) {
                    throw new TruthTableReaderWrongTerm("Tamaño de los terminos incorrecto, se encontraron " + line.length() + " para el termino nº " + i + " '" + line + "'");
                }
                continue;
            }
            
            //sacamos el 1 del final
            line = line.substring(0, line.length() - 1);

            term = Term.read(line);
            //validacion tamaño de cada termino
            if (term.getSize() != variableNames.length()) {
                throw new TruthTableReaderWrongTerm("Tamaño de los terminos incorrecto, se encontraron " + term.getSize() + " para el termino nº " + i + " '" + line + "'");
            }
            terms.add(term);
        }

        //validacion cantidad de terminos
        if ((parts.length - 1) != Math.pow(2, cantVars)) {
            throw new TruthTableReaderWrongTerm("Cantidad de terminos incorrecta");
        }


        return new TruthTableReader(terms, variableNames);
    }

    public void printReadable() {
        System.out.println("variables: " + variableNames + "\n" + "terminos: " + terms.toString());
    }

    public void letrasVariables(ArrayList<Term> tablaUsuario) {
    }
}
