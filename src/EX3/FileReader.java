package EX3;

import java.io.FileNotFoundException;

public class FileReader {
    public static void main(String[] args) throws FileNotFoundException {
        String pathFormula = "test2";
        int[][] mat = TxtToMat.convert(pathFormula);
        TxtToMat.printMat(mat);
        System.out.println("\nTaille de matrice : " + mat.length + "*" + mat.length);

        System.out.println("\nTranslating into SAT...\n");
        MatToSAT sat = new MatToSAT(mat);
        sat.process();
        sat.export();
        sat.printClauses();
        System.out.println("\n...Done");



        System.out.println("\nResovling SAT...\n");

        System.out.println("\n...Done");
    }
}
