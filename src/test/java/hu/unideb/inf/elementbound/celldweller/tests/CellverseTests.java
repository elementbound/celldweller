package hu.unideb.inf.elementbound.celldweller.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import hu.unideb.inf.elementbound.celldweller.model.Cellverse;
import hu.unideb.inf.elementbound.celldweller.model.Cellverse.Point;

public class CellverseTests {
	@Test
	public void emptyInitializerTest() {
		Cellverse cellverse = new Cellverse();
		assertTrue(cellverse.isEmpty());
	}
	
	@Test
	public void clearTest() {
		Cellverse cellverse = new Cellverse();
		
		cellverse.setCell(new Point(0,0), true);
		cellverse.swapBuffers();
		
		cellverse.clear();
		cellverse.swapBuffers();
		
		assertTrue(cellverse.isEmpty());
	}
	
	@Test
	public void setTest() {
		Cellverse cellverse = new Cellverse();
		
		cellverse.setCell(new Point(0,0), true);
		cellverse.setCell(new Point(1,0), false);
		cellverse.swapBuffers();
		
		assertEquals(1, cellverse.getAliveCells().size());
	}
	
	@Test
	public void hashCodeTest() {
		Cellverse.Point point = new Cellverse.Point(1, 1);
		assertEquals(0x10001, point.hashCode());
	}
}
