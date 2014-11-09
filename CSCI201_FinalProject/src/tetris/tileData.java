package tetris;

import java.awt.Color;

import tetris.shapes.pieces;

public class tileData {
	
	Color tileColor;
	private boolean isFull;
	
	public tileData() {
		isFull = false;
		tileColor = Color.BLACK;
	}
	
	public void setAll(pieces p) {
		// Shape names - i, o, t, s, z, j, l
		
		Color[] list = {Color.CYAN, Color.YELLOW, Color.MAGENTA, Color.GREEN, Color.RED, Color.BLUE, Color.ORANGE};
		
		tileColor = list[p.ordinal()];
		isFull = true;
	}
	
	public void reset() {
		tileColor = Color.BLACK;
		isFull = false;
	}
	
	public void setIsFull(boolean b) {
		isFull = b;
	}
	
	public void setColor(Color c) {
		tileColor = c;
	}
	
	public boolean isFull() {
		return isFull;
	}
	
	public Color getColor() {
		return tileColor;
	}
}
