package miniStat;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DimacsCnfReader {

    public static FNC getFNCFromFile(String path) throws IOException {
        int nbVar;
        int nbClause;

        BufferedReader brTest = new BufferedReader(new FileReader(path));
        String text = brTest.readLine();
        String[] strArray = text.split(" ");

        nbVar = Integer.parseInt(strArray[strArray.length-2]);
        nbClause = Integer.parseInt(strArray[strArray.length-1]);

        final List<List<Integer>> clauses = Files.lines(Paths.get(path))
                // Suppression des espaces:
                .map(line -> line.trim().replaceAll("\\s+", " ").trim())
                // traduire seulement pour les lignes finissant par 0:
                .filter(line -> line.endsWith(" 0"))
                // Transformer chaque ligne en liste d'integers:
                .map(line -> Arrays.stream(line
                        .substring(0, line.length() - 2)
                        .trim().split("\\s+"))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList())
                ).collect(Collectors.toList());
        return new FNC(nbVar, nbClause, clauses);
    }

    public static ArrayList<Integer> getAssignmentFromPath(String path) throws IOException {
        BufferedReader brTest = new BufferedReader(new FileReader(path));
        String text = brTest.readLine();
        String[] strArray = text.split(" ");
        ArrayList<Integer> fillings = new ArrayList<>();
        for (int i = 0; i<strArray.length; i++){
            fillings.add(Integer.parseInt(strArray[i]));
        }
        return fillings;
    }
}
