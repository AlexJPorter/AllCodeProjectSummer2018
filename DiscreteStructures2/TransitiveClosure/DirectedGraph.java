
package transitive.Closure;
import java.util.ArrayList;
/**
 * Models a directed graph used to find a transitive closure
 * @author alexj
 *
 */
public class DirectedGraph {
	/** A list of the vertices. This is more 
	 * for user information rather than anything else. **/
	public ArrayList<String> vertexList;
	/** A list of the edges in the graph. Order matters here. **/
	public ArrayList<Edge> edgeList;
	
	/**
	 * Default constructor initializes lists.
	 */
	public DirectedGraph() {
		vertexList = new ArrayList<String>();
		edgeList = new ArrayList<Edge>();
	}
	
	/**
	 * Add a vertex to the list
	 * @param A Vertex to add
	 */
	public void addVertex(String A) {
		vertexList.add(0,A);
	}
	/**
	 * Add an edge to the list. Equivalent to adding a 
	 * relation between two elements.
	 * @param edge Edge to add
	 */
	public void addEdge(Edge edge) {
		edgeList.add(0,edge);
	}
	/**
	 * Remove a vertex from the list. Does nothing if the list
	 * does not contain the vertex.
	 * @param A Vertex to remove.
	 */
	public void removeVertex(String A) {
		for(String V : vertexList) {
			if (V.equals(A)) {
				vertexList.remove(V);
			}
		}
	}
	/**
	 * Remove an edge from the list. Does nothing if the list
	 * does not contain the edge.
	 * @param B Edge to be removed
	 */
	public void removeEdge(Edge B) {
		for(Edge E : edgeList) {
			if (B.equals(E)) {
				edgeList.remove(E);
			}
		}
	}
	/**
	 * Removes any duplicate edges in the edge list 
	 * using the equalsEdge method defined in the edge class.
	 */
	public void removeDuplicates() {
			for(int i = 0; i < edgeList.size(); i++) {
				for(int j = 0; j < edgeList.size(); j++) {
				if(edgeList.get(i).equalsEdge(edgeList.get(j)) && j != i) {
					edgeList.remove(j);
				}
			}
		}
	}
	/**
	 * Returns the edgeList.
	 * @return edgeList
	 */
	public ArrayList<Edge> getEdgeList() {
		return edgeList;
	}
	/**
	 * Returns a string representation of the graph
	 * @return String
	 */
	public String toString() {
		String result = "Edges: ";
		for(Edge E : edgeList) {
			result += E.toString() + " ";
		}
		result += "\nVertices: ";
		for(String V : vertexList) {
			result += V + " ";
		}
		return result;
	}
}
