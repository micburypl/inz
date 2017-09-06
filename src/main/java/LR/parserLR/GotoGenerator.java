package LR.parserLR;

import commonUtility.CommonUtility;

import java.util.*;

/**
 * Created by DELL6430u on 2017-04-16.
 */
public class GotoGenerator {

    public HashMap<Integer, GotoElement> gotoElementMap;
    //transition list to graph
    public ArrayList<GotoTransition> gotoTransitionList;
    //all possibility combination of production with dots
    public HashMap<Integer, ClosureElement> closureElementCombination;
    //map with first elements of all elements
    public HashMap<String, ArrayList<Integer>> firstElementMap;
    // values of last created combination
    private Integer closureElementLastCombination;
    //information about which number is last element last element
    private Integer gotoLastElement;
    //temp parameter
    private ArrayList<String> tempArrayString;
    //all parameters ( S, a, *, etc.)
    private Set<String> allParameters;

    ClosureElement correctFinal;

    public Map<Integer, ArrayList<String>> listOfProduction;

    GotoElement currentGotoElement;

    public GotoGenerator () {
        gotoElementMap = new HashMap<>();
        gotoTransitionList = new ArrayList<>();
        closureElementCombination = new HashMap<>();
        firstElementMap = new HashMap<>();
        closureElementLastCombination = 1;
        currentGotoElement = new GotoElement();
        allParameters = new HashSet<>();
        gotoLastElement = 0;
        listOfProduction = new HashMap<>();


        //gotoLastElement = 1;
    }

