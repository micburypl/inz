package DFA.algorithmDFA;

import commonUtility.CommonUtility;

import java.util.*;

/**
 * Created on 19.03.2017.
 */
public class DFA {

    private String word;
    private Boolean nextCharFlag;
    public Boolean errorFlag;
    private Integer sizeOfString;
    private Integer levelOfPriority;
    private Boolean lastSymbol;
    private Boolean eMessage;
    private Integer symbolNumber;
    public DFAPart rootOfTree;
    private char currentSymbol = 'A';
    public Integer finalState;
    public List<DFAPart> inputList;
    public List<DFAPart> outputList;

    public Boolean isCorrect;


    public Map<Integer,HashSet<Integer>> followMap;
    //list of word

    public Map<String, HashSet<Integer>> transitionData;
    public Map<String, HashSet<Integer>> transitionProduction;
    public List<TransitionTableElement> transitionTable;

    private Integer tempControlNumber;


    public DFA() {
        word = "";
        nextCharFlag = false;
        errorFlag = false;
        sizeOfString = 0;
        lastSymbol = false;
        eMessage = false;
        //line = "";
        symbolNumber = 1;
        tempControlNumber = 1;


        inputList = new ArrayList<>();
        outputList = new ArrayList<>();
        followMap = new HashMap<>();

        transitionData = new HashMap<>();
        transitionProduction = new HashMap<>();
        transitionTable = new ArrayList<>();
    }

    public void printList(List<DFAPart> iQ) {

        List<DFAPart> inputList = new ArrayList<>(iQ);

        System.out.println();
        System.out.print("start.Start print queue");
        System.out.println();
        System.out.print("Queue:");
        System.out.println();

        for (DFAPart anInputList : inputList) {
            System.out.print(anInputList);
            System.out.print(anInputList.typeTreePart);
            if (!anInputList.typeTreePart) {
                System.out.print(" " + anInputList.operatorText + " priority " + anInputList.priority);
                System.out.println();
            } else {
                System.out.print(" " + anInputList.symbolsText);
                System.out.println();
            }
        }

        System.out.print("End print queue");
        System.out.println();
        System.out.println();
    }

    public void printTree(DFAPart rootOfTree, int level){
        for(int i = 0; i < level; i++){
            System.out.print("->");
        }
        if(rootOfTree.typeTreePart){
            System.out.print(rootOfTree.symbolsText + "\t" + rootOfTree.nodeNumber + "\t" + rootOfTree.nullable + "\t" + rootOfTree.firstList + "\t" + rootOfTree.lastList);

            System.out.println();
            return;
        }
        System.out.print(rootOfTree.operatorText+ "\t\t" + rootOfTree.nullable  + "\t" + rootOfTree.firstList + "\t" + rootOfTree.lastList);
        System.out.println();
        if(rootOfTree.typeChild){
            if(rootOfTree.singleChild != null){
                printTree(rootOfTree.singleChild, level+1);
            }

        }else{
            if(rootOfTree.leftChild != null){
                printTree(rootOfTree.leftChild, level+1);
            }
            if(rootOfTree.rightChild != null){
                printTree(rootOfTree.rightChild, level+1);
            }
        }
    }

    private void createOperand(Integer prior, char word){
        lastSymbol = false;
        DFAPart newTreePart = new DFAPart();
        newTreePart.priority = prior + levelOfPriority;
        newTreePart.typeTreePart = false;
        newTreePart.operatorText = word;
        newTreePart.typeChild = false;
        if(word == (CommonUtility.starSign)){
            newTreePart.typeChild = true;
            for(Integer i = inputList.size()-1; i >= 0; i--) {
                if(inputList.get(i).typeTreePart) {
                    continue;
                }
                if(inputList.get(i).priority > newTreePart.priority) {
                    continue;
                }
                inputList.add(i+1, newTreePart);
                return;
            }
            inputList.add(0, newTreePart);
            return;
        }
        inputList.add(newTreePart);
    }

    private void createSymbol(String word, List<DFAPart> inputList){
        lastSymbol = true;
        DFAPart newTreePart = new DFAPart();
        newTreePart.symbolsText = word;
        newTreePart.typeTreePart = true;
        newTreePart.nodeNumber = symbolNumber++;
        followMap.put(newTreePart.nodeNumber, new HashSet<>());
        addToTransitionData(word, newTreePart.nodeNumber);
        inputList.add(newTreePart);
    }

