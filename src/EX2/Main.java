package EX2;

import miniStat.DimacsCnfReader;
import miniStat.FNC;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        int[][] graph = TxtToGraph.convert("graph.txt");
        printGraph(graph);
        GraphToSAT graphToSAT = new GraphToSAT(graph, 3);
        try {
            graphToSAT.process();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        graphToSAT.printClauses();
        try {
            graphToSAT.export();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        testResult();
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
