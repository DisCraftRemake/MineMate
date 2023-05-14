package me.Rl242Dev.classes.Items.Ressource.Ores;

/*

@A = Rl242Dev
@U = Items
@E = Class for Gold that will be used when user mines

 */

public class Gold {

    /* Attributes */

    public final static int price = 50;
    public final static float breakingSpeed = 2.0F;

    /* Methods */

    public static float getBreakingSpeed(){
        return breakingSpeed;
    }

    public static int getPrice(){
        return price;
    }
}
