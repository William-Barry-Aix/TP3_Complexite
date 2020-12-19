package miniStat;

import java.util.*;

public class FNC {
    public int nbVar;
    public int nbClause;
    public List<List<Integer>> clauses;

    public FNC(int nbVar, int nbClause, List<List<Integer>> clauses){
        this.nbVar = nbVar;
        this.nbClause = nbClause;
        this.clauses = clauses;
    }

    public boolean testAssignment(ArrayList<Integer> fillings){
        List<List<Integer>> clausesTmp = new ArrayList<>(clauses);

        System.out.println(clausesTmp);
        for (int var = 0; var < fillings.size(); var++){
            for (int clauseId = 0; clauseId < clausesTmp.size(); clauseId++){
                List<Integer> clause = clausesTmp.get(clauseId);
                for (int varId = 0; varId < clause.size(); varId++){
                    if (Math.abs(fillings.get(var))  == Math.abs(clause.get(varId))){
                        if (fillings.get(var) * clause.get(varId) < 0){
                            clausesTmp.get(clauseId).set(varId, 0);
                        }
                    }
                }
            }
        }
        System.out.println(clausesTmp);
        for (int clauseId = 0; clauseId < nbClause; clauseId++){
            int cpt = 0;
            List<Integer> clause = clausesTmp.get(clauseId);
            for (int varId = 0; varId < clause.size(); varId++){
                if (clause.get(varId) != 0){
                    cpt++;
                }
            }
            if (cpt == 0) return false;
        }
        return true;
    }
}
