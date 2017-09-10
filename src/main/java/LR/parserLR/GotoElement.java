package LR.parserLR;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by DELL6430u on 2017-04-16.
 */
public class GotoElement {
    public Set<Integer> closureElementList;

    public GotoElement() {

        closureElementList = new HashSet<>();
    }
}
