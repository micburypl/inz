package graph;

import algorithmFLF.FLFPart;
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

public class GraphThreeMethods {
    mxGraph mainGraph;
    Map<Integer, mxCell> vertexMap;
    List<mxCell> cellList;
    List<mxCell> vertexList;
    public mxGraphComponent graphComponent;
    Integer x;
    Integer y;
    Boolean shift;

    public GraphThreeMethods(List<FLFPart> outputList){
        shift = true;
        x = 0;
        y = 0;

        mainGraph = new mxGraph();
        GraphUtils.setStyles(mainGraph);
        vertexMap = new HashMap<>();
        vertexList = new ArrayList<>();
        cellList = new ArrayList<>();

        //create cell
        for(FLFPart element: outputList) {
            addVertex(element);
        }
        for(FLFPart element: outputList) {
            addCell(element);
        }


        mxHierarchicalLayout layout = new mxHierarchicalLayout(mainGraph);
        layout.execute(mainGraph.getDefaultParent());
        graphComponent = new mxGraphComponent(mainGraph);
        graphComponent.setEnabled(false);

    }



    void addVertex(FLFPart cellElement) {
        String tempString;
        if(!cellElement.typeTreePart) {
            // operator
            tempString = String.valueOf(cellElement.operatorText) + "\n el. " + cellElement.controlNumber;
        } else {
            // symbol
            tempString = cellElement.symbolsText + "\n el. " + cellElement.controlNumber + "\n(" + cellElement.nodeNumber + ")";
        }
        //tempString += " / element" + cellElement.controlNumber;
        mxCell cell = (mxCell)  mainGraph.insertVertex(mainGraph.getDefaultParent(), null, tempString, x, y, 50, 50, GraphUtils.REGULAR_STYLE);

        if (shift) {
            x += 100;
            shift = false;
        } else {
            y += 100;
            shift = true;
        }
        vertexMap.put(cellElement.controlNumber, cell);
    }

    void addCell(FLFPart cellElement) {

        if(!cellElement.typeTreePart) {
            // operator
            if(cellElement.typeChild) {
                //star
                mxCell cell = (mxCell) mainGraph.insertEdge(mainGraph.getDefaultParent(), null, "", vertexMap.get(cellElement.controlNumber), vertexMap.get(cellElement.singleChild.controlNumber),"Style");
            } else {
                //not star
                mxCell cell1 = (mxCell) mainGraph.insertEdge(mainGraph.getDefaultParent(), null, "", vertexMap.get(cellElement.controlNumber), vertexMap.get(cellElement.leftChild.controlNumber),"Style");
                mxCell cell2 = (mxCell) mainGraph.insertEdge(mainGraph.getDefaultParent(), null, "", vertexMap.get(cellElement.controlNumber), vertexMap.get(cellElement.rightChild.controlNumber),"Style");
            }
            // symbol
            //do not do nithing
        }
    }


}
