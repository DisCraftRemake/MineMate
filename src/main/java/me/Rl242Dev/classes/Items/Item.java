package me.Rl242Dev.classes.Items;

import me.Rl242Dev.classes.Items.Ressource.RessourceUtils;
import me.Rl242Dev.classes.Items.Ressource.Type;

/*

@a = Rl242Dev
@u = Main
@e = Item Class for general items that will be loaded on start

 */

public class Item {

    /* Attributes */

    private String name;
    private Type material;
    private Enchants enchants;
    private int breakingSpeed;
    private final int EmojiID;

    /* Methods */

    public int getEmojiID() {
        return EmojiID;
    }

    public String getDisplayName() {
        return name;
    }

    public Type getMaterial() {
        return material;
    }

    public Enchants getEnchants() {
        return enchants;
    }

    public void setDisplayName(String name) {
        this.name = name;
    }

    public void setMaterial(Type material) {
        this.material = material;
    }

    public void setEnchants(Enchants enchants) {
        this.enchants = enchants;
    }

    public int getBreakingSpeed() {
        return breakingSpeed;
    }

    public void setBreakingSpeed(int breakingSpeed) {
        this.breakingSpeed = breakingSpeed;
    }

    /* Constructor */

    public Item(String name, Type material, Enchants enchants, int EmojiID){
        this.name = name;
        this.material = material;
        this.enchants = enchants;
        this.breakingSpeed = RessourceUtils.getBreakingSpeedFromType(material) + (int) (RessourceUtils.getBreakingSpeedFromEnchants(enchants));
        this.EmojiID = EmojiID;
    }
}
