package firstFollow;

import java.util.HashSet;
import java.util.Set;


public class FollowElement {
    String value;
    public Set<String> followSet;

    FollowElement(String v) {
        value = v;
        followSet = new HashSet<>();
    }
}
