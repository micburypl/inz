import java.util.HashSet;
import java.util.Set;

/**
 * Created on 23.03.2017.
 */
public class FirstElement {
    String value;
    Boolean isTerminal;
    Set<String> firstSet;

    FirstElement(String v) {
        firstSet = new HashSet<String>();
        value = v;
        isTerminal = !(value.charAt(0) >= 'A' && value.charAt(0) <= 'Z');
        if(isTerminal) {
            firstSet.add(v);
        }
    }
}
