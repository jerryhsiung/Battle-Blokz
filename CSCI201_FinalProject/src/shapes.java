
import java.awt.Color;
import java.util.Random;

import tetris.gui;

public class shapes {
	// Shape names - i, o, t, s, z, j, l
	enum pieces { i, o, t, s, z, j, l };

    private int[][][] shapesTable = new int[][][] {
    		// LINE
            { {0,-1} , {0,0} , {0,1} , {0,2} },
            // SQUARE
            { {0,-1} , {1,-1} , {0,0} , {1,0} },
            // T
            { {0,-1} , {-1,0} , {0,0} , {1,0} },
            // S
            { {-1,-1} , {0,-1} , {0,0} , {1,0} },
            // Z
            { {0,-1} , {1,-1}, {-1,0} , {0,0} },
            // J
            { {1,-1} , {-1,0} , {0,0} , {1,0} },
            // L
            { {-1,-1} , {-1,0} , {0,0} , {1,0} }
    };;
    
    private int shapeXY[][];
    private boolean hasShape;
    private Color shapeColor;
    private pieces pieceType;
    
    public shapes() {
    	// Currently has no shape
    	hasShape = false;
    	shapeXY = new int[4][2];
    	
    	// Initialize coordinates to 0 
    	for(int i = 0; i < 4; i++) {
    		for(int j = 0; j < 2; j++) {
    			shapeXY[i][j] = 0;
    		}
    	}
    	
    	shapeColor = Color.BLACK;
    	
    }
    
    public void setPiece(pieces p) {
    	hasShape = true;
    	
    	for(int i = 0; i < 4; i++) {
    		for(int j = 0; j < 2; j++) {
    	    	shapeXY[i][j] = shapesTable[p.ordinal()][i][j];
    		}
    	}
    	
    	Color[] list = { 
    			new Color(0,128,255) ,
    			new Color(255,255,0) ,
    			new Color(100,23,255) , 
    			new Color(85,255,0) , 
    			new Color(255,0,0) , 
    			new Color(255,117,164) , 
    			new Color(255,172,100) , 
    	};
    	
    	shapeColor = list[p.ordinal()];
		
		pieceType = p;
    }
    
    public void setRandomPiece() {
    	pieces [] pieceList = pieces.values();
    	
    	Random rn = new Random();
    	
    	int randPiece = rn.nextInt(7);
    	System.out.println(randPiece);	
		setPiece(pieceList[randPiece]);
    }
    
    public void setNoShape() {
    	hasShape = false; 
    	shapeColor = Color.BLACK;
    }
    
    public boolean hasShape() {
    	return hasShape;
    }
    
    public int[][] getShapeXY() {
    	return shapeXY;
    }
    
    public Color getColor() {
    	return shapeColor;
    }
    
    public boolean isLine() {
    	if(pieceType == pieces.i) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    
    public void rotate() {
    	int [][] temp = shapeXY;
    	
    	if(pieceType == pieces.o) {
    		return;
    	}
    	else {
    		for(int i = 0; i < 4; i ++) {
    			
        		// CHECKING IF NEW COORDINATES FIT IN THE CURRENT GRID
    			if(gui.tetrisBoard[(gui.currentPieceY+temp[i][0])*gui.tetrisBoardWidthSquare + gui.currentPieceX+temp[i][1]].isFull()) {
    				return;
    			}
    			// CHECK BORDERS (SIDES)
    			else if(gui.currentPieceX+temp[i][1] > 9 || gui.currentPieceX+temp[i][1] < 0) {
    	    	//	System.out.println("SIDES");
    				return;
    	    	}
    	    		
    		}
    		
    		for (int i = 0; i < 4; i++) {
    			int x = temp[i][0];
    			int y = temp[i][1];
    			// SET X : x = y
                shapeXY[i][0] = y;
                // SET Y : y = -x
                shapeXY[i][1] = -x;
                
            //    System.out.println(shapeXY[i][0] + ", " + shapeXY[i][1]);
            }
    	}
    	
    	
    }
    
}
