import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxGraph;
import javafx.util.Pair;

import java.util.*;

/**
 * Created on 22.03.2017.
 */
class First {
    private List<String> inputData;
    private Map<String, ArrayList<ArrayList<String>>> parsedSet;
    private Map<String, Set<String>> nonterminalsTransitionMap;
    private Map<String, FirstElement> firstElementMap;
    private Map<String, FollowElement> followElementMap;
    private Map<String, ArrayList<ArrayList<String>>> nonterminalsRelationMap;
    private ArrayList<String> listToInsert;
    private Boolean toRecalculate;
    private String firstProduction;
    private Map<String, ArrayList<PredictiveMapElement>> predictiveMap;
    private Map<String, Map<String, ArrayList<String>>> predictiveMaptest;

    First(List<String> inputList){
        inputData = new ArrayList<>();
        inputData = inputList;
        parsedSet = new HashMap<>();
        nonterminalsTransitionMap = new HashMap<>();
        firstElementMap = new HashMap<>();
        followElementMap = new HashMap<>();
        nonterminalsRelationMap = new HashMap<>();
        predictiveMap = new HashMap<>();
    }

    void generateParsedSet() {
        String[] parsedString;
        if(inputData.size() == 0) {
            System.out.println("Empty Input");
            return;
        }

        for(String inputLine: inputData) {

            //START VERIFICATION

            //check if string contains "->"
            if(!inputLine.contains("->")) {
                System.out.println("There is not production on this string:" + inputLine);
                continue;
            }

            //remove multispaces
            while(true) {
                if(inputLine.contains("  ")) {
                    inputLine = inputLine.replaceAll("  ", " ");
                } else {
                    break;
                }
            }
            //parse string to list of element
            parsedString = inputLine.split(" ");

            //check in on left site there is only one symbol
            if(!parsedString[1].equals("->")) {
                System.out.println("to many symbols on left site: " + inputLine);
                continue;
            }

            //check if set have at least one production
            if(parsedString.length < 3) {
                System.out.println("There is not any production: " + inputLine);
                continue;
            }

            //verify if is left part is production
            if(parsedString[0].charAt(0) < 'A' || parsedString[0].charAt(0) > 'Z') {
                System.out.println("Line start with terminal, not with non-terminal: " + inputLine);
                continue;
            }

            //END VERIFICATION

            //check if terminal exist on paredSet set and add
            if(!parsedSet.containsKey(parsedString[0])) {
                parsedSet.put(parsedString[0], new ArrayList<>());
                if(parsedSet.size() == 1) {
                    firstProduction = parsedString[0];
                }
            }

            //verify if is new element on first List
            if(!firstElementMap.containsKey(parsedString[0])) {
                firstElementMap.put(parsedString[0],new FirstElement(parsedString[0]));
            }

            //for across production divide to sets and insert
            listToInsert = new ArrayList<>();
            for(Integer i = 2; i < parsedString.length; i++) {

                if(!parsedString[i].equals("|")) {
                    //verify if is new element on first List AND it is not epsValue
                    if(!firstElementMap.containsKey(parsedString[i]) && !parsedString[i].equals(CommonUtility.epsValue)) { //
                        firstElementMap.put(parsedString[i],new FirstElement(parsedString[i]));
                    }

                    //add to array
                    listToInsert.add(parsedString[i]);
                    continue;
                }
                //addToList (eq "|")
                //verify if not exist
                if(setComparison(listToInsert, parsedSet.get(parsedString[0]))) {
                    parsedSet.get(parsedString[0]).add(listToInsert);
                    //check first symbol
                    addElementToFirstSetSet(parsedString[0], listToInsert, firstElementMap);

                }
                listToInsert = new ArrayList<>();
            }
            //last word
            if((!listToInsert.isEmpty() && setComparison(listToInsert, parsedSet.get(parsedString[0])))) {// && (listToInsert.size() == 1 && listToInsert.get(0) != CommonUtility.epsValue)

                parsedSet.get(parsedString[0]).add(listToInsert);
            }
            addElementToFirstSetSet(parsedString[0], listToInsert, firstElementMap);
        }
        System.out.println(parsedSet);
        System.out.println("Before non-terminal addition");
        printFirstList(firstElementMap);
        System.out.println(nonterminalsTransitionMap);
        System.out.println(nonterminalsRelationMap);

        // sprawdznie eps-transition
        for(FirstElement fe : firstElementMap.values()) {
            if(!fe.isTerminal && fe.firstSet.contains(CommonUtility.epsValue)) {
                fe.isEpsilonTransition = true;
            }
        }
        System.out.println("With eps-transition");
        printFirstList(firstElementMap);

        System.out.println(nonterminalsRelationMap);
        System.out.println(nonterminalsTransitionMap);

        // przejście po firstElementMap add Map< String, set<String>> do uzupełnienia (ogarnięcie eps)
        Boolean toEnd;
        do {
            toRecalculate = false;
            for(String symbol: nonterminalsRelationMap.keySet()) {
                //for across Lists
                toEnd = true;
                for(ArrayList<String>lString: nonterminalsRelationMap.get(symbol)) {
                    //for across elements
                    for(String ls: lString) {
                        //check if element has eps-transition
                        if(!firstElementMap.get(ls).isEpsilonTransition) {
                            //if not add element to nonterminalsTransitionMap and go to next list
                            addToNonterminalsTransitionMap(nonterminalsTransitionMap, symbol, ls);
                            toEnd = false;
                            break;
                        } else {
                            //if yes add element to nonterminalsTransitionMap and go to next element
                            addToNonterminalsTransitionMap(nonterminalsTransitionMap, symbol, ls);
                        }
                        //check if
                    }
                    if(toEnd) {
                        // add eps to element List
                        if(!firstElementMap.get(symbol).firstSet.contains(CommonUtility.epsValue)) {
                            firstElementMap.get(symbol).firstSet.add(CommonUtility.epsValue);
                            //check if element is eps transition
                            if(!firstElementMap.get(symbol).isEpsilonTransition) {
                                firstElementMap.get(symbol).isEpsilonTransition = true;
                                toRecalculate = true;
                            }
                        }
                    }
                }
            }

//            System.out.println(parsedSet);
//            System.out.println("After next part");
//            printfirstList(firstElementMap);

        } while (toRecalculate);


        System.out.println("Before adding transition ");
        printFirstList(firstElementMap);
        System.out.println(nonterminalsRelationMap);
        System.out.println(nonterminalsTransitionMap);
        //for po liście par do momentu przejścia bez dodawania elementów

        Set<String> elementToAddList;

        do {
            toRecalculate = false;
            //for po nonterninalsTransitionMap
            for(String symbol: nonterminalsTransitionMap.keySet()) {
                //for po elementach
                for(String element: nonterminalsTransitionMap.get(symbol)) {
                    elementToAddList = firstElementMap.get(element).firstSet;
                    for(String s:elementToAddList) {
                        if(!firstElementMap.get(symbol).firstSet.contains(s) && !s.equals(CommonUtility.epsValue)) {
                            firstElementMap.get(symbol).firstSet.add(s);
                            toRecalculate = true;
                        }
                    }
                }
            }
        } while(toRecalculate);

        System.out.println("Final");
        printFirstList(firstElementMap);
        System.out.println(nonterminalsRelationMap);
        System.out.println(nonterminalsTransitionMap);
        System.out.println(parsedSet);


        generateFollowSet(firstElementMap, parsedSet);
    }


