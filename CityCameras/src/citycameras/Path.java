/*******************************************************************************
 * Copyright (c) 2014 Michael Burns
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Used in CS3733, Software Engineering at Worcester Polytechnic Institute
 *******************************************************************************/
package citycameras;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class implements a single series of nodes to pass through
 * in order to get from a start node to an end node.
 * @author Michael
 * @version April 7, 2014
 */
public class Path {

	private ArrayList<String> pathNodes;
	
	/**
	 * The constructor takes a Collection of node names
	 * that are members of the path.
	 * @param pathNodes the nodes that make up the path.
	 */
	public Path(Collection<String> pathNodes) {
		this.pathNodes = new ArrayList<String>();
		for (String node : pathNodes) {
			this.pathNodes.add(node);
		}
	}
	
	/**
	 * This function returns the name of the start node.
	 * @return the name of the start node.
	 */
	public String getStartNode() {
		return pathNodes.get(0);
	}
	
	/**
	 * This function returns the name of the end node.
	 * @return the name of the end node.
	 */
	public String getEndNode() {
		return pathNodes.get(pathNodes.size() - 1);
	}

	/**
	 * This function returns whether or not the path
	 * contains a specific node.
	 * @param node The node to check the list for.
	 * @return True if the node is in the path, else false.
	 */
	public boolean contains(String node) {
		return pathNodes.contains(node);
	}

	/**
	 * @return the pathNodes
	 */
	public ArrayList<String> getPathNodes() {
		return pathNodes;
	}
}
