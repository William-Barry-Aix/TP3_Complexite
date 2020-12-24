package EX3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**pour vérifier nos résultats**/

public class DIMACSMatToMat {

    public static void convert(String path) throws IOException {
        ArrayList<Integer> sudoku = new ArrayList<>();
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
        line = brTest.readLine();
        do {
            for (int i = 0; i < line.length(); i++) {
                if(line.charAt(i)==' '){
                    continue;
                }

                if(line.charAt(i)=='-'){
                    boolean flag=false;
                    while (line.charAt(i)!=' ' && !flag){
                        i++;
                        if(i==line.length()-1) flag=true;
                    }
                }

                if(line.charAt(i)==' '){
                    continue;
                }

                if((int) line.charAt(i)>0 && i<line.length()-1){
                    String test= ("");
                    while(line.charAt(i)!=' '){
                        test=test + line.charAt(i);
                        i++;
                    }
                    int valeur = Integer.parseInt(test);
                    sudoku.add(valeur);
                }
            }
            line=brTest.readLine();
        }
        while(line != null);
        int[][] mat = new int[(int)Math.sqrt(sudoku.size())][(int)Math.sqrt(sudoku.size())];

        int curseur=0;
        System.out.println();
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat.length; j++) {
                if(sudoku.get(curseur)%mat.length==0)  mat[i][j] = mat.length;
                else mat[i][j] = (sudoku.get(curseur)%mat.length);
                System.out.print(mat[i][j] + " ");
                curseur++;
            }
            System.out.println();
        }
    }
}