    public void numerateTree(DFAPart rootOfTree) {
        if(rootOfTree.typeTreePart) {
            //symbol
            rootOfTree.controlNumber = tempControlNumber++;
            outputList.add(rootOfTree);
        } else if(rootOfTree.typeChild) {
            //* operator
            numerateTree(rootOfTree.singleChild);
            rootOfTree.controlNumber = tempControlNumber++;
            outputList.add(rootOfTree);
        } else {
            //other operator
            numerateTree(rootOfTree.leftChild);
            numerateTree(rootOfTree.rightChild);
            rootOfTree.controlNumber = tempControlNumber++;
            outputList.add(rootOfTree);
        }
    }


    public List<DFAPart> createList(String line){
        isCorrect = true;
        word = "";
        levelOfPriority = 0;
        errorFlag = false;
        lastSymbol = false;
        eMessage = false;
        sizeOfString = line.length();

        //verifying dot in input string

        if(line.length() == 1 && !(
                (line.charAt(0) >= 'a' && line.charAt(0) <='z') ||
                (line.charAt(0) >= 'A' && line.charAt(0) <='Z') ||
                (line.charAt(0) >= '0' && line.charAt(0) <='9'))) {
            errorFlag = true;
            return new LinkedList<>();
        }

        String stringToVerify = line;
        for(Integer i = 0; i< sizeOfString-1; i++) {
            if(stringToVerify.charAt(i) == CommonUtility.starSign) {

               if(stringToVerify.charAt(i + 1) == '(' ||
                        (stringToVerify.charAt(i + 1) >= 'a' && stringToVerify.charAt(i + 1) <='z')||
                        (stringToVerify.charAt(i + 1) >= 'A' && stringToVerify.charAt(i + 1) <='Z')||
                        (stringToVerify.charAt(i + 1) >= '0' && stringToVerify.charAt(i + 1) <='9')) {
                   //error input
                   errorFlag = true;
                   return new LinkedList<>();
               }
            }
        }



        for(int i = 0; i < sizeOfString; i++){

            if(nextCharFlag){
                nextCharFlag = false;
                continue;
            }
            if((line.charAt(i) >= 'a' && line.charAt(i) <='z')||(line.charAt(i) >= 'A' && line.charAt(i) <='Z')||(line.charAt(i) >= '0' && line.charAt(i) <='9')){
                word += line.charAt(i);
                continue;
            }
            if(line.charAt(i) == ' '){
                continue;
            }
            switch(line.charAt(i)){
                case CommonUtility.starSign:

                    createOperand(4, CommonUtility.starSign);
//                    if(line.charAt(i+1) != CommonUtility.starSign && line.charAt(i+1) != CommonUtility.andSign && line.charAt(i+1) != CommonUtility.orSign) {
//                        errorFlag = true;
//                    }
                    break;
                case CommonUtility.orSign:
                    if(word.length() == 0 && line.charAt(i-1) != CommonUtility.starSign){
                        errorFlag = true;
                        break;
                    }
                    createSymbol(word, inputList);
                    word = "";
                    createOperand(2, CommonUtility.orSign);
                    break;
                case CommonUtility.andSign:
                    if(word.length() == 0 && line.charAt(i-1) != CommonUtility.starSign){
                        errorFlag = true;
                        break;
                    }
                    createSymbol(word, inputList);
                    word = "";
                    createOperand(3, CommonUtility.andSign);
                    break;
                case '(':
                    if(i>0){
                        if((line.charAt(i-1) >= 'a' && line.charAt(i-1) <='z')||(line.charAt(i-1) >= 'A' && line.charAt(i-1) <='Z')||(line.charAt(i-1) >= '0' && line.charAt(i-1) <='9')){
                            errorFlag = true;
                            break;
                        }
                    }
                    levelOfPriority += 10;
                    break;
                case ')':

                    if(i!=line.length()-1){
                        System.out.println(line.charAt(i));
                        if((line.charAt(i+1) >= 'a' && line.charAt(i+1) <='z')||(line.charAt(i+1) >= 'A' && line.charAt(i+1) <='Z')||(line.charAt(i+1) >= '0' && line.charAt(i+1) <='9')){
                            errorFlag = true;
                            break;
                        }
                    }
                    levelOfPriority -= 10;
                    break;
                default:
                    errorFlag = true;
                    break;
            }
            if(errorFlag){

                System.out.print("---------------------------");
                System.out.println();
                System.out.print("input line: ");
                System.out.println();

                System.out.print("error input line: " + line);
                System.out.println();

                eMessage = true;
                break;
            }
        }

        if(levelOfPriority != 0 && !errorFlag){
            System.out.print("Wrong Bracket");
            System.out.println();

            errorFlag = true;
        }

        if(word.length() > 0){
            createSymbol(word, inputList);
            word = "";
        }
        if(!lastSymbol && !eMessage && line.charAt(sizeOfString - 1) != CommonUtility.starSign){
            errorFlag = true;
            System.out.print("---------------------------");
            System.out.println();
            System.out.print("input line: " + line);
            System.out.println();

            System.out.print("error input line: " + line);
            System.out.println();

        }
        if(errorFlag){
            return new LinkedList<>();
        }
        System.out.print(inputList);
        return inputList;

    }

