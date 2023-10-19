package me.Rl242Dev.Classes.Entity.Pets;

import me.Rl242Dev.Classes.Utils.Emoji;

/*

@U = Pets
@E = Utils for pets
@A = Rl242Dev

 */

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

    public static String getEmojiFromPet(Class<? extends PetIdentifier> pet){
        String displayName = null;

        if(pet.equals(Bee.class)){
            displayName = Emoji.getBeeEmoji();
        } else if (pet.equals(Cat.class)) {
            displayName = Emoji.getCatEmoji();
        } else if (pet.equals(Goat.class)) {
            displayName = Emoji.getGoatEmoji();
        }

        return displayName;
    }

    public static String getOnlyNameFromPet(Class<? extends PetIdentifier> pet){
        String displayName = null;

        if(pet.equals(Bee.class)){
            displayName = "Bee";
        } else if (pet.equals(Cat.class)) {
            displayName = "Cat";
        } else if (pet.equals(Goat.class)) {
            displayName = "Goat";
        }

        return displayName;
    }

    public static Pets getPetFromString(String arg){
        Pets pet = switch (arg.toLowerCase()){
            case "goat" -> Pets.GOAT;
            case "cat" -> Pets.CAT;
            case "bee" -> Pets.BEE;
            default -> null;
        };

        return pet;
    }
}
