package hu.unideb.inf.elementbound.celldweller.view;

import hu.unideb.inf.elementbound.celldweller.model.Cellverse;
import hu.unideb.inf.elementbound.celldweller.model.Cellverse.Point;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class CellverseDisplay extends Canvas {
	public double originX = 0.0;
	public double originY = 0.0;
	public double zoom = 8.0;
	
	private Cellverse cellverse = null;

	public void setCellverse(Cellverse cellverse) {
		this.cellverse = cellverse;
	}
	
	@Override
	public void paint(Graphics g) {
		if(cellverse == null)
			return;
		
		Dimension dims = this.getSize();
		
		Graphics2D g2 = (Graphics2D)g;
		AffineTransform backupTransform = g2.getTransform();
		AffineTransform transform = (AffineTransform) backupTransform.clone();
			transform.translate(dims.getWidth()/2.0, dims.getHeight()/2.0);
			transform.scale(zoom, zoom);
			transform.translate(-originX, -originY);
			
		g2.setTransform(transform);
		g2.setColor(new Color(0));
		for(Point cell : cellverse.getAliveCells()) 
			g2.fillRect(cell.x, cell.y, 1, 1);
		
		g2.setTransform(backupTransform);
	}
}
