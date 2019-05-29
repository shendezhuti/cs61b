/*
Vertex.java
*/

package graph;

import list.*;

public class Vertex {

	protected DList adjList; //store edges
	protected DListNode vertexNode; //point the position on vertices list.
	protected int degree;
	private Object item;

	public Vertex(Object item) {
		this.item = item;
		this.adjList = new DList();
		this.vertexNode = null;
		this.degree = 0;	
	}

	public Object getItem() {
		return item;
	}

}