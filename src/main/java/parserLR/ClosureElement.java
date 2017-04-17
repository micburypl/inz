package parserLR;

import commonUtility.CommonUtility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL6430u on 2017-04-17.
 */
public class ClosureElement {
    public List<String> production;
    public Integer nextElement;
    public Boolean isLastItem;
    public Boolean beforeNonterminal;
    public Integer counterValue;

    ClosureElement() {
        production = new ArrayList<>();
        nextElement = -1;
        isLastItem = true;
        beforeNonterminal = false;
        counterValue = 0;
    }

    public ClosureElement(Integer counter, ArrayList<String> inputList) {
        //create list
        production = new ArrayList<>();
        production.addAll(inputList);
        //find dot
        Integer tempInt = production.indexOf(CommonUtility.dot);
        //check if place of dot is in the end
        if(tempInt.equals(production.size() - 1)) {
            isLastItem = true;
        } else {
            isLastItem = false;
            //check if element after dot is nonterminal (if exits)
            beforeNonterminal = CommonUtility.isNonTerminal(production.get(tempInt + 1));
        }
        //set counter value
        counterValue = counter;
        //set next element after generation
    }
}
