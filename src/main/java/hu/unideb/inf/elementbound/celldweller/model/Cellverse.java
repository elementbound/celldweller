package hu.unideb.inf.elementbound.celldweller.model;

import java.util.Set;

/**
 * Class representing the 2D space of cellular automata
 */
public class Cellverse {
	/**
	 * Class representing a point in space. 
	 * Used to identify cells. 
	 */
	public static class Point {
		public int x;
		public int y;
		
		/**
		 * Construct point from coordinates
		 * @param x x coordinate in space
		 * @param y y coordinate in space
		 */
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	private Set<Point>[] aliveCells;
	
	/**
	 * Set cell state
	 * @param cell Point instance identifying cell
	 * @param value Cell state. True for alive, false for dead.
	 * @see #getCell(Point)
	 * @see #clear()
	 */
	public void setCell(Point cell, boolean value) {
		;
	}
	
	/**
	 * Get cell state
	 * @param cell Point instance identifying cell
	 * @return Cell state. True for alive, false for dead. 
	 * @see #setCell(Point, boolean)
	 */
	public boolean getCell(Point cell) {
		return false;
	}
	
	/**
	 * Get alive cells
	 * @return A set of alive cells
	 */
	public Set<Point> getAliveCells() {
		return aliveCells[0];
	}
	
	/**
	 * Clear universe - set every cell to dead
	 * @see #isEmpty()
	 */
	public void clear() {
		;
	}
	
	/**
	 * Check whether the universe is empty
	 * @return True for empty, false if there are any cells alive
	 * @see #clear()
	 */
	public boolean isEmpty() {
		return true;
	}
	
	/**
	 * Swap front- and backbuffer. Used to mark the end of a simulation step. 
	 */
	public void swapBuffers() {
		;
	}
}
