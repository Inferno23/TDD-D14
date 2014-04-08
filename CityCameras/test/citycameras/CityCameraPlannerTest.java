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

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

/**
 * Sample tests for the TDD assignment in CS3733.
 * @version Mar 30, 2014
 * @author gpollice
 */
public class CityCameraPlannerTest
{
	/**
	 * This function takes in an array of roads, and uses
	 * it to populate a new CityCameraPlanner.
	 * @param roads The array of roads for the city.
	 * @return The CityCameraPlanner to use in testing.
	 */
	private CityCameraPlanner generateCity(final Road[] roads) {
		Collection<Road> city = new HashSet<Road>();
		for (Road r : roads) {
			city.add(r);
		}
		final CityCameraPlanner cameraPlanner = new CityCameraPlanner(city);
		return cameraPlanner;
	}

	@Test
	public void testStraightLineOfThree()
	{
		final Road[] roads = {
				new Road("A", "B"), new Road("B", "C")	
		};

		final CityCameraPlanner cameraPlanner = generateCity(roads); 
		assertEquals(1, cameraPlanner.getCameras().size());
		assertTrue(cameraPlanner.getCameras().contains("B"));
		assertTrue(cameraPlanner.hasCamera("B"));
		assertFalse(cameraPlanner.hasCamera("A"));
	}

	@Test
	public void testTriangle()
	{
		final Road[] roads = {
				new Road("A", "B"), new Road("B", "C"), new Road("A", "C")	
		};

		final CityCameraPlanner cameraPlanner = generateCity(roads); 
		assertEquals(0, cameraPlanner.getCameras().size());
		assertFalse(cameraPlanner.getCameras().contains("B"));
	}

	@Test
	public void testFourInARow() {
		final Road[] roads = {
				new Road("A", "B"), new Road("B", "C"), new Road("C", "D")
		};

		final CityCameraPlanner cameraPlanner = generateCity(roads); 
		assertEquals(2, cameraPlanner.getCameras().size());
		assertFalse(cameraPlanner.getCameras().contains("A"));
		assertTrue(cameraPlanner.getCameras().contains("B"));
		assertTrue(cameraPlanner.getCameras().contains("C"));
		assertFalse(cameraPlanner.getCameras().contains("D"));
	}

	@Test
	public void testFourInACircle() {
		final Road[] roads = {
				new Road("A", "B"), new Road("B", "C"), new Road("C", "D"), new Road("A", "D")
		};
		final CityCameraPlanner cameraPlanner = generateCity(roads); 
		assertEquals(0, cameraPlanner.getCameras().size());
		assertFalse(cameraPlanner.getCameras().contains("A"));
		assertFalse(cameraPlanner.getCameras().contains("B"));
		assertFalse(cameraPlanner.getCameras().contains("C"));
		assertFalse(cameraPlanner.getCameras().contains("D"));
	}

	@Test
	public void testTwoTrianglesWithTwoConnections() {
		final Road[] roads = {
				new Road("A", "B"), new Road("B", "C"), new Road("C", "D"), new Road("A", "D"), new Road("D", "B")
		};
		final CityCameraPlanner cameraPlanner = generateCity(roads);
		assertEquals(0, cameraPlanner.getCameras().size());
		assertFalse(cameraPlanner.getCameras().contains("A"));
		assertFalse(cameraPlanner.getCameras().contains("B"));
		assertFalse(cameraPlanner.getCameras().contains("C"));
		assertFalse(cameraPlanner.getCameras().contains("D"));
	}

	@Test
	public void testCalculatePathForEndToEndOfThreeInARow() {
		final Road[] roads = {
				new Road("A", "B"), new Road("B", "C")	
		};

		final CityCameraPlanner cameraPlanner = generateCity(roads); 
		final ArrayList<Path> paths = (ArrayList<Path>) cameraPlanner.calculatePaths("A", "C");
		final Path path = paths.get(0);
		assertEquals(1, paths.size());
		assertEquals(3, path.getPathNodes().size());
		assertEquals("A", path.getStartNode());
		assertEquals("C", path.getEndNode());
	}
	
	@Test
	public void testCalculatePathForMidToEndOfThreeInARow() {
		final Road[] roads = {
				new Road("A", "B"), new Road("B", "C")	
		};

		final CityCameraPlanner cameraPlanner = generateCity(roads); 
		final ArrayList<Path> paths = (ArrayList<Path>) cameraPlanner.calculatePaths("B", "C");
		final Path path = paths.get(0);
		assertEquals(1, paths.size());
		assertEquals(2, path.getPathNodes().size());
		assertEquals("B", path.getStartNode());
		assertEquals("C", path.getEndNode());
	}
	
