import java.awt.Graphics;
import java.awt.Color;

public class Person
{
    //make sure to keep these 4 variables
    double x;
    double y;
    boolean infected;
    boolean dead;

    //add other class variables you might want (e.g. age, wearsMask, timeInfected, hasAntibodies)
    double homeX;
    double homeY;

    double workX;
    double workY;
   
    //other variables
    int TimeElapsed;
    int TimeInfected;
    int TimewithAntibodies;
    boolean wearsMask;
    boolean Antibodies;
    
    //constructor
    public Person(int xMax, int yMax)
    {
        //put the person at a random position on the screen
        x = Math.random() * xMax;
        y = Math.random() * yMax;
        
        //they have a 10% chance of being infected initially
        infected = Math.random() < 0.1;
        
        //40% chance of wearing a mask initially
        wearsMask = Math.random() < 0.4;
        
        //home location
        homeX = 200 + 100 * Math.random();
        homeY = 300 + 100 * Math.random();
        
        //work location
        workX = 400 + 100 * Math.random();
        workY = 100 + 100 * Math.random();
        
        //timer
        TimeElapsed = 0;
        TimeInfected = 0;
        TimewithAntibodies = 0;
        
        //no one is initially dead or has Antibodies
        dead = false;
        Antibodies = false;
    }
    
    //how does a person change over time
    //maybe they move
    //if they're infected, do they recover?
    //do they die??
    public void updatePerson()
    {
        if (this.infected)
        {
            TimeInfected++;
            if (TimeInfected > 250)
            {
                if (Math.random() < 0.06) // 6% chance of death
                {
                    dead = true;
                }
                else
                {
                    infected = false;
                    Antibodies = true;
                    TimeInfected = 0;
                }
            }
        }
        
        if (this.Antibodies)
        {
            TimewithAntibodies++;
            if (TimewithAntibodies > 100)
            {
                if (Math.random() < 0.6) // 60% chance of antibodies going away
                {
                    Antibodies = false;
                }
                TimewithAntibodies = 0;
            }
        }
        
        if (this.dead) {  //don't do anything if this person is dead
            return;
        }
        
        if (TimeElapsed % 700 < 180)//go home
        {
            if (x - homeX > 1)
            {
                x -= 2;
            }
            else if (x - homeX < -1)
            {
                x += 2;
            }
            if (y - homeY > 1)
            {
                y -= 2;
            }
            else if (y - homeY < -1)
            {
                y += 2;
            }
        }
        else if (TimeElapsed % 700 < 300)
        {
            double r = Math.random();
            if (r < 0.25)
            {
                x+=2;
            }
            else if (r < 0.5)
            {
                x-=2;
            }
            else if (r < 0.75)
            {
                y+=2;
            }
            else 
            {
                y-=2;
            }
        }
        else if (TimeElapsed % 700 < 450)//go to work
        {
            if (x - workX > 1)
            {
                x -= 2;
            }
            else if (x - workX < -1)
            {
                x += 2;
            }
            if (y - workY > 1)
            {
                y -= 2;
            }
            else if (y - workY < -1)
            {
                y += 2;
            }
        }
        else
        {
            double r = Math.random();
            if (r < 0.25)
            {
                x+=2;
            }
            else if (r < 0.5)
            {
                x-=2;
            }
            else if (r < 0.75)
            {
                y+=2;
            }
            else 
            {
                y-=2;
            }
        }
        TimeElapsed++;
    }
    
    //how does this person interact with p?
    //how should we calculate the chance that p infects them??
    public void interactWith(Person p)
    {
        //if have antibodies no chance of infection
        //0.01% to be infected if you and the infected are wearing mask
        if (!this.Antibodies && this.wearsMask && p.wearsMask && !p.dead && p.infected && Math.random() < 0.0001)
        {
            if ((p.x - this.x) * (p.x - this.x) + (p.y - this.y) * (p.y - this.y) < 10 * 10){
                this.infected = true;
            }
        }
        //0.05% to be infected if only you or them are wearing mask
        if (!this.Antibodies && this.wearsMask && !p.wearsMask && !p.dead && p.infected && Math.random() < 0.0005)
        {
            if ((p.x - this.x) * (p.x - this.x) + (p.y - this.y) * (p.y - this.y) < 10 * 10){
                this.infected = true;
            }
        }
        if (!this.Antibodies && !this.wearsMask && p.wearsMask && !p.dead && p.infected && Math.random() < 0.0005)
        {
            if ((p.x - this.x) * (p.x - this.x) + (p.y - this.y) * (p.y - this.y) < 10 * 10){
                this.infected = true;
            }
        }
        //if p is infected (and not dead) and they are within 10 units of us when you
        //both are not wearing a mask, then there is a 1% chance they will infect us
        if (!this.Antibodies && !this.wearsMask && !p.wearsMask && !p.dead && p.infected && Math.random() < 0.01){
            if ((p.x - this.x) * (p.x - this.x) + (p.y - this.y) * (p.y - this.y) < 10 * 10){
                this.infected = true;
            }
        }
    }
    
    //You shouldn't need to modify the methods down here
    //Although if you want, you can modify the draw method
    //to give more feedback using the color 
    public boolean isInfected(){
        return infected;
    }
    
    public boolean isDead()
    {
        return dead;
    }
    
    public void draw(Graphics g)
    {
        //don't draw people below the boundary
        if (y > 480)
        {
            return;
        }
        //set color based on the person's status
        if (dead)
        {
            g.setColor(Color.black);
        }
        else if (infected)
        {
            g.setColor(Color.green);
        }
        else if (Antibodies)
        {
            g.setColor(Color.blue);
        }
        else {
            g.setColor(Color.gray);
        }
        g.fillOval((int) x, (int) y, 10, 10);  //draw a radius 5 circle for the person
        if (wearsMask)
        {
            g.setColor(Color.orange);
            g.fillOval((int) x + 2, (int) y + 5, 6, 4);
        }
    }
}
