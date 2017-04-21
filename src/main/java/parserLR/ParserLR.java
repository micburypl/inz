package parserLR;

import commonUtility.CommonUtility;
import firstFollow.FirstFollow;
import firstFollow.FollowElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DELL6430u on 2017-04-16.
 */

// parse input to list of produciton
// create goto element
// generate list with element
// generate list with transition element
// construct generate follow set (necessary to Action table)
//TODO construct action table
//TODO construct goto table
//TODO construct moves table

public class ParserLR {
    public FirstFollow firstFollowSolution;
    public Map<String, FollowElement> followElementMap;
    public Map<String, ArrayList<ArrayList<String>>> parsedSet;
    public String firstProduction;
    public Map<Integer, Map<String, Integer>> gotoTable;
    public Map<Integer, Map<String, ActionTableElement>> actionTable;
    public GotoGenerator gotoGeneratorMap;


    public ParserLR(List<String> inputList) {

        gotoTable = new HashMap<>();
        actionTable = new HashMap<>();

        //get follow set
        firstFollowSolution = new FirstFollow(inputList);
        firstFollowSolution.generateSolutionSet();
        followElementMap = new HashMap<>();
        followElementMap = firstFollowSolution.followElementMap;

        parsedSet = new HashMap<>();
        parsedSet = firstFollowSolution.parsedSet;

        firstProduction = firstFollowSolution.firstProduction;

        gotoGeneratorMap = new GotoGenerator();
        gotoGeneratorMap.generateGoto("S", parsedSet);

        generateLRSolution();
    }

    public void generateLRSolution() {

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
                    for(String followElement: followElementMap.get(gotoGeneratorMap.listOfProduction.get(currProdNumber).get(0)).followSet) {
                        //tutaj powinno być prawidlowo // hehe działa :D
                        if(!actionTable.containsKey(closureElementCounter)) {
                            actionTable.put(closureElementCounter, new HashMap<>());
                        }
                        actionTable.get(closureElementCounter).put(followElement, new ActionTableElement(currProdNumber, false) );
                    }
                }
            }
        }


        for(Integer i: actionTable.keySet()) {
            System.out.println(i);
            for(String s: actionTable.get(i).keySet()) {
                System.out.println(s + "\t" + actionTable.get(i).get(s).value + "\t" + actionTable.get(i).get(s).isShift);
            }

        }

        //System.out.println(actionTable);
        System.out.println(gotoTable);

    }

}
