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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.HashSet;

import org.junit.Test;

/**
 * Tests for the Path class.
 * @version April 7, 2014
 * @author Michael Burns
 */
public class PathTest {
	

	/**
	 * This function takes in an array of nodes and uses
	 * it to populate a new Path.
	 * @param nodes The names of the nodes in the path.
	 * @return The Path to use in testing.
	 */
	private Path generatePath(final String[] nodes) {
		Collection<String> pathNodes = new HashSet<String>();
		for (String node : nodes) {
			pathNodes.add(node);
		}
		
		return new Path(pathNodes);
	}

	@Test
	public void testGetStartAndEndPointsFromPath() {
		final String[] nodes = {
				"A", "B", "C"
		};
		
		final Path path = generatePath(nodes);
		assertEquals("A", path.getStartNode());
		assertEquals("C", path.getEndNode());
	}
	
	@Test
	public void testPathContains() {
		final String[] nodes = {
				"A", "B", "C"
		};
		
		final Path path = generatePath(nodes);
		assertTrue(path.contains("A"));
		assertFalse(path.contains("D"));
	}
}
