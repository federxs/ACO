/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tpaco;

import java.util.*;
import java.io.*;

/**
 *
 * @author Federico Prado Macat
 */
public class Formula {

    private List<Term> termList;
    private List<Term> originalTermList;
    private String variables;

    public Formula(List<Term> termList) {
        this.termList = termList;
    }

    @Override
    public String toString() {
        String result = "";
        result += termList.size() + " terminos, " + termList.get(0).getNumVars() + " variables\n";
        for (int i = 0; i < termList.size(); i++) {
            result += termList.get(i) + "\n";
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public void reduceToPrimeImplicants() {
        originalTermList = new ArrayList<Term>(termList);
        int numVars = termList.get(0).getNumVars();
        ArrayList<Term>[][] table = new ArrayList[numVars + 1][numVars + 1];
        for (int dontKnows = 0; dontKnows <= numVars; dontKnows++) {
            for (int ones = 0; ones <= numVars; ones++) {
                table[dontKnows][ones] = new ArrayList<Term>();
            }
        }
        for (int i = 0; i < termList.size(); i++) {
            int dontCares = termList.get(i).countValues(Term.DontCare);
            int ones = termList.get(i).countValues((byte) 1);
            table[dontCares][ones].add(termList.get(i));
        }
        for (int dontKnows = 0; dontKnows <= numVars - 1; dontKnows++) {
            for (int ones = 0; ones <= numVars - 1; ones++) {
                ArrayList<Term> left = table[dontKnows][ones];
                ArrayList<Term> right = table[dontKnows][ones + 1];
                ArrayList<Term> out = table[dontKnows + 1][ones];
                for (int leftIdx = 0; leftIdx < left.size(); leftIdx++) {
                    for (int rightIdx = 0; rightIdx < right.size(); rightIdx++) {
                        Term combined = left.get(leftIdx).combine(right.get(rightIdx));
                        if (combined != null) {
                            if (!out.contains(combined)) {
                                out.add(combined);
                            }
                            termList.remove(left.get(leftIdx));
                            termList.remove(right.get(rightIdx));
                            if (!termList.contains(combined)) {
                                termList.add(combined);
                            }
                        }
                    }
                }
            }
        }
    }

    public void reducePrimeImplicantsToSubset() {
        int numPrimeImplicants = termList.size();
        int numOriginalTerms = originalTermList.size();
        boolean[][] table = new boolean[numPrimeImplicants][numOriginalTerms];
        for (int impl = 0; impl < numPrimeImplicants; impl++) {
            for (int term = 0; term < numOriginalTerms; term++) {
                table[impl][term] = termList.get(impl).implies(originalTermList.get(term));
            }
        }
        ArrayList<Term> newTermList = new ArrayList<Term>();
        boolean done = false;
        int impl;
        while (!done) {
            impl = extractEssentialImplicant(table);
            if (impl != -1) {
                newTermList.add(termList.get(impl));
            } else {
                impl = extractLargestImplicant(table);
                if (impl != -1) {
                    newTermList.add(termList.get(impl));
                } else {
                    done = true;
                }
            }
        }
        termList = newTermList;
    }

    public static Formula read(Reader reader) throws IOException {
        ArrayList<Term> terms = new ArrayList<Term>();
        Term term;
        while ((term = Term.read(reader)) != null) {
            terms.add(term);
        }
        return new Formula(terms);
    }

    public static Formula read(String formulas) {
        String parts[] = formulas.split("\\n");
        ArrayList<Term> terms = new ArrayList<Term>();

        for (int i = 0; i < parts.length; i++) {
            terms.add(Term.read(parts[i]));
        }

        return new Formula(terms);
    }

    private int extractEssentialImplicant(boolean[][] table) {
        for (int term = 0; term < table[0].length; term++) {
            int lastImplFound = -1;
            for (int impl = 0; impl < table.length; impl++) {
                if (table[impl][term]) {
                    if (lastImplFound == -1) {
                        lastImplFound = impl;
                    } else {
                        // This term has multiple implications
                        lastImplFound = -1;
                        break;
                    }
                }
            }
            if (lastImplFound != -1) {
                extractImplicant(table, lastImplFound);
                return lastImplFound;
            }
        }
        return -1;
    }

    private void extractImplicant(boolean[][] table, int impl) {
        for (int term = 0; term < table[0].length; term++) {
            if (table[impl][term]) {
                for (int impl2 = 0; impl2 < table.length; impl2++) {
                    table[impl2][term] = false;
                }
            }
        }
    }

    private int extractLargestImplicant(boolean[][] table) {
        int maxNumTerms = 0;
        int maxNumTermsImpl = -1;
        for (int impl = 0; impl < table.length; impl++) {
            int numTerms = 0;
            for (int term = 0; term < table[0].length; term++) {
                if (table[impl][term]) {
                    numTerms++;
                }
            }
            if (numTerms > maxNumTerms) {
                maxNumTerms = numTerms;
                maxNumTermsImpl = impl;
            }
        }
        if (maxNumTermsImpl != -1) {
            extractImplicant(table, maxNumTermsImpl);
            return maxNumTermsImpl;
        }
        return -1;
    }

    public String getHumanReadable() {
        //convertir esto:
        String result = "";
        for (int i = 0; i < termList.size(); i++) {
            result += termList.get(i).getHumanReadable(this.getVariables()) + ((i + 1) == termList.size() ? "" : " + ");
        }
        if(result.compareToIgnoreCase("") == 0){
            result = "1";
        }
        return result;
    }

    public void setVariables(String variables) {
        this.variables = variables;
    }

    public String getVariables() {
        return variables.substring(0, this.termList.get(0).getNumVars());
    }

//    public void terminosDerivados(){
//        StringBuilder r = new StringBuilder();
//        for (int i = 0; i < termList.size(); i++) {
//            r.append("\nEl termino "+termList.get(i)+" proviene de:");
//            for (int j = 0; j < originalTermList.size(); j++) {
//                if (termList.get(i).implies(originalTermList.get(j)) == true) {
//                    r.append("\n"+originalTermList.get(j));   
//                }
//            }
//        }
//        System.out.println(r.toString());
//    }
    public String derivedTerms() {
        StringBuilder r = new StringBuilder();
        List<Term> newTerms;
        Formula newFormula, termFormula;
        int i;
        for (i = 0; i < termList.size(); i++) {
            newTerms = new ArrayList<Term>();
            for (int j = 0; j < originalTermList.size(); j++) {
                if (termList.get(i).implies(originalTermList.get(j)) == true) {
                    newTerms.add(originalTermList.get(j));
                }
            }
            newFormula = new Formula(newTerms);
            newFormula.variables = variables;
            newTerms = new ArrayList<Term>();
            newTerms.add(termList.get(i));
            termFormula = new Formula(newTerms);
            termFormula.variables = variables;
            r.append("\nEl termino (" + termFormula.getHumanReadable() + ") proviene de: \n" + newFormula.getHumanReadable());
        }
        return r.toString();
    }
}
