package parserLR;

import java.util.List;

/**
 * Created on 21.04.2017.
 */
public class MovesElementLR {
    Integer movesNumber;
    List<String> stack;
    List<String> input;

    MovesElementLR  (Integer mN, List<String> s, List<String> i) {
        movesNumber = mN;
        stack = s;
        input = i;

    }
}
