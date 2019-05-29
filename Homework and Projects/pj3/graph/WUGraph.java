/* WUGraph.java */

package graph;

import list.*;
import dict.*;

/**
 * The WUGraph class represents a weighted, undirected graph.  Self-edges are
 * permitted.
 */

public class WUGraph {

  private DList vertices;
  private HashTableChained hashVertices;
  private HashTableChained hashEdges;
  private int numberOfVertices;
  private int numberOfEdges;

  /**
   * WUGraph() constructs a graph having no vertices or edges.
   *
   * Running time:  O(1).
   */ 
  public WUGraph() {
    vertices = new DList();
    hashVertices = new HashTableChained();
    hashEdges = new HashTableChained();
    numberOfVertices = 0;
    numberOfEdges = 0;
  }

  /**
   * vertexCount() returns the number of vertices in the graph.
   *
   * Running time:  O(1).
   */
  public int vertexCount() {
    return numberOfVertices;
  }

  /**
   * edgeCount() returns the total number of edges in the graph.
   *
   * Running time:  O(1).
   */
  public int edgeCount() {
    return numberOfEdges;
  }

  /**
   * getVertices() returns an array containing all the objects that serve
   * as vertices of the graph.  The array's length is exactly equal to the
   * number of vertices.  If the graph has no vertices, the array has length
   * zero.
   *
   * (NOTE:  Do not return any internal data structure you use to represent
   * vertices!  Return only the same objects that were provided by the
   * calling application in calls to addVertex().)
   *
   * Running time:  O(|V|).
   */
  public Object[] getVertices() {
    Object[] v = new Object[numberOfVertices];
    DListNode cur = vertices.front();
    int index = 0;
          try{
    while (cur.isValidNode()) {
      v[index++] = ((Vertex)cur.item()).getItem();
      cur = cur.next();
      }
    }catch(InvalidNodeException e){
        e.printStackTrace();
      }
    return v;
  }

  /**
   * addVertex() adds a vertex (with no incident edges) to the graph.
   * The vertex's "name" is the object provided as the parameter "vertex".
   * If this object is already a vertex of the graph, the graph is unchanged.
   *
   * Running time:  O(1).
   */

  public void addVertex(Object vertex) {
    if (isVertex(vertex)) {
      return;
    }
    Vertex v = new Vertex(vertex);
    vertices.insertBack(v);
    v.vertexNode = vertices.back();
    hashVertices.insert(vertex, v);
    numberOfVertices++; 
  }

  /**
   * removeVertex() removes a vertex from the graph.  All edges incident on the
   * deleted vertex are removed as well.  If the parameter "vertex" does not
   * represent a vertex of the graph, the graph is unchanged.
   *
   * Running time:  O(d), where d is the degree of "vertex".
   */
  public void removeVertex(Object vertex) {
    if (!isVertex(vertex)) {
      return;
    }
    Vertex v = (Vertex)(hashVertices.find(vertex)).value();
    DListNode cur = v.adjList.front();
    try{
    while (cur.isValidNode()) {
      Edge edge = (Edge)cur.item();
      DListNode next = cur.next();
      removeEdge((edge.u).getItem(), (edge.v).getItem());
      cur = next;
    }
    v.vertexNode.remove();
    hashVertices.remove(vertex);
    numberOfVertices--;
    }catch(InvalidNodeException e){
      e.printStackTrace();
    }
  }

  /**
   * isVertex() returns true if the parameter "vertex" represents a vertex of
   * the graph.
   *
   * Running time:  O(1).
   */
  public boolean isVertex(Object vertex) {
    return hashVertices.find(vertex) != null;
  }

  /**
   * degree() returns the degree of a vertex.  Self-edges add only one to the
   * degree of a vertex.  If the parameter "vertex" doesn't represent a vertex
   * of the graph, zero is returned.
   *
   * Running time:  O(1).
   */
  public int degree(Object vertex) {
    if (!isVertex(vertex)) {
      return 0;
    }
    Vertex v = (Vertex)hashVertices.find(vertex).value();
    return v.degree;
  }

