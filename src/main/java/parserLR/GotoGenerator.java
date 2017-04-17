package parserLR;

import commonUtility.CommonUtility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DELL6430u on 2017-04-16.
 */
public class GotoGenerator {
    public HashMap<Integer, GotoElement> gotoElementMap;
    public ArrayList<GotoTransiiton> gotoTransitionList;
    public HashMap<Integer, ClosureElement> closureElementCombination;
    private Integer closureElementLastCombination; // values of last created combinaiton
    private Integer gotoLastElement;

    public GotoGenerator () {
        gotoElementMap = new HashMap<>();
        gotoTransitionList = new ArrayList<>();
        closureElementCombination = new HashMap<>();
        closureElementLastCombination = 1;
        gotoLastElement = 1;
    }

    public void generateGoto(String startElement, Map<String, ArrayList<ArrayList<String>>> parsedSet) {
        GotoElement currentGotoElement = new GotoElement();
        ClosureElement currentClosureElement;
        ClosureElement nextClosureElement;
        //First Add S' as first element to elementCombination
        currentClosureElement = new ClosureElement(closureElementLastCombination++, CommonUtility.generateList(CommonUtility.beginGoto, CommonUtility.arrow, CommonUtility.dot, startElement));
        nextClosureElement = new ClosureElement(closureElementLastCombination++, CommonUtility.generateList(CommonUtility.beginGoto, CommonUtility.arrow, startElement, CommonUtility.dot));
        //add closure element to first goto
        currentGotoElement.closureElementList.add(currentClosureElement.counterValue);
        // set in first element info about second
        currentClosureElement.nextElement = nextClosureElement.counterValue;
        // S' -> . S
        closureElementCombination.put(currentClosureElement.counterValue, currentClosureElement);
        // S' -> S .
        closureElementCombination.put(nextClosureElement.counterValue, nextClosureElement);






        return;
    }

}
