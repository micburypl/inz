package algorithmFLF;

import java.util.HashSet;

/**
 * Created on 10.05.2017.
 */
public class TransitionSolverElement {


    public String currentState;
    public String currentWord;
    public String endState;
    public Boolean isNormalElement;

    TransitionSolverElement(String bS, String w, String eS,  Boolean iNE){
        currentState = bS;
        currentWord = w;
        endState = eS;
        isNormalElement = iNE;
    }
}
