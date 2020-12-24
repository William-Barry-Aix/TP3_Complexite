package EX2;

import miniStat.DimacsCnfReader;
import miniStat.FNC;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        long startTime = System.nanoTime();
        int[][] graph = TxtToGraph.convert("graph3.txt");
        String path = "DIMACSGraph.CNF";
        printGraph(graph);
        GraphToSAT graphToSAT = new GraphToSAT(graph, 6);
        try {
            graphToSAT.process();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        graphToSAT.printClauses();
        try {
            graphToSAT.export(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Process p;
        p = Runtime.getRuntime().exec("./MiniSat_v1.14_linux DIMACSGraph.CNF affectationGraph.txt");
        try {
            p.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        p.destroy();

        long endTime = System.nanoTime();

        System.out.println("Duration: " + (endTime - startTime)/1000000000.0);

    }

    public static void testResult(){
        String pathFormula = "DIMACSGraph.CNF";
        FNC fnc = null;
        try {
            fnc = DimacsCnfReader.getFNCFromFile(pathFormula);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String pathAssignment = "AssignmentsGraph.CNF";
        ArrayList<Integer> assignments = new ArrayList<>();
        try {
            assignments = DimacsCnfReader.getAssignmentFromPath(pathAssignment);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert fnc != null;
        assert assignments.size() != 0;

        boolean res = fnc.testAssignment(assignments);
        System.out.println(res);
    }

    public static void printGraph(int[][] graph){
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph.length; j++) {
                System.out.printf("%d ", graph[i][j]);
            }
            System.out.println();
        }
    }
}
