package DFA.algorithmDFA;

import java.util.HashSet;

/**
 * Created on 19.03.2017.
 */
public class DFAPart {

    public Boolean typeChild;
    public Boolean typeTreePart;
    public DFAPart rightChild;
    public DFAPart leftChild;
    public DFAPart singleChild;
    public DFAPart parentPointer;
    public Integer priority;
    public char operatorText;
    public String symbolsText;
    public HashSet<Integer>  firstList;
    public HashSet<Integer>  lastList;
    public Integer nodeNumber;
    public Boolean nullable;
    public Integer controlNumber;

    public DFAPart() {
        leftChild = null;
        rightChild = null;
        singleChild = null;
        parentPointer = null;
        firstList = new HashSet<>();
        lastList = new HashSet<>();
    }
}
