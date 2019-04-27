package transitive.Closure;

import java.util.ArrayList;
/**
 * Main working class of the transitive closure program
 * @author alexj
 *
 */
public class GraphMain {
	
	/**
	 * Gives a modified directed graph that includes all transitive edges if they are missing.
	 * @param G DirectedGraph to modify
	 * @return G The transitively(is that a word?) closed directed graph/relation
	 */
	public DirectedGraph transitiveClosure(DirectedGraph G) {
		ArrayList<Edge> transitiveEdges = new ArrayList<Edge>();
		for(int i =0; i < G.getEdgeList().size(); i++) {
			for(int j = 0; j < G.getEdgeList().size(); j++) {
				if(G.getEdgeList().get(j).start.equals(G.getEdgeList().get(i).end)) {
					transitiveEdges.add(new Edge(G.getEdgeList().get(i).start, G.getEdgeList().get(j).end));
				}
			}
		}
		G.edgeList.addAll(transitiveEdges);
		G.removeDuplicates();
		return G;
	}
	/**
	 * Main method used to create and modify a graph
	 * @param args
	 */
	public static void main(String[] args) {
		GraphMain closer = new GraphMain();
		DirectedGraph graph = new DirectedGraph();
		graph.addVertex("a");
		graph.addVertex("b");
		graph.addVertex("c");
		graph.addEdge(new Edge("a", "b"));
		graph.addEdge(new Edge("b", "c"));
		graph.addEdge(new Edge("a", "c"));
		System.out.println(closer.transitiveClosure(graph));
	}
}
