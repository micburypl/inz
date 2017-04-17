package commonUtility;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommonUtility {
    public final static String epsValue = "eps";
    final static String dolarValue = "$";
    public final static String arrow = "->";
    public final static String beginGoto = "S'";
    public final static String dot = ".";

    public static Boolean isTerminal(String testString) {
        if (testString.charAt(0) >= 'a' && testString.charAt(0) <= 'z') {
            return true;
        }
        return false;
    }

    static Boolean isTerminal(char testChar) {
        if (testChar >= 'a' && testChar <= 'z') {
            return true;
        }
        return false;
    }

    public static Boolean isNonTerminal(String testString) {
        if (testString.charAt(0) >= 'A' && testString.charAt(0) <= 'Z') {
            return true;
        }
        return false;
    }

    static Boolean isNonTerminal(char testChar) {
        if (testChar >= 'A' && testChar <= 'Z') {
            return true;
        }
        return false;
    }

    public static ArrayList<String> parsedInputBySpace(String inputData) {

        while (true) {
            if (inputData.contains("  ")) {
                inputData = inputData.replaceAll("  ", " ");
            } else {
                break;
            }
        }
        //parse string to list of element
        String[] newList = inputData.split(" ");

        ArrayList<String> newListToReturn = new ArrayList<String>();

        for (String s : newList) {
            newListToReturn.add(s);
        }
        return newListToReturn;
    }

    public static ArrayList<String> generateList(String one, String two, String three, String four) {
        ArrayList<String> returnList = new ArrayList<>();
        returnList.add(one);
        returnList.add(two);
        returnList.add(three);
        returnList.add(four);
        return returnList;
    }
}
