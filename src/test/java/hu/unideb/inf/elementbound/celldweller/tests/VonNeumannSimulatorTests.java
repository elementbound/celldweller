package hu.unideb.inf.elementbound.celldweller.tests;

import static org.junit.Assert.*;

import hu.unideb.inf.elementbound.celldweller.controller.VonNeumannSimulator;
import hu.unideb.inf.elementbound.celldweller.model.Cellverse;
import hu.unideb.inf.elementbound.celldweller.model.Cellverse.Point;

import java.util.BitSet;

import org.junit.Test;

public class VonNeumannSimulatorTests {
	@Test
	public void emptyRuleTest() {
		BitSet rule = new BitSet();
		for(int i = 0; i < 32; i++)
			rule.set(i, false);
		
		VonNeumannSimulator sim = new VonNeumannSimulator();
		sim.setRule(rule);
		
		Cellverse cellverse = new Cellverse();
		cellverse.setCell(new Point(0,0), true);
		cellverse.swapBuffers();
		
		sim.step(cellverse);
		cellverse.swapBuffers();
		
		assertFalse(cellverse.getCell(new Point(0,0)));
	}
	
	@Test
	public void simpleRuleTest() {
		BitSet rule = new BitSet();
		VonNeumannSimulator sim = new VonNeumannSimulator();
		Cellverse cellverse = new Cellverse();
		
		rule.set(VonNeumannSimulator.CENTER_RULE, true);
		sim.setRule(rule);
		
		cellverse.setCell(new Point(0,0), true);
		cellverse.swapBuffers();
		
		sim.step(cellverse);
		cellverse.swapBuffers();
		
		assertEquals(cellverse.getAliveCells().size(), 1);
		assertEquals(cellverse.getCell(new Point(0,0)), true);
	}
}
