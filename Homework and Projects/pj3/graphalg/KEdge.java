/*
KEdge.java
*/

package graphalg;


public class KEdge implements Comparable {

	protected Object u;
	protected Object v;
	protected int weight;

	public KEdge(Object u, Object v, int weight) {
		this.u = u;
		this.v = v;
		this.weight = weight;
	}

	public int compareTo(Object o) {
		if (weight < ((KEdge)o).weight) {
			return -1;
		} else if (weight > ((KEdge)o).weight) {
			return 1;
		} else {
			return 0;
		}
	}
}