    public void generateGoto(String startElement, Map<String, ArrayList<ArrayList<String>>> parsedSet) {
        GotoElement currentGotoElement = new GotoElement();
        ClosureElement currentClosureElement;
        ClosureElement nextClosureElement;
        GotoTransition tempGotoTransition;

        Integer currProdNumber = 1;
        //First Add S' as first element to elementCombination
        // S' -> . S
        currentClosureElement = new ClosureElement(closureElementLastCombination++, CommonUtility.generateList(CommonUtility.beginGoto, CommonUtility.arrow, CommonUtility.dot, startElement), 0);
        // S' -> S .
        nextClosureElement = new ClosureElement(closureElementLastCombination++, CommonUtility.generateList(CommonUtility.beginGoto, CommonUtility.arrow, startElement, CommonUtility.dot), 0);
        correctFinal = nextClosureElement;
        //nextClosureElement.isEnd = true;
        //add closure element to first goto
        currentGotoElement.closureElementList.add(currentClosureElement.counterValue);
        // set in first element info about second
        currentClosureElement.nextElement = nextClosureElement.counterValue;
        // S' -> . S
        firstElementMap.put("S'", new ArrayList<>());
        closureElementCombination.put(currentClosureElement.counterValue, currentClosureElement);
        firstElementMap.get("S'").add(currentClosureElement.counterValue);
        // S' -> S .
        closureElementCombination.put(nextClosureElement.counterValue, nextClosureElement);
        firstElementMap.get("S'").add(nextClosureElement.counterValue);

        //adding elements from parsedSet

        //add S' -> S
        ArrayList<ArrayList<String>> temp = new ArrayList<>();
        ArrayList<String> tempArrayList = new ArrayList<>();
        tempArrayList.add(startElement);

        temp.add(tempArrayList);
        parsedSet.put("S'", temp);


        for(String parsedSetKey: parsedSet.keySet()) {
            //add new element to first element map
            if(!firstElementMap.containsKey(parsedSetKey)) {
                firstElementMap.put(parsedSetKey, new ArrayList<>());
            }


            for(ArrayList<String> parsedSetProduction: parsedSet.get(parsedSetKey)) {
                allParameters.addAll(parsedSetProduction);

                //access to element
                //create first element
                tempArrayString = new ArrayList<>();
                tempArrayString.addAll(parsedSetProduction);
                //add to produciton List
                listOfProduction.put(currProdNumber, CommonUtility.generateList(parsedSetKey, CommonUtility.arrow, tempArrayString));

                tempArrayString.add(0, CommonUtility.dot);
                currentClosureElement = new ClosureElement(closureElementLastCombination++, CommonUtility.generateList(parsedSetKey, CommonUtility.arrow, tempArrayString), currProdNumber);
                //add to first element map
                currentGotoElement.closureElementList.add(currentClosureElement.counterValue);
                //add to start goto element
                firstElementMap.get(parsedSetKey).add(currentClosureElement.counterValue);

                //for across element

                for(Integer i = 0; i <= parsedSetProduction.size() - 1; i++) {
                    tempArrayString = new ArrayList<>();
                    tempArrayString.addAll(parsedSetProduction);
                    tempArrayString.add(i + 1, CommonUtility.dot);
                    nextClosureElement = new ClosureElement(closureElementLastCombination++, CommonUtility.generateList(parsedSetKey, CommonUtility.arrow, tempArrayString), currProdNumber);
                    currentClosureElement.nextElement = nextClosureElement.counterValue;
                    closureElementCombination.put(currentClosureElement.counterValue, currentClosureElement);
                    currentClosureElement = new ClosureElement(nextClosureElement.counterValue, CommonUtility.generateList(parsedSetKey, CommonUtility.arrow, tempArrayString), currProdNumber);
                }
                closureElementCombination.put(currentClosureElement.counterValue, currentClosureElement);
                currProdNumber++;
            }
        }
        //cheching
        for (Integer i = 0; i< firstElementMap.get("S'").size(); i++) {
            if (firstElementMap.get("S'").get(i) > 2) {
                firstElementMap.remove(i);
            }
        }

        //add first element to gotomap
        gotoElementMap.put(gotoLastElement++, currentGotoElement);

        System.out.println(allParameters);

        System.out.println(firstElementMap);
        System.out.println(currentGotoElement.closureElementList);
        for(Integer s: closureElementCombination.keySet()) {
            System.out.println(s + "\t -> " + closureElementCombination.get(s).production);
        }

        //all things are generated start generate elements
        Integer currentGotoMapElement = 0;
        Integer indexOfDot;
        Integer sizeOfList;
        Boolean toAdd;
        Boolean emptyValue;
         do {
            //for across all possible element
            for(String currentLookingParameter: allParameters) {
                //new current goto element
                currentGotoElement = new GotoElement();
                //for across current Goto Map Element
                for(Integer currentElementCombination: gotoElementMap.get(currentGotoMapElement).closureElementList) {
                    indexOfDot = closureElementCombination.get(currentElementCombination).production.indexOf(CommonUtility.dot);
                    if(!closureElementCombination.get(currentElementCombination).isLastItem && closureElementCombination.get(currentElementCombination).production.get(indexOfDot + 1).equals(currentLookingParameter)) {
                        //if correct add all element
                        currentGotoElement.closureElementList.add(closureElementCombination.get(currentElementCombination).nextElement);
                    }
                }

                while(true) {
                    sizeOfList = currentGotoElement.closureElementList.size();
                    Set<Integer> tempArray = new HashSet<>();
                    tempArray.addAll(currentGotoElement.closureElementList);
                    for(Integer currentElementCombination: tempArray) {
                        indexOfDot = closureElementCombination.get(currentElementCombination).production.indexOf(CommonUtility.dot);
                        if(!closureElementCombination.get(currentElementCombination).isLastItem && firstElementMap.containsKey(closureElementCombination.get(currentElementCombination).production.get(indexOfDot + 1))) {
                            currentGotoElement.closureElementList.addAll(firstElementMap.get(closureElementCombination.get(currentElementCombination).production.get(indexOfDot + 1)));
                        }
                    }

                    if(sizeOfList == currentGotoElement.closureElementList.size()) {
                        break;
                    }
                }

                //check if currentGotoElement exist of map
                toAdd = true;
                for(Integer tempGotoElement: gotoElementMap.keySet()) {
                    if(gotoElementMap.get(tempGotoElement).closureElementList.equals(currentGotoElement.closureElementList)){
                        tempGotoTransition = new GotoTransition(currentGotoMapElement, tempGotoElement, currentLookingParameter);
                        gotoTransitionList.add(tempGotoTransition);
                        toAdd = false;
                        break;
                    }
                }
                if(toAdd && currentGotoElement.closureElementList.size() != 0){
                    gotoElementMap.put(gotoLastElement, currentGotoElement);
                    tempGotoTransition = new GotoTransition(currentGotoMapElement, gotoLastElement, currentLookingParameter);
                    gotoTransitionList.add(tempGotoTransition);
                    gotoLastElement++;
                }

            }

            currentGotoMapElement++;
        } while(currentGotoMapElement < gotoElementMap.size());

        // calculate finalStates


        //print transition
        for(Integer tempElement: gotoElementMap.keySet()) {
            System.out.println(tempElement);
            for(Integer tempValue: gotoElementMap.get(tempElement).closureElementList) {
                System.out.println(closureElementCombination.get(tempValue).production);
            }
            System.out.println("----------------");
        }

        for(GotoTransition tempTransition: gotoTransitionList) {
            System.out.println(tempTransition.from + "\t" + tempTransition.to + "\t" + tempTransition.value);
        }


        return;
    }


}
