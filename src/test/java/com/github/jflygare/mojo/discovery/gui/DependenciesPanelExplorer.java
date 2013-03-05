package com.github.jflygare.mojo.discovery.gui;

import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.github.jflygare.mojo.discovery.util.GraphFactory;
import com.github.jflygare.mojo.discovery.util.DummyDependencyGraphFactory;

public class DependenciesPanelExplorer {

	/**
	 * @param args
	 * @throws Exception
	 * @throws HeadlessException
	 */
	public static void main(String[] args) throws HeadlessException, Exception {
		GraphFactory graphFactory = new DummyDependencyGraphFactory();
        JFrame jf = new JFrame();
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel jp = new DependenciesPanel(graphFactory).startPanel();
        jf.getContentPane().add(jp);
        jf.pack();
        jf.setVisible(true);
	}

}
