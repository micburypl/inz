package parserLR;

import java.util.List;

/**
 * Created on 21.04.2017.
 */
public class MovesElementLR {
    public Integer movesNumber;
    public List<String> stack;
    public List<String> input;

    MovesElementLR  (Integer mN, List<String> s, List<String> i) {
        movesNumber = mN;
        stack = s;
        input = i;

    }
}
