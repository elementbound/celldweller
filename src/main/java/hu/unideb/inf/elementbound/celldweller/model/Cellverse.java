package hu.unideb.inf.elementbound.celldweller.model;

import java.util.Set;

public class Cellverse {
	public static class Point {
		public int x;
		public int y;
		
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	private Set<Point>[] aliveCells;
	
	void setCell(Point cell, boolean value) {
		;
	}
	
	boolean getCell(Point cell) {
		return false;
	}
	
	Set<Point> getAliveCells() {
		return aliveCells[0];
	}
	
	void swapBuffers() {
		;
	}
}
