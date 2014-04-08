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
import java.util.List;

/**
 * This class implements the calculations to determine
 * all unique, loop-free paths from a start to an end point.
 * @author Michael
 * @version April 8, 2014
 */
public class PathCalculater {
	
	/**
	 * This function takes in a starting and an end point, and returns a
	 * List of all of the distinct, loop-free paths connecting the two.
	 * This function is effectively a wrapper around the depthFirstSearch function.
	 * @param start The node to start from.
	 * @param end The node to end at.
	 * @param city The roads of the network
	 * @return A list of paths from the start node to the end node,
	 * including the start and end nodes.
	 */
	public List<Path> calculatePaths(String start, String end, Collection<Road> city) {
		List<Path> paths = new ArrayList<Path>();
		List<String> pathSoFar = new ArrayList<String>();
		pathSoFar.add(start);
		return depthFirstSearch(start, end, pathSoFar, paths, city);
	}
	
	/**
	 * This function performs a depth first search to find all
	 * paths from the start node to the end node.
	 * @param start The node to start from.
	 * @param end The node to end at.
	 * @param pathSoFar The current path of nodes from start on the way to the end.
	 * @param paths The list of complete Paths that go from start to end.
	 * @param city The roads of the network
	 * @return The complete set of Paths from start to end.
	 */
	private List<Path> depthFirstSearch(String start, String end, List<String> pathSoFar, List<Path> paths, Collection<Road> city) {
		String currentNode = pathSoFar.get(pathSoFar.size() - 1);
		for (Road r : city) {
			String nextNode = null; // Declare this in here so it nulls every time
			// currentNode is Neighborhood1
			if (r.getNeighborhood1().equals(currentNode)) {
				nextNode = r.getNeighborhood2();
			}
			// currentNode is Neighborhood2
			else if (r.getNeighborhood2().equals(currentNode)) {
				nextNode = r.getNeighborhood1();
			}
			
			// Common code for nextNode
			if (nextNode != null) {
				// Prevents loops
				if (!pathSoFar.contains(nextNode)) {
					// Safe copy of the pathSoFar variable that we can touch
					List<String> myPathSoFar = new ArrayList<String>();
					for (String s : pathSoFar) {
						myPathSoFar.add(s);
					}	
					// Add to the path
					myPathSoFar.add(nextNode);
					if (nextNode.equals(end)) {	// This path is complete
						paths.add(new Path(myPathSoFar));
					}
					else {	// Recurse
						paths = depthFirstSearch(start, end, myPathSoFar, paths, city);
					}
				}
			}
		}
		
		return paths;
	}
}
