package hu.unideb.inf.elementbound.celldweller.view;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class CellverseDisplay extends Canvas {
	private String[] texts = {
			"Nothing to see here. ",
			"Yet.",
			"Really tho.",
			"Move along.", 
			"I don't have all day for this. ",
			"...",
			"... ...",
			"... ... ...", 
			"!!!", 
			"-.-",
			"I'm outta here",
			"Peace", 
			"   ",
			"   ",
			" . "};
	
	private int textId = 0;
	
	public void advanceText() {
		textId++;
		if(textId >= texts.length)
			textId = texts.length-1;
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.drawString(texts[textId], 2, 24);
	}
}
