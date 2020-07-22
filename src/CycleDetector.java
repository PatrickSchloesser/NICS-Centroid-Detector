import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import org.jgrapht.*;
import org.jgrapht.alg.interfaces.*;

public class CycleDetector {

    private ArrayList<ArrayList<Double>> connections;

    private ArrayList<ArrayList<Double>> nodeLess;

    private ArrayList<ArrayList<Integer>> edges;

    private ArrayList<ArrayList<Integer>> nodes;

    private ArrayList<ArrayList<ArrayList<Integer>>> allPaths;

    private ArrayList<ArrayList<Integer>> allEdges;





    private static final int NO_PARENT = -1;

    public CycleDetector(Reader reader){


        ArrayList<ArrayList<Double>> structure = reader.getConnectivitySimple();

        ArrayList fullStructure = reader.getConnectivityComplex();

        connections = fullStructure;
        nodeLess = structure;

//        System.out.println("Full Edges " + connections);
//        System.out.println("Edges " + nodeLess);

        ArrayList coordinates = reader.getCoordinates();
//        System.out.println(coordinates);

        setup();
        int[][] adj = adjacencyList();

       // System.out.println("AdjacencyList: " + adj );

        dijkstra(adj);

//        allCycles();

//        1.	For every pair of vertices (x,y) compute the shortest path P(x,y)  between the two vertices
//        2.	For every combination of each vertex v with every edge (x,y), compute P(x,v) and P(y,v). If the intersection of sets P(x,v) and P(y,v) contains only the node v, then a cycle exists and is added to a list of cycles.
//        3.	Order the cycles by weight
//        4.	Using Gaussian elimination, determine the set of cycles that are linearly independent from one another. This is the minimum cycle basis.
//
//

    }



    private void setup(){
        nodes = new ArrayList<>(connections.size());

        for(int i =0; i < connections.size(); i++){
            ArrayList intReplace = new ArrayList<Integer>();

            for(int j = 0; j < connections.get(i).size(); j = j+2){
                intReplace.add(connections.get(i).get(j).intValue());
            }

            nodes.add(intReplace);

        }

//        System.out.println("Nodes:" +nodes);


        edges = new ArrayList<>(nodeLess.size());
        for(int i =0; i < nodeLess.size(); i++){
            ArrayList intReplace = new ArrayList<Integer>();
            intReplace.add(nodeLess.get(i).get(0).intValue());
            for(int j = 1; j < nodeLess.get(i).size(); j = j+2){
                intReplace.add(nodeLess.get(i).get(j).intValue());
            }
            if(intReplace.size()> 0) {
                edges.add(intReplace);
            }
        }

//        System.out.println("Eges: " +edges);

        allEdges = new ArrayList<>();
        for(int i = 0; i < edges.size(); i++){
            for(int j = 1; j < edges.get(i).size(); j++){
                if(edges.get(i).size()> 1) {
                    ArrayList<Integer> pair = new ArrayList<>();
                    pair.add(edges.get(i).get(0));
                    pair.add(edges.get(i).get(j));
                    allEdges.add(pair);
                }
            }
        }

//        System.out.println("Pairs: " + allEdges);


    }




    public ArrayList<ArrayList> detectCycles(){



        return null;
    }
    // Gives a list of lists where each list corresponds to an atom with 1s at atoms it has a bond with and zeros otherwise
    public int[][] adjacencyList(){
        int dim = nodes.size();

        int[][] adj = new int[dim][dim];

        for(int i = 0; i < dim; i++){
            for(int j = 0; j < nodes.get(i).size(); j++){
                adj[i][nodes.get(i).get(j)-1] = 1;
            }
        }
//        System.out.println("AdjacencyLIst: " + Arrays.deepToString(adj));

        return adj;
    }

