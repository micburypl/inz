package algorithmFLF;

import java.util.HashSet;

/**
 * Created on 19.03.2017.
 */
public class FLFPart {

    public Boolean typeChild;
    public Boolean typeTreePart;
    public FLFPart rightChild;
    public FLFPart leftChild;
    public FLFPart singleChild;
    public FLFPart parentPointer;
    public Integer priority;
    public String operatorText;
    public String symbolsText;
    public HashSet<Integer>  firstList;
    public HashSet<Integer>  lastList;
    public Integer nodeNumber;
    public Boolean nullable;
    public Integer controlNumber;

    public FLFPart() {
        leftChild = null;
        rightChild = null;
        singleChild = null;
        parentPointer = null;
        firstList = new HashSet<>();
        lastList = new HashSet<>();
    }
}
