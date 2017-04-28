package commonUtility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CommonUtility {
    public final static String epsValue = "eps";
    public final static String dolarValue = "$";
    public final static String arrow = "->";
    public final static String beginGoto = "S'";
    public final static String dot = ".";

    public static Boolean isTerminal(String testString) {
        return testString.charAt(0) >= 'a' && testString.charAt(0) <= 'z';
    }

    public static Boolean isNonTerminal(String testString) {
        return testString.charAt(0) >= 'A' && testString.charAt(0) <= 'Z';
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

        ArrayList<String> newListToReturn = new ArrayList<>();

        newListToReturn.addAll(Arrays.asList(newList));
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

    public static ArrayList<String> generateList(String one, String two, ArrayList<String> three) {
        ArrayList<String> returnList = new ArrayList<>();
        returnList.add(one);
        returnList.add(two);
        returnList.addAll(three);
        return returnList;
    }
}
