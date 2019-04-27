package transitive.Closure;
/**
 * Used to model an edge in a directed graph.
 * Also considered a relation between two elements.
 * These elements are vertices, and represented as strings
 * @author alexj
 *
 */
public class Edge {
	/** Starting point of the directed edge */
	public String start;
	/** End point of the directed edge */
	public String end;
	/**
	 * Create a new edge with start A and end B
	 * @param A Start point
	 * @param B End point
	 */
	public Edge(String A, String B) {
		start = A;
		end = B;
	}
	/**
	 * A toString method for edge
	 * @return A string representation of the edge
	 */
	public String toString() {
		return "(" + start + ", " + end + ")";
	}
	/**
	 * Compares this edge to a given edge, returns true if equal.
	 * That is, the start and end of the edge are the same.
	 * @param E Edge to compare to
	 * @return boolean True if they are equal
	 */
	public boolean equalsEdge(Edge E) {
		if(E.start.equals(this.start) && E.end.equals(this.end)) {
			return true;
		}
		return false;
	}
}
