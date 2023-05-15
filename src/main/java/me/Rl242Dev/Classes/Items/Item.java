package me.Rl242Dev.Classes.Items;

import me.Rl242Dev.Classes.Items.Ressource.Material;
import me.Rl242Dev.Classes.Items.Ressource.RessourceUtils;
import me.Rl242Dev.Classes.Items.Ressource.Type;

/*

@a = Rl242Dev
@u = Main
@e = Item Class for general items that will be loaded on start

 */

public class Item {

    /* Attributes */

    private String name;
    private Material material;
    private int generalSpeed;
    private final Type type;
    private final String id;
    private final String EmojiID;

    /* Methods */

    public String getEmojiID() {
        return EmojiID;
    }

    public String getDisplayName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public Material getMaterial() {
        return material;
    }

    public void setDisplayName(String name) {
        this.name = name;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public int getGeneralSpeed() {
        return generalSpeed;
    }

    public void setGeneralSpeed(int generalSpeed) {
        this.generalSpeed = generalSpeed;
    }

    /* Constructor */

    public Item(String name, Material material, Type type, String id) {
        this.name = name;
        this.material = material;
        this.type = type;
        this.EmojiID = RessourceUtils.getEmojiIDFromType(type);
        this.id = id;
    }

}