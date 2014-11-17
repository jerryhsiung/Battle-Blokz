package tetris;

import java.awt.Color;

//import tetrisWorking.shapes.pieces;

public class tileData {
	
	Color tileColor;
	private boolean isFull;
	
	public tileData() {
		isFull = false;
		tileColor = Color.DARK_GRAY.darker().darker().darker().darker();
	}
	
	public void reset() {
		tileColor = Color.DARK_GRAY.darker().darker().darker().darker();
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
