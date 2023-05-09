package me.Rl242Dev.classes.Items;

import me.Rl242Dev.Main;
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
    private Enchants enchant;
    private int generalSpeed;
    private final int id;
    private final int EmojiID;

    /* Methods */

    public int getEmojiID() {
        return EmojiID;
    }

    public String getDisplayName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public Type getMaterial() {
        return material;
    }

    public Enchants getEnchants() {
        return enchant;
    }

    public void setDisplayName(String name) {
        this.name = name;
    }

    public void setMaterial(Type material) {
        this.material = material;
    }

    public void setEnchants(Enchants enchants) {
        this.enchant = enchants;
    }

    public int getGeneralSpeed() {
        return generalSpeed;
    }

    public void setGeneralSpeed(int generalSpeed) {
        this.generalSpeed = generalSpeed;
    }

    /* Constructor */

    public Item(String name, Type material, Enchants enchant, int EmojiID, int id){
        this.name = name;
        this.material = material;
        this.enchant = enchant;
        this.generalSpeed = RessourceUtils.getBreakingSpeedFromType(material) + (int) (RessourceUtils.getBreakingSpeedFromEnchants(enchant));
        this.EmojiID = EmojiID;
        this.id = id;
    }

    /*

    En gros, l'utilisateur va faire sa commande : on va faire le choix random de tout ses ressources selon le type de son item

    Wood = Stone/Coal
    Stone = Stone/Coal/Iron
    Iron = Stone/Coal/Iron/Gold/Diamond
    Gold = Stone/Coal/Iron/Gold (et un truc en plus comme ça c'est rentable de l'avoir)
    Diamond = Stone/Coal/Iron/Gold (et un truc en plus comme ça c'est rentable de l'avoir)

    par exemple il a Wood Pickaxe : .Mine on va prendre 2 Stone et 1 Coal (donc stone = 1 BreakingSpeed et Coal = 3) donc (2*1 + 3*1) = 5 / Item.getGeneralSpeed()

     */
}