    public DFAPart createTree(List<DFAPart> inputList){
        DFAPart rootOfTree;
        DFAPart currentPlace;
        DFAPart tempPart;
        rootOfTree = inputList.get(0);
        currentPlace = rootOfTree;

        System.out.println();
        System.out.print("current text:");
        System.out.println();
        System.out.println(inputList);
        if (rootOfTree.typeTreePart) {
            System.out.printf(rootOfTree.symbolsText);
            System.out.println();
        } else {
            System.out.print(rootOfTree.operatorText);
            System.out.println();
        }
        System.out.println();

        for (Integer i = 1; i < inputList.size(); i++) {

            tempPart = inputList.get(i);
            System.out.print("current text: ");
            System.out.println();
            if(!tempPart.typeTreePart){
                System.out.print(tempPart.operatorText);
                System.out.println();
            }else{
                System.out.print(tempPart.symbolsText);
                System.out.println();
            }
            System.out.println();

            if(!tempPart.typeTreePart){
                //operator
                System.out.println(tempPart.typeChild);
                if(tempPart.typeChild){
                    //if it is "!"
                    if(!currentPlace.typeChild && currentPlace.leftChild == null) {
                        currentPlace.leftChild = tempPart;
                        tempPart.parentPointer = currentPlace;
                        currentPlace = tempPart;
                    }else if(!currentPlace.typeChild && currentPlace.leftChild != null && currentPlace.rightChild ==null){
                        currentPlace.rightChild = tempPart;
                        tempPart.parentPointer = currentPlace;
                        currentPlace = tempPart;
                    }else if(currentPlace.typeChild){
                        currentPlace.singleChild = tempPart;
                        tempPart.parentPointer = currentPlace;
                        currentPlace = tempPart;
                    }

                }else{
                    while(true){
                        if(rootOfTree.typeTreePart){
                            rootOfTree.parentPointer = tempPart;
                            tempPart.leftChild = rootOfTree;
                            rootOfTree = tempPart;
                            currentPlace = rootOfTree;
                            break;
                        }
                        if((tempPart.priority <= currentPlace.priority) && (currentPlace != rootOfTree)){
                            currentPlace = currentPlace.parentPointer;
                            continue;
                        }
                        if((tempPart.priority <= currentPlace.priority)&& (currentPlace == rootOfTree)){
                            tempPart.leftChild = rootOfTree;
                            rootOfTree.parentPointer = tempPart;
                            rootOfTree = tempPart;
                            currentPlace = tempPart;
                            break;
                        }
                        if(tempPart.priority > currentPlace.priority){
                            if(!currentPlace.typeChild){
                                tempPart.leftChild = currentPlace.rightChild;
                                tempPart.parentPointer = currentPlace;
                                currentPlace.rightChild = tempPart;
                                currentPlace = tempPart;
                            }else{
                                tempPart.leftChild = currentPlace.singleChild;
                                tempPart.parentPointer = currentPlace;
                                currentPlace.singleChild = tempPart;
                                currentPlace = tempPart;
                            }


                            break;
                        }
                    }
                }

            }else{
                //symbol

                if((currentPlace.typeChild == null || !currentPlace.typeChild) && currentPlace.leftChild == null){
                    currentPlace.leftChild = tempPart;
                    tempPart.parentPointer = currentPlace;
                }else

                if(!currentPlace.typeChild && currentPlace.leftChild != null && currentPlace.rightChild == null){
                    currentPlace.rightChild = tempPart;
                    tempPart.parentPointer = currentPlace;
                }else

                if(currentPlace.typeChild){

                    currentPlace.singleChild = tempPart;
                    tempPart.parentPointer = currentPlace;
                }

            }

        }
        return CreateSyntaxTree(rootOfTree);
    }

