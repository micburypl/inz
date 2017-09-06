package LR.parserLR;

import commonUtility.CommonUtility;
import FF.firstFollow.FirstFollow;
import FF.firstFollow.FollowElement;

import java.util.*;

/**
 * Created by DELL6430u on 2017-04-16.
 */

public class ParserLR {
    public FirstFollow firstFollowSolution;
    public Map<String, FollowElement> followElementMap;
    public Map<String, ArrayList<ArrayList<String>>> parsedSet;
    public String firstProduction;
    public Map<Integer, Map<String, Integer>> gotoTable;
    public Map<Integer, Map<String, ActionTableElement>> actionTable;
    public GotoGenerator gotoGeneratorMap;
    public ArrayList<MovesElementLR> movesList;
    public Boolean correctExit = false;
    public String startElement;


    public ParserLR(List<String> inputList) {

        gotoTable = new HashMap<>();
        actionTable = new HashMap<>();
        movesList = new ArrayList<>();



        //get follow set
        firstFollowSolution = new FirstFollow(inputList);
        firstFollowSolution.generateSolutionSet();



        if(firstFollowSolution.errorFlag) {
            return;
        }
        ////find startElement
        if(inputList != null && inputList.size() > 0) {
            startElement = inputList.get(0).substring(0, inputList.get(0).indexOf("->")-1);
            for(String s: inputList) {
                if(s.contains("S'")) {
                    firstFollowSolution.errorFlag = true;
                    firstFollowSolution.errorMessages.put(0, CommonUtility.getKey("parserLR.WrongSign"));
                    return;
                }
            }
        }

        followElementMap = new HashMap<>();
        followElementMap = firstFollowSolution.followElementMap;

        parsedSet = new HashMap<>();
        parsedSet = firstFollowSolution.parsedSet;

        firstProduction = firstFollowSolution.firstProduction;

        gotoGeneratorMap = new GotoGenerator();

        gotoGeneratorMap.generateGoto(startElement, parsedSet);

        generateLRSolution();
    }

    private void generateLRSolution() {

        //add elements from

        for(GotoTransition tempTransition: gotoGeneratorMap.gotoTransitionList) {

            //check where input this 'data'
            if(gotoGeneratorMap.firstElementMap.containsKey(tempTransition.value)) {
                //if Transition is non-terminal (exist in keyset)
                if(!gotoTable.containsKey(tempTransition.from)) {
                    gotoTable.put(tempTransition.from, new HashMap<>());
                }
                gotoTable.get(tempTransition.from).put(tempTransition.value, tempTransition.to);
            } else {
                //if not add to action table as shift
                if(!actionTable.containsKey(tempTransition.from)) {
                    actionTable.put(tempTransition.from, new HashMap<>());
                }
                actionTable.get(tempTransition.from).put(tempTransition.value, new ActionTableElement(tempTransition.to, true));

            }


        }

        //add reduce element to actionTable
        System.out.println("----------------------------");
        System.out.println("gotoGeneratorMap");
        for(Integer closureElementCounter: gotoGeneratorMap.gotoElementMap.keySet()) {
            for(Integer closureElement: gotoGeneratorMap.gotoElementMap.get(closureElementCounter).closureElementList) {
                if(gotoGeneratorMap.closureElementCombination.get(closureElement).isLastItem) {
                    //for across follow element of this
                    Integer currProdNumber = gotoGeneratorMap.closureElementCombination.get(closureElement).producitonNumber;
                    if(currProdNumber.equals(0)){
                        //final place
                        if(!actionTable.containsKey(closureElementCounter)) {
                            actionTable.put(closureElementCounter, new HashMap<>());
                        }
                        actionTable.get(closureElementCounter).put(CommonUtility.epsValue, new ActionTableElement(currProdNumber, false) );
                        continue;
                    }
                    System.out.println(gotoGeneratorMap.listOfProduction.get(currProdNumber));
                    if(!followElementMap.containsKey(gotoGeneratorMap.listOfProduction.get(currProdNumber).get(0))) {
                        continue;
                    }
                    for(String followElement: followElementMap.get(gotoGeneratorMap.listOfProduction.get(currProdNumber).get(0)).followSet) {
                        if(!actionTable.containsKey(closureElementCounter)) {
                            actionTable.put(closureElementCounter, new HashMap<>());
                        }
                        actionTable.get(closureElementCounter).put(followElement, new ActionTableElement(currProdNumber, false) );
                    }
                }
            }
        }

        // add final state elemen to list

        for(Integer ge: gotoGeneratorMap.gotoElementMap.keySet()) {
            if(gotoGeneratorMap.gotoElementMap.get(ge).closureElementList.contains(gotoGeneratorMap.correctFinal.counterValue)) {

                actionTable.get(ge).put("eps", new ActionTableElement(-1, false) );
            }
        }


        System.out.println("gotoGeneratorMap END");
        System.out.println("----------------------------");

        System.out.println("----------------------------");
        System.out.println("actionTable");
        for(Integer i: actionTable.keySet()) {
            System.out.println(i);
            for(String s: actionTable.get(i).keySet()) {
                System.out.println(s + "\t" + actionTable.get(i).get(s).value + "\t" + actionTable.get(i).get(s).isShift);
            }

        }


        System.out.println("actionTable END");
        System.out.println("----------------------------");

        System.out.println("----------------------------");
        System.out.println("gotoTable");
        System.out.println(gotoTable);
        System.out.println("gotoTable END");
        System.out.println("----------------------------");


    }

