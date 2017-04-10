package other;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by DELL6430u on 2017-03-20.
 */
public class TransitionTableElement {
    String beginState;
    String word;
    HashSet<Integer> numberSet;
    String endState;

    TransitionTableElement(String bS, String w, HashSet<Integer> nS, String eS){
        beginState = bS;
        word = w;
        numberSet = nS;
        endState = eS;
    }
}
