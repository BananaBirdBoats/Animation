
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;


public class Main implements KeyListener{
    public static void main(String []args){
        Main b = new Main();
        b.run();

    }

    private Sprite sprite;
    private Animation a;
    private Animation idle;
    private Animation jump;
    private Animation crouch;
    private Animation attack;
    private ScreenManager s;
    private Image bg;
    private boolean running;
    private Animation currentAnimation;
    
    private static final DisplayMode modes1[] = {
        new DisplayMode(800, 600, 32, 0),
        new DisplayMode(800, 600, 24, 0),
        new DisplayMode(800, 600, 16, 0),

        new DisplayMode(640, 480, 32, 0),
        new DisplayMode(640, 480, 24, 0),
        new DisplayMode(640, 480, 16, 0),
    };

    //load images and add scenes
    public void loadImages() {
        bg = new ImageIcon("C:\\Users\\Skippy\\Pictures\\sideScrollRun\\background\\bg.jpg").getImage();
        idle=new Animation();
        for(int i=0;i<=2;i++){
        	Image face= new ImageIcon("C:\\Users\\Skippy\\Pictures\\sideScrollRun\\Cyber Woo\\idleAnimation\\Cyber Woo_"+i+".png").getImage();
        	System.out.println("Loading Idle Animation "+i);
        	idle.addScene(face,80);
        }
        a = new Animation();
        for(int i=1;i<=12;i++){
        	Image face= new ImageIcon("C:\\Users\\Skippy\\Pictures\\sideScrollRun\\Cyber Woo\\runAnimation\\"+i+".png").getImage();
        	System.out.println("Displaying picture "+i);
        	a.addScene(face,80);
        }
        
       
        jump=new Animation();
        for(int i=0;i<=7;i++){
        	Image face= new ImageIcon("C:\\Users\\Skippy\\Pictures\\sideScrollRun\\Cyber Woo\\jumpAnimation\\"+i+".png").getImage();
        	System.out.println("Loading jump Animation "+i);
        	jump.addScene(face,80);
        }
        

       crouch=new Animation();
        for(int i=0;i<=4;i++){
        	Image face= new ImageIcon("C:\\Users\\Skippy\\Pictures\\sideScrollRun\\Cyber Woo\\crouchAnimation\\"+i+".png").getImage();
        	System.out.println("Loading crouch Animation "+i);
        	crouch.addScene(face,80);
        }
        

       attack=new Animation();
        for(int i=1;i<=13;i++){
        	Image face= new ImageIcon("C:\\Users\\Skippy\\Pictures\\sideScrollRun\\Cyber Woo\\attackAnimation\\"+i+".png").getImage();
        	System.out.println("Loading attack Animation "+i);
        	attack.addScene(face,80);
        }
        currentAnimation=idle;
        
 

        sprite = new Sprite(a,idle,jump,crouch,attack);
        sprite.setX(50);
        sprite.setY(50);


    }

    //main method called from main
    public void run() {
        s = new ScreenManager();
        try {
            init();           
            gameLoop(idle);
        }finally {
            s.restoreScreen();
        }
    }

    //this is run to initialize the display mode, screen and image loading.
    private void init() {
    	 DisplayMode dm = s.findFirstCompatibleMode(modes1);
         s.setFullScreen(dm);
         loadImages();
         
         Window w=s.getFullScreenWindow();
         w.addKeyListener(this);
         running=true;
		
	}

	//play movie
    public void gameLoop(Animation a) {
        long startingTime = System.currentTimeMillis();
        long cumTime = startingTime;
     

        while(running) {
            long timePassed = System.currentTimeMillis() - cumTime;
            cumTime += timePassed;
            update(currentAnimation,timePassed);

            //draw and update the screen
            Graphics2D g = s.getGraphics();
            draw(currentAnimation,g);
            g.dispose();
            s.update();

            try{
                Thread.sleep(20);
            }catch(Exception ex) {
                System.err.println("Error: " + ex);
            }
        }
    }

    //draws graphics
    public void draw(Animation a,Graphics g) {
        g.drawImage(bg, 0, 0, null);
        g.drawImage(sprite.getImage(a), Math.round(sprite.getX()), Math.round(sprite.getY()), null);
    }

    //update sprite
    public void update(Animation a,long timePassed) {
        if(sprite.getX() < 0) {
            sprite.setVelocityX(Math.abs(sprite.getVelocityX()));
        } else if (sprite.getX() + sprite.getWidth(a) >= s.getWidth()) {
            sprite.setVelocityX(-Math.abs(sprite.getVelocityX()));
        }

        if(sprite.getY() < 0) {
            sprite.setVelocityY(Math.abs(sprite.getVelocityY()));
        } else if (sprite.getY() + sprite.getHeight(a) >= s.getHeight()) {
            sprite.setVelocityY(-Math.abs(sprite.getVelocityY()));
        }

        sprite.update(a,timePassed);
    }
    

    //Constructor
    public Main() {
    }

//When the user presses one of the below buttons a new animation is loaded and played	
	public void keyPressed(KeyEvent e) {
		 int keyCode = e.getKeyCode();
	        if (keyCode == KeyEvent.VK_ESCAPE) {
	            stop();
	        } else if(keyCode==KeyEvent.VK_RIGHT) {
	        	this.setCurrentAnimation(a);
	        	sprite.setVelocityX(.3f);	           
	            e.consume();
	           
	        }else if(keyCode==KeyEvent.VK_LEFT){
	        	this.setCurrentAnimation(a);
	        	sprite.setVelocityX(-.3f);
	        	e.consume();
	        	
	        }else if(keyCode==KeyEvent.VK_UP){
	        	this.setCurrentAnimation(jump);
	        	sprite.setVelocityY(-.3f);
	        	e.consume();
	        }else if(keyCode==KeyEvent.VK_DOWN){
	        	this.setCurrentAnimation(crouch);
	        	sprite.setVelocityY(.3f);
	        	e.consume();
	        }else if(keyCode==KeyEvent.VK_SPACE){
	        	this.setCurrentAnimation(attack);
	        	sprite.setVelocityY(0);
	        	e.consume();
	        }
	}
	
	//stop the loop, used to exit the full screen mode
	public void stop(){
		running=false;
	}

//	Resets the animation set so that when you let go a default animation is played
	public void keyReleased(KeyEvent e) {
		int keyCode=e.getKeyCode();
		
		if(keyCode==KeyEvent.VK_RIGHT) {
        	this.setCurrentAnimation(idle);

			sprite.setVelocityX(0);	           
            e.consume();
            
        }else if(keyCode==KeyEvent.VK_LEFT){
        	this.setCurrentAnimation(idle);

        	sprite.setVelocityX(0);
        	e.consume();
        	
        }else if(keyCode==KeyEvent.VK_UP){
        	this.setCurrentAnimation(idle);
        	sprite.setVelocityY(0);
        	e.consume();
        }else if(keyCode==KeyEvent.VK_DOWN){
        	this.setCurrentAnimation(idle);
        	sprite.setVelocityY(0);
        	e.consume();
        }else if(keyCode==KeyEvent.VK_SPACE){
        	this.setCurrentAnimation(idle);
        	sprite.setVelocityY(0);
        	e.consume();
        }
		
	}
//tells the main loop what animation to play	
	public void setCurrentAnimation(Animation targetAnimation){
		this.currentAnimation=targetAnimation;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}////////END//////////