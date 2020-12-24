package EX2;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class GraphToSAT {
    private ArrayList<ArrayList<Integer>> clauses;
    private int nbSommets;
    private int nbVariables;
    private int nbClauses;
    private int k;
    private int[][] graph;

    public GraphToSAT(int [][] graph, int k){
        this.graph = graph;
        this.k = k;
        this.nbSommets = graph.length;
        this.nbVariables = graph.length * k;
        this.nbClauses = 0;
        this.clauses = new ArrayList<>();
    }
    public void process() throws FileNotFoundException {
        //O(n * k)
        firstConstraint();
        //O(n * k^2)
        secondConstraint();
        //O(n^2 * k^2)
        thirdConstraint();
    }

    public void export(String path) throws FileNotFoundException{
        PrintWriter writer = new PrintWriter(path);
        writer.println("p cnf " + nbVariables + " " + nbClauses);
        for(ArrayList<Integer> clause : clauses){
            StringBuilder stringBuilder = new StringBuilder();
            for(Integer integer : clause){
                stringBuilder.append(String.valueOf(integer) + " ");
            }
            stringBuilder.append("0");

            writer.println(stringBuilder.toString());
        }
        writer.close();
    }

    //O(n * k)
    private void firstConstraint(){
        for (int i = 0; i < k; i++) {
            ArrayList<Integer> clause = new ArrayList<>();
            for (int j = 0; j < nbSommets; j++) {
                // +1 pour que les indices commencent à 0
                clause.add(j*k + i +1);
            }
            clauses.add(clause);
            nbClauses++;
        }
    }
    //O(n * k^2)
    private void secondConstraint(){
        for (int i = 0; i < nbSommets; i++) {
            for (int j = 0; j < k; j++) {
                for (int l = 0; l < k; l++) {
                    if(j < l){
                        ArrayList<Integer> clause = new ArrayList<>();
                        clause.add(-(i*k+j+1));
                        clause.add(-(i*k+l+1));
                        clauses.add(clause);
                        nbClauses++;
                    }
                }
            }
        }
    }
    //O(n^2 * k^2)
    private void thirdConstraint(){
        for (int i = 0; i < nbSommets; i++) {
            for (int j = 0; j < k; j++) {
                int indA = i*k+j+1;
                for (int l = 0; l < nbSommets; l++) {
                    for (int m = 0; m < k; m++) {
                        int indB = l*k+m+1;
                        if(i < l && j != m && graph[i][l] == 1){
                            ArrayList<Integer> clause = new ArrayList<>();
                            clause.add(-indA);
                            clause.add(-indB);
                            clauses.add(clause);
                            nbClauses++;
                        }
                    }
                }
            }
        }
    }

    public void printClauses(){
        for(ArrayList<Integer> clause : clauses){
            System.out.println(clause);
        }
    }
}