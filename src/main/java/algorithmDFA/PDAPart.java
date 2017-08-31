package algorithmDFA;

import java.util.HashSet;

/**
 * Created on 19.03.2017.
 */
public class PDAPart {

    public Boolean typeChild;
    public Boolean typeTreePart;
    public PDAPart rightChild;
    public PDAPart leftChild;
    public PDAPart singleChild;
    public PDAPart parentPointer;
    public Integer priority;
    public char operatorText;
    public String symbolsText;
    public HashSet<Integer>  firstList;
    public HashSet<Integer>  lastList;
    public Integer nodeNumber;
    public Boolean nullable;
    public Integer controlNumber;

    public PDAPart() {
        leftChild = null;
        rightChild = null;
        singleChild = null;
        parentPointer = null;
        firstList = new HashSet<>();
        lastList = new HashSet<>();
    }
}