    private DFAPart CreateSyntaxTree(DFAPart rootOfTree){
        DFAPart syntaxRoot = new DFAPart();
        syntaxRoot.typeTreePart = false;
        syntaxRoot.operatorText = CommonUtility.andSign;
        syntaxRoot.typeChild = false;
        syntaxRoot.leftChild = rootOfTree;
        rootOfTree.parentPointer = rootOfTree;


        DFAPart endWord = new DFAPart();
        endWord.symbolsText = "#";
        endWord.typeTreePart = true;
        endWord.parentPointer = syntaxRoot;
        syntaxRoot.rightChild = endWord;
        endWord.nodeNumber = symbolNumber++;
        followMap.put(endWord.nodeNumber, new HashSet<Integer>());



        return syntaxRoot;
    }

    public void calcNullable(DFAPart treeElement) {
        //nullable is calculated
        if(treeElement.nullable != null) {
            return;
        }
        if(treeElement.typeTreePart) {
            //if element is leaf
            if(treeElement.symbolsText.toLowerCase().equals(CommonUtility.epsValue)) {
                //if symbol is epsilon
                treeElement.nullable = true;
            } else {
                //rest words
                treeElement.nullable = false;
            }
        } else {
            //if element is node
            if(treeElement.operatorText == (CommonUtility.starSign)) {
                //star operator
                calcNullable(treeElement.singleChild);
                treeElement.nullable = true;
            } else if(treeElement.operatorText == (CommonUtility.orSign)) {
                //or operator
                calcNullable(treeElement.leftChild);
                calcNullable(treeElement.rightChild);
                treeElement.nullable = treeElement.leftChild.nullable || treeElement.rightChild.nullable;
            } else {
                //and operator
                calcNullable(treeElement.leftChild);
                calcNullable(treeElement.rightChild);
                treeElement.nullable = treeElement.leftChild.nullable && treeElement.rightChild.nullable;
            }
        }
    }
    public void calcFirstLast(DFAPart treeElement) {
        if(treeElement.typeTreePart) {
            //if element is leaf
            // first = number
            treeElement.firstList.add(treeElement.nodeNumber);
            // last = number
            treeElement.lastList.add(treeElement.nodeNumber);
        } else {
            //if element is node
            if(treeElement.operatorText == CommonUtility.starSign) {
                //star operator
                //calc to child
                calcFirstLast(treeElement.singleChild);
                //first  = child first
                treeElement.firstList.addAll(treeElement.singleChild.firstList);
                //last = child follow
                treeElement.lastList.addAll(treeElement.singleChild.lastList);
            } else if(treeElement.operatorText == CommonUtility.orSign) {
                //or operator
                //calc left and right child
                calcFirstLast(treeElement.leftChild);
                calcFirstLast(treeElement.rightChild);
                //first = sum first of childs
                treeElement.firstList.addAll(treeElement.leftChild.firstList);
                treeElement.firstList.addAll(treeElement.rightChild.firstList);
                // last = sum last of childs
                treeElement.lastList.addAll(treeElement.leftChild.lastList);
                treeElement.lastList.addAll(treeElement.rightChild.lastList);
            } else {
                //and operator
                //calc left and right child
                calcFirstLast(treeElement.leftChild);
                calcFirstLast(treeElement.rightChild);
                //first = if left nullable -> both else left
                treeElement.firstList.addAll(treeElement.leftChild.firstList);
                if (treeElement.leftChild.nullable) {
                    treeElement.firstList.addAll(treeElement.rightChild.firstList);
                }
                // last = if right nullable -> both else right
                treeElement.lastList.addAll(treeElement.rightChild.lastList);
                if (treeElement.rightChild.nullable) {
                    treeElement.lastList.addAll(treeElement.leftChild.lastList);
                }
            }
        }
    }

    public void calcFollow(DFAPart treeElement) {
        if(treeElement.typeTreePart) {
            //if element is leaf
            //do not do anything
        } else {
            //if element is node
            if(treeElement.operatorText == (CommonUtility.starSign)) {
                //run for child
                calcFollow(treeElement.singleChild);
                //star operator
                //for cross list of last
                for(Integer lastNumber: treeElement.lastList) {
                    //add first list to set
                    followMap.get(lastNumber).addAll(treeElement.firstList);
                }
            } else if(treeElement.operatorText == (CommonUtility.andSign)) {
                //and operator
                //run for childs
                calcFollow(treeElement.leftChild);
                calcFollow(treeElement.rightChild);
                //forInteger across last left
                for(Integer lastNumber: treeElement.leftChild.lastList) {
                    //add to first right list to set
                    followMap.get(lastNumber).addAll(treeElement.rightChild.firstList);
                }
            } else {
                //or operator
                //run for childs
                calcFollow(treeElement.leftChild);
                calcFollow(treeElement.rightChild);
                //do not do anything
            }
        }
    }

