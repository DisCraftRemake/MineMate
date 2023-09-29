package me.Rl242Dev.Classes.Entity.Pets;

import me.Rl242Dev.Classes.Utils.Emoji;

public class PetUtils {
    public static String getNameFromPet(Pets pets){
        String displayName = switch (pets){
            case CAT -> Emoji.getCatEmoji()+" Cat";
            case GOAT -> Emoji.getGoatEmoji()+" Goat";
            case BEE -> Emoji.getBeeEmoji()+" Bee";
        };

        return displayName;
    }

    public static String getNameFromPet(Class<? extends PetIdentifier> pet){
        String displayName = null;

        if(pet.equals(Bee.class)){
            displayName = Emoji.getBeeEmoji()+" Bee";
        } else if (pet.equals(Cat.class)) {
            displayName = Emoji.getCatEmoji()+" Cat";
        } else if (pet.equals(Goat.class)) {
            displayName = Emoji.getGoatEmoji()+" Goat";
        }

        return displayName;
    }
}