    //Returns the following: A list of lists of lists:
    // - The topmost list contains lists for paths starting at each node
    // - The next list down has the lists containing the shortest path from the particular node to every other
    // - node in the graph where the index+1 in the list specifies the node at the end of the path
    private ArrayList dijkstra(int[][] adjacencyMatrix){
        allPaths = new ArrayList<ArrayList<ArrayList<Integer>>>();

        for(int i = 0; i < nodes.size(); i++){
            ArrayList<ArrayList<Integer>> levelDown = new ArrayList<>();
            for(int j = 0; j< nodes.size(); j++){
                levelDown.add(new ArrayList<Integer>());
            }
            allPaths.add(levelDown);
        }
        for(int i = 0; i< nodes.size(); i++){

        }
        for(int i = 0; i < nodes.size(); i++){
            int startVertex = i;
            int nVertices = adjacencyMatrix[0].length;

            // shortestDistances[i] will hold the
            // shortest distance from src to i
            int[] shortestDistances = new int[nVertices];

            // added[i] will true if vertex i is
            // included / in shortest path tree
            // or shortest distance from src to
            // i is finalized
            boolean[] added = new boolean[nVertices];

            // Initialize all distances as
            // INFINITE and added[] as false
            for (int vertexIndex = 0; vertexIndex < nVertices;
                 vertexIndex++)
            {
                shortestDistances[vertexIndex] = Integer.MAX_VALUE;
                added[vertexIndex] = false;
            }

            // Distance of source vertex from
            // itself is always 0
            shortestDistances[startVertex] = 0;

            // Parent array to store shortest
            // path tree
            int[] parents = new int[nVertices];

            // The starting vertex does not
            // have a parent
            parents[startVertex] = NO_PARENT;

            // Find shortest path for all
            // vertices
            for (int j = 1; j < nVertices; j++)
            {

                // Pick the minimum distance vertex
                // from the set of vertices not yet
                // processed. nearestVertex is
                // always equal to startNode in
                // first iteration.
                int nearestVertex = -1;
                int shortestDistance = Integer.MAX_VALUE;
                for (int vertexIndex = 0;
                     vertexIndex < nVertices;
                     vertexIndex++)
                {
                    if (!added[vertexIndex] &&
                            shortestDistances[vertexIndex] <
                                    shortestDistance)
                    {
                        nearestVertex = vertexIndex;
                        shortestDistance = shortestDistances[vertexIndex];
                    }
                }

                // Mark the picked vertex as
                // processed
                added[nearestVertex] = true;

                // Update dist value of the
                // adjacent vertices of the
                // picked vertex.
                for (int vertexIndex = 0;
                     vertexIndex < nVertices;
                     vertexIndex++)
                {
                    int edgeDistance = adjacencyMatrix[nearestVertex][vertexIndex];

                    if (edgeDistance > 0
                            && ((shortestDistance + edgeDistance) <
                            shortestDistances[vertexIndex]))
                    {
                        parents[vertexIndex] = nearestVertex;
                        shortestDistances[vertexIndex] = shortestDistance +
                                edgeDistance;
                    }
                }
            }

            for (int vertexIndex = 0;
                 vertexIndex < nVertices;
                 vertexIndex++)
            {
                if (vertexIndex != startVertex)
                {


                    printPath(startVertex, vertexIndex, vertexIndex, parents);
                }
            }



        }

//        System.out.println(allPaths);

        return null;

    }

