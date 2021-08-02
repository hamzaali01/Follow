/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package follow;

import javax.swing.ImageIcon;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.event.*;
//import java.awt.MouseInfo;
//import java.awt.Point;

public class Player
{
    private static Player player;
    private int w,h;
    private double x,y,x2,y2,dx,dy;
    private double midx , midy;
    private boolean CanWalk;
    public static boolean CanFollow;
   // private boolean DrawArrow;
    private boolean X2greater;
    protected Image[] WalkImages;
    protected Image[] StandImages;
    protected Image Arrow;
    protected long Walkcount = 0;
    protected long Standcount = 0;
    protected int rand = 0;
    private double speed;
    private double angle;
    private double gradient;
    //protected Point p;
    int num=0;

	private Player(int x, int y)
	{
	this.x = x;
    this.y = y;	
    midx = x+75;
    midy = y+117;
    CanWalk=false;
    CanFollow=false;
    X2greater=true;
    //DrawArrow=false;
    speed=4;
    WalkImages = new Image[8];
    StandImages = new Image[8];
    for(int i=0; i<8; i++) // Iterating between 3 images for each aircraft
		{
		ImageIcon imageIcon = new ImageIcon("E:/GamesAndStuff/Jump/knightwalk/right/knightwalk" + i + ".png" );
		WalkImages[i] = imageIcon.getImage();
		}				
    for(int i=0; i<8; i++) // Iterating between 3 images for each aircraft
		{
		ImageIcon imageIcon = new ImageIcon("E:/GamesAndStuff/Jump/knightstand/knightidle" + i + ".png" );
		StandImages[i] = imageIcon.getImage();
		}		
        
       // ImageIcon imageIcon = new ImageIcon("E://arrow.png" );
	   //Arrow = imageIcon.getImage();

		w = WalkImages[5].getWidth(null);
        h = WalkImages[5].getHeight(null);   
	}
	
        
        public static Player getInstance( int x, int y){
            if(player==null)
                return new Player(x,y);
            else
                return player;
        }
     
  /*  public void follow() 
    {  
           // p = MouseInfo.getPointerInfo().getLocation();  //this one uses whole laptop screen for x and y mouse coordinates
            //x2=p.x;
            //y2=p.y;
    }  */
    
    //uses gradient
    public void move(){
    if(CanWalk==true){
        if(x2>midx){
            midy+=gradient*speed;
            midx+=1*speed;
            x=midx-75;
            y=midy-117;
            if(getCenterRect().contains(x2, y2)) //for stopping the player when its midx and midy are near x2 and y2
            CanWalk=false;
        }
        if(x2<midx){
            midy+=(-gradient*speed);
            midx+=(-1*speed);
            x=midx+75;
            y=midy-117;
            if(getCenterRect().contains(x2, y2))
            CanWalk=false;
        }
    }
    }
    //uses angle
    public void move2(){
        if(CanWalk==true){
        midx += Math.cos(angle * Math.PI/180) * speed;
        midy += Math.sin(angle * Math.PI/180) * speed;

        if(X2greater==true){
        x=midx-75;
        y=midy-117;
        }
        else{
        x=midx+75;
        y=midy-117;
        }
        }
        if(X2greater==true && midx>=x2)
        CanWalk=false;
        if(X2greater==false && x2>midx)
        CanWalk=false;
    }
	
    public void mouseMoved(MouseEvent e){
        if(CanFollow==true){
        x2=e.getX();
        y2=e.getY();
        CanWalk=true;
        if(x2>midx)
        X2greater=true;
       if(x2<midx)
        X2greater=false;
       if(x2==midx)
        CanWalk=false;
       
       updateX();

       if(X2greater==true){
       midx = x+75;
       midy = y+117;
       }
       else{
       midx = x-75;
       midy = y+117;
       }
       dx = x2-midx;
       dy = y2-midy;
       angle = (float)Math.atan2(dy, dx)*180 /Math.PI;
       gradient = calculateGradient();
        }
    }
    
    public void mouseReleased(MouseEvent e)
    {
        if(e.getButton()==3 && CanFollow==false)
        {
        CanWalk=true;
        x2 = e.getX();
        y2 = e.getY();
        if(x2>midx)
         X2greater=true;
        if(x2<midx)
         X2greater=false;
        if(x2==midx) //basically eliminating the possibility of gradient becoming infinity (dx will never be zero)
         CanWalk=false; //the chances of x2==midx are vry vry low since they are both in double
        
        updateX();

        if(X2greater==true){
        midx = x+75;
        midy = y+117;
        }
        else{
        midx = x-75;
        midy = y+117;
        }
        dx = x2-midx;
        dy = y2-midy;
        angle = (float)Math.atan2(dy, dx)*180 /Math.PI;
        gradient = calculateGradient();

        //System.out.println(x2 + " " + y2);
        //DrawArrow=true;
        }

        if(e.getButton()==1){
            //System.out.println(x + " " + y);
        }
        if(e.getButton()==2){
           //System.out.println(midx + " " + midy); 
        }
    }
   
    public double calculateGradient(){
    dy = y2-midy;
    dx = x2-midx;
    if(dx==0 && dy>0)    //incase of vertical motion and gradient tending to infinity
    return 5;            //this part(line 193 to line 196) can be removed since I have eliminated the possibility of dx being 0 in the mouse released function
    if(dx==0 && dy<0)
    return -5;

    if(dy/dx>3 || dy/dx<-3)     //adjusting speed depending on the value of gradient
    speed = 0.7;
    if(dy/dx>50 || dy/dx<-50)
    speed = 0.1;
    if(dy/dx>100 || dy/dx<-100)
    speed = 0.05;
    if(dy/dx<3 && dy/dx>-3)
    speed = 4;

    return dy/dx;
    }
    

    // to make sure the image does not move when it flips
    public void updateX(){
        if(X2greater==true)
        x=midx-75;
        else 
        x=midx+75;
    }

    public void paintComponent(Graphics2D g) 
	{
    if(CanWalk==true){
        if(rand%3==0){
        num = (int)(Walkcount%8);
        Walkcount++;
        }
        rand++;
        //g.drawImage(WalkImages[num], x - w/2, y - h/2, null); old
        if(X2greater==true)
        g.drawImage(WalkImages[num], (int)(x), (int)y, w, h, null); 
        else
        g.drawImage(WalkImages[num],(int)(x),(int)y, -w, h, null);
    }
    if(CanWalk==false ){
        if(rand%3==0){
            num = (int)(Standcount%8);
            Standcount++;
            }
            rand++;
            if(X2greater==true)
            g.drawImage(StandImages[num], (int)(x), (int)y, w, h, null);  // new
            else{  
            g.drawImage(StandImages[num], (int)(x),(int)y, -w, h, null);
            }

       // g.setColor(Color.red);
       // g.drawRect((int)midx-10,(int)midy-10,20,20);
    }

    //if(DrawArrow==true){
      //g.drawImage(Arrow,(int)x2,(int)y2,Arrow.getWidth(null),Arrow.getHeight(null),null);  
    //}
	}

    public double getX(){
    return x;
    }
    public double getY(){
        return y;
    }

     //for stopping the player when its midx and midy are near x2 and y2
      public Rectangle getCenterRect() {
           Rectangle r = new Rectangle((int)midx-10,(int)midy-10,20,20);
           return r;
	} 
    public void setCanFollow(boolean val){
        CanFollow=val;
    }
}
