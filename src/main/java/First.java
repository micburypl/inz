import javafx.util.Pair;

import java.util.*;

/**
 * Created on 22.03.2017.
 */
public class First {
    private List<String> inputData;
    private Map<String, ArrayList<ArrayList<String>>> parsedSet;
    private Map<String, Set<String>> outputData;
    private Map<String, FirstElement> firstElementMap;
    private Set<Pair<String, String>> nonterminalsRelationSet;

    private ArrayList<String> listToInsert;

    First(List<String> inputList){
        inputData = new ArrayList<String>();
        inputData = inputList;
        parsedSet = new HashMap<String, ArrayList<ArrayList<String>>>();
        outputData = new HashMap<String, Set<String>>();
        firstElementMap = new HashMap<String, FirstElement>();
        nonterminalsRelationSet = new HashSet<Pair<String, String>>();
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
                parsedSet.put(parsedString[0], new ArrayList<ArrayList<String>>());
            }

            //verify if is new element on first List
            if(!firstElementMap.containsKey(parsedString[0])) {
                firstElementMap.put(parsedString[0],new FirstElement(parsedString[0]));
            }

            //for across production divide to sets and insert
            listToInsert = new ArrayList<String>();
            for(Integer i = 2; i < parsedString.length; i++) {

                if(!parsedString[i].equals("|")) {
                    //verify if is new element on first List
                    if(!firstElementMap.containsKey(parsedString[i])) {
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
                    addElementToFirstSetSet(parsedString, listToInsert, firstElementMap);

                }
                listToInsert = new ArrayList<String>();
            }
            //last word
            if(!listToInsert.isEmpty() && setComparison(listToInsert, parsedSet.get(parsedString[0]))) {
                parsedSet.get(parsedString[0]).add(listToInsert);
            }
            addElementToFirstSetSet(parsedString, listToInsert, firstElementMap);
        }
        System.out.println(parsedSet);
        System.out.println("Before non-terminal addition");
        printfirstList(firstElementMap);

        //TODO sprawdznie eps-transition

        //TODO przejście po firstElementMap dodanie par string string do uzupełnienia (ogarnięcie eps)

        //TODO for po liście par do momentu przejścia bez dodawania elementów

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

    void printfirstList(Map<String, FirstElement> FirstList) {

        if(FirstList != null && !FirstList.isEmpty()) {
            System.out.println("First SET");
            System.out.println("-------------");
            for(FirstElement fe: FirstList.values()) {
                System.out.println(fe.value + "\t" + fe.isTerminal + "\t" + fe.firstSet);
            }
            System.out.println("-------------");
            return;
        }
        System.out.println("First SET is empty");
    }

    private Boolean isNonTerminal(String testString) {
        return !(testString.charAt(0) < 'A' || testString.charAt(0) > 'Z');
    }

    void addElementToFirstSetSet(String[] parsedString, ArrayList<String> listToInsert, Map<String, FirstElement> firstElementMap) {
        if(isNonTerminal(listToInsert.get(0))) {
            //first symbol is non-terminal

            //
            //TODO dodanie par string, list to insert

        } else {
            //first symbol is terminal
            if(!firstElementMap.get(parsedString[0]).firstSet.contains(listToInsert.get(0))) {
                firstElementMap.get(parsedString[0]).firstSet.add(listToInsert.get(0));
            }
            //firstElementList

        }
    }
}
