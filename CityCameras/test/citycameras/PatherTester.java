package citycameras;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

public class PatherTester {
	
	PathCalculater pather;

	/**
	 * This function takes in an array of roads, and uses
	 * it to populate a collection.
	 * @param roads The array of roads for the city.
	 * @return The Collection to use in testing.
	 */
	private Collection<Road> generateCollection(final Road[] roads) {
		Collection<Road> city = new HashSet<Road>();
		for (Road r : roads) {
			city.add(r);
		}
		return city;
	}
	
	@Before
	public void setup() {
		pather = new PathCalculater();
	}
	
	@Test
	public void testCalculatePathForEndToEndOfThreeInARow() {
		final Road[] roads = {
				new Road("A", "B"), new Road("B", "C")	
		};

		final Collection<Road> roadCollection = generateCollection(roads);
		final ArrayList<Path> paths = (ArrayList<Path>) pather.calculatePaths("A", "C", roadCollection);
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

		final Collection<Road> roadCollection = generateCollection(roads);
		final ArrayList<Path> paths = (ArrayList<Path>) pather.calculatePaths("B", "C", roadCollection);
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

		final Collection<Road> roadCollection = generateCollection(roads);
		final ArrayList<Path> paths = (ArrayList<Path>) pather.calculatePaths("A", "D", roadCollection);
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

		final Collection<Road> roadCollection = generateCollection(roads);
		final ArrayList<Path> paths = (ArrayList<Path>) pather.calculatePaths("A", "D", roadCollection);
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

		final Collection<Road> roadCollection = generateCollection(roads);
		final ArrayList<Path> paths = (ArrayList<Path>) pather.calculatePaths("A", "D", roadCollection);
		assertEquals(3, paths.size());
		final Path pathZero = paths.get(0);
		final Path pathOne = paths.get(1);
		final Path pathTwo = paths.get(2);
		assertEquals(4, pathZero.getPathNodes().size());
		assertEquals(2 + 4 + 3, pathZero.getPathNodes().size() + pathOne.getPathNodes().size(), pathTwo.getPathNodes().size());
	}
	
}
