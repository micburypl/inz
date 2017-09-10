package DFA.algorithmDFA;

import java.util.HashSet;

public class TransitionTableElement {
    public String beginState;
    public String word;
    public HashSet<Integer> numberSet;
    public String endState;

    TransitionTableElement(String bS, String w, HashSet<Integer> nS, String eS){
        beginState = bS;
        word = w;
        numberSet = nS;
        endState = eS;
    }
}
