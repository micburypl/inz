package graph;

import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxUtils;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;
import com.sun.corba.se.impl.orbutil.graph.Graph;
//import ece.diploma.graph.Graph;

import java.awt.*;
import java.util.Hashtable;

public class GraphUtils {

    static public final String REGULAR_STYLE = "Regular style";
    static public final String END_STATE_STYLE = "End state style";

    public static Hashtable<String, Object> style = new Hashtable<String, Object>();
    public static Hashtable<String, Object> endStyle = new Hashtable<String, Object>();

    static public void setStyles(mxGraph graph){

        prepareStyles();
        mxStylesheet stylesheet = graph.getStylesheet();
        stylesheet.putCellStyle(REGULAR_STYLE, style);
        stylesheet.putCellStyle(END_STATE_STYLE, endStyle);
    }

    private static void prepareStyles(){

        style.put(mxConstants.STYLE_FILLCOLOR, mxUtils.getHexColorString(Color.WHITE));
        style.put(mxConstants.STYLE_STROKEWIDTH, 1.5);
        style.put(mxConstants.STYLE_STROKECOLOR, mxUtils.getHexColorString(new Color(0, 0, 170)));
        style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
        style.put(mxConstants.STYLE_PERIMETER, mxConstants.PERIMETER_ELLIPSE);

        endStyle.put(mxConstants.STYLE_FILLCOLOR, mxUtils.getHexColorString(Color.WHITE));
        endStyle.put(mxConstants.STYLE_STROKEWIDTH, 1.5);
        endStyle.put(mxConstants.STYLE_STROKECOLOR, mxUtils.getHexColorString(new Color(0, 0, 170)));
        endStyle.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_DOUBLE_ELLIPSE);
        endStyle.put(mxConstants.STYLE_PERIMETER, mxConstants.PERIMETER_ELLIPSE);
    }

//    static public void setStartLabel(Graph graph){
//        graph.getStartVertex().setLabel("START");
//    }

}
