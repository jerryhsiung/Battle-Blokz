package tetris;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Stroke;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class gui extends JPanel {
	
	static int tetrisBoardWidthSquare = 10; 
	static int tetrisBoardHeightSquare = 24;
	int tileLength = 30; 
//	int tetrisBoardWidth = 10*tileLength;
//	int tetrisBoardHeight = 22*tileLength;
	
	
	public static tileData [] tetrisBoard = new tileData[tetrisBoardWidthSquare*tetrisBoardHeightSquare];
	
	// Current Piece Info
	shapes currentPiece = new shapes();
	static int currentPieceX = 0;
	static int currentPieceY = 4;
	
	boolean dropped = false;
	boolean gameOver = true;
	
	public int lineSent = 0;
	public JLabel scoreBoard = new JLabel();
	
	Timer timer = new Timer();

	public keyboardListener kl = new keyboardListener();
	
	public gui() {
		this.setBackground(Color.GRAY);
		
		// Initialize tetrisBoard 
		for(int i = 0; i < tetrisBoardWidthSquare*tetrisBoardHeightSquare; i++) {
			tetrisBoard[i] = new tileData();
		}
				
		nextPiece();
		
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				repaint();
				if(!gameOver) {
			        if (dropped) {
			            nextPiece();
			        } else {
			            downOne();
			        }
				}
				else {
					gui.this.removeKeyListener(kl);
				}
			}
		}, 400, 400);
		
