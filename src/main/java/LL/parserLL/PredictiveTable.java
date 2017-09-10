package LL.parserLL;

import FF.firstFollow.FirstElement;
import FF.firstFollow.FollowElement;
import commonUtility.CommonUtility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PredictiveTable {
    public Map<String, Map<String, ArrayList<ArrayList<String>>>> predictiveMap;


    public PredictiveTable() {
        predictiveMap = new HashMap<>();
    }

    public void generatePredictiveMap(Map<String, ArrayList<ArrayList<String>>> parsedSet, Map<String, FirstElement> firstElementMap, Map<String, FollowElement> followElementMap) {

        List<String> nonterminalListFromFirstSet;

        for(String symbol: parsedSet.keySet()) {
            //for across nonterminal
            if(parsedSet.get(symbol) != null && !parsedSet.get(symbol).isEmpty()) {
                //if there is any production
                for(ArrayList<String> symbolSets: parsedSet.get(symbol)) {
                    nonterminalListFromFirstSet = new ArrayList<>();
                    //get first from symbolSet
                    if(CommonUtility.isTerminal(symbolSets.get(0))) {
                        nonterminalListFromFirstSet.add(symbolSets.get(0));
                    } else {
                        nonterminalListFromFirstSet.addAll(firstElementMap.get(symbolSets.get(0)).firstSet);
                    }
                    //--
                    //input production to element position from first
                    //--
                    if(nonterminalListFromFirstSet != null && !nonterminalListFromFirstSet.isEmpty()) {
                        //check if symbol exist on predicitve Map
                        if(!predictiveMap.containsKey(symbol)) {
                            //if not add this
                            predictiveMap.put(symbol, new HashMap<>());
                        }
                        //for all element on non-terminalListFromFirstSet add production
                        for(String nonteminal: nonterminalListFromFirstSet) {
                            //check if symbol contains nonterminal on map

                            if(nonteminal.equals(CommonUtility.epsValue)) {
                                //if it is eps transition
                                //for across follow set
                                if(followElementMap.containsKey(symbol) && !followElementMap.get(symbol).followSet.isEmpty()){
                                    for(String followNonterminal: followElementMap.get(symbol).followSet) {
                                        if(!predictiveMap.get(symbol).containsKey(followNonterminal)) {
                                            predictiveMap.get(symbol).put(followNonterminal, new ArrayList<>());
                                        }
                                        predictiveMap.get(symbol).get(followNonterminal).add(symbolSets);
                                    }
                                }
                            } else {
                                //if not only add this element
                                if(!predictiveMap.get(symbol).containsKey(nonteminal)) {
                                    //if not add this
                                    predictiveMap.get(symbol).put(nonteminal, new ArrayList<>());
                                }
                                predictiveMap.get(symbol).get(nonteminal).add(symbolSets);
                            }
                        }
                    }
                }
            }
        }
        System.out.println(predictiveMap);
    }


}
