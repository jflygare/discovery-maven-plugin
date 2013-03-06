package com.github.jflygare.mojo.discovery.util;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.LegacySupport;
import org.apache.maven.shared.dependency.graph.DependencyGraphBuilder;
import org.apache.maven.shared.dependency.graph.DependencyNode;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;
import org.codehaus.plexus.logging.Logger;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.decorators.StringLabeller;
import edu.uci.ics.jung.graph.decorators.StringLabeller.UniqueLabelException;
import edu.uci.ics.jung.graph.impl.DirectedSparseEdge;
import edu.uci.ics.jung.graph.impl.DirectedSparseGraph;
import edu.uci.ics.jung.graph.impl.DirectedSparseVertex;

@Component(role = GraphFactory.class, hint = "dependency")
public class DependencyGraphFactory implements GraphFactory {

	@Requirement
	private LegacySupport legacySupport;

	@Requirement
	private DependencyGraphBuilder graphBuilder;

	@Requirement
	private Logger logger;

	public Graph createGraph() throws Exception {
		Graph graph = new DirectedSparseGraph();
		DependencyNode root = graphBuilder.buildDependencyGraph(legacySupport
				.getSession().getCurrentProject(), null);
		addVertex(graph, root);
		return graph;
	}

	public Vertex addVertex(Graph graph, DependencyNode node)
			throws UniqueLabelException {
		Artifact artifact = node.getArtifact();
		String id = ArtifactUtil.key(artifact);
		StringLabeller labeller = StringLabeller.getLabeller(graph);
		Vertex v = labeller.getVertex(id);
		if (v == null) {
			logger.debug("Adding " + id + " vertex to dependency graph");
			v = graph.addVertex(new DirectedSparseVertex());
			labeller.setLabel(v, id);
		}
		for (DependencyNode child : node.getChildren()) {
			Vertex c = addVertex(graph, child);
			String eid = labeller.getLabel(v) + " <-> " + labeller.getLabel(c);
			// TODO: Edge labeller
			logger.debug("Adding " + eid + " edge to dependency graph");
			graph.addEdge(new DirectedSparseEdge(v, c));
		}
		return v;
	}

}
