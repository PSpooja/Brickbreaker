package brickBreaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

// to make it panel extends it from Jpanel
// KeyListener for detecting the arrow keys i.e. moving the slider
// ActionListener for moving the ball
public class Gameplay extends JPanel implements KeyListener, ActionListener {
	
	//properties of game
	private boolean play = false;
	private int score = 0;
	private int totalBricks = 21;
	
	private Timer timer;        // how fast ball move
	private int delay = 8;
	
	private int playerX = 310;   // starting position of slider
	private int ballposX = 120;  // starting position of ballX
	private int ballposY = 350;  // starting position of ballY
	private int ballXdir = -1;   // setting direction of ballX
	private int ballYdir = -2;   //	setting direction of ballY
	private MapGenerator map;
	
	public Gameplay() {
		map = new MapGenerator(3,7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);   // creating object for Timer
		timer.start();
		
	}
	
	// Add new method paint which receives Graphics object
	public void paint(Graphics g) {
		// background
		g.setColor(Color.black);
		g.fillRect(1, 1, 692, 592);
		
		// draw map
		map.draw((Graphics2D)g);
		
		// scores
		g.setColor(Color.white);
		g.setFont(new Font("serif",Font.BOLD,25));
		g.drawString(""+score, 590, 30);
		
		// borders
		g.setColor(Color.yellow);
		// creating three rectangles for border
		g.fillRect(0, 0, 3, 593);   
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 593);
		
		// the paddle
		g.setColor(Color.green);
		g.fillRect(playerX, 550, 100, 8);
		
		// the ball
		g.setColor(Color.yellow);
		g.fillOval(ballposX, ballposY, 20, 20);
		
		//game over
		if(totalBricks <= 0) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.red);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("You WON: ", 260, 300);
			
		}
		
		
		if(ballposY > 570) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.red);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("Game over, Scores: " + score, 190, 300);
			
			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Press Enter to RESTART ", 230, 350);
		}
		
		g.dispose();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// abstract method from ActionListener which must be override here
		timer.start();
		if(play) {
			
			// intersection of ball and paddle
			if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
				ballYdir = -ballYdir;
				
			}
			
			A: for(int i = 0; i<map.map.length; i++) {
				for(int j = 0; j<map.map[0].length; j++) {
					if(map.map[i][j] > 0)   // map[][] position is greater than 0
					{
						int brickX = j* map.brickwidth + 80;
						int brickY = i* map.brickheight + 50;
						int brickwidth = map.brickwidth;
						int brickheight = map.brickheight;
						
						Rectangle rect = new Rectangle(brickX, brickY, brickwidth, brickheight);
						// create Rectangle around the brick
						Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
						Rectangle brickRect = rect;
						
						// detect intersection between ball and brick
						if(ballRect.intersects(brickRect)) {
							map.setBrickValue(0,i,j);
							totalBricks--;
							score += 5;
							
							if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
								ballXdir = -ballXdir;// opposite direction
							}else {
								ballYdir = -ballYdir;
							}
							break A;
						}
					}
				}
			}
			ballposX += ballXdir;
			ballposY += ballYdir;
			if(ballposX < 0) // left border 
				{
				ballXdir = -ballXdir;
			}
			if(ballposY < 0)  // top
				{
				ballYdir = -ballYdir;
			}
			if(ballposX > 670) // right border
				{
				ballXdir = -ballXdir;
			}
		}
		repaint();
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// abstract method from KeyListener which must be override here
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// abstract method from KeyListener which must be override here
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// abstract method from KeyListener which must be override here
		// detecting Arrow keys
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if(playerX >= 600) // player doesn't go outside the panel
			{
				playerX = 600;
			}else {
				moveRight();
			}
			
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			if(playerX <= 10) // player doesn't go outside the panel
			{
				playerX = 10;
			}else {
				moveLeft();
			}
			
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(!play) {
				play = true;
				ballposX = 120;
				ballposY = 350;
				ballXdir = -1;
				ballYdir = -2;
				playerX  = 310;
				score = 0;
				totalBricks = 21;
				map = new MapGenerator(3,7);
				
				repaint();
			}
		}
		
	}
	
	// creating moveLeft and moveRight method
	public void moveRight() {
		play = true;
		playerX += 20;
	}
	public void moveLeft() {
		play = true;
		playerX -= 20;
	}
}
