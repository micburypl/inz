package parserLR;

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
//TODO create goto element
//TODO generate list with element
//TODO generate list with transition element
// construct generate follow set (necessary to Action table)
//TODO construct action table
//TODO construct goto table
//TODO construct moves table

public class ParserLR {
    public FirstFollow firstFollowSolution;
    public Map<String, FollowElement> followElementMap;
    public Map<String, ArrayList<ArrayList<String>>> parsedSet;
    public String firstProduction;


    public ParserLR(List<String> inputList) {

        //get follow set
        firstFollowSolution = new FirstFollow(inputList);
        firstFollowSolution.generateSolutionSet();
        followElementMap = new HashMap<>();
        followElementMap = firstFollowSolution.followElementMap;

        parsedSet = new HashMap<>();
        parsedSet = firstFollowSolution.parsedSet;

        firstProduction = firstFollowSolution.firstProduction;



    }
}
