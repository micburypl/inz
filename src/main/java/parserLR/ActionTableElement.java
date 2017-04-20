package parserLR;

/**
 * Created on 21.04.2017.
 */
public class ActionTableElement {
    public Integer value;
    public Boolean isShift;

    ActionTableElement(Integer v, Boolean iS) {
        value = v;
        isShift = iS;
    }
}
