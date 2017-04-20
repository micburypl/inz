package parserLR;

/**
 * Created by DELL6430u on 2017-04-17.
 */
public class GotoTransition {
    public Integer from;
    public Integer to;
    public String value;

    GotoTransition(Integer f, Integer t, String v) {
        from = f;
        to = t;
        value = v;
    }
}