    public void printFollow() {
        for(Integer i = 1; i <= followMap.size(); i++) {
            System.out.println(i + ": " + followMap.get(i));
        }
    }

    void addToTransitionData(String word, Integer number) {
        //check if word exist
        if(!transitionData.containsKey(word)) {
            //if do not exist add new value to set
            transitionData.put(word, new HashSet<Integer>());
        }
        //add number to correct word
        transitionData.get(word).add(number);
    }
    public void generateTransitionTable() {
        Map<String, HashSet<Integer>> transitionProductionToRecalculate = new HashMap<>();

        transitionProduction.put(String.valueOf(currentSymbol), rootOfTree.firstList);
        transitionProductionToRecalculate.put(String.valueOf(currentSymbol), rootOfTree.firstList);

        calculateTransitionTable(transitionProductionToRecalculate);
    }
    void calculateTransitionTable(Map<String, HashSet<Integer>> transitionProductionToCalculate) {
        Map<String, HashSet<Integer>> transitionProductionToRecalculate = new HashMap<>();
        Boolean toAdd;
        String endState;
        //for across state
        for(String i: transitionProductionToCalculate.keySet()) {
            //for across words
            for(String j: transitionData.keySet()){
                endState = j;
                toAdd = true;
                HashSet<Integer> transitionList = new HashSet<>();
                //generate list from follow
                for(Integer tPTC: transitionProductionToCalculate.get(i)){
                    if(transitionData.get(j).contains(tPTC)) {
                        transitionList.addAll(followMap.get(tPTC));
                    }
                }

                //check if exist on list
                for(String k: transitionProduction.keySet()) {
                    if(transitionList.containsAll(transitionProduction.get(k)) && transitionProduction.get(k).containsAll(transitionList)) {
                        toAdd = false;
                        endState = k;
                    }
                }
                if(toAdd) {
                    transitionProduction.put(String.valueOf(++currentSymbol),transitionList);
                    endState = String.valueOf(currentSymbol);
                    System.out.println(transitionProduction);
                    transitionProductionToRecalculate.put(String.valueOf(currentSymbol),transitionList);
                }
                transitionTable.add(new TransitionTableElement(i, j, transitionList ,endState));

            }
        }

        if(transitionProductionToRecalculate.size() > 0) {
            calculateTransitionTable(transitionProductionToRecalculate);
        }
    }

    public void printTransitionTable() {
        for(TransitionTableElement t: transitionTable) {
            System.out.println(t.beginState + "\t" + t.word + "\t" + t.endState + "\t" + t.numberSet);
        }
    }
    public void printFinalState() {
        finalState = rootOfTree.rightChild.nodeNumber;
        System.out.println("Final State/States:");
        for(String production: transitionProduction.keySet()) {
            if(transitionProduction.get(production).contains(finalState)){
                System.out.println(production);
            }
        }
    }

    public ArrayList<TransitionSolverElement> generateTransitionCheckList(ArrayList<String> wordListToVerify) {

        ArrayList<TransitionSolverElement> answerList = new ArrayList<>();
        TransitionSolverElement tempElement;

        ArrayList<String> inputList = new ArrayList<>();
        inputList.addAll(wordListToVerify);
        String currentState = "A";
        String currentInput;
        Boolean transitionExist;
        while(true) {
            //if empty input and final state
            if(inputList.isEmpty() && transitionProduction.get(currentState).contains(finalState)) {
                tempElement = new TransitionSolverElement("", "Correct", "", false);
                answerList.add(tempElement);
                return answerList;
            }
            //if empty input and not final state
            if(inputList.isEmpty() && !transitionProduction.get(currentState).contains(finalState)) {
                tempElement = new TransitionSolverElement("", "Incorrect", "", false);
                answerList.add(tempElement);
                return answerList;
            }

            currentInput = inputList.get(0);
            inputList.remove(0);
            transitionExist = false;
            //check if exist correct production on Transition producitonlist
             for(TransitionTableElement tte: transitionTable) {
                 //if exist
                 if(tte.beginState.equals(currentState) && tte.word.equals(currentInput)) {
                     transitionExist = true;
                     inputList.toString();
                     tempElement = new TransitionSolverElement(currentState, inputList.toString(), tte.endState, true);
                     answerList.add(tempElement);
                     currentState = tte.endState;

                     break;
                 }
             }

             if(!transitionExist) {
                 tempElement = new TransitionSolverElement("", CommonUtility.getKey("firstFollow.BeginError") + currentState + " " +
                         "" + CommonUtility.getKey("firstFollow.EndError") + currentInput, "", false);
                 answerList.add(tempElement);
                 return answerList;
             }

        }
    }

}