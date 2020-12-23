package EX3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TxtToMat {

    public static int[][] convert(String path){
        BufferedReader brTest = null;
        String line = null;

        try {
            brTest = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            line = brTest.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int[][] result = new int[line.length()][line.length()];
        int i = 0;
        do{
            for (int j = 0; j < line.length(); j++) {
                result[i][j] = line.charAt(j) - '0';
            }
            try {
                line = brTest.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            i++;
        }while (line != null);

        return result;
    }

    public static void printMat(int[][] mat){
        for (int i = 0; i < mat.length; i++) {
            System.out.println();
            for (int j = 0; j < mat.length; j++) {
                System.out.print(mat[i][j]);
            }
        }
    }
}