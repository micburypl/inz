package FF.firstFollow.firstFollowTestData;

/**
 * Created on 09.05.2017.
 */
public class FirstFollowTestSetElement {

    public String leftPart;
    public String rightPart;

    FirstFollowTestSetElement (String lp, String rp) {
        leftPart = lp;
        rightPart = rp;
    }

    static FirstFollowTestSetElement generateFFTSE (String lp, String rp) {
        FirstFollowTestSetElement tempElement = new FirstFollowTestSetElement(lp, rp);
        return tempElement;

    }
}
