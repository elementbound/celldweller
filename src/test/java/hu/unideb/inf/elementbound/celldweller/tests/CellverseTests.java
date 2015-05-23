package hu.unideb.inf.elementbound.celldweller.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import hu.unideb.inf.elementbound.celldweller.model.Cellverse;

public class CellverseTests {
	@Test
	public void emptyInitializerTest() {
		Cellverse cellverse = new Cellverse();
		assertTrue(cellverse.isEmpty());
	}
	
	@Test
	public void hashCodeTest() {
		Cellverse.Point point = new Cellverse.Point(1, 1);
		assertEquals(0x1_0001, point.hashCode());
	}
}
