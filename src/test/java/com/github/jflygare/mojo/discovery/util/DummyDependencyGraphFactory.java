package com.github.jflygare.mojo.discovery.util;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.decorators.StringLabeller;
import edu.uci.ics.jung.graph.decorators.StringLabeller.UniqueLabelException;
import edu.uci.ics.jung.graph.impl.DirectedSparseEdge;
import edu.uci.ics.jung.graph.impl.DirectedSparseGraph;
import edu.uci.ics.jung.graph.impl.DirectedSparseVertex;

public class DummyDependencyGraphFactory implements GraphFactory {

	public Graph createGraph() {
		Graph graph = new DirectedSparseGraph();
		try {
			Vertex p1 = addVertex(graph, "Top Parent");
			Vertex c11 = addVertex(graph, "Level 1 Child 1");
			graph.addEdge(new DirectedSparseEdge(p1, c11));
			Vertex c12 = addVertex(graph, "Level 1 Child 2");
			graph.addEdge(new DirectedSparseEdge(p1, c12));
			Vertex c21 = addVertex(graph, "Level 2 Child 1");
			graph.addEdge(new DirectedSparseEdge(c11, c21));
			graph.addEdge(new DirectedSparseEdge(c12, c21));
		} catch (UniqueLabelException e) {
			throw new RuntimeException(e);
		}
		return graph;
	}

	private Vertex addVertex(Graph graph, String label) throws UniqueLabelException {
		Vertex v = graph.addVertex(new DirectedSparseVertex());
		StringLabeller.getLabeller(graph).setLabel(v, label);
		return v;
	}

}
