/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package follow;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.event.*;
import javax.swing.Timer;


public class Board extends JPanel implements ActionListener
{
	/**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    private final int DELAY = 10;
    private int w = 1024;
    private int h = 768;	
    private Timer timer;
    private int count = 0;
    public int score=0;
    public static final Color LIGHT_BLUE  = new Color(51,153,255);
    public static final Color GREEN  = new Color(0,204,0);
    public static boolean inGame;
    private MyMouseHandler handler;

    private Player player;

    int ballx = w/2; int bally = h/2; int dx = 5; int dy = 5;
	
       
    public Board() 
    {    	
        Menu menu = new Menu();
        initBoard();
    }
    
    private void initBoard() //Initializes all the game objects
    {	
        //setBackground(Color.DARK_GRAY);
        setFocusable(true);
        handler  = new MyMouseHandler();

        player = Player.getInstance(0,0);
        this.addMouseListener( handler );
        this.addMouseMotionListener( handler );
	
        setPreferredSize(new Dimension((int)w, (int)h));   //Set the size of Window     
        timer = new Timer(DELAY, this); //Timer with 10 ms delay 
        timer.start();
    }
    
    
    @Override
    public void paintComponent(Graphics g) //Draws all the components on screen
    {
    	g.setColor(getBackground());		//get the background color
        g.clearRect(0 , 0, (int)w, (int)h);	//clear the entire window
    	Dimension size = getSize();  //get the current window size  	
        w = (int)size.getWidth();
        h = (int)size.getHeight();

        
        //g.setColor(LIGHT_BLUE);
        //g.fillRect(0, 0, w, (h/2)+170);
        //g.setColor(GREEN);
        //g.fillRect(0, 0, w, h);

        g.setColor(LIGHT_BLUE);
        g.fillOval(ballx, bally, 40, 40);

        Graphics2D g2d = (Graphics2D) g;
        player.paintComponent(g2d);
        Toolkit.getDefaultToolkit().sync();
    }
    
    
    public void actionPerformed(ActionEvent e) {
        step();
        count++;
        //if(count%13==0)
        //score++;
    }
    public void step(){
     if(inGame==true){

        //player.setCanFollow(true); //should be true if you want player to follow cursor without any click
       // player.move();
        player.move2();
        ballx +=dx;
        bally +=dy;
        
        if(ballx>w-40 || ballx<0)
        dx = -dx;
        if(bally>h-40 || bally<0)
        dy = -dy;

        repaint();
     }
    }
    private class MyMouseHandler extends MouseAdapter
    {	
      public void mouseReleased(MouseEvent e)
      {
        player.mouseReleased(e);
      }
      public void mouseMoved(MouseEvent e)
      {
          player.mouseMoved(e);
      }
}
}