	@Test
	public void testCalculatePathForEndToEndOfFourInRow() {
		final Road[] roads = {
				new Road("A", "B"), new Road("B", "C"), new Road("C", "D")
		};

		final CityCameraPlanner cameraPlanner = generateCity(roads); 
		final ArrayList<Path> paths = (ArrayList<Path>) cameraPlanner.calculatePaths("A", "D");
		final Path path = paths.get(0);
		assertEquals(1, paths.size());
		assertEquals(4, path.getPathNodes().size());
		assertEquals("A", path.getStartNode());
		assertEquals("D", path.getEndNode());
		assertTrue(path.contains("A"));
		assertTrue(path.contains("B"));
		assertTrue(path.contains("C"));
		assertTrue(path.contains("D"));
	}
	
	@Test
	public void testCalculatePathInSquare() {
		final Road[] roads = {
				new Road("A", "B"), new Road("B", "C"), new Road("C", "D"), new Road("A", "D")
		};

		final CityCameraPlanner cameraPlanner = generateCity(roads); 
		final ArrayList<Path> paths = (ArrayList<Path>) cameraPlanner.calculatePaths("A", "D");
		assertEquals(2, paths.size());
		final Path pathZero = paths.get(0);
		final Path pathOne = paths.get(1);
		assertEquals(4, pathZero.getPathNodes().size());
		assertEquals("A", pathZero.getStartNode());
		assertEquals("D", pathZero.getEndNode());
		assertEquals("A", pathOne.getStartNode());
		assertEquals("D", pathOne.getEndNode());
		assertEquals(2 + 4, pathZero.getPathNodes().size() + pathOne.getPathNodes().size());
	}
	
	@Test
	public void testCalculatePathInSquareWithDiagonal() {
		final Road[] roads = {
				new Road("A", "B"), new Road("B", "C"), new Road("C", "D"), new Road("A", "D"), new Road("B", "D")
		};

		final CityCameraPlanner cameraPlanner = generateCity(roads); 
		final ArrayList<Path> paths = (ArrayList<Path>) cameraPlanner.calculatePaths("A", "D");
		assertEquals(3, paths.size());
		final Path pathZero = paths.get(0);
		final Path pathOne = paths.get(1);
		final Path pathTwo = paths.get(2);
		assertEquals(4, pathZero.getPathNodes().size());
		assertEquals(2 + 4 + 3, pathZero.getPathNodes().size() + pathOne.getPathNodes().size(), pathTwo.getPathNodes().size());
	}
	
	@Test
	public void testTwoTrianglesWithOneConnection() {
		final Road[] roads = {
				new Road("A", "B"), new Road("B", "C"), new Road("A", "C"),
				new Road("C", "D"), new Road("D", "E"), new Road("C", "E")
		};
		final CityCameraPlanner cameraPlanner = generateCity(roads);
		assertEquals(1, cameraPlanner.getCameras().size());
		assertFalse(cameraPlanner.getCameras().contains("A"));
		assertFalse(cameraPlanner.getCameras().contains("B"));
		assertTrue(cameraPlanner.getCameras().contains("C"));
		assertFalse(cameraPlanner.getCameras().contains("D"));
		assertFalse(cameraPlanner.getCameras().contains("E"));
	}
	
	@Test
	public void testExampleWithAThroughG() {
		final Road[] roads = {
				new Road("A", "B"), new Road("B", "C"), new Road("A", "C"),
				new Road("C", "D"), new Road("D", "E"), new Road("F", "E"),
				new Road("C", "F"), new Road("F", "G")
		};
		final CityCameraPlanner cameraPlanner = generateCity(roads);
		assertEquals(2, cameraPlanner.getCameras().size());
		assertFalse(cameraPlanner.getCameras().contains("A"));
		assertFalse(cameraPlanner.getCameras().contains("B"));
		assertTrue(cameraPlanner.getCameras().contains("C"));
		assertFalse(cameraPlanner.getCameras().contains("D"));
		assertFalse(cameraPlanner.getCameras().contains("E"));
		assertTrue(cameraPlanner.getCameras().contains("F"));
		assertFalse(cameraPlanner.getCameras().contains("G"));
	}
}