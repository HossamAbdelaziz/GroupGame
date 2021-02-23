/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hossam
 */
import java.util.Random;

public class Unocard
{
    public String color;
    public int value;
    private Random rand;
    private String face;

    public Unocard(int v, String c)
    {
        value = v;
        color = c; 
    }
    
   
    public Unocard()
    {
        rand = new Random();
        value = rand.nextInt(28); 
        
        if (value >= 14) 
            value -= 14;
        
        rand = new Random();
        switch(rand.nextInt(4) )
        {
            case 0 -> color = "Red";
            case 1 -> color = "Green";
            case 2 -> color = "Blue";
            case 3 -> color = "Yellow";
        }
        
        if (value >= 13)
            color = "none";
    }

    public String getFace()
    {
       
        
        face = "[";
        if (!"none".equals(color))
        {
            face += this.color + " ";
        }

        switch(this.value)
        {
            default -> face += String.valueOf(this.value);
            case 10 -> face += "Skip";
            case 11 -> face += "Reverse";
            case 12 -> face += "Draw 2";
            case 13 -> face += "Wild";
            case 14 -> face += "Wild Draw 4";
        }
        face += "]";
        return face;
    }

    public boolean canPlace(Unocard o, String c)
    {
        if (this.color == null ? c == null : this.color.equals(c))
            return true;
        else if (this.value == o.value)
            return true;
        else if ("none".equals(this.color)) {
            return true;
        } else {
        }
        return false;
    }
}