//		timer.scheduleAtFixedRate(new TimerTask() {
//			public void run() {
//				addRandomLine();
//			}
//		}, 2000, 2000);

		
		scoreBoard.setAlignmentX(CENTER_ALIGNMENT);
		scoreBoard.setAlignmentY(CENTER_ALIGNMENT);
		scoreBoard.setHorizontalAlignment(SwingConstants.CENTER);
		scoreBoard.setVerticalAlignment(SwingConstants.CENTER);
		scoreBoard.setHorizontalTextPosition(SwingConstants.CENTER);
		scoreBoard.setVerticalTextPosition(SwingConstants.CENTER);
		scoreBoard.setFont(createFont());
		scoreBoard.setText("LINES - " + lineSent);
		scoreBoard.setForeground(Color.CYAN.darker());
		scoreBoard.setBackground(Color.DARK_GRAY.darker().darker().darker().darker());
		scoreBoard.setOpaque(true);
		
		scoreBoard.setBounds(0, (tetrisBoardHeightSquare-4)*tileLength, tetrisBoardWidthSquare*tileLength, scoreBoard.getPreferredSize().height);
		this.add(scoreBoard);
		
		this.setLayout(null);
		setFocusable(true);
		this.addKeyListener(kl);
	}
	
	public void startGame() {
		gameOver = false;
		this.addKeyListener(kl);

	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// DRAW EXISTING BOARD
		fillGrid(g);
		
		// DRAW CURRENT SHAPE
		if(currentPiece.hasShape()) {
			drawShadow(g, currentPieceX, currentPieceY, currentPiece);
			drawShape(g, currentPieceX, currentPieceY, currentPiece);
			
		}
		
//		// SCORE BOARD DRAW
//		updateScore();
	}
	
	public Font createFont() {
		// IMPLEMENT FONT
	    try {
	    	File file = new File("src/Font/digitalFont2.ttf");
	    	
	    	System.out.println(file.getAbsolutePath());
	    	
	    	Font font = Font.createFont(Font.TRUETYPE_FONT, file).deriveFont(12f);
		    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, file));
			
			return font.deriveFont(58f);
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    return null;
	}
	
	public void updateScore() {
		lineSent++;
		scoreBoard.setText("LINES - " + lineSent);
		
	}
	
	public void fillGrid(Graphics g) {
		
		// starts at 4 because 4 empty rows
		for(int i = 4; i < tetrisBoardHeightSquare; i++) {
			for(int j = 0; j < tetrisBoardWidthSquare; j++) {

				drawSquare(g, j, i, tetrisBoard[i*tetrisBoardWidthSquare + j].getColor());
			}
		}
		
		
	}

	public void drawSquare(Graphics g, int gridX, int gridY, Color c) {
		Graphics2D g2d = (Graphics2D) g;
		
		float thickness = 6;
		Stroke oldStroke = g2d.getStroke();
		
		g2d.setStroke(new BasicStroke(thickness));
		
		g2d.setColor(c);
		
//		g.fillRect(gridX*tileLength, gridY*tileLength, tileLength, tileLength);
		// GRIDY - 4 because 4 invisible rows
		g2d.fillRect(gridX*tileLength, (gridY-4)*tileLength, tileLength, tileLength);
		
		g2d.setColor(c.darker());
//		g.drawRect(gridX*tileLength, gridY*tileLength, tileLength, tileLength);
		// GRIDY - 4 because 4 invisible rows
		g2d.drawRect(gridX*tileLength+3, (gridY-4)*tileLength+3, tileLength-6, tileLength-6);

		g2d.setStroke(oldStroke);
		
	}
	
	public void drawShape(Graphics g, int pieceX, int pieceY, shapes currentPiece) {
		int [][] temp = currentPiece.getShapeXY();
		
		for(int i = 0; i < 4; i++) {
			drawSquare(g, pieceX+temp[i][0], pieceY-temp[i][1], currentPiece.getColor());
		}
	}
	
	public void drawShadow(Graphics g, int pieceX, int pieceY, shapes currentPiece) {
		int [][] temp = currentPiece.getShapeXY();
		
		boolean check = true;
		int tempX = pieceX;
		int tempY = pieceY;
		
		while(check) {
			
			// CHECK ALL 4 TILES OF SHAPE
			for(int i = 0; i < 4; i++) {
				// CHECK OUT OF BOUNDS OR BOTTOM
				if(((tempY+1-temp[i][1])*tetrisBoardWidthSquare + tempX+temp[i][0]) >= tetrisBoardWidthSquare*tetrisBoardHeightSquare) {
					check = false;
					break;
				}
				// CHECK TILE BELOW
				else if(tetrisBoard[(tempY+1-temp[i][1])*tetrisBoardWidthSquare + tempX+temp[i][0]].isFull()) {
					check = false;
					break;
				}		
			}
			
			if(check) {
				// SET NEW Y 
				tempY = tempY +1;;
			}
		}
		
		for(int i = 0; i < 4; i++) {
			drawSquare(g, tempX+temp[i][0], tempY-temp[i][1], Color.WHITE);
		}
		
	}
	
	public void downOne() {
		if(!canMove(currentPieceX, currentPieceY+1, currentPiece)) {
			pieceDropped();	
		}
	}
	
	public boolean canMove(int newX, int newY, shapes currentPiece) {
		
		int[][] temp = currentPiece.getShapeXY();
		
		

		// CHECK ALL 4 TILES OF SHAPE
		for(int i = 0; i < 4; i++) {
			// CHECK OUT OF BOUNDS OR BOTTOM
			if(((newY-temp[i][1])*tetrisBoardWidthSquare + newX+temp[i][0]) >= tetrisBoardWidthSquare*tetrisBoardHeightSquare) {
				return false;
			}
			// CHECK TILE BELOW
			else if(tetrisBoard[(newY-temp[i][1])*tetrisBoardWidthSquare + newX+temp[i][0]].isFull()) {
				return false;
			}		
		}
		
		// SET NEW XY 
		currentPieceX = newX;
		currentPieceY = newY;
		
		
		return true;
	}
	
	public void moveLeft() {
		int[][] temp = currentPiece.getShapeXY();

		for(int i = 0; i < 4; i++) {
			if((currentPieceX+temp[i][0])%tetrisBoardWidthSquare == 0) {
				return;
			}
		}
		
        canMove(currentPieceX-1, currentPieceY, currentPiece);
	}
	
	public void moveRight() {
		int[][] temp = currentPiece.getShapeXY();

		for(int i = 0; i < 4; i++) {
			if((currentPieceX+temp[i][0])%tetrisBoardWidthSquare == 9) {
				return;
			}
		}
		
        canMove(currentPieceX+1, currentPieceY, currentPiece);
	}
	
	public void pieceDropped() {
		
		int[][] temp = currentPiece.getShapeXY();
		
		// SET PIECE TO BOARD
		for(int i = 0; i < 4; i++) {
			tetrisBoard[(currentPieceY-temp[i][1])*tetrisBoardWidthSquare + currentPieceX+temp[i][0]].setIsFull(true);
			tetrisBoard[(currentPieceY-temp[i][1])*tetrisBoardWidthSquare + currentPieceX+temp[i][0]].setColor(currentPiece.getColor());
		}
				
		// RESET CURRENT PIECE DATA
		currentPiece.setNoShape();
		
		dropped = true;
		
		// REMOVE LINES
		removeLines();
		
	}
	
	public void removeLines() {
		
		for(int i = 0; i < tetrisBoardHeightSquare; i++) {
			boolean full = true;
			for(int j = 0; j < tetrisBoardWidthSquare; j++) {
				if(!tetrisBoard[i*tetrisBoardWidthSquare + j].isFull()) {
					full = false;
		//			break;
				}
			}
			if(full) {
				System.out.println(i);
			//	main.updateLabel();
				updateScore();
				
				// REPLACE ROWS BELOW WITH ROWS ABOVE
				for(int j = i; j > 0; j--) {
					for(int x = 0; x < tetrisBoardWidthSquare; x++) {
						tetrisBoard[j*tetrisBoardWidthSquare + x].setColor(tetrisBoard[(j-1)*tetrisBoardWidthSquare + x].getColor());
						tetrisBoard[j*tetrisBoardWidthSquare + x].setIsFull(tetrisBoard[(j-1)*tetrisBoardWidthSquare + x].isFull());
						tetrisBoard[(j-1)*tetrisBoardWidthSquare + x].reset();
					}
				}
				
				// SET ALL OF ROW 0 TO 0 (RESET)
				for(int x = 0; x < 10; x++) {
					tetrisBoard[0].reset();
				}
			}
		}
		
		repaint();
	}
	
	public void addRandomLine() {
		Random rn = new Random();
    	
    	int[] randArray = randNumArray();
    	
		// REPLACE ROWS BELOW WITH ROWS ABOVE
		for(int j = 0; j < tetrisBoardHeightSquare-1; j++) {
			for(int x = 0; x < tetrisBoardWidthSquare; x++) {
				tetrisBoard[j*tetrisBoardWidthSquare + x].setColor(tetrisBoard[(j+1)*tetrisBoardWidthSquare + x].getColor());
				tetrisBoard[j*tetrisBoardWidthSquare + x].setIsFull(tetrisBoard[(j+1)*tetrisBoardWidthSquare + x].isFull());
				tetrisBoard[(j+1)*tetrisBoardWidthSquare + x].reset();
			}
		}
		
		for(int i = tetrisBoardWidthSquare*tetrisBoardHeightSquare-10; i < tetrisBoardWidthSquare*tetrisBoardHeightSquare; i++) {
			if(randArray[0] == i-(tetrisBoardWidthSquare*tetrisBoardHeightSquare-10)) {
				tetrisBoard[i].reset();
			}
			else if(randArray[1] == i-(tetrisBoardWidthSquare*tetrisBoardHeightSquare-10)) {
				tetrisBoard[i].reset();
			} 
			else if(randArray[2] == i-(tetrisBoardWidthSquare*tetrisBoardHeightSquare-10)) {
				tetrisBoard[i].reset();
			}
			else if(randArray[3] == i-(tetrisBoardWidthSquare*tetrisBoardHeightSquare-10)) {
				tetrisBoard[i].reset();
			}
			else {
				tetrisBoard[i].setColor(Color.GRAY);
				tetrisBoard[i].setIsFull(true);
			}

		}
	}
	
	public int[] randNumArray() {
		Random rn = new Random();

		int[] randArray = new int[5];
		
		randArray[0] = rn.nextInt(10);
		randArray[1] = rn.nextInt(10);
		randArray[2] = rn.nextInt(10);
		randArray[3] = rn.nextInt(10);
		randArray[4] = rn.nextInt(10);
		
		
		return randArray;
	}
	
	public void nextPiece() {
		
		currentPiece.setRandomPiece();
		currentPieceX = 4;
		currentPieceY = 2;
		dropped = false;
		int attempts;
		
		if(currentPiece.isLine()) {
			attempts = 4;
		}
		else {
			attempts = 2;
		}
		
		for(int i = 0; i < attempts; i++) {
	        if (!canMove(currentPieceX, currentPieceY+1, currentPiece)) {
	        	System.out.println("GAME OVER");
	            gameOver = true;
	            break;
	        }
		}
		
	}        
	
	class keyboardListener extends KeyAdapter {
		
		public void keyPressed(KeyEvent ke) {

	    	// CHECK IF A PIECE EXISTS
			if (!currentPiece.hasShape()) {  
                return;
            }
			
	        int keycode = ke.getKeyCode();

	        switch (keycode) {
		        case KeyEvent.VK_LEFT:
		        	moveLeft();
		        	repaint();
		            break;
		        case KeyEvent.VK_RIGHT:
		        	moveRight();
		        	repaint();
		            break;
		        case KeyEvent.VK_DOWN:
		        	downOne();
		            repaint();
		        	break;
		        case KeyEvent.VK_UP:
		            currentPiece.rotate();
		            repaint();
		            break;
		        case KeyEvent.VK_SPACE:
		            while(true){
		            	if(!canMove(currentPieceX, currentPieceY+1, currentPiece)) {
		            		break;
		            	}
		            }
		            pieceDropped();
		            repaint();
		            break;

	        }
		}
	}
}

    

