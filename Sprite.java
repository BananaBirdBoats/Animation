
import java.awt.Image;

public class Sprite {

    private Animation a;
    private Animation idle;
    private Animation jump;
    private Animation crouch;
    private Animation attack;
    private float x;
    private float y;
    private float vx;
    private float vy;

    //Constructor
    public Sprite(Animation a,Animation idle,Animation jump,Animation crouch, Animation attack) {
        this.a = a;
        this.idle=idle;
        this.jump=jump;
        this.crouch=crouch;
        this.attack=attack;
    }

    //Change position
    public void update(Animation z,long timePassed) {
        x += vx * timePassed;
        y += vy * timePassed;
        z.update(timePassed);
    }

    //get x position
    public float getX() {
        return x;
    }

    //get y position
    public float getY() {
        return y;
    }

    //set x position
    public void setX(float x) {
        this.x = x;
    }

    //set y position
    public void setY(float y) {
        this.y = y;
    }

    // get sprite width
    public int getWidth(Animation x) {
        return x.getImage().getWidth(null);
    }

    // get sprite height
    public int getHeight(Animation x) {
        return x.getImage().getHeight(null);
    }

    //get horizontal velocity
    public float getVelocityX() {
        return vx;
    }

    //get vertical velocity
    public float getVelocityY() {
        return vy;
    }

    //set horizontal velocity
    public void setVelocityX(float vx) {
        this.vx = vx;
    }

    //set vertical velocity
    public void setVelocityY(float vy) {
        this.vy = vy;
    }

    //get sprite/image
    public Image getImage(Animation x) {
        return x.getImage();
    }
}
