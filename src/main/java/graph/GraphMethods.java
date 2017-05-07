package graph;

import algorithmFLF.TransitionTableElement;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
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
    //public mxRadialTreeLayout layout;
    Map<String, mxCell> vertexMap;
    List<mxCell> cellList;
    List<mxCell> vertexList;
    public mxGraphComponent graphComponent;
    SwingNode node;
    Integer x;
    Integer y;
    Boolean shift;

    public GraphMethods(Map<String, Boolean> vertexLabelList, ArrayList<TransitionTableElement> transitionTableList){
        shift = true;
        x = 0;
        y = 0;

        mainGraph = new mxGraph();
        GraphUtils.setStyles(mainGraph);
        vertexMap = new HashMap<>();
        vertexList = new ArrayList<>();
        cellList = new ArrayList<>();
        addVertexList(vertexLabelList);
        addCellList(transitionTableList);

        mxHierarchicalLayout layout = new mxHierarchicalLayout(mainGraph);
        layout.execute(mainGraph.getDefaultParent());
        graphComponent = new mxGraphComponent(mainGraph);
        graphComponent.setEnabled(false);

    }

    void addVertexList(Map<String, Boolean> vertexLabelList) {
        for(String vertexLabel: vertexLabelList.keySet()) {
            if (vertexLabelList.get(vertexLabel)) {
                addVertex(vertexLabel, true);
            } else {
                addVertex(vertexLabel, false);
            }

        }
    }

    void addVertex(String label, Boolean finalState) {
        mxCell cell;
        if(finalState){
            cell = (mxCell)  mainGraph.insertVertex(mainGraph.getDefaultParent(), null, label, x, y, 50, 50, GraphUtils.END_STATE_STYLE);
        } else {
            cell = (mxCell)  mainGraph.insertVertex(mainGraph.getDefaultParent(), null, label, x, y, 50, 50, GraphUtils.REGULAR_STYLE);
        }

        if (shift) {
            x += 100;
            shift = false;
        } else {
            y += 100;
            shift = true;
        }
        vertexMap.put(label, cell);
    }

    void addCellList(ArrayList<TransitionTableElement> transitionTableList) {
        for(TransitionTableElement tte: transitionTableList) {
            addCell(tte.word, tte.beginState, tte.endState);
        }
    }

    void addCell(String label, String begin, String end) {
        mxCell cell = (mxCell) mainGraph.insertEdge(mainGraph.getDefaultParent(), null, label, vertexMap.get(begin), vertexMap.get(end),"Style");
    }


}