    //compare sets of strings
    private Boolean setComparison(ArrayList<String> listToCheck, ArrayList<ArrayList<String>> setToCheck) {

        if(setToCheck != null && !setToCheck.isEmpty()){
            for(ArrayList al: setToCheck) {
                if(al.equals(listToCheck)) {
                    return false;
                }
            }
        }
        return true;

    }
    private void printFirstList(Map<String, FirstElement> FirstList) {

        if(FirstList != null && !FirstList.isEmpty()) {
            System.out.println("First SET");
            System.out.println("-------------");
            for(FirstElement fe: FirstList.values()) {
                System.out.println(fe.value + "\t" + fe.isTerminal + "\t" + fe.isEpsilonTransition +  "\t" + fe.firstSet);
            }
            System.out.println("-------------");
            return;
        }
        System.out.println("First SET is empty");
    }

    private Boolean isNonTerminal(String testString) {
        return !(testString.charAt(0) < 'A' || testString.charAt(0) > 'Z');
    }

    private void addElementToFirstSetSet(String parsedString, ArrayList<String> listToInsert, Map<String, FirstElement> firstElementMap) {
        if(isNonTerminal(listToInsert.get(0))) {
            //first symbol is non-terminal

            //
            // dodanie list do map nonternimalRelationMap
            //

            //verify if element exist on map
            if(!nonterminalsRelationMap.keySet().contains(parsedString)) {
                nonterminalsRelationMap.put(parsedString, new ArrayList<>());
            }

            Boolean toAdd = true;
            Boolean theSame = false;
            //verify if list exist on list
            if(nonterminalsRelationMap.get(parsedString).isEmpty()) {
                nonterminalsRelationMap.get(parsedString).add(listToInsert);
            } else { // nonterminalsRelationMap.get(0) != null &&
                for(List<String> lString: nonterminalsRelationMap.get(parsedString)) {
                    if(lString.equals(listToInsert)) {
                        theSame = true;
                    }
                    if(theSame) {
                        toAdd = false;
                    }
                }
                if(toAdd) {
                    nonterminalsRelationMap.get(parsedString).add(listToInsert);
                }
            }
        } else {
            //first symbol is terminal
            if(!firstElementMap.get(parsedString).firstSet.contains(listToInsert.get(0))) {
                firstElementMap.get(parsedString).firstSet.add(listToInsert.get(0));
            }
            //firstElementList
        }
    }
    private void addToNonterminalsTransitionMap(Map<String, Set<String>> nonterminalsTransitionMap, String symbol, String Transition) {
        if(!nonterminalsTransitionMap.containsKey(symbol)) {
            nonterminalsTransitionMap.put(symbol, new HashSet<>());
        }
        if(!nonterminalsTransitionMap.get(symbol).contains(Transition)) {
            nonterminalsTransitionMap.get(symbol).add(Transition);
        }
    }
    private void generateFollowSet(Map<String, FirstElement> firstElementMap, Map<String, ArrayList<ArrayList<String>>> parsedSet) {
        String lastWord;
        String nextElement;
        String currentElement;
        //First put $ to first production


        followElementMap.put(firstProduction, new FollowElement(firstProduction));
        followElementMap.get(firstProduction).followSet.add(CommonUtility.dolarValue);

        do {
            toRecalculate = false;
            for(String element: parsedSet.keySet()) {

                for(ArrayList<String> lString: parsedSet.get(element)) {

                    //all elements without last
                    for(Integer i = 0; i < lString.size()-1; i++) {
                        currentElement = lString.toArray()[i].toString();
                        //for terminals go to next element
                        if(firstElementMap.get(currentElement).isTerminal) {
                            continue;
                        }
                        nextElement = lString.toArray()[i+1].toString();
                        //add all elements from first set of next element

                        if(!followElementMap.containsKey(currentElement)) {
                            followElementMap.put(currentElement, new FollowElement(currentElement));
                        }

                        for(String elementToAdd: firstElementMap.get(nextElement).firstSet) {
                            if(!followElementMap.get(currentElement).followSet.contains(elementToAdd)) {
                                followElementMap.get(currentElement).followSet.add(elementToAdd);
                                toRecalculate = true;
                            }
                        }

                        //if next element iseps transition add all element from Follow(A) to follow(B)
                        if(firstElementMap.get(nextElement).isEpsilonTransition) {
                            if(followElementMap.containsKey(nextElement)) {
                                followElementMap.put(nextElement, new FollowElement(nextElement));
                            }
                            //if followElementMap is empty add all
                            if(followElementMap.containsKey(element) && !followElementMap.get(element).followSet.isEmpty()){
                                for(String elementToAdd: followElementMap.get(element).followSet) {
                                    if(!followElementMap.get(nextElement).followSet.contains(elementToAdd)) {
                                        followElementMap.get(nextElement).followSet.add(elementToAdd);
                                        toRecalculate = true;
                                    }
                                }
                            }
                        }

                    }
                    //last element
                    // A -> aB then Follow(A) in Follow(B)
                    lastWord = (String) lString.toArray()[lString.size()-1];
                    if(!lastWord.equals(CommonUtility.epsValue) && !firstElementMap.get(lastWord).isTerminal) {

                        if(!followElementMap.containsKey(lastWord)) {
                            followElementMap.put(lastWord, new FollowElement(lastWord));
                        }
                        //if followElementMap is empty add all
                        if(followElementMap.containsKey(element) && !followElementMap.get(element).followSet.isEmpty()){
                            for(String elementToAdd: followElementMap.get(element).followSet) {
                                if(!followElementMap.get(lastWord).followSet.contains(elementToAdd)) {
                                    followElementMap.get(lastWord).followSet.add(elementToAdd);
                                    toRecalculate = true;
                                }
                            }
                        }
                    }
                }
            }
        }while (toRecalculate);
        printFollowList(followElementMap);
    }

