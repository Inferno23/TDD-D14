/*******************************************************************************
 * Copyright (c) 2014 Gary F. Pollice
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * This class implements the algorithms for planning the city security cameras
 * as described in the TDD assignment for CS3733. The idea is taken from the
 * <a href="http://www.programming-challenges.com/pg.php?page=downloadproblem&probid=111006&format=html">
 * <em>Tourist Guide</em></a> problem at the Programming Challenges website.
 * 
 * @version Mar 30, 2014
 * @author Michael Burns
 */
public class CityCameraPlanner
{
	private Collection<Road> city;
	private HashMap<String, Integer> connections;
	private Collection<String> cameras;

	/**
	 * The constructor takes a collection of all of the roads in the city that
	 * connect neighborhoods and initializes the instance so that it can provide
	 * the locations of the cameras via a couple of query methods.
	 * @param roads the collection of roads that connect neighborhoods in the city
	 */
	public CityCameraPlanner(Collection<Road> roads)
	{
		// Populate the city
		city = new HashSet<Road>();
		for (Road r : roads) {
			city.add(r);
		}
		
		// Populate the connections
		populateConnections(roads);
		// Calculate the camera locations
		calculateCameras();
	}

	/**
	 * This function takes in a Collection of roads, and uses
	 * it to populate the connections HashMap, which maps
	 * node names to the number of connections for the node.
	 * @param roads the collection of roads that connect neighborhoods in the city
	 */
	private void populateConnections(Collection<Road> roads) {
		connections = new HashMap<String, Integer>();
		for (Road r : roads) {
			// Neighborhood one
			if (connections.containsKey(r.getNeighborhood1())) {
				connections.put(r.getNeighborhood1(), connections.get(r.getNeighborhood1()) + 1);
			}
			else {
				connections.put(r.getNeighborhood1(), 1);
			}
			// Neighborhood two
			if (connections.containsKey(r.getNeighborhood2())) {
				connections.put(r.getNeighborhood2(), connections.get(r.getNeighborhood2()) + 1);
			}
			else {
				connections.put(r.getNeighborhood2(), 1);
			}
		}
	}

	/**
	 * This function does the actual algorithm to determine
	 * which cities need to have cameras.
	 */
	private void calculateCameras() {
		// Set up the camera list
		cameras = new HashSet<String>();
//		
//		// Check for single connections
//		for (String node : connections.keySet()) {
//			// If a city only has one connection, then the adjacent
//			// city should have a camera
//			if (connections.get(node) == 1) {
//				String cameraCity = null;
//				for (Road r : city) {
//					if (r.getNeighborhood1().equals(node)) {	// Find the adjacent city
//						cameraCity = r.getNeighborhood2();
//					}
//					else if (r.getNeighborhood2().equals(node)) {
//						cameraCity = r.getNeighborhood1();
//					}
//				}
//				cameras.add(cameraCity);	// Add the adjacent city to the list
//			}
//		}
		
		// Check for single dependency
		for (String start : connections.keySet()) {	// Iterate over all start
			for (String end : connections.keySet()) {	// and end points
				if (!start.equals(end)) {				// that are different.
					List<Path> paths = calculatePaths(start, end);	// Find all of the paths
					HashMap<String, Integer> candidates = new HashMap<String, Integer>();
					for(Path p : paths) {	// Iterate over all paths
						for (String s : p.getPathNodes()) {	// Iterate over all nodes in the path
							if (!s.equals(p.getStartNode()) && !s.equals(p.getEndNode())) {
								if (candidates.containsKey(s)) {
									candidates.put(s, candidates.get(s) + 1);
								}
								else {
									candidates.put(s, 1);
								}
							}
						}
					}
					
					// Check the candidates for nodes that appear every time
					for (String node : candidates.keySet()) {
						if (candidates.get(node) == paths.size()) {
							cameras.add(node);
						}
					}
				}
			}
		}
	}

	/**
	 * This function takes in a starting and an end point, and returns a
	 * List of all of the distinct, loop-free paths connecting the two.
	 * This function is effectively a wrapper around the depthFirstSearch function.
	 * @param start The node to start from.
	 * @param end The node to end at.
	 * @return A list of paths from the start node to the end node,
	 * including the start and end nodes.
	 */
	public List<Path> calculatePaths(String start, String end) {
		List<Path> paths = new ArrayList<Path>();
		List<String> pathSoFar = new ArrayList<String>();
		pathSoFar.add(start);
		return depthFirstSearch(start, end, pathSoFar, paths);
	}
	
	/**
	 * This function performs a depth first search to find all
	 * paths from the start node to the end node.
	 * @param start The node to start from.
	 * @param end The node to end at.
	 * @param pathSoFar The current path of nodes from start on the way to the end.
	 * @param paths The list of complete Paths that go from start to end.
	 * @return The complete set of Paths from start to end.
	 */
	private List<Path> depthFirstSearch(String start, String end, List<String> pathSoFar, List<Path> paths) {
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
						paths = depthFirstSearch(start, end, myPathSoFar, paths);
					}
				}
			}
		}
		
		return paths;
	}

	/**
	 * @return a collection of all neighborhoods containing cameras
	 */
	public Collection<String> getCameras()
	{
		return cameras;
	}

	/**
	 * @param neighborhood the neighborhood under consideration
	 * @return true if the neighborhood has a camera
	 */
	public boolean hasCamera(String neighborhood) {
		return cameras.contains(neighborhood);
	}
}
