package hu.unideb.inf.elementbound.celldweller.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Class representing the 2D space of cellular automata. 
 * It uses double-buffering: the front buffer is used for reading, and the back buffer is used 
 * for reading. This way, the class can simulate the cells' parallel nature. 
 * 
 * Double buffering can also lead to some confusion, <b>so DON'T forget to call swapBuffers()!</b>
 * <pre>{@code
 * Cellverse cellverse = new Cellverse();
 * ISimulator simulator = new VonNeumannSimulator();
 * simulator.setRule(...);
 * 
 * cellverse.setCell(new Point(0,0), true);
 * cellverse.swapBuffers(); //!
 * 
 * simulator.step(cellverse);
 * cellverse.swapBuffers(); //!
 * }</pre>
 */
public class Cellverse {
	/**
	 * Class representing a point in space. 
	 * Used to identify cells. 
	 */
	public static class Point {
		/**
		 * x coordinate in space. 
		 */
		public int x;
		
		/**
		 * y coordinate in space. 
		 */
		public int y;
		
		/**
		 * Construct point from coordinates. 
		 * @param x x coordinate in space
		 * @param y y coordinate in space
		 */
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		@Override
		public boolean equals(Object rhs) {
			if(rhs == null) return false;
			if(rhs == this) return true;
			Point p = (Point)rhs;
			return x == p.x && y == p.y;
		}
		
		@Override
		public int hashCode() {
			return (x & 0xFFFFFFFF) | ((y & 0xFFFFFFFF) << 16);
		}
	}
	
	/**
	 * Front buffer of living cells. 
	 * Reading operations are performed on this set. 
	 */
	private Set<Point> frontBuffer;
	
	/**
	 * Back buffer of living cells. 
	 * Writing operations are performed on this set. 
	 */
	private Set<Point> backBuffer;
	
	/**
	 * Construct empty cellverse. 
	 */
	public Cellverse() {
		frontBuffer = new HashSet<Point>();
		backBuffer = new HashSet<Point>();
	}
	
	/**
	 * Set cell state. 
	 * @param cell Point instance identifying cell
	 * @param value Cell state. True for alive, false for dead.
	 * @see #getCell(Point)
	 * @see #clear()
	 */
	public void setCell(Point cell, boolean value) {
		if(value == true) {
			if(!backBuffer.contains(cell))
				backBuffer.add(cell);
		}
		else
			backBuffer.remove(cell);
	}
	
	/**
	 * Get cell state. 
	 * @param cell Point instance identifying cell
	 * @return Cell state. True for alive, false for dead. 
	 * @see #setCell(Point, boolean)
	 */
	public boolean getCell(Point cell) {
		return frontBuffer.contains(cell);
	}
	
	/**
	 * Get alive cells. 
	 * @return A set of living cells
	 */
	public Set<Point> getAliveCells() {
		return Collections.unmodifiableSet(frontBuffer);
	}
	
	/**
	 * Clear universe - set every cell to dead. 
	 * @see #isEmpty()
	 */
	public void clear() {
		backBuffer.clear();
	}
	
	/**
	 * Check whether the universe is empty. 
	 * @return True for empty, false if there are any cells alive
	 * @see #clear()
	 */
	public boolean isEmpty() {
		return frontBuffer.isEmpty();
	}
	
	/**
	 * Swap front- and back buffers. Used to mark the end of a simulation step. 
	 */
	public void swapBuffers() {
		Set<Point> tmp = frontBuffer;
		frontBuffer = backBuffer;
		backBuffer = tmp;
		
		backBuffer.clear();
		backBuffer.addAll(frontBuffer);
	}
}
