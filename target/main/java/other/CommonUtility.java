package other;

class CommonUtility {
    final static String epsValue = "eps";
    final static String dolarValue = "$";

    static Boolean isTerminal(String testString) {
        if(testString.charAt(0) >= 'a' && testString.charAt(0) <= 'z') {
            return true;
        }
        return false;
    }

    static Boolean isTerminal(char testChar) {
        if(testChar >= 'a' && testChar <= 'z') {
            return true;
        }
        return false;
    }
}
