package parserLL;

import java.util.ArrayList;

/**
 * Created by DELL6430u on 2017-04-15.
 */
public class MovesTableElement {
    public String stack;
    public String input;
    public String output;


    MovesTableElement(ArrayList<String> newStack, ArrayList<String> newInput, String newOutputLeft, ArrayList<String> newOutputRight) {

        //stack
        stack = "$";
        for(String s: newStack) {
            stack = stack + s;
        }

        //input
        input = "";
        for(String s: newInput) {
            input = input + s;
        }
        input = input + "$";

        //output
        output = "";
        if(newOutputRight != null && !newOutputRight.isEmpty()) {
            output = newOutputLeft;
            output = output + " -> ";
            for(String s: newOutputRight) {
                output = output + s;
            }
        }
    }
}
