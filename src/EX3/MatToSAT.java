package EX3;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class MatToSAT {
    private ArrayList<ArrayList<Integer>> clauses;
    private int n;
    private int nbVariables;
    private int nbClauses;
    private int[][] mat;

    public MatToSAT(int [][] mat){
        this.mat = mat;
        this.n = (int)Math.sqrt(mat.length);
        this.nbVariables = mat.length * mat.length * mat.length;
        this.nbClauses = 0;
        this.clauses = new ArrayList<>();
    }

    public void process(){
        //O(n^6 + n^8)
        allDigitOnRowConstraint();
        //O(n^6 + n^8)
        allDigitOnColConstraint();
        //O(n^6 + n^8)
        allDigitOnSquareConstraint();
        //O(n^6 + n^8)
        oneDigitPerCellConstraint();
        //O(n^4)
        allReadyHereConstraint();
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

    private int coordToCell(int i, int j, int k){
        return ((i*n*n*n*n)+(j*n*n)+k+1);
    }

    // O(n^6 + n^8)
    public void oneDigitPerCellConstraint() {
        for (int i = 0; i < n * n; i++) {
            for (int j = 0; j < n * n; j++) {
                ArrayList<Integer> clause = new ArrayList<>();
                for (int k = 0; k < n * n; k++) {
                    clause.add(coordToCell(i, j, k));
                }
                clauses.add(clause);
                nbClauses++;
            }
        }

        for (int i = 0; i < n * n; i++) {
            for (int j = 0; j < n * n; j++) {
                for (int k1 = 0; k1 < n * n; k1++) {
                    for (int k2 = k1 + 1; k2 < n * n; k2++) {
                        ArrayList<Integer> clause = new ArrayList<>();
                        clause.add(-coordToCell(i, j, k1));
                        clause.add(-coordToCell(i, j, k2));
                        clauses.add(clause);
                        nbClauses++;
                    }
                }
            }
        }
    }

    /** Toutes les valeurs doient être représentées dans chaques lignes O(n^6 + n^8)**/
    public void allDigitOnRowConstraint(){
        //Au moins une fois chaque valeur par ligne
        for (int i = 0; i < n*n ; i++) {
            for (int k = 0; k < n*n; k++) {
                ArrayList<Integer> clause = new ArrayList<>();
                for (int j = 0; j < n*n; j++) {
                    clause.add(coordToCell(i,j,k));
                }
                clauses.add(clause);
                nbClauses++;
            }
        }

        //Au plus une fois chaque valeur par ligne
        for (int i = 0; i < n*n; i++) {
            for (int k = 0; k < n*n; k++) {
                for (int j1 = 0; j1 < n*n; j1++) {
                    //NOTE SI SOUCIS ENLEVER LE +1 ET RAJOUTER j1 != j2
                    for (int j2 = j1+1; j2 < n*n; j2++) {
                        ArrayList<Integer> clause = new ArrayList<>();
                        clause.add(-coordToCell(i,j1,k));
                        clause.add(-coordToCell(i,j2,k));
                        clauses.add(clause);
                        nbClauses++;
                    }
                }
            }
        }
    }

    /** Toutes les valeurs doient être représentées dans chaques colonnes O(n^6 + n^8)**/
    public void allDigitOnColConstraint(){
        //Au moins une fois chaque valeur par colonne
        for (int j = 0; j < n*n ; j++) {
            for (int k = 0; k < n*n; k++) {
                ArrayList<Integer> clause = new ArrayList<>();
                for (int i = 0; i < n*n; i++) {
                    clause.add(coordToCell(i,j,k));
                }
                clauses.add(clause);
                nbClauses++;
            }
        }
        //Au plus une fois chaque valeur par colonne
        for (int j = 0; j < n*n; j++) {
            for (int k = 0; k < n*n; k++) {
                for (int i1 = 0; i1 < n*n; i1++) {
                    for (int i2 = i1+1; i2 < n*n; i2++) {
                        ArrayList<Integer> clause = new ArrayList<>();
                        clause.add(-coordToCell(i1,j,k));
                        clause.add(-coordToCell(i2,j,k));
                        clauses.add(clause);
                        nbClauses++;
                    }
                }
            }
        }
    }

    /** Toutes les valeurs doient être représentées dans chaques case de n*n  O(n^6 + n^8)**/
    public void allDigitOnSquareConstraint() {

        //Au moins une fois chaque valeur par carré de n*n
        for (int offSetI = 0; offSetI < n; offSetI++) {
            for (int offSetJ = 0; offSetJ < n; offSetJ++) {
                for (int k = 0; k < n * n; k++) {
                    ArrayList<Integer> clause = new ArrayList<>();
                    for (int i = 0; i < n; i++) {
                        for (int j = 0; j < n; j++) {
                            clause.add(coordToCell(i + offSetI * n, j + offSetJ * n, k));
                        }
                    }
                    clauses.add(clause);
                    nbClauses++;
                }
            }
        }
        //Au plus une fois chaque valeur par carré de n*n
        for (int k = 0; k < n * n; k++) {
            for (int offSetI = 0; offSetI < n; offSetI++) {
                for (int offSetJ = 0; offSetJ < n; offSetJ++) {
                    for (int i1 = 0; i1 < n; i1++) {
                        for (int j1 = 0; j1 < n; j1++) {
                            for (int i2 = i1; i2 < n; i2++) {
                                for (int j2 = 0; j2 < n; j2++) {
                                    ArrayList<Integer> clause = new ArrayList<>();
                                    if (i1 != i2 || j1 != j2) {
                                        int ci1 = i1 + offSetI * n;
                                        int cj1 = j1 + offSetJ * n;
                                        int ci2 = i2 + offSetI * n;
                                        int cj2 = j2 + offSetJ * n;
                                        clause.add(-coordToCell(ci1, cj1, k));
                                        clause.add(-coordToCell(ci2, cj2, k));
                                        clauses.add(clause);
                                        nbClauses++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    //O(n^4)
    private void allReadyHereConstraint(){
        for (int i = 0; i < n*n; i++) {
            for (int j = 0; j < n*n; j++) {
                if(mat[i][j]!=0){
                    ArrayList<Integer> clause = new ArrayList<>();
                    clause.add(coordToCell(i,j,mat[i][j]-1));
                    clauses.add(clause);
                    nbClauses++;
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
