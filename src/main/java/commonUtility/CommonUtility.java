package commonUtility;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import start.Base;
import start.Start;

import java.util.*;

public class CommonUtility {
    public final static String epsValue = "eps";
    public final static String dolarValue = "$";
    public final static String arrow = "->";
    public final static String beginGoto = "S'";
    public final static String dot = ".";

    public final static char andSign = '&';
    public final static char orSign = '|';
    public final static char starSign = '*';

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

    public static Boolean compareGridPane(GridPane answer, GridPane input, Integer rN, Integer cN, Boolean removeSpace) {
        return compareGridPane(answer, input, rN, cN, removeSpace, 1, 1);
    }


    public static Boolean compareGridPane(GridPane answer, GridPane input, Integer rN, Integer cN, Boolean removeSpace, Integer rowToVerify, Integer columnToVerify) {
        Boolean isCorrect = true;

        Integer columnNumber = cN;
        Integer rowNumber = rN;

        String tempAnswer;
        String tempInput;


        //nie trzeba pierwszych elementów sprawdzać
        for(Integer i = columnToVerify; i < columnNumber; i++) {
            for(Integer j = rowToVerify; j < rowNumber; j++) {

                tempAnswer = getNodeFromGridPane(answer, i, j);
                tempInput = getNodeFromGridPane(input, i, j);

                if(tempAnswer == null) {
                    tempAnswer = "";
                }

                if(tempInput == null) {
                    tempInput = "";
                }

                if(!compareStrings(tempAnswer, tempInput, removeSpace)) {
                    isCorrect = false;
                    printRed(input, i, j);
                } else {
                    printGreen(input, i, j);
                }
            }
        }
        return isCorrect;
    }

    public static Boolean compareStrings(String answerString, String inputStirng, Boolean removeSpace) {
        if(removeSpace) {
            answerString = answerString.replaceAll(" ", "");
            inputStirng = inputStirng.replaceAll(" ", "");
        }

        HashSet<String> answerSet = new HashSet<>();
        for(String s: answerString.split(",")) {
            answerSet.add(s);
        }

        HashSet<String> inputSet = new HashSet<>();
        for(String s: inputStirng.split(",")) {
            inputSet.add(s);
        }

        return answerSet.equals(inputSet);
    }

    public static String getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (node != null && GridPane.getColumnIndex(node) != null && GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) == row) {
                if(node instanceof Label ){
                    return ((Label) node).getText();
                }
                if(node instanceof TextField ){
                    return ((TextField) node).getText();
                }
            }
        }
        return null;
    }

    static void printRed(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (node != null && GridPane.getColumnIndex(node) != null && GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) == row) {
                node.setStyle("-fx-background-color: #ea5d5d;");
                return;
            }
        }
    }

    static void printGreen(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (node != null && GridPane.getColumnIndex(node) != null && GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) == row) {
                node.setStyle("-fx-background-color: #7bea5d;");
                return;
            }
        }
    }

    public static String getKey(String key){

        if(Start.getInstance().getResourceBundle().containsKey(key)){
            return Start.getInstance().getResourceBundle().getString(key);
        }
        return "error label -> " + key;
    }
}
