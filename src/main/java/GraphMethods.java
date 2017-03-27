import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import javafx.embed.swing.SwingNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DELL6430u on 2017-03-27.
 */
public class GraphMethods {
    mxGraph mainGraph;
    Map<String, mxCell> vertexMap;
    mxGraphComponent graphComponent;
    SwingNode node;

    GraphMethods() {
        mainGraph = new mxGraph();
        vertexMap = new HashMap<>();
    }

    GraphMethods(ArrayList<String> vertexLabelList, ArrayList<TransitionTableElement> transitionTableList){
        mainGraph = new mxGraph();
        vertexMap = new HashMap<>();
        addVertexList(vertexLabelList);
        addEdgeList(transitionTableList);
        node = new SwingNode();
        graphComponent = new mxGraphComponent(mainGraph);
        graphComponent.setEnabled(false);
        node.setContent(graphComponent);
    }

    void addVertexList(ArrayList<String> vertexLabelList) {
        for(String vertexLabel: vertexLabelList) {
            addVertex(vertexLabel);
        }
    }

    void addVertex(String label) {
        mxCell cell = (mxCell)  mainGraph.createVertex(mainGraph.getDefaultParent(), null, label, 0, 0, 50, 50, "style");
        vertexMap.put(label, cell);
    }

    void addEdgeList(ArrayList<TransitionTableElement> transitionTableList) {
        for(TransitionTableElement tte: transitionTableList) {
            addEdge(tte.word, tte.beginState, tte.endState);
        }
    }

    void addEdge(String label, String begin, String end) {
        mxCell cell = (mxCell) mainGraph.createEdge(mainGraph.getDefaultParent(), null, label, vertexMap.get(begin), vertexMap.get(end),"Style");

    }


}
