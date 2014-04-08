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