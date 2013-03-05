package com.github.jflygare.mojo.discovery.util;

import edu.uci.ics.jung.graph.Graph;

public interface GraphFactory {
	Graph createGraph() throws Exception;
}
