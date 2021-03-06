package hu.unideb.inf.elementbound.celldweller.controller;

import hu.unideb.inf.elementbound.celldweller.model.Cellverse;
import hu.unideb.inf.elementbound.celldweller.model.Cellverse.Point;

import java.util.BitSet;
import java.util.HashSet;
import java.util.Set;

/**
 * Implements simulation based on von Neumann neighborhood, 
 * which means using the top, left, bottom, right neighbors. 
 * 
 * The class provides some handy constants to produce rules. 
 * For example, if you want a rule where the cell lives only when: 
 * the cell itself is alive <br>
 * its top neighbor is alive <br>
 * and its right neighbor is alive: 
 * <pre>
 * {@code
 * ISimulator simulator = new VonNeumannSimulator();
 * BitSet ruleSet = new BitSet();
 * 
 * int rule = 0;
 * 	rule |= VonNeumannSimulator.CENTER_RULE;
 * 	rule |= VonNeumannSimulator.TOP_RULE;
 * 	rule |= VonNeumannSimulator.RIGHT_RULE;
 * ruleSet.set(rule, true);
 * 
 * simulator.setRule(ruleSet);
 * }
 * </pre>
 * @author adminus
 */
public class VonNeumannSimulator implements ISimulator {
	/**
	 * Rule stored internally. 
	 */
	private BitSet rule;
	
	/**
	 * An array of neighboring cells to consider during simulation. 
	 * These offsets are always relative to the currently simulated cell. 
	 */
	private static Point[] offsets = {
		new Point( 0,  0), 
		new Point(-1,  0), 
		new Point(+1,  0), 
		new Point( 0, -1),
		new Point( 0, +1)
	};

	/**
	 * Bit ID for center rule - That is, set the bit when the cell is alive. 
	 */
	public static final int CENTER_BIT	= 0;

	/**
	 * Bit ID for left rule - That is, set the bit when left neighbor is alive.  
	 */
	public static final int LEFT_BIT	= 1;

	/**
	 * Bit ID for right rule - That is, set the bit when right neighbor is alive.  
	 */
	public static final int RIGHT_BIT 	= 2;

	/**
	 * Bit ID for top rule - That is, set the bit when top neighbor is alive.  
	 */
	public static final int TOP_BIT 	= 3;

	/**
	 * Bit ID for bottom rule - That is, set the bit when bottom neighbor is alive.  
	 */
	public static final int BOTTOM_BIT	= 4;
	
	/**
	 * Bit mask for center rule. 
	 * These can be bitwise OR'd together, e.g. {@code CENTER_RULE | LEFT_RULE} to produce an 
	 * index into the rule BitSet.
	 */
	public static final int CENTER_RULE	= 1;
	
	/**
	 * Bit mask for left rule. 
	 * These can be bitwise OR'd together, e.g. {@code CENTER_RULE | LEFT_RULE} to produce an 
	 * index into the rule BitSet.
	 */
	public static final int LEFT_RULE	= 2;
	
	/**
	 * Bit mask for right rule. 
	 * These can be bitwise OR'd together, e.g. {@code CENTER_RULE | LEFT_RULE} to produce an 
	 * index into the rule BitSet.
	 */
	public static final int RIGHT_RULE 	= 4;
	
	/**
	 * Bit mask for top rule. 
	 * These can be bitwise OR'd together, e.g. {@code CENTER_RULE | LEFT_RULE} to produce an 
	 * index into the rule BitSet.
	 */
	public static final int TOP_RULE 	= 8;
	
	/**
	 * Bit mask for bottom rule. 
	 * These can be bitwise OR'd together, e.g. {@code CENTER_RULE | LEFT_RULE} to produce an 
	 * index into the rule BitSet.
	 */
	public static final int BOTTOM_RULE	= 16;
	
	/**
	 * Set rule. 
	 * @param rule Bitset of rules
	 */
	@Override
	public void setRule(BitSet rule) {
		this.rule = rule;
	}

	/**
	 * Get rule. 
	 * @return Bitset of rules
	 */
	@Override
	public BitSet getRule() {
		return this.rule;
	}

	/**
	 * Perform a simulation step. 
	 * <b>Do note that you still have to call Cellverse.swapBuffers() manually</b>
	 * @param cellverse Cellverse to modify
	 */
	@Override
	public void step(Cellverse cellverse) {
		Set<Point> cellsToVisit = new HashSet<Point>();
		
		for(Point p : cellverse.getAliveCells()) 
			for(Point o : offsets) 
				cellsToVisit.add(new Point(p.x+o.x, p.y+o.y));
		
		for(Point cell : cellsToVisit) {
			int bitIndex = 0;
			int bitMultiplier = 1;
			
			for(Point o : offsets) {
				if(cellverse.getCell(new Point(cell.x + o.x, cell.y + o.y)))
					bitIndex += bitMultiplier;
				
				bitMultiplier *= 2;
			}
			
			cellverse.setCell(cell, rule.get(bitIndex));
		}
	}
}
