package algorithmFLF;

import java.util.HashSet;

/**
 * Created by DELL6430u on 2017-03-20.
 */
public class TransitionTableElement {
    public String beginState;
    public String word;
    HashSet<Integer> numberSet;
    public String endState;

    TransitionTableElement(String bS, String w, HashSet<Integer> nS, String eS){
        beginState = bS;
        word = w;
        numberSet = nS;
        endState = eS;
    }
}
