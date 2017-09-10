package other;

/**
 * Created on 14.03.2017.
 */
public class TreePart {

    Boolean typeChild;
    Boolean typeTreePart;
    TreePart rightChild;
    TreePart leftChild;
    TreePart singleChild;
    TreePart parentPointer;
    Integer priority;
    String operatorText;
    String symbolsText;

    public TreePart() {
        leftChild = null;
        rightChild = null;
        singleChild = null;
        parentPointer = null;
    }
}
