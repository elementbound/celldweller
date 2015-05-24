package hu.unideb.inf.elementbound.celldweller.controller;

import hu.unideb.inf.elementbound.celldweller.model.Cellverse;
import hu.unideb.inf.elementbound.celldweller.model.Cellverse.Point;

import java.util.BitSet;
import java.util.HashSet;
import java.util.Set;

/**
 * Implements simulation based on von Neumann neighborhood, 
 * which means using the top, left, bottom, right neighbors. 
 * @author adminus
 */
public class VonNeumannSimulator implements ISimulator {
	private BitSet rule;
	private static Point[] offsets = {
		new Point( 0,  0), 
		new Point(-1,  0), 
		new Point(+1,  0), 
		new Point( 0, -1),
		new Point( 0, +1)
	};

	public static final int CENTER_BIT	= 0;
	public static final int LEFT_BIT	= 1;
	public static final int RIGHT_BIT 	= 2;
	public static final int TOP_BIT 	= 3;
	public static final int BOTTOM_BIT	= 4;
	
	public static final int CENTER_RULE	= 1;
	public static final int LEFT_RULE	= 2;
	public static final int RIGHT_RULE 	= 4;
	public static final int TOP_RULE 	= 8;
	public static final int BOTTOM_RULE	= 16;

	@Override
	public void setRule(BitSet rule) {
		this.rule = rule;
	}

	@Override
	public BitSet getRule() {
		return this.rule;
	}

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
