import java.util.ArrayList;
import java.util.List;

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
        List<Integer> firstList;
        List<Integer> lastList;
        List<Integer> followList;
        Integer nodeNumber;
        Boolean nullable;

        public FLFPart() {
            leftChild = null;
            rightChild = null;
            singleChild = null;
            parentPointer = null;
            firstList = new ArrayList<Integer>();
            lastList = new ArrayList<Integer>();
            followList = new ArrayList<Integer>();
        }
}
