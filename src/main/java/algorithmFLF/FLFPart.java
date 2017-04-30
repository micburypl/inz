package algorithmFLF;

import java.util.HashSet;

/**
 * Created on 19.03.2017.
 */
public class FLFPart {

        Boolean typeChild;
        Boolean typeTreePart;
        FLFPart rightChild;
        FLFPart leftChild;
        FLFPart singleChild;
        FLFPart parentPointer;
        Integer priority;
        String operatorText;
        String symbolsText;
        HashSet<Integer>  firstList;
        HashSet<Integer>  lastList;
        Integer nodeNumber;
        Boolean nullable;

        public FLFPart() {
            leftChild = null;
            rightChild = null;
            singleChild = null;
            parentPointer = null;
            firstList = new HashSet<>();
            lastList = new HashSet<>();
        }
}