  /**
   * getNeighbors() returns a new Neighbors object referencing two arrays.  The
   * Neighbors.neighborList array contains each object that is connected to the
   * input object by an edge.  The Neighbors.weightList array contains the
   * weights of the corresponding edges.  The length of both arrays is equal to
   * the number of edges incident on the input vertex.  If the vertex has
   * degree zero, or if the parameter "vertex" does not represent a vertex of
   * the graph, null is returned (instead of a Neighbors object).
   *
   * The returned Neighbors object, and the two arrays, are both newly created.
   * No previously existing Neighbors object or array is changed.
   *
   * (NOTE:  In the neighborList array, do not return any internal data
   * structure you use to represent vertices!  Return only the same objects
   * that were provided by the calling application in calls to addVertex().)
   *
   * Running time:  O(d), where d is the degree of "vertex".
   */
  public Neighbors getNeighbors(Object vertex) {
    if (degree(vertex) == 0) {
      return null;
    }
    Vertex v = (Vertex)hashVertices.find(vertex).value();
    DListNode cur = v.adjList.front();
    Neighbors neighbors = new Neighbors();
    neighbors.neighborList = new Object[v.degree];
    neighbors.weightList = new int[v.degree];
    int index = 0;
    try{
    while (cur.isValidNode()) {
      Edge edge = (Edge)cur.item();
      if (edge.u.equals(v)) {
        neighbors.neighborList[index] = (edge.v).getItem();
      } else {
        neighbors.neighborList[index] = (edge.u).getItem();
      }
      neighbors.weightList[index] = edge.weight;
      index++;
      cur = cur.next();
    }}catch(InvalidNodeException e){
      e.printStackTrace();
    }
    return neighbors;
  }

  /**
   * addEdge() adds an edge (u, v) to the graph.  If either of the parameters
   * u and v does not represent a vertex of the graph, the graph is unchanged.
   * The edge is assigned a weight of "weight".  If the graph already contains
   * edge (u, v), the weight is updated to reflect the new value.  Self-edges
   * (where u.equals(v)) are allowed.
   *
   * Running time:  O(1).
   */
  public void addEdge(Object u, Object v, int weight) {
    if (isVertex(u) && isVertex(v)) {
      VertexPair vPair = new VertexPair(u, v);
      if (hashEdges.find(vPair) == null) {
        Vertex vertexU = (Vertex)hashVertices.find(u).value();
        Vertex vertexV = (Vertex)hashVertices.find(v).value();
        Edge edge = new Edge(vertexU, vertexV, weight);
        vertexU.adjList.insertBack(edge);
        vertexU.degree++;
        edge.uNode = vertexU.adjList.back();
        if (vertexU != vertexV) {
          vertexV.adjList.insertBack(edge);
          vertexV.degree++;
          edge.vNode = vertexV.adjList.back();
        }
        hashEdges.insert(vPair, edge);
        numberOfEdges++;
      } else {
        Edge edge = (Edge)hashEdges.find(vPair).value();
        edge.weight = weight;
      }
    }
  }

  /**
   * removeEdge() removes an edge (u, v) from the graph.  If either of the
   * parameters u and v does not represent a vertex of the graph, the graph
   * is unchanged.  If (u, v) is not an edge of the graph, the graph is
   * unchanged.
   *
   * Running time:  O(1).
   */
  public void removeEdge(Object u, Object v) {
    if (isEdge(u, v)) {
      VertexPair vPair = new VertexPair(u, v);
      Edge edge = (Edge)hashEdges.find(vPair).value();
      (edge.u).degree--;
      try{
      edge.uNode.remove();
      if (edge.u != edge.v) {
        (edge.v).degree--;
        edge.vNode.remove();
      }
      hashEdges.remove(vPair);
      numberOfEdges--;
      }catch(InvalidNodeException e){
        e.printStackTrace();
      }
    }
  }

  /**
   * isEdge() returns true if (u, v) is an edge of the graph.  Returns false
   * if (u, v) is not an edge (including the case where either of the
   * parameters u and v does not represent a vertex of the graph).
   *
   * Running time:  O(1).
   */
  public boolean isEdge(Object u, Object v) {
    if (!isVertex(u) || !isVertex(v)) {
      return false;
    }
    VertexPair vPair = new VertexPair(u, v);
    return hashEdges.find(vPair) != null;
  }

  /**
   * weight() returns the weight of (u, v).  Returns zero if (u, v) is not
   * an edge (including the case where either of the parameters u and v does
   * not represent a vertex of the graph).
   *
   * (NOTE:  A well-behaved application should try to avoid calling this
   * method for an edge that is not in the graph, and should certainly not
   * treat the result as if it actually represents an edge with weight zero.
   * However, some sort of default response is necessary for missing edges,
   * so we return zero.  An exception would be more appropriate, but also more
   * annoying.)
   *
   * Running time:  O(1).
   */
  public int weight(Object u, Object v) {
    if (!isEdge(u, v)) {
      return 0;
    }
    VertexPair vPair = new VertexPair(u, v);
    if (hashEdges.find(vPair) == null) {
      return 0;
    }
    Edge edge = (Edge)hashEdges.find(vPair).value();
    return edge.weight;
  }

}