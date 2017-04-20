package parserLR;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
