package Test;

import miniStat.DimacsCnfReader;
import miniStat.FNC;

import java.io.IOException;
import java.util.ArrayList;

public class TestFileReading {
    public static void main(String[] args) {
        String pathFormula = "DIMACS.CNF";
        FNC fnc = null;
        try {
            fnc = DimacsCnfReader.getFNCFromFile(pathFormula);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String pathAssignment = "Assignments.CNF";
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
}
