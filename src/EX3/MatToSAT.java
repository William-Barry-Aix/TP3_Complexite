package EX3;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class MatToSAT {
    private ArrayList<ArrayList<Integer>> clauses;
    private int lengthN;
    private int nbVariables;
    private int nbClauses;
    private int[][] mat;

    public MatToSAT(int [][] mat){
        this.mat = mat;
        this.lengthN = mat.length;
        this.nbVariables = lengthN * lengthN * lengthN;
        this.nbClauses = 0;
        this.clauses = new ArrayList<>();
    }

    public void export() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter("DIMACSMat.CNF");
        writer.println("p cnf " + nbVariables + " " + nbClauses);
        for (ArrayList<Integer> clause : clauses) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Integer integer : clause) {
                stringBuilder.append(String.valueOf(integer) + " ");
            }
            stringBuilder.append("0");

            writer.println(stringBuilder.toString());
        }
        writer.close();
    }

    public void printClauses(){
        for(ArrayList<Integer> clause : clauses){
            System.out.println(clause);
        }
    }

}
