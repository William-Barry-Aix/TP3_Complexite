package EX3;

import EX3.TxtToMat;
import org.w3c.dom.ls.LSOutput;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class FileReader {
    public static void main(String[] args) throws IOException {
        String pathFormula = "test6";
        String path2 = "affectation.txt";
        int[][] mat = TxtToMat.convert(pathFormula);
        TxtToMat.printMat(mat);
        System.out.println("\nTaille de matrice : " + mat.length + "*" + mat.length);

        long reductionStartTime = System.nanoTime();
        System.out.println("\nTranslating into SAT...\n");
        MatToSAT sat = new MatToSAT(mat);
        sat.process();
        sat.export();
        long reductionEndTime = System.nanoTime();
        sat.printClauses();



        System.out.println("\n...Done");



        System.out.println("\nResovling SAT...\n");
        long sovlerStartTime = System.nanoTime();
        Process p;
        p = Runtime.getRuntime().exec("./MiniSat_v1.14_linux DIMACSMat.CNF affectation.txt");
        try {
            p.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        p.destroy();

        long solverEndTime = System.nanoTime();

        DIMACSMatToMat.convert(path2);

        System.out.println("\n...Done");

        double reductionDuration = (reductionEndTime - reductionStartTime)/1000000000.0;
        double sovlingDuration = (solverEndTime - sovlerStartTime)/1000000000.0;
        System.out.println("Reduction Duration: " + reductionDuration + "s");
        System.out.println("Solving Duration: " + sovlingDuration + "s");
        System.out.println("Total Duration: " + (reductionDuration + sovlingDuration) + "s");


    }
}