    private void printPath(int startVertex, int vertexIndex, int permanentIndex, int[] parents) {

        if (vertexIndex == NO_PARENT)
        {
            return;
        }

        printPath(startVertex, parents[vertexIndex], permanentIndex, parents);
        allPaths.get(startVertex).get(permanentIndex);
        allPaths.get(startVertex).get(permanentIndex).add(vertexIndex+1);

    }
    // For every pair (x,y), comput the shortest path to a node v. If those two paths share only one node, v, then it is a cycle
    public ArrayList<ArrayList<Integer>> allCycles(){
//        System.out.println("Alledges: " + allEdges);
//        System.out.println("Allpaths: " + allPaths);

        ArrayList<ArrayList<Integer>> cycles = new ArrayList<>();
        ArrayList<ArrayList<Integer>> unsortedCycles = new ArrayList<>();

        for(int q = 0; q < nodes.size(); q++){
            for(int j = 0; j < allEdges.size(); j++){
                int x = allEdges.get(j).get(0);
                int y = allEdges.get(j).get(1);
//                System.out.println("(" + x + "," + y + ")");


                ArrayList<Integer> xToV = allPaths.get(x-1).get(q);
                ArrayList<Integer> yToV = allPaths.get(y-1).get(q);


                ArrayList<Integer> intersect = new ArrayList<>();
//                System.out.println(q+1 + ":" + "(" + x + "," + y + ")");
//                System.out.println("x: " + xToV);
//                System.out.println("y: " +yToV);


                for(Integer h: xToV){
                    if(yToV.contains(h)){
                        intersect.add(h);
                    }
                }
//                System.out.println(intersect);



                if(intersect.size() == 1){

                    if(intersect.get(0) == q+1){
//                        System.out.println("yes");
//                        System.out.println(xToV);
//                        System.out.println(yToV);
                        ArrayList<Integer> cycleAddition = new ArrayList<>();
                        ArrayList<Integer> unsortedCycleAddition = new ArrayList<>();

                        for(int w: xToV){
                            cycleAddition.add(w);
                            unsortedCycleAddition.add(w);
                        }
//                        for(int l: yToV){
//                            cycleAddition.add(l);
//                        }

                        for(int i = yToV.size()-2; i>= 0; i--){
                            cycleAddition.add(yToV.get(i));
                            unsortedCycleAddition.add(yToV.get(i));
                        }
//                        for(int i =0; i<cycleAddition.size();i++){
//                            if(cycleAddition.get(i) == q+1){
//                                cycleAddition.remove(i);
//                                break;
//                            }
//                        }
//                        for(int f = xToV.size()-1; f >= 0; f--){
//                            cycleAddition.add(f);
//                        }
//                        for(int d = 0; d < yToV.size(); d++){
//                            cycleAddition.add(d);
//                        }
                        unsortedCycles.add(unsortedCycleAddition);
                        cycles.add(cycleAddition);
                    }
                }

            }

        }

        for (ArrayList a: cycles){
            Collections.sort(a);
        }

//        System.out.println(cycles);
//        System.out.println("Unsorted Cycles: " + unsortedCycles);

//        double[][] ref = new double[cycles.size()][nodes.size()];
//
//        for(int i = 0; i< cycles.size(); i++){
//            for(int a: cycles.get(i)){
//                ref[i][a-1] = 1.0;
//            }
//        }
//        System.out.println(Arrays.deepToString(ref));
//
//
//        Matrix m = new Matrix();
//        double[][] h = m.rref(ref);
//
//
//        System.out.println(Arrays.deepToString(m.rref(ref)));

        for(int i= 0; i < cycles.size(); i++){
            HashSet set = new HashSet(cycles.get(i));
            for(int j = 0; j< cycles.size(); j++){
                if(i != j){
                if((new HashSet(cycles.get(j))).containsAll(set)) {
                    cycles.get(j).set(0,4500);
                }
                }
            }
        }
        ArrayList<ArrayList<Integer>> minimumBasis = new ArrayList<>();
        ArrayList<ArrayList<Integer>> unsortedMinimumBasis = new ArrayList<>();

        for(int i = 0; i < cycles.size(); i++){
            if((int) cycles.get(i).get(0)!= 4500){
                minimumBasis.add(cycles.get(i));
                unsortedMinimumBasis.add(unsortedCycles.get(i));
            }
        }

//        System.out.println("Sorted: " + minimumBasis);
//        System.out.println("Unsorted: " +unsortedMinimumBasis);

        ArrayList<ArrayList<Integer>>  finalBasis = new ArrayList<>();

        for(ArrayList a: unsortedMinimumBasis){
            Boolean pathCheck = true;
            for(int i = 0; i< a.size(); i++){
                for(int j = i+1; j < a.size(); j++){
                    int shortestPath = allPaths.get((int)a.get(i)-1).get((int)a.get(j)-1).size() - 1;
                    int firstPath = j - i;
                    int secondPath = a.size() - firstPath;

                    if(shortestPath < firstPath && shortestPath < secondPath){
                        pathCheck = false;
                    }


                }
            }
            if(pathCheck){
                finalBasis.add(a);
            }
        }

//        System.out.println("Final Basis: " +finalBasis.size() + finalBasis);
        int fiveCount = 0;
        int sixCount = 0;
        for(ArrayList a : finalBasis){
            if(a.size()== 5){
                fiveCount++;
            }
            else if(a.size()==6){
                sixCount++;
            }
            else{
//                System.out.println(a);
            }
        }
        System.out.println(fiveCount + "," + sixCount);
        return finalBasis;
//        for(int i = 0; i < minimumBasis.size(); i++ ){
//
//                System.out.println(minimumBasis.get(i));
//                System.out.println(minimumBasis.get(i).size());
//
//        }
    }


//    private String[][] stringer(double[][] mat){
//        for(int i =0; i < mat.length();i++){
//
//        }
//
//        return null;
//    }

    private ArrayList<ArrayList<Integer>> removeMatches(ArrayList<ArrayList<Integer>> cycles){
        for(int i =0; i< cycles.size(); i++){
            ArrayList comparer = cycles.get(i);
            Collections.sort(comparer);
            for(int k = i+1; k < cycles.size(); k++){
                if(comparer.size() == cycles.get(k).size() && comparer.equals(cycles.get(k))){
                    cycles.remove(k);
                }
            }
        }


        return cycles;
    }

    private void rowReduce(){


    }

    public Boolean checkConnected(ArrayList<Integer> toCheck){



        return false;
    }

}
