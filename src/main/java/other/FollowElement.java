package other;

import java.util.HashSet;
import java.util.Set;


class FollowElement {
    String value;
    Set<String> followSet;

    FollowElement(String v) {
        value = v;
        followSet = new HashSet<>();
    }
}
