package other;

public class oldThink {
//    List<String> inputData;
//    Map<String, ArrayList<ArrayList<String>>> parsedSet;
//    Map<String, Set<String>> outputData;
//    Map <String, Set<FirstElement>> firstElementMap;
//    Set<Pair<String, String>> nonterminalsRelationSet;
//
//    ArrayList<String> listToInsert;
//
//    First(List<String> inputList){
//        inputData = new ArrayList<String>();
//        inputData = inputList;
//        parsedSet = new HashMap<String, ArrayList<ArrayList<String>>>();
//        outputData = new HashMap<String, Set<String>>();
//        firstElementMap = new HashMap<String, Set<FirstElement>>();
//        nonterminalsRelationSet = new HashSet<Pair<String, String>>();
//
//    }
//
//    void generateParsedSet() {
//        String[] parsedString;
//        if(inputData.size() == 0) {
//            System.out.println("Empty Input");
//            return;
//        }
//
//        for(String inputLine: inputData) {
//
//            //START VERIFICATION
//
//            //check if string contains "->"
//            if(!inputLine.contains("->")) {
//                System.out.println("There is not production on this string:" + inputLine);
//                continue;
//            }
//
//            //remove multispaces
//            while(true) {
//                if(inputLine.contains("  ")) {
//                    inputLine = inputLine.replaceAll("  ", " ");
//                } else {
//                    break;
//                }
//            }
//            //parse string to list of element
//            parsedString = inputLine.split(" ");
//
//            //check in on left site there is only one symbol
//            if(!parsedString[1].equals("->")) {
//                System.out.println("to many symbols on left site: " + inputLine);
//                continue;
//            }
//
//            //check if set have at least one production
//            if(parsedString.length < 3) {
//                System.out.println("There is not any production: " + inputLine);
//                continue;
//            }
//
//            //verify if is left part is production
//            if(isNonTerminal(parsedString[0])) {
//                System.out.println("Line start with terminal, not with non-terminal: " + inputLine);
//                continue;
//            }
//
//            //END VERIFICATION
//
//            //check if terminal exist on paredSet set and add
//            if(!parsedSet.containsKey(parsedString[0])) {
//                parsedSet.put(parsedString[0], new ArrayList<ArrayList<String>>());
//            }
//
//            //verify if is new element on first List
//            if(addToFirstList(parsedString[0], firstElementMap.get(parsedString[0]))) {
//                firstElementMap.get(parsedString[0]).add(new FirstElement(parsedString[0]));
//            }
//
//            //for across production divide to sets and insert
//            listToInsert = new ArrayList<String>();
//            for(Integer i = 2; i < parsedString.length; i++) {
//
//                if(!parsedString[i].equals("|")) {
//                    //verify if is new element on first List
//                    if(addToFirstList(parsedString[i], firstElementMap.get(parsedString[0]))) {
//                        firstElementMap.get(parsedString[0]).add(new FirstElement(parsedString[i]));
//                    }
//                    //add to array
//                    listToInsert.add(parsedString[i]);
//                    continue;
//                }
//
//                //addToList (eq "|")
//                //verify if not exist
//                if(setComparison(listToInsert, parsedSet.get(parsedString[0]))) {
//                    //add List to non-terminal as production
//                    parsedSet.get(parsedString[0]).add(listToInsert);
//                    //check first symbol
//                    if(isNonTerminal(listToInsert.get(0))) {
//                        //first symbol is non-terminal
//
//                        //
//                        //ADD TO PAIRS SET
//
//                    } else {
//                        //first symbol is terminal
//
//                        //firstElementList
//
//                    }
//
//                }
//                listToInsert = new ArrayList<String>();
//            }
//            //last word
//            if(!listToInsert.isEmpty() && setComparison(listToInsert, parsedSet.get(parsedString[0]))) {
//                parsedSet.get(parsedString[0]).add(listToInsert);
//            }
//
//        }
//        System.out.println(parsedSet);
//
//        printfirstMap(firstElementMap);
//    }
//
//
//    //compare sets of strings
//    Boolean setComparison(ArrayList<String> listToCheck, ArrayList<ArrayList<String>> setToCheck) {
//
//        if(setToCheck != null && !setToCheck.isEmpty()){
//            for(ArrayList al: setToCheck) {
//                if(al.equals(listToCheck)) {
//                    return false;
//                }
//            }
//        }
//        return true;
//
//    }
//
//    Boolean addToFirstList(String potentialElement, Set<FirstElement> FirstList) {
//        if(FirstList != null && !FirstList.isEmpty()) {
//            for(FirstElement fe: FirstList) {
//                if (fe.value.equals(potentialElement)) {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }
//
//    //REBUILD ale kurwa jak??
//
//    void printfirstMap(Map< String, Set<FirstElement>> FirstMap) {
//
//        if(FirstMap != null && !FirstMap.isEmpty()) {
//            System.out.println("First SET");
//            System.out.println("-------------");
//            for(FirstElement fe: FirstMap) {
//                System.out.println(fe.value + "\t" + fe.isTerminal + "\t" + fe.firstSet);
//            }
//            System.out.println("-------------");
//            return;
//        }
//        System.out.println("First SET is empty");
//    }
//
//    Boolean isNonTerminal(String testString) {
//        if (testString.charAt(0) < 'A' || testString.charAt(0) > 'Z') {
//            return false;
//        }
//        return  true;
//    }

}