    public void generateLRParser(String inputData) {
        MovesElementLR currentMovesElementLR;
        ArrayList<String> inputDataSet = CommonUtility.parsedInputBySpace(inputData);
        inputDataSet.add(CommonUtility.epsValue);
        Integer movesNumber = 1;
        Stack<String> stack = new Stack<>();
        stack.push("0");
        Integer tempInt;
        String tempString;
        String errorMessage;
        currentMovesElementLR = new MovesElementLR(movesNumber++, new ArrayList<>(stack) , new ArrayList<>(inputDataSet) );
        movesList = new ArrayList<>();
        movesList.add(currentMovesElementLR);


        while(true) {



            if(!actionTable.containsKey(Integer.parseInt(stack.peek()))) {
                System.out.println(CommonUtility.getKey("parserLR.ActionRow") + " " + stack.peek());

                errorMessage = CommonUtility.getKey("parserLR.ActionRow") + " " + stack.peek();
                currentMovesElementLR = new MovesElementLR(errorMessage);
                movesList.add(currentMovesElementLR);

                break;
            }

            if(!actionTable.get(Integer.parseInt(stack.peek())).containsKey(inputDataSet.get(0))) {
                System.out.println("There is not this value on ACTION table (column): " + stack.peek());

                errorMessage = CommonUtility.getKey("parserLR.ActionCol") + " " + stack.peek();
                currentMovesElementLR = new MovesElementLR(errorMessage);
                movesList.add(currentMovesElementLR);

                break;
            }

            if(actionTable.get(Integer.parseInt(stack.peek())).get(inputDataSet.get(0)).value.equals(-1)) {
                //correct end
                correctExit = true;
                break;

            }
            tempInt = actionTable.get(Integer.parseInt(stack.peek())).get(inputDataSet.get(0)).value;
            if(actionTable.get(Integer.parseInt(stack.peek())).get(inputDataSet.get(0)).isShift) {
                //is shift
                //first element go to top stack, add to stack value from action table
                stack.push(inputDataSet.get(0));
                stack.push(String.valueOf(tempInt));
                inputDataSet.remove(0);
                currentMovesElementLR = new MovesElementLR(movesNumber++, new ArrayList<>(stack) , new ArrayList<>(inputDataSet) );
                movesList.add(currentMovesElementLR);
            } else {
                //is reduce

                for(Integer i = 0; i < gotoGeneratorMap.listOfProduction.get(tempInt).size()-2 ; i++) {
                    stack.pop();
                    stack.pop();
                }

                tempString = stack.peek();
                stack.push(gotoGeneratorMap.listOfProduction.get(tempInt).get(0));

                //verify if goto table have value

                if(!gotoTable.containsKey(Integer.parseInt(tempString))) {
                    System.out.println("There is not this value on GOTO table (row): " + tempString);

                    errorMessage = CommonUtility.getKey("parserLR.GotoRow") + " " + tempString;
                    currentMovesElementLR = new MovesElementLR(errorMessage);
                    movesList.add(currentMovesElementLR);

                    break;
                }
                if(!gotoTable.get(Integer.parseInt(tempString)).containsKey(stack.peek())) {
                    System.out.println("There is not this value on GOTO table (column): " + inputDataSet.get(0));

                    errorMessage = CommonUtility.getKey("parserLR.GotoCol") + " " + inputDataSet.get(0);
                    currentMovesElementLR = new MovesElementLR(errorMessage);
                    movesList.add(currentMovesElementLR);

                    break;
                }
                //if exist add to stack
                stack.push(String.valueOf(gotoTable.get(Integer.parseInt(tempString)).get(stack.peek())));

                currentMovesElementLR = new MovesElementLR(movesNumber++, new ArrayList<>(stack) , new ArrayList<>(inputDataSet) );
                movesList.add(currentMovesElementLR);
            }

//            for(MovesElementLR mElem: movesList) {
//                System.out.println(mElem.movesNumber);
//                System.out.println(mElem.stack);
//                System.out.println(mElem.input);
//            }


        }

        if(correctExit) {
            while(stack.size() > 1) {
                stack.pop();
            }
            movesList.add(new MovesElementLR(movesNumber++, new ArrayList<>(stack) , new ArrayList<>(inputDataSet)));
            for(MovesElementLR mElem: movesList) {
                System.out.println(mElem.movesNumber);
                System.out.println(mElem.stack);
                System.out.println(mElem.input);
            }
        }


    }

}
