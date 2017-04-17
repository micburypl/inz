package parserLR;

/**
 * Created by DELL6430u on 2017-04-17.
 */
public class GotoTransiiton {
    public Integer from;
    public Integer to;
    public String value;

    GotoTransiiton(Integer f, Integer t, String v) {
        from = f;
        to = t;
        value = v;
    }
}