    private void printFollowList(Map<String, FollowElement> followElementMap) {
        System.out.println("FOLLOW SET");
        System.out.println("----------");
        for(String element: followElementMap.keySet()) {
            System.out.println(element + "\t" + followElementMap.get(element).followSet);
        }
        System.out.println("----------");
    }

    void generatePredictiveMap(Map<String, ArrayList<ArrayList<String>>> parsedSet, Map<String, FirstElement> firstElementMap, Map<String, FirstElement> followElementMap) {

        for(String symbol: parsedSet.keySet()) {

        }

    }

    void addElementToPredictiveMap(String symbol, String prediction, ArrayList<String> production, Map<String, ArrayList<ArrayList<String>>> predictiveMap2) {
        //check if element exist on map
        if(!predictiveMap.containsKey(symbol)) {
           predictiveMap.put(symbol, new ArrayList<>());
            for(String predictionElement: firstElementMap.keySet()) {
                if(firstElementMap.get(predictionElement).isTerminal){
                    predictiveMap.get(symbol).add(new PredictiveMapElement());
                }

            }
        }
        String test;
        predictiveMaptest = new HashMap<>();
        predictiveMaptest.put("test", new HashMap<>());
        predictiveMaptest.get("test").put("test2", new ArrayList<>());
        predictiveMaptest.get("test").get("test2").add("test3");
        //check if production exist on symbol array
//        if(!predictiveMap.get(symbol).contains(prediction)) {
//            predictiveMap.get(symbol).put(prediction, new)
//        }
    }



}
