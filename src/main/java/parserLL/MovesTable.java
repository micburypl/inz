package parserLL;

import commonUtility.CommonUtility;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by DELL6430u on 2017-04-15.
 */
public class MovesTable {
    public ArrayList<MovesTableElement> movesList;

    public MovesTable() {
        movesList = new ArrayList<>();
    }

    public void generateMovesTable(String startElement, String inputData, Map<String, Map<String, ArrayList<ArrayList<String>>>> predictiveMap) {
        MovesTableElement currentElement;
        ArrayList<String> inputDataSet = CommonUtility.parsedInputBySpace(inputData);
        ArrayList<String> stack = new ArrayList<>();
        ArrayList<String> productionListToAddToStack = new ArrayList<>();
        stack.add(startElement);
        String symbol;
        String terminal;

        //elementToAdd = new MovesTableElement(stack, inputDataSet, new ArrayList<>());

        while(true) {

            //check if input is not empty
            if(inputDataSet.isEmpty()) {
                if(stack.isEmpty()) {
                    break;
                } else {
                    //check if top stack element has eps tranition
                    if(predictiveMap.containsKey(stack.get(stack.size()-1)) && predictiveMap.get(stack.get(stack.size()-1)).containsKey(CommonUtility.epsValue) && !predictiveMap.get(stack.get(stack.size()-1)).get(CommonUtility.epsValue).isEmpty()) {


                        //if yes remove element
                        currentElement = new MovesTableElement(stack, inputDataSet, stack.get(stack.size()-1), predictiveMap.get(stack.get(stack.size()-1)).get(CommonUtility.epsValue).get(0));
                        movesList.add(currentElement);
                        stack.remove(stack.size()-1);
                        continue;
                    }

                    System.out.println("input is already empty, stack not");
                    return;
                }
            }
            //check if stack is empty
            if(stack.isEmpty()) {
                System.out.println("stack is already empty, input not");
                return;
            }


            //if top of stack and first element of input are equal erase them
            if(stack.get(stack.size()-1).equals(inputDataSet.get(0))) {
                currentElement = new MovesTableElement(stack, inputDataSet, stack.get(stack.size()-1), new ArrayList<>());
                movesList.add(currentElement);
                stack.remove(stack.size()-1);
                inputDataSet.remove(0);
                continue;
            }
            //check if symbol exist on predictiveMap
            if(!predictiveMap.containsKey(stack.get(stack.size()-1))) {
                System.out.println("in Predictive map there is not element from top of stack");
                return;
            }
            //check if on map exist prediction
            symbol = stack.get(stack.size()-1);
            terminal = inputDataSet.get(0);
            if(predictiveMap.containsKey(symbol) && predictiveMap.get(symbol).containsKey(terminal) && !predictiveMap.get(symbol).get(terminal).isEmpty()) {
                //if exist
                //get produciton from table
                productionListToAddToStack = new ArrayList<>();
                productionListToAddToStack.addAll(predictiveMap.get(symbol).get(terminal).get(0));
                //add current values to list
                currentElement = new MovesTableElement(stack, inputDataSet, symbol, productionListToAddToStack);
                movesList.add(currentElement);

                //remove last element on stack
                stack.remove(stack.size()-1);
                //add production elements to stack in reverse

                if(!productionListToAddToStack.get(0).equals(CommonUtility.epsValue)){
                    for(Integer i = productionListToAddToStack.size()-1; i >= 0; i--) {
                        stack.add(productionListToAddToStack.get(i));
                    }
                }
            } else {
                //if not error message
                System.out.println("Trere is not Symbol: " + symbol + ", with production in terminal: " + terminal);
            }
        }
        currentElement = new MovesTableElement(stack, inputDataSet, "", new ArrayList<>());
        movesList.add(currentElement);
    }

    public void printMovesTable() {
        Integer i = 1;
        for(MovesTableElement mte: movesList) {
            System.out.println("-------");
            System.out.println(i);
            System.out.println("Stack:  " + mte.stack);
            System.out.println("Input:  " + mte.input);
            System.out.println("Output: " + mte.output);
            i++;
        }
    }
}
