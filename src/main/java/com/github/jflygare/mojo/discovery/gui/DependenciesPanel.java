package com.github.jflygare.mojo.discovery.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.Border;

import com.github.jflygare.mojo.discovery.util.GraphFactory;
import com.github.jflygare.mojo.discovery.util.MavenContext;

import edu.uci.ics.jung.graph.ArchetypeVertex;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.decorators.AbstractVertexShapeFunction;
import edu.uci.ics.jung.graph.decorators.DefaultToolTipFunction;
import edu.uci.ics.jung.graph.decorators.EdgeShape;
import edu.uci.ics.jung.graph.decorators.SettableVertexShapeFunction;
import edu.uci.ics.jung.graph.decorators.StringLabeller;
import edu.uci.ics.jung.graph.decorators.VertexAspectRatioFunction;
import edu.uci.ics.jung.graph.decorators.VertexShapeFunction;
import edu.uci.ics.jung.graph.decorators.VertexSizeFunction;
import edu.uci.ics.jung.graph.decorators.VertexStringer;
import edu.uci.ics.jung.graph.impl.SparseTree;
import edu.uci.ics.jung.visualization.Layout;
import edu.uci.ics.jung.visualization.PluggableRenderer;
import edu.uci.ics.jung.visualization.ShapePickSupport;
import edu.uci.ics.jung.visualization.SpringLayout;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.contrib.DAGLayout;
import edu.uci.ics.jung.visualization.contrib.TreeLayout;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;
import edu.uci.ics.jung.visualization.control.ScalingControl;

public class DependenciesPanel extends JApplet implements ActionListener {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private GraphFactory graphFactory;

	private PluggableRenderer pr;
	private VisualizationViewer vv;

	public DependenciesPanel(GraphFactory graphFactory)
			throws HeadlessException {
		this.graphFactory = graphFactory;
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	public JPanel startPanel() throws Exception {
		Graph graph = graphFactory.createGraph();

		pr = new PluggableRenderer();
		pr.setVertexShapeFunction(new VNodeShapeFunction());
		pr.setVertexLabelCentering(true);
		pr.setVertexStringer(StringLabeller.getLabeller(graph));
		pr.setEdgeShapeFunction(new EdgeShape.Line());
		DEPLayout layout = new DEPLayout(graph);
		//FIXME: Calculate based on objects in layout
		layout.initialize(new Dimension(1900, 1024));
		//DAGLayout layout = new DAGLayout(graph);
		//SpringLayout layout = new SpringLayout(graph);
		layout.setForceMultiplier(.00005);
		layout.setRepulsionRange(10000);
		layout.setStretch(5);
		vv = new VisualizationViewer(layout, pr);
		vv.setBackground(Color.WHITE);

		DefaultModalGraphMouse graphMouse = new DefaultModalGraphMouse();
		graphMouse.setMode(Mode.PICKING);
		vv.setGraphMouse(graphMouse);
		vv.setPickSupport(new ShapePickSupport());
		vv.setToolTipFunction(new VNodeToolTipFunction());

		JPanel jp = new JPanel();
		jp.setPreferredSize(new Dimension(1280, 1024));
		jp.setLayout(new BorderLayout());
		jp.add(vv, BorderLayout.CENTER);
		return jp;
	}

	private static class VNodeShapeFunction extends AbstractVertexShapeFunction
			implements VertexSizeFunction, VertexAspectRatioFunction {

		public VNodeShapeFunction() {
			setSizeFunction(this);
			setAspectRatioFunction(this);
		}

		public Shape getShape(Vertex v) {
			return factory.getRoundRectangle(v);
		}

		public float getAspectRatio(Vertex v) {
			return .5f;
		}

		public int getSize(Vertex v) {
			return 100;
		}

	}

	private static class VNodeToolTipFunction extends DefaultToolTipFunction {

		@Override
		public String getToolTipText(Vertex v) {
			//return v.getUserDatum("DAGLayout.minimumLevel").toString();
			String label = StringLabeller.getLabeller((Graph) v.getGraph()).getLabel(v);
			return "<html><b>" + label + "</b><html>";
		}

	}

}
