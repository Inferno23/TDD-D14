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
	private PathCalculater pather;

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
		
		pather = new PathCalculater();
		
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
		
		for (String start : connections.keySet()) {	// Iterate over all start
			for (String end : connections.keySet()) {	// and end points
				if (!start.equals(end)) {				// that are different.
					List<Path> paths = pather.calculatePaths(start, end, city);	// Find all of the paths
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
