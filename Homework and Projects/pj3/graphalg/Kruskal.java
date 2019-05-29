/* Kruskal.java */

package graphalg;

import graph.*;
import set.*;
import list.*;
import dict.*;

/**
 * The Kruskal class contains the method minSpanTree(), which implements
 * Kruskal's algorithm for computing a minimum spanning tree of a graph.
 */

public class Kruskal {

  /**
   * minSpanTree() returns a WUGraph that represents the minimum spanning tree
   * of the WUGraph g.  The original WUGraph g is NOT changed.
   *
   * @param g The weighted, undirected graph whose MST we want to compute.
   * @return A newly constructed WUGraph representing the MST of g.
   */
  public static WUGraph minSpanTree(WUGraph g) {
    LinkedQueue que = new LinkedQueue();
    WUGraph newGraph = new WUGraph();
    Object[] vertexObj = g.getVertices();
    for (int i = 0; i < vertexObj.length; i++) {
      newGraph.addVertex(vertexObj[i]);
      Neighbors neighbor = g.getNeighbors(vertexObj[i]);
      for (int j = 0; j < (neighbor.neighborList).length; j++) {
        KEdge edge = new KEdge(vertexObj[i], neighbor.neighborList[j], neighbor.weightList[j]);
        que.enqueue(edge);
      }
    }

    ListSorts.quickSort(que);

    DisjointSets st = new DisjointSets(g.vertexCount());
    HashTableChained vertexHash = new HashTableChained();
    for (int i = 0; i < vertexObj.length; ++i) {
      vertexHash.insert(vertexObj[i], i);
    }
    while(!que.isEmpty()) {
      KEdge edge = null;
      try {
        edge = (KEdge)que.dequeue();
      } catch(QueueEmptyException e) {
        System.err.println(e);
      }
      int st1 = st.find((Integer)(vertexHash.find(edge.u)).value());
      int st2 = st.find((Integer)(vertexHash.find(edge.v)).value());
      if (st1 != st2) {
        st.union(st1, st2);
        newGraph.addEdge(edge.u, edge.v, edge.weight);
      }
    }

    return newGraph;
  